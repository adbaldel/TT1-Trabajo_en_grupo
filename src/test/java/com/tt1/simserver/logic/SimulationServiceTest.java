package com.tt1.simserver.logic;

import com.tt1.simserver.logic.creatures.LogicCreature;
import com.tt1.simserver.logic.creatures.MobileCreature;
import com.tt1.simserver.logic.creatures.StaticRabbit;
import com.tt1.simserver.mocks.database.DBManagerFake;
import com.tt1.simserver.mocks.database.DBManagerMock;
import com.tt1.simserver.mocks.logic.SimulationEngineManagerFake;
import com.tt1.simserver.mocks.logic.SimulationRequestManagerFake;
import com.tt1.simserver.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationServiceTest {
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
    private SimulationRequestManagerFake requestManagerFake;
    private SimulationEngineManagerFake engineManagerFake;
    private DBManagerFake dbFake;
    private SimulationService service;

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
        mobileCreature = new MobileCreature(new Creature("mc", "mobile-test"), starvationThreshold, moveProbability,
                topLeft, random);
        staticRabbit = new StaticRabbit(new Creature("sr", "rabbit-test"), starvationThreshold, multiplyProbability,
                bottomRight, random);
        stepMap = new HashMap<>();
        stepMap.put(topLeft, mobileCreature);
        stepMap.put(bottomRight, staticRabbit);
        initialStep = new SimulationStep(gridSize, stepMap);
        token = 1;
        simulationGrid = new SimulationGrid(initialStep, foodPerTick, random);
        simulation = new LogicSimulation(token, user, ticksToRun, simulationGrid);

        dbFake = new DBManagerFake();
        requestManagerFake = new SimulationRequestManagerFake();
        engineManagerFake = new SimulationEngineManagerFake();
        service = new SimulationService(requestManagerFake, engineManagerFake, dbFake);
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

        dbFake = null;
        requestManagerFake = null;
        engineManagerFake = null;
        service = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test existsSimulation(Simulation simulation) ----------------------------------------------------------------

    @Test
    @DisplayName("existsSimulation: devuelve cierto si la simulación existe")
    public void given_existingSimulation_when_existsSimulation_then_returnsTrue() {
        // Arrange (Given)
        dbFake.setSimulationToReturn(simulation);

        // Act (When)
        boolean exists = service.existsSimulation(simulation);

        // Assert (Then)
        assertTrue(exists, "Se esperaba que devolviera cierto porque la simulación sí existe en el " + "sistema.");
    }

    @Test
    @DisplayName("existsSimulation: devuelve falso si la simulación no existe")
    public void given_nonExistingSimulation_when_existsSimulation_then_returnsFalse() {
        // Arrange (Given)
        dbFake.setSimulationToReturn(null);

        // Act (When)
        boolean exists = service.existsSimulation(simulation);

        // Assert (Then)
        assertFalse(exists, "Se esperaba que devolviera falso porque la simulación no existe en el " + "sistema.");
    }

    // --- Test getSimulationStatus(Simulation simulation) -------------------------------------------------------------

    @Test
    @DisplayName("getSimulationStatus: devuelve el estado correspondiente de la simulación")
    public void given_simulationWithStatus_when_getSimulationStatus_then_returnsExpectedStatus() {
        // Arrange (Given)
        simulation.startSimulation(); // Estado: RUNNING
        dbFake.setSimulationToReturn(simulation);

        // Act (When)
        SimulationStatus status = service.getSimulationStatus(simulation);

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, status, String.format("El estado devuelto debía ser RUNNING, pero fue "
                + "%s.", status));
    }

    // --- Test getSimulationResult(Simulation simulation) -------------------------------------------------------------

    @Test
    @DisplayName("getSimulationResult: devuelve el resultado de la simulación")
    public void given_completedSimulation_when_getSimulationResult_then_returnsSimulationData() {
        // Arrange (Given)
        SimulationData dataToReturn = new SimulationData(10);
        dbFake.setSimulationResultToReturn(dataToReturn);

        // Act (When)
        SimulationData result = service.getSimulationResult(simulation);

        // Assert (Then)
        assertEquals(dataToReturn, result, "El resultado devuelto no corresponde con los datos esperados de la " +
                "simulación.");
    }

    // --- Test getUserTokens(User user) -------------------------------------------------------------------------------

    @Test
    @DisplayName("getUserTokens: usuario inexistente devuelve lista vacía")
    public void given_nonExistingUser_when_getUserTokens_then_returnsEmptyList() {
        // Arrange (Given)
        dbFake.setUserTokensToReturn(List.of());

        // Act (When)
        Collection<Integer> tokens = service.getUserTokens(user);

        // Assert (Then)
        assertTrue(tokens.isEmpty(), "Un usuario inexistente debía devolver una colección vacía de " + "tokens.");
    }

    @Test
    @DisplayName("getUserTokens: usuario existente con tokens devuelve la lista de sus tokens")
    public void given_existingUserWithTokens_when_getUserTokens_then_returnsUserTokens() {
        // Arrange (Given)
        List<Integer> expectedTokens = List.of(123, 456);
        dbFake.setUserTokensToReturn(expectedTokens);

        // Act (When)
        Collection<Integer> tokens = service.getUserTokens(user);

        // Assert (Then)
        assertEquals(2, tokens.size(), "El usuario debía tener 2 tokens registrados.");
        assertTrue(tokens.containsAll(expectedTokens), "Los tokens devueltos no coinciden con los " + "esperados.");
    }

    // --- Test requestSimulation(SimulationRequest simulationRequest) -------------------------------------------------

    @Test
    @DisplayName("requestSimulation: si el usuario no existe, lo guarda, crea y arranca la simulación")
    public void given_newRequestForNewUser_when_requestSimulation_then_savesUserAndStartsSimulation() {
        // Arrange (Given)
        dbFake.setUserToReturn(null); // Usuario no almacenado
        requestManagerFake.setSimulationToReturn(simulation);

        Map<String, Integer> creatures = new HashMap<>();
        creatures.put("mobile-test", 1);
        SimulationRequest request = new SimulationRequest(user, creatures, totalNumberOfCreatures, ticksToRun,
                initialOccupancy, foodProbability, new Random());

        // Act (When)
        Simulation returnedSimulation = service.requestSimulation(request);

        // Assert (Then)
        assertTrue(dbFake.isSaveUserCalled(), "El usuario no existía en BD, por lo tanto debió llamarse a guardar el "
                + "usuario.");
        assertEquals(simulation, returnedSimulation, "La simulación devuelta no es la generada por el RequestManager.");
        assertTrue(engineManagerFake.isRunningSimulation(simulation), "La simulación debía haber sido mandada a " +
                "correr al EngineManager.");
    }

    @Test
    @DisplayName("requestSimulation: si el usuario ya existe, lo carga, crea y arranca la simulación")
    public void given_newRequestForExistingUser_when_requestSimulation_then_loadsUserAndStartsSimulation() {
        // Arrange (Given)
        dbFake.setUserToReturn(user); // Usuario ya existe
        requestManagerFake.setSimulationToReturn(simulation);

        Map<String, Integer> creatures = new HashMap<>();
        creatures.put("mobile-test", 1);
        SimulationRequest request = new SimulationRequest(user, creatures, totalNumberOfCreatures, ticksToRun,
                initialOccupancy, foodProbability, new Random());

        // Act (When)
        service.requestSimulation(request);

        // Assert (Then)
        assertFalse(dbFake.isSaveUserCalled(), "El usuario ya existía en BD, no debía llamarse al método para " +
                "guardarlo.");
        assertTrue(engineManagerFake.isRunningSimulation(simulation), "La simulación debía haber sido mandada a " +
                "correr al EngineManager.");
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test requestSimulation(SimulationRequest simulationRequest) -------------------------------------------------

    @Test
    @DisplayName("requestSimulation Integración: Flujo completo persistiendo, ejecutando y finalizando simulación mock")
    public void given_validSimulationRequest_when_requestSimulation_then_executesFullFlowWithDbMock() throws InterruptedException {
        // Arrange (Given)
        DBManagerMock dbMock = new DBManagerMock();
        SimulationRequestManager realRequestManager = new SimulationRequestManager(new Random(), dbMock);
        SimulationEngineManager realEngineManager = new SimulationEngineManager(dbMock);
        SimulationService realService = new SimulationService(realRequestManager, realEngineManager, dbMock);

        Map<String, Integer> creatures = new HashMap<>();
        creatures.put("static-test", 1);
        SimulationRequest request = new SimulationRequest(user, creatures, totalNumberOfCreatures, ticksToRun,
                initialOccupancy, foodProbability, new Random());

        // Act (When)
        Simulation runningSim = realService.requestSimulation(request);

        // Esperamos ejecución asíncrona (es un Test de Integración con hilo subyacente)
        while (runningSim.getStatus() == SimulationStatus.RUNNING || runningSim.getStatus() == SimulationStatus.PENDING) {
            Thread.sleep(10);
        }

        // Assert (Then)
        User storedUser = dbMock.getUser(user);
        assertNotNull(storedUser, "El usuario debía guardarse y poder ser recuperado de la persistencia Mock.");

        Simulation storedSim = dbMock.getSimulation(runningSim);
        assertNotNull(storedSim, "La simulación debía almacenarse en la persistencia Mock.");

        assertEquals(SimulationStatus.COMPLETED, storedSim.getStatus(), "El flujo debió finalizar la simulación, " +
                "actualizando su estado en Mock a COMPLETED.");

        SimulationData resultData = dbMock.getSimulationResult(runningSim);
        assertNotNull(resultData,
                "Al completarse, los resultados debían guardarse y ser recuperables desde la DB " + "Mock.");
    }
}