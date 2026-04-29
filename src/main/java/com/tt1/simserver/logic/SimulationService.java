package com.tt1.simserver.logic;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.model.creatures.MobileCreature;
import com.tt1.simserver.model.creatures.StaticCreature;
import com.tt1.simserver.model.creatures.StaticRabbit;
import com.tt1.simserver.model.jsonrepresentations.Request;

import java.util.*;

/**
 * Servicio centralizado del negocio que coordina los usuarios, procesa peticiones web,
 * configura los tableros iniciales y orquesta la creación y consulta de las simulaciones.
 */
public class SimulationService implements SimulationServiceInterface {
    private static final double INITIAL_OCCUPANCY = 0.35;
    private static final int MAX_SECONDS = 100;

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

    private final Collection<User> users;
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
        users = new ArrayList<>();
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
        User storedUser = null;
        boolean userLoaded;

        userLoaded = false;

        for (User existingUser : users) {
            if (user.equals(existingUser)) {
                storedUser = existingUser;
                userLoaded = true;
            }
        }

        // En un futuro se llamará a la capa de persistencia para ver si se tiene almacenado un usuario con la misma
        // clave (username) y si existe se cargará en la lista.

        if (!userLoaded) {
            if (storedUser == null) {
                storedUser = new User(user.getUsername());
                // También se guardará el usuario en la capa de persistencia.
                // NOTA: cuando implementemos esto cuidado con la concurrencia.
            }

            users.add(storedUser);
        }

        return storedUser;
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
        boolean simulationLoaded = false;
        boolean simulationExists = false;
        User storedUser = getUser(user);

        simulationLoaded = storedUser.existsSimulation(token);

        if (!simulationLoaded) {
            // En un futuro se llamará a la capa de persistencia para ver si se tiene almacenado una simulación con la
            // misma clave (token, ó username y token) y si existe se cargará en el usuario de la lista.
        } else {
            simulationExists = true;
        }

        return simulationExists;
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
        int numberOfCreatures;
        GridInterface grid;
        SimulationEngineInterface simulationEngine;
        SimulationManagerInterface simulationManager;
        User storedUser;
        int token;

        numberOfCreatures = calculateInitialNumberOfCreatures(initialCreatureQuantities);

        grid = new Grid(numberOfCreatures, INITIAL_OCCUPANCY);
        loadGrid(grid, creatureNames, initialCreatureQuantities);
        simulationEngine = new SimulationEngine(grid, MAX_SECONDS);
        simulationManager = new SimulationManager(simulationEngine);

        storedUser = getUser(user);
        token = simulationManager.startSimulation();
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
            creaturePosition = emptyPositions.get(random.nextInt(emptyPositions.size()));

            if (creatureType.equals("static")) {
                for (int j = 0; j < creatureQuantity; j++) {
                    grid.addCreature(new StaticCreature(creatureName, creatureColor, creaturePosition));
                }
            } else if (creatureType.equals("mobile")) {
                creatureMoveProbability = creatureNameToMoveProbability.get(creatureName);

                for (int j = 0; j < creatureQuantity; j++) {
                    grid.addCreature(new MobileCreature(creatureName, creatureColor, creatureMoveProbability, creaturePosition));
                }
            } else if (creatureType.equals("rabbit")) {
                creatureMultiplyProbability = creatureNameToMultiplyProbability.get(creatureName);

                for (int j = 0; j < creatureQuantity; j++) {
                    grid.addCreature(new StaticRabbit(creatureName, creatureColor, creatureMultiplyProbability, creaturePosition));
                }
            } else {
                throw new IllegalArgumentException("Invalid creature type");
            }

            emptyPositions.remove(creaturePosition);
        }
    }
}