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
     * Precondición: random no es nulo.
     *
     * @param random la instancia de generador aleatorio.
     */
    public SimulationService(Random random) {
        users = new ArrayList<>();
        this.random = random;
    }

    /**
     * Constructor por defecto del servicio. Lo inicializa con el Random por defecto.
     */
    public SimulationService() {
        this(new Random());
    }


    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public SimulationStatus getSimulationStatus(User user, int token) {
        User storedUser = getUser(user);

        return storedUser.getSimulationStatus(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimulationResult getSimulationResult(User user, int token) {
        User storedUser = getUser(user);

        return storedUser.getSimulationResult(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Integer> getUserTokens(User user) {
        User storedUser = getUser(user);
        return storedUser.getTokens();
    }

    /**
     * {@inheritDoc}
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
     * Calcula la suma de criaturas iniciales que van a poblar el tablero.
     * Precondición: las cantidades iniciales de las criaturas son no nulas y mayores que cero.
     *
     * @param initialCreatureQuantities lista de cantidades a ser sumada.
     * @return el total de entidades.
     */
    private int calculateInitialNumberOfCreatures(List<Integer> initialCreatureQuantities) {
        int numberOfCreatures = 0;

        for (Integer quantity : initialCreatureQuantities) {
            numberOfCreatures += quantity;
        }

        return numberOfCreatures;
    }

    /**
     * Pobla un nuevo tablero distribuyendo las entidades indicadas en posiciones aleatorias y libres.
     * Precondición: grid es no nula, grid está vacía, los nombres de las criaturas son no nulas y mayores que cero,
     * las cantidades iniciales de las criaturas son no nulas y mayores que cero.
     *
     * @param grid                      el tablero que debe ser rellenado.
     * @param creatureNames             la lista de nombres/identificadores de especies de criatura.
     * @param initialCreatureQuantities las repeticiones indicadas para cada respectiva criatura.
     * @throws IllegalArgumentException si un tipo de criatura solicitado no existe o no se soporta.
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
