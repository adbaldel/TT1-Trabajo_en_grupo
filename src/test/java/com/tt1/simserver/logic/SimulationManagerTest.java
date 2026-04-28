package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.SimulationEngineFake;
import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationManagerTest {

    private SimulationEngineFake engineFake;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        engineFake = new SimulationEngineFake();
    }

    @AfterEach
    public void tearDown() {
        engineFake = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test getToken -----------------------------------------------------------------------------------------------

    @Test
    void given_newSimulationManager_when_getToken_then_returnsMinusOne() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        int token = manager.getToken();

        // Assert (Then)
        assertEquals(-1, token, "Un manager recién creado y no iniciado debe tener token -1.");
    }

    // --- Test getSimulationStatus ------------------------------------------------------------------------------------

    @Test
    void given_newSimulationManager_when_getSimulationStatus_then_returnsPending() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "La simulación debe estar PENDING antes de arrancar.");
    }

    // --- Test getSimulationResult ------------------------------------------------------------------------------------

    @Test
    void given_simulationManager_when_getSimulationResult_then_returnsResultFromEngine() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        SimulationResult expectedResult = new SimulationResult();
        engineFake.setDone(true);
        engineFake.setResult(expectedResult);

        // Act (When)
        SimulationResult actualResult = manager.getSimulationResult();

        // Assert (Then)
        assertEquals(expectedResult, actualResult, "Debe devolver exactamente el resultado provisto por el motor.");
    }

    // --- Test startSimulation ----------------------------------------------------------------------------------------

    @Test
    void given_startedSimulation_when_startSimulation_then_returnsPositiveToken() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        int token = manager.startSimulation();

        // Assert (Then)
        assertTrue(token >= 0, "El token debe ser positivo.");
    }

    @Test
    void given_startedSimulation_when_startSimulation_then_returnsSameTokenAndDoesNotRestart() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        int firstToken = manager.startSimulation();
        int secondToken = manager.startSimulation();

        // Assert (Then)
        assertEquals(firstToken, secondToken, "No se debe reasignar un token nuevo a una simulación ya iniciada.");
    }

    // --- Test updateSimulationStatus & getSimulationStatus -----------------------------------------------------------

    @Test
    void given_pendingSimulation_when_updateSimulationStatus_then_statusRemainsPending() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        manager.updateSimulationStatus();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "Debe estar en PENDING antes de arrancar y de que la simulación se complete.");
    }

    @Test
    void given_pendingSimulationAndEngineDone_when_updateSimulationStatus_then_statusRemainsPending() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        engineFake.setDone(true);

        // Act (When)
        manager.updateSimulationStatus();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.COMPLETED, status, "Debe pasar a COMPLETED si la simulación ha acabado.");
    }

    // --- Test getSimulationStatus & startSimulation -----------------------------------------

    @Test
    void given_runningSimulationAndEngineNotDone_when_getSimulationStatus_then_returnsRunning() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        engineFake.setDone(false);

        // Act (When)
        manager.startSimulation();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, status, "Si el motor no ha acabado, pero la simualación ha empezado, el status debe devolver RUNNING.");
    }

    @Test
    void given_runningSimulationAndEngineDone_when_getSimulationStatus_then_returnsCompleted() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        engineFake.setDone(true);

        // Act (When)
        manager.startSimulation();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.COMPLETED, status, "Si el motor ha acabado, el status debe devolver COMPLETED.");
    }

    // --- Test getToken & getSimulationStatus & startSimulation -------------------------------------------------------

    @Test
    void given_newSimulationManager_when_startSimulation_then_statusIsRunningAndTokenIsAssigned() throws InterruptedException {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        int token = manager.startSimulation();
        int storedToken = manager.getToken();
        SimulationStatus status = manager.getSimulationStatus();

        // Esperamos máximo 2 segundos para dar tiempo al pool de hilos de disparar el método run()
        boolean executedAsynchronously = engineFake.awaitRun(2, TimeUnit.SECONDS);

        // Assert (Then)
        assertTrue(token >= 0, "Debe haberse asignado un token global >= 0.");
        assertEquals(token, storedToken, "El token devuelto debe coincidir con el almacenado internamente.");
        assertEquals(SimulationStatus.RUNNING, status, "El estado de la simulación debe transitar a RUNNING.");
        assertTrue(executedAsynchronously, "El motor debió enviarse al ExecutorService y ejecutar su método run().");
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN (Con SimulationEngine y Grid Reales)
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test getToken -----------------------------------------------------------------------------------------------

    @Test
    void integration_given_newSimulationManagerWithRealEngine_when_getToken_then_returnsMinusOne() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine engine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        int token = manager.getToken();

        // Assert (Then)
        assertEquals(-1, token, "Un manager recién creado y no iniciado debe tener token -1.");
    }

    // --- Test getSimulationStatus ------------------------------------------------------------------------------------

    @Test
    void integration_given_newSimulationManagerWithRealEngine_when_getSimulationStatus_then_returnsPending() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine engine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "La simulación debe estar PENDING antes de arrancar.");
    }

    // --- Test getSimulationResult ------------------------------------------------------------------------------------

    @Test
    void integration_given_simulationManagerWithRealEngineNotDone_when_getSimulationResult_then_returnsNull() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        SimulationResult result = manager.getSimulationResult();

        // Assert (Then)
        assertNull(result, "Debe devolver null porque el motor aún no ha generado el resultado.");
    }

    // --- Test startSimulation ----------------------------------------------------------------------------------------

    @Test
    void integration_given_startedSimulationWithRealEngine_when_startSimulation_then_returnsPositiveToken() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        int token = manager.startSimulation();

        // Assert (Then)
        assertTrue(token >= 0, "El token debe ser positivo.");
    }

    @Test
    void integration_given_startedSimulationWithRealEngine_when_startSimulation_then_returnsSameTokenAndDoesNotRestart() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        int firstToken = manager.startSimulation();
        int secondToken = manager.startSimulation();

        // Assert (Then)
        assertEquals(firstToken, secondToken, "No se debe reasignar un token nuevo a una simulación ya iniciada.");
    }

    // --- Test updateSimulationStatus & getSimulationStatus -----------------------------------------------------------

    @Test
    void integration_given_pendingSimulationAndRealEngineDone_when_updateSimulationStatus_then_statusRemainsPending() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        manager.updateSimulationStatus();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "Debe estar en PENDING antes de arrancar y de que la simulación se complete.");
    }

    // --- Test getSimulationStatus & startSimulation -----------------------------------------------------------------------------------------

    @Test
    void integration_given_runningSimulationAndRealEngineNotDone_when_getSimulationStatus_then_returnsRunning() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        // Ponemos muchos ticks (10.000) para forzar que el hilo asíncrono no termine instantáneamente
        SimulationEngine engine = new SimulationEngine(grid, 10000);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        manager.startSimulation();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, status, "El motor real necesita tiempo, por lo que debe continuar en RUNNING.");
    }

    // --- Test getToken & getSimulationStatus & startSimulation -------------------------------------------------------

    @Test
    void integration_given_newSimulationManagerWithRealEngine_when_startSimulation_then_statusIsRunningAndTokenIsAssigned() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine engine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        int token = manager.startSimulation();
        int storedToken = manager.getToken();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertTrue(token >= 0, "Debe haberse asignado un token global >= 0.");
        assertEquals(token, storedToken, "El token devuelto debe coincidir con el almacenado internamente.");
        // Puede que ya esté COMPLETED si el PC es muy rápido, pero la transición RUNNING ocurrió.
        assertTrue(status == SimulationStatus.RUNNING || status == SimulationStatus.COMPLETED);
    }

    // --- Test getSimulationStatus & getSimulationResult & startSimulation ------------------------------------------------------------------------------------

    @Test
    void integration_given_completedSimulationAndRealEngine_when_getSimulationResult_then_returnsResult() throws InterruptedException {
        // Arrange (Given)
        Grid grid = new Grid(5, 0.35);
        // Solo 1 tick, terminará casi instantáneamente
        SimulationEngine engine = new SimulationEngine(grid, 1);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        manager.startSimulation();

        // Esperamos proactivamente a que termine el hilo (Timeout a prueba de fallos: 2 segundos)
        int attempts = 40;
        while (manager.getSimulationStatus() != SimulationStatus.COMPLETED && attempts > 0) {
            Thread.sleep(50);
            attempts--;
        }

        SimulationStatus status = manager.getSimulationStatus();
        SimulationResult result = manager.getSimulationResult();

        // Assert (Then)
        assertEquals(SimulationStatus.COMPLETED, status, "La simulación real debería haberse completado.");
        assertNotNull(result, "El resultado real no debe ser nulo.");
        // Un tick = estado inicial (sec 0) + paso tras 1 tick (sec 1) = 2 pasos totales
        assertEquals(2, result.getSeconds(), "El histórico debe contener el instante inicial y 1 tick calculado.");
    }
}