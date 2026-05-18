package com.tt1.simserver.logic;

import com.tt1.simserver.database.DBManagerInterface;
import com.tt1.simserver.logic.creatures.LogicCreatureFactory;
import com.tt1.simserver.model.*;

import java.util.*;

/**
 * Implementa la funcionalidad de un gestor de solicitudes de simulaciones.
 */
public class SimulationRequestManager implements SimulationRequestManagerInterface {
    private final Random random;
    private final DBManagerInterface dbManager;

    /**
     * Construye un gestor de solicitudes de simulaciones con el generador de números pseudo-aleatorios {@code random }
     * y asociado al gestor de base de datos {@code dbManager}. El generador de números pseudo-aleatorios será el que
     * genere los tokens de las simulaciones y el gestor de base de datos se encargará de la persistencia de las
     * simulaciones. Asume que ambos son no nulos.
     *
     * @param random    el generador de números pseudo-aleatorios.
     * @param dbManager el gestor de base de datos.
     */
    public SimulationRequestManager(Random random, DBManagerInterface dbManager) {
        this.random = random;
        this.dbManager = dbManager;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Notas sobre implementación: genera un token único de manera pseudo-aleatoria que identifica a la
     * simulación y la distingue del resto de simulaciones de la base de datos asociada; las criaturas son creadas como
     * criaturas lógicas según su nombre y la definición del archivo de propiedades de la aplicación; las posiciones
     * iniciales de las criaturas en la simulación son decididas de forma pseudo-aleatoria con el generador de
     * pseudo-aleatoriedad de la solicitud; y las criaturas y el tablero de simulación son inyectados con el generador
     * de números pseudo-aleatorios de la solicitud para poder generar comportamiento pseudo-aleatorio. En cuanto a la
     * concurrencia: la función es {@code synchronized} para evitar problemas que puedan surgir por estar creando dos
     * simulaciones a la vez.</p>
     */
    @Override
    public synchronized LogicSimulation createSimulation(SimulationRequest simulationRequest) {
        int token = generateUniqueToken(simulationRequest);
        SimulationStep initialSimulationStep = createInitialSimulationStep(simulationRequest);
        int gridSize = initialSimulationStep.getGridSize();
        int foodPerTick = SimulationGrid.calculateFoodPerTick(gridSize, simulationRequest.getFoodProbability());
        // Creamos el tablero en el que se corre la simulación y asociamos sus criaturas
        SimulationGridInterface simulationGrid = new SimulationGrid(initialSimulationStep, foodPerTick,
                simulationRequest.getRandom());
        LogicSimulation simulation = new LogicSimulation(token, simulationRequest.getUser(),
                simulationRequest.getTicksToRun(), simulationGrid);

        dbManager.saveSimulation(simulation);

        return simulation;
    }

    /**
     * Genera un token único para una solicitud de simulación. El token es generado de manera pseudo-aleatoria y no
     * existe otra simulación en la base de datos con el mismo token. Asume que la solicitud de simulación es no nula.
     *
     * @param simulationRequest la solicitud de simulación.
     * @return el token generado.
     */
    private int generateUniqueToken(SimulationRequest simulationRequest) {
        int token;
        boolean isUnique = false;
        Simulation simulation;

        do {
            token = Math.abs(random.nextInt());
            simulation = new Simulation(token, simulationRequest.getUser(), 0);
            if (dbManager.getSimulation(simulation) == null) {
                isUnique = true;
            }
        } while (!isUnique);

        return token;
    }

    /**
     * Crea un paso inicial de simulación que contiene las criaturas en las cantidades especificadas en la solicitud de
     * simulación, siendo criaturas lógicas según su nombre y la definición del archivo de propiedades de la aplicación,
     * estando en posiciones generadas de manera pseudo-aleatoria. Asume que la solicitud de simulación es no nula.
     *
     * @param simulationRequest la solicitud de simulación.
     * @return el paso inicial de simulación creado.
     */
    private SimulationStep createInitialSimulationStep(SimulationRequest simulationRequest) {
        // 1. Calcular el tamaño del tablero
        int gridSize = SimulationGrid.calculateSize(simulationRequest.getTotalNumberOfCreatures(),
                simulationRequest.getInitialOccupancy());

        // 2. Generar una lista ordenada aleatoriamente con todas las posiciones disponibles en el tablero
        List<Position> availablePositions = new ArrayList<>();
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                availablePositions.add(new Position(x, y));
            }
        }
        Collections.shuffle(availablePositions, simulationRequest.getRandom());

        // 3. Crear las criaturas solicitadas asignándoles posiciones aleatorias (siguiendo la lista generada en el
        // paso anterior)
        Map<Position, Creature> creatures = new HashMap<>();
        int positionIndex = 0;
        for (String creatureName : simulationRequest.getCreaturesNames()) {
            for (int i = 0; i < simulationRequest.getCreatureQuantity(creatureName); i++) {
                creatures.put(availablePositions.get(positionIndex),
                        LogicCreatureFactory.createCreature(CreatureFactory.createCreature(creatureName,
                                UUID.randomUUID().toString()), availablePositions.get(positionIndex), random));
                positionIndex++;
            }
        }

        // 4. Devolver el estado inicial creado decidiendo las posiciones de las criaturas aleatoriamente
        return new SimulationStep(gridSize, creatures);
    }
}
