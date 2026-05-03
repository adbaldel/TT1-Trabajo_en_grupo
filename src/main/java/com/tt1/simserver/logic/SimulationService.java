package com.tt1.simserver.logic;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.model.creatures.MobileCreature;
import com.tt1.simserver.model.creatures.StaticCreature;
import com.tt1.simserver.model.creatures.StaticRabbit;
import com.tt1.simserver.presentation.jsonobjects.Request;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Servicio centralizado del negocio que coordina los usuarios, procesa peticiones web,
 * configura los tableros iniciales y orquesta la creación y consulta de las simulaciones.
 */
public class SimulationService implements SimulationServiceInterface {
    private static final double INITIAL_OCCUPANCY = 0.35;
    private static final int MAX_SECONDS = 100;

    private static final SimulationService instance = new SimulationService();

    // Diccionarios con los metadatos de entidades registrados en el sistema
    private static final Map<String, String> creatureNameToType;
    private static final Map<String, String> creatureNameToColor;
    private static final Map<String, Double> creatureNameToMoveProbability;
    private static final Map<String, Double> creatureNameToMultiplyProbability;

    static {
        creatureNameToType = new HashMap<>();
        creatureNameToType.put("perezoso", "static");
        creatureNameToType.put("gato", "mobile");
        creatureNameToType.put("conejo", "rabbit");

        creatureNameToColor = new HashMap<>();
        creatureNameToColor.put("perezoso", "green");
        creatureNameToColor.put("gato", "red");
        creatureNameToColor.put("conejo", "blue");

        creatureNameToMoveProbability = new HashMap<>();
        creatureNameToMoveProbability.put("gato", 0.5);

        creatureNameToMultiplyProbability = new HashMap<>();
        creatureNameToMultiplyProbability.put("conejo", 0.2);
    }

    // Usamos ConcurrentMap para búsquedas O(1) e inserciones Thread-safe
    private final ConcurrentMap<String, User> users;
    private final Random random;

    /**
     * Constructor para instanciar el servicio inyectando una semilla de números aleatorios custom.
     *
     * <p>Precondición: {@code random} no es nulo.
     *
     * <p>Postcondición: Crea el servicio con una colección de usuarios vacía y el generador de números aleatorios inyectado.
     *
     * @param random la instancia de generador aleatorio.
     */
    public SimulationService(Random random) {
        users = new ConcurrentHashMap<>();
        this.random = random;
    }

    /**
     * Constructor por defecto del servicio.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Crea el servicio inicializándolo con un generador de números aleatorios por defecto y una colección de usuarios vacía.
     */
    public SimulationService() {
        this(new Random());
    }

    /**
     * Devuelve la instancia Singleton de {@code SimulationService}
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el servicio singleton.
     *
     * @return el servicio de la aplicación.
     */
    public static SimulationService getInstance() {
        return instance;
    }

    /**
     * Recupera un usuario en memoria o lo crea si no existe.
     *
     * <p>Precondición: {@code user} no es nulo y tiene un nombre asignado.
     *
     * <p>Postcondición: Funciona como caché. Si el usuario ya está registrado, devuelve la misma referencia en memoria. Si no existe, lo instancia, lo guarda en la colección y lo devuelve conservando su nombre intacto.
     *
     * @param user el objeto usuario usado para buscar o registrar.
     * @return la instancia persistente del usuario en el sistema.
     */
    @Override
    public User getUser(User user) {
        // computeIfAbsent es atómico: Si el usuario existe, lo devuelve.
        // Si no existe, lo crea, lo guarda de forma segura en el mapa y lo devuelve.
        return users.computeIfAbsent(user.getUsername(), new Function<String, User>() {
            @Override
            public User apply(String usernameKey) {
                // Aquí en un futuro puedes añadir la lógica para cargarlo de la Base de Datos
                return new User(usernameKey);
            }
        });
    }

    /**
     * Comprueba si una simulación pertenece a un usuario concreto.
     *
     * <p>Precondición: {@code user} no es nulo.
     *
     * <p>Postcondición: Devuelve verdadero si el sistema certifica que el token consta en el registro de peticiones del usuario. Devuelve falso si el usuario no es propietario de dicho token.
     *
     * @param user  el usuario que hace la solicitud.
     * @param token el identificador de la simulación a verificar.
     * @return verdadero si el token pertenece al usuario, falso en caso contrario.
     */
    @Override
    public boolean existsSimulation(User user, int token) {
        User storedUser = getUser(user);
        return storedUser.existsSimulation(token);
    }

    /**
     * Consulta el estado de una simulación vinculada a un usuario.
     *
     * <p>Precondición: {@code user} no es nulo y es el propietario real del token indicado.
     *
     * <p>Postcondición: Delega la consulta y retorna el estado exacto extraído directamente desde el gestor interno de esa simulación.
     *
     * @param user  el usuario propietario de la simulación.
     * @param token el identificador de la simulación.
     * @return el estado de ejecución actual de la simulación solicitada.
     */
    @Override
    public SimulationStatus getSimulationStatus(User user, int token) {
        User storedUser = getUser(user);
        return storedUser.getSimulationStatus(token);
    }

    /**
     * Recupera el resultado histórico de una simulación específica de un usuario.
     *
     * <p>Precondición: {@code user} no es nulo y es el propietario real del token indicado.
     *
     * <p>Postcondición: Extrae y devuelve el historial completo del tablero expuesto por el gestor subyacente de la simulación.
     *
     * @param user  el usuario propietario de la simulación.
     * @param token el identificador de la simulación.
     * @return el objeto con el historial de resultados devuelto por el gestor.
     */
    @Override
    public SimulationResult getSimulationResult(User user, int token) {
        User storedUser = getUser(user);
        return storedUser.getSimulationResult(token);
    }

    /**
     * Lista todos los identificadores de simulación registrados a nombre de un usuario.
     *
     * <p>Precondición: {@code user} no es nulo.
     *
     * <p>Postcondición: Devuelve una colección que agrupa todos los tokens solicitados históricamente por el usuario. La colección nunca es nula, aunque el usuario no tenga simulaciones.
     *
     * @param user el usuario a consultar.
     * @return una colección con los tokens pertenecientes al usuario.
     */
    @Override
    public Collection<Integer> getUserTokens(User user) {
        User storedUser = getUser(user);
        return storedUser.getTokens();
    }

    /**
     * Orquesta la creación, configuración y arranque de una nueva simulación para un usuario.
     *
     * <p>Precondición: {@code user} y {@code request} no son nulos. Las cantidades iniciales en la solicitud son enteros no negativos.
     *
     * <p>Postcondición: Construye un tablero de tamaño dinámico, lo puebla aleatoriamente con las criaturas solicitadas, arranca asíncronamente los cálculos de turnos y amarra la petición a la cuenta del usuario. Devuelve un token válido superior o igual a cero.
     *
     * @param user    el usuario que solicita crear la simulación.
     * @param request el objeto con la especificación de criaturas a incluir.
     * @return el nuevo token numérico asignado a la simulación.
     */
    @Override
    public int requestSimulation(User user, Request request) {
        List<String> creatureNames = request.getCreatureNames();
        List<Integer> initialCreatureQuantities = request.getInitialCreatureQuantities();

        int numberOfCreatures = calculateInitialNumberOfCreatures(initialCreatureQuantities);

        GridInterface grid = new Grid(numberOfCreatures, INITIAL_OCCUPANCY);
        loadGrid(grid, creatureNames, initialCreatureQuantities);

        SimulationEngineInterface simulationEngine = new SimulationEngine(grid, MAX_SECONDS);
        SimulationManagerInterface simulationManager = new SimulationManager(simulationEngine);

        User storedUser = getUser(user);
        int token = simulationManager.startSimulation();
        storedUser.addRequest(simulationManager);

        return token;
    }

    /**
     * Calcula la suma total de criaturas que poblarán el tablero.
     *
     * <p>Precondición: {@code initialCreatureQuantities} no es nula. Todas las cantidades contenidas son números enteros.
     *
     * <p>Postcondición: Devuelve la suma entera de todas las cantidades proporcionadas.
     *
     * @param initialCreatureQuantities lista de cantidades a sumar.
     * @return el número total de criaturas.
     */
    private int calculateInitialNumberOfCreatures(List<Integer> initialCreatureQuantities) {
        int numberOfCreatures = 0;

        for (Integer quantity : initialCreatureQuantities) {
            numberOfCreatures += quantity;
        }

        return numberOfCreatures;
    }

    /**
     * Distribuye las criaturas solicitadas aleatoriamente en casillas libres del tablero.
     *
     * <p>Precondición: {@code grid}, {@code creatureNames} y {@code initialCreatureQuantities} no son nulos. El tablero cuenta con suficientes casillas libres. Los tipos de criaturas indicados coinciden con los registrados internamente en el sistema.
     *
     * <p>Postcondición: Instancia y ubica aleatoriamente cada criatura en el tablero dentro de casillas libres, restando su disponibilidad de las opciones globales.
     *
     * @param grid                      el tablero físico que se va a poblar.
     * @param creatureNames             los nombres identificadores de las especies de criaturas.
     * @param initialCreatureQuantities las cantidades exactas requeridas para cada especie de criatura.
     * @throws IllegalArgumentException si el tipo derivado del nombre de criatura solicitado no está registrado como válido.
     */
    private void loadGrid(GridInterface grid, List<String> creatureNames, List<Integer> initialCreatureQuantities) {
        String creatureName;
        String creatureType;
        String creatureColor;
        double creatureMoveProbability;
        double creatureMultiplyProbability;
        Position creaturePosition;
        int creatureQuantity;
        List<Position> emptyPositions;

        emptyPositions = new ArrayList<>();
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                emptyPositions.add(new Position(x, y));
            }
        }

        for (int i = 0; i < creatureNames.size(); i++) {
            creatureName = creatureNames.get(i).toLowerCase();
            creatureQuantity = initialCreatureQuantities.get(i);
            creatureType = creatureNameToType.get(creatureName);
            creatureColor = creatureNameToColor.get(creatureName);

            for (int j = 0; j < creatureQuantity; j++) {
                creaturePosition = emptyPositions.remove(random.nextInt(emptyPositions.size()));

                if (creatureType.equals("static")) {
                    grid.addCreature(new StaticCreature(creatureName, creatureColor, creaturePosition));
                } else if (creatureType.equals("mobile")) {
                    creatureMoveProbability = creatureNameToMoveProbability.get(creatureName);
                    grid.addCreature(new MobileCreature(creatureName, creatureColor, creatureMoveProbability, creaturePosition));
                } else if (creatureType.equals("rabbit")) {
                    creatureMultiplyProbability = creatureNameToMultiplyProbability.get(creatureName);
                    grid.addCreature(new StaticRabbit(creatureName, creatureColor, creatureMultiplyProbability, creaturePosition));
                } else {
                    throw new IllegalArgumentException("Invalid creature type");
                }
            }
        }
    }
}