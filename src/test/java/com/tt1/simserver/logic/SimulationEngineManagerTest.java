package com.tt1.simserver.logic;

import com.tt1.simserver.logic.creatures.LogicCreature;
import com.tt1.simserver.logic.creatures.MobileCreature;
import com.tt1.simserver.logic.creatures.StaticRabbit;
import com.tt1.simserver.mocks.database.DBManagerFake;
import com.tt1.simserver.mocks.database.DBManagerMock;
import com.tt1.simserver.mocks.logic.LogicCreatureFake;
import com.tt1.simserver.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineManagerTest {
    Map<String, Integer> creaturesNamesQuantities;
    Map<Position, Creature> stepMap;
    private User user;
    private int totalNumberOfCreatures;
    private int ticksToRun;
    private double initialOccupancy;
    private double foodProbability;
    private Random random;
    private SimulationRequest simulationRequest;
    private int gridSize;
    private int foodPerTick;
    private int starvationThreshold;
    private Position topLeft;
    private Position bottomRight;
    private double moveProbability;
    private double multiplyProbability;
    private LogicCreature mobileCreature;
    private LogicCreature staticRabbit;
    private SimulationStep initialStep;
    private int token;
    private SimulationGridInterface simulationGrid;
    private LogicSimulation simulation;
    private SimulationEngineManager simulationEngineManager;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        user = new User("testUser");
        totalNumberOfCreatures = 2;
        creaturesNamesQuantities = new HashMap<>();
        creaturesNamesQuantities.put("mobile-test", 1);
        creaturesNamesQuantities.put("rabbit-test", 1);
        ticksToRun = 100000;
        initialOccupancy = 0.35;
        foodProbability = 0.2;
        random = new Random();
        simulationRequest = new SimulationRequest(user, creaturesNamesQuantities, totalNumberOfCreatures, ticksToRun,
                initialOccupancy, foodProbability, random);
        gridSize = SimulationGrid.calculateSize(2, initialOccupancy);
        foodPerTick = SimulationGrid.calculateFoodPerTick(gridSize, foodProbability);
        starvationThreshold = 5;
        topLeft = new Position(0, 0);
        bottomRight = new Position(gridSize - 1, gridSize - 1);
        moveProbability = 0.5;
        multiplyProbability = 0.2;
        mobileCreature = new MobileCreature(new Creature("mc", "mobile-test"), starvationThreshold,
                moveProbability, topLeft, random);
        staticRabbit = new StaticRabbit(new Creature("sr", "rabbit-test"), starvationThreshold,
                multiplyProbability, bottomRight, random);
        stepMap = new HashMap<>();
        stepMap.put(topLeft, mobileCreature);
        stepMap.put(bottomRight, staticRabbit);
        initialStep = new SimulationStep(gridSize, stepMap);
        token = 1;
        simulationGrid = new SimulationGrid(initialStep, foodPerTick, random);
        simulation = new LogicSimulation(token, user, ticksToRun, simulationGrid);
    }

    @AfterEach
    public void tearDown() {
        user = null;
        totalNumberOfCreatures = 0;
        creaturesNamesQuantities = null;
        ticksToRun = 0;
        initialOccupancy = 0;
        foodProbability = 0;
        random = null;
        simulationRequest = null;
        gridSize = 0;
        foodPerTick = 0;
        starvationThreshold = 0;
        topLeft = null;
        bottomRight = null;
        moveProbability = 0;
        multiplyProbability = 0;
        mobileCreature = null;
        staticRabbit = null;
        stepMap = null;
        initialStep = null;
        token = 0;
        simulationGrid = null;
        simulation = null;
        simulationEngineManager = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN CON DBMANAGER FAKE
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test startSimulation(Simulation simulation, SimulationRequest simulationRequest) ----------------------------

    @Test
    @DisplayName("startSimulation: ejecuta la simulación completa y la guarda usando Fake")
    public void given_validSimulation_when_startSimulation_then_statusIsCompletedAndSaved() throws InterruptedException {
        // Arrange (Given)
        DBManagerFake dbFake = new DBManagerFake();
        simulationEngineManager = new SimulationEngineManager(dbFake);

        // Act (When)
        simulationEngineManager.startSimulation(simulation);

        // Esperamos a que el hilo empiece
        while (simulation.getStatus() == SimulationStatus.PENDING) {
            Thread.sleep(1);
        }

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, simulation.getStatus(),
                String.format("La persistencia mock debía registrar estado RUNNING, pero registró %s.",
                        simulation.getStatus()));

        // Esperamos a que el hilo termine
        while (simulation.getStatus() == SimulationStatus.RUNNING || simulation.getStatus() == SimulationStatus.PENDING) {
            Thread.sleep(10);
        }

        // Assert (Then)
        assertTrue(dbFake.isUpdateSimulationCalled(),
                "Se esperaba que se actualizara el estado en persistencia, pero no se llamó al método " +
                        "updateSimulationStatus.");
        assertEquals(SimulationStatus.COMPLETED, simulation.getStatus(),
                String.format("La simulación debería haber terminado con estado COMPLETED, pero su estado es %s.",
                        simulation.getStatus()));
        assertTrue(dbFake.isSaveSimulationResultCalled(),
                "Se esperaba que se guardaran los resultados en persistencia, pero no se llamó a " +
                        "saveSimulationResult.");
        assertEquals(ticksToRun + 1, simulation.getSimulationData().getTicks(),
                String.format("La simulación debía tener %d ticks en total (1 inicial + 5 ejecutados), pero tiene %d" +
                        ".", ticksToRun + 1, simulation.getSimulationData().getTicks()));
    }

    @Test
    @DisplayName("startSimulation: si el hilo es interrumpido, para la simulación y actualiza a interrumpida usando " +
            "Fake")
    public void given_runningSimulation_when_threadInterrupted_then_statusIsInterruptedAndSaved() throws InterruptedException {
        // Arrange (Given)
        DBManagerFake dbFake = new DBManagerFake();
        simulationEngineManager = new SimulationEngineManager(dbFake);

        Position topRight = new Position(0, gridSize - 1);
        creaturesNamesQuantities.put("interrupt-test", 1);
        simulationRequest = new SimulationRequest(user, creaturesNamesQuantities, totalNumberOfCreatures, ticksToRun,
                initialOccupancy, foodProbability, random);
        // Usamos una criatura fake que interrumpe su propio hilo de ejecución para simular la interrupción del Engine
        LogicCreatureFake interruptingCreature = new LogicCreatureFake("id-fake") {
            @Override
            public void performEat() {
                super.performEat();
                Thread.currentThread().interrupt();
            }
        };
        interruptingCreature.setPosition(topRight);
        stepMap.put(topRight, interruptingCreature);
        initialStep = new SimulationStep(gridSize, stepMap);
        simulationGrid = new SimulationGrid(initialStep, foodPerTick, random);
        simulation = new LogicSimulation(token, user, ticksToRun, simulationGrid);

        // Act (When)
        simulationEngineManager.startSimulation(simulation);

        // Esperamos a que procese
        Thread.sleep(10);

        // Assert (Then)
        assertTrue(dbFake.isUpdateSimulationCalled(),
                "Se esperaba que se actualizara el estado en persistencia, pero no se llamó al método " +
                        "updateSimulationStatus.");
        assertEquals(SimulationStatus.INTERRUPTED, simulation.getStatus(),
                String.format("La simulación debería haber terminado con estado INTERRUPTED, pero su estado es %s.",
                        simulation.getStatus()));
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN CON DBMANAGER MOCK
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test startSimulation(Simulation simulation, SimulationRequest simulationRequest) ----------------------------

    @Test
    @DisplayName("startSimulation: ejecuta y guarda en DBManagerMock (Persistencia Mock)")
    public void given_validSimulation_when_startSimulation_then_updatesAndSavesInDbMock() throws InterruptedException {
        // Arrange (Given)
        DBManagerMock dbMock = new DBManagerMock();
        simulationEngineManager = new SimulationEngineManager(dbMock);
        dbMock.saveUser(user);
        dbMock.saveSimulation(simulation);

        // Act (When)
        simulationEngineManager.startSimulation(simulation);

        // Esperamos a que el hilo empiece
        while (simulation.getStatus() == SimulationStatus.PENDING) {
            Thread.sleep(1);
        }

        // Assert (Then)
        Simulation storedSim = dbMock.getSimulation(simulation);
        assertEquals(SimulationStatus.RUNNING, storedSim.getStatus(),
                String.format("La persistencia mock debía registrar estado RUNNING, pero registró %s.",
                        storedSim.getStatus()));

        // Esperamos
        while (simulation.getStatus() == SimulationStatus.RUNNING || simulation.getStatus() == SimulationStatus.PENDING) {
            Thread.sleep(10);
        }

        // Assert (Then)
        storedSim = dbMock.getSimulation(simulation);
        assertEquals(SimulationStatus.COMPLETED, storedSim.getStatus(),
                String.format("La persistencia mock debía registrar estado COMPLETED, pero registró %s.",
                        storedSim.getStatus()));
        assertEquals(ticksToRun + 1, storedSim.getSimulationData().getTicks(),
                String.format("La simulación guardada en DB mock debía tener %d ticks en total, pero tiene %d.",
                        ticksToRun + 1, storedSim.getSimulationData().getTicks()));
        assertNotNull(dbMock.getSimulationResult(simulation),
                "El resultado de la simulación debía haberse guardado en la DB mock y no ser nulo.");
    }
}