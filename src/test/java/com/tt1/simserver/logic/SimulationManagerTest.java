package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.SimulationEngineFake;
import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Token: Un gestor recién instanciado devuelve -1 por defecto")
    void given_newSimulationManager_when_getToken_then_returnsMinusOne() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        int token = manager.getToken();

        // Assert (Then)
        assertEquals(-1, token, "El token de control de una simulación no arrancada debe ser siempre -1");
    }

    // --- Test getSimulationStatus ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Estado: Toda nueva simulación comienza en estado PENDING")
    void given_newSimulationManager_when_getSimulationStatus_then_returnsPending() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "Una simulación en espera de ser arrancada debe reportarse como pendiente");
    }

    // --- Test getSimulationResult ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Resultado: Traspasa el objeto de resultado directo desde el motor interno")
    void given_simulationManager_when_getSimulationResult_then_returnsResultFromEngine() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        SimulationResult expectedResult = new SimulationResult(1);
        engineFake.setDone(true);
        engineFake.setResult(expectedResult);

        // Act (When)
        SimulationResult actualResult = manager.getSimulationResult();

        // Assert (Then)
        assertEquals(expectedResult, actualResult, "El gestor debe exponer exactamente la misma instancia de resultado generada por su motor de ejecución");
    }

    // --- Test startSimulation ----------------------------------------------------------------------------------------

    @Test
    @DisplayName("Arrancar: Asigna y devuelve un token positivo al iniciar el hilo de simulación")
    void given_startedSimulation_when_startSimulation_then_returnsPositiveToken() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        int token = manager.startSimulation();

        // Assert (Then)
        assertTrue(token >= 0, "Al arrancar la simulación, el gestor está obligado a emitir un token válido (mayor o igual a cero)");
    }

    @Test
    @DisplayName("Arrancar: Mantiene el mismo token si se intenta arrancar repetidas veces")
    void given_startedSimulation_when_startSimulation_then_returnsSameTokenAndDoesNotRestart() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        int firstToken = manager.startSimulation();
        int secondToken = manager.startSimulation();

        // Assert (Then)
        assertEquals(firstToken, secondToken, "Las llamadas redundantes para iniciar una simulación ya arrancada no deben generar un token nuevo");
    }

    // --- Test updateSimulationStatus & getSimulationStatus -----------------------------------------------------------

    @Test
    @DisplayName("Actualizar estado: Se mantiene PENDING si el motor interno sigue procesando turnos")
    void given_pendingSimulation_when_updateSimulationStatus_then_statusRemainsPending() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);

        // Act (When)
        manager.updateSimulationStatus();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "La simulación no debe cambiar de estado automáticamente a menos que el motor confirme su término");
    }

    @Test
    @DisplayName("Actualizar estado: Transita a COMPLETED en el momento en que el motor da por finalizados sus turnos")
    void given_pendingSimulationAndEngineDone_when_updateSimulationStatus_then_statusRemainsPending() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        engineFake.setDone(true);

        // Act (When)
        manager.updateSimulationStatus();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.COMPLETED, status, "El gestor debe actualizar el estado visible a COMPLETED cuando detecta que el motor ha concluido");
    }

    // --- Test getSimulationStatus & startSimulation -----------------------------------------

    @Test
    @DisplayName("Estado en progreso: Devuelve RUNNING mientras el hilo de simulación está calculando")
    void given_runningSimulationAndEngineNotDone_when_getSimulationStatus_then_returnsRunning() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        engineFake.setDone(false);

        // Act (When)
        manager.startSimulation();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, status, "Toda simulación iniciada que aún no haya procesado todos sus turnos debe catalogarse como ejecutándose");
    }

    @Test
    @DisplayName("Estado en progreso: Transita velozmente a COMPLETED si el motor acaba instantáneamente")
    void given_runningSimulationAndEngineDone_when_getSimulationStatus_then_returnsCompleted() {
        // Arrange (Given)
        SimulationManager manager = new SimulationManager(engineFake);
        engineFake.setDone(true);

        // Act (When)
        manager.startSimulation();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.COMPLETED, status, "Si el hilo procesa el motor al instante, el estado debe actualizarse automáticamente a completado");
    }

    // --- Test getToken & getSimulationStatus & startSimulation -------------------------------------------------------

    @Test
    @DisplayName("Flujo de arranque: Inicia asíncronamente, otorga el token y cambia el estado a RUNNING")
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
        assertTrue(token >= 0, "Se debió asignar un token numérico válido para identificar esta ejecución");
        assertEquals(token, storedToken, "El token público devuelto debe coincidir con el almacenado en el interior del gestor");
        assertEquals(SimulationStatus.RUNNING, status, "El estado reportado debe transitar obligatoriamente a RUNNING tras ser encolada");
        assertTrue(executedAsynchronously, "La lógica del motor debe ejecutarse de forma paralela en el hilo secundario del ExecutorService");
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN (Con SimulationEngine y Grid Reales)
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test getToken -----------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Token: El gestor con motor real inicializa con el token vacío (-1)")
    void integration_given_newSimulationManagerWithRealEngine_when_getToken_then_returnsMinusOne() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine engine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        int token = manager.getToken();

        // Assert (Then)
        assertEquals(-1, token, "Incluso utilizando un motor y tablero reales, el token no iniciado es -1");
    }

    // --- Test getSimulationStatus ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Estado: El gestor con motor real arranca marcado como pendiente")
    void integration_given_newSimulationManagerWithRealEngine_when_getSimulationStatus_then_returnsPending() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine engine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "La simulación real aguarda en estado pendiente antes del arranque");
    }

    // --- Test getSimulationResult ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Resultado: Bloquea resultados nulos si el motor real no ha acabado sus turnos")
    void integration_given_simulationManagerWithRealEngineNotDone_when_getSimulationResult_then_returnsNull() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        SimulationResult result = manager.getSimulationResult();

        // Assert (Then)
        assertNull(result, "No existe historial de resultados disponible porque el motor real aún no ha completado el trabajo");
    }

    // --- Test startSimulation ----------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Arrancar: Emitir token válido al lanzar un motor real")
    void integration_given_startedSimulationWithRealEngine_when_startSimulation_then_returnsPositiveToken() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        int token = manager.startSimulation();

        // Assert (Then)
        assertTrue(token >= 0, "El inicio del hilo real requiere la emisión de un token numérico positivo");
    }

    @Test
    @DisplayName("Integración Arrancar: Protege el token de reasignaciones erróneas en motores reales")
    void integration_given_startedSimulationWithRealEngine_when_startSimulation_then_returnsSameTokenAndDoesNotRestart() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        int firstToken = manager.startSimulation();
        int secondToken = manager.startSimulation();

        // Assert (Then)
        assertEquals(firstToken, secondToken, "Un motor real en marcha no debe recibir un identificador nuevo si se le vuelve a dar la orden de arranque");
    }

    // --- Test updateSimulationStatus & getSimulationStatus -----------------------------------------------------------

    @Test
    @DisplayName("Integración Actualizar estado: No altera el estado prematuramente si el motor real sigue procesando")
    void integration_given_pendingSimulationAndRealEngineDone_when_updateSimulationStatus_then_statusRemainsPending() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        SimulationEngine realEngine = new SimulationEngine(grid, 5);
        SimulationManager manager = new SimulationManager(realEngine);

        // Act (When)
        manager.updateSimulationStatus();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.PENDING, status, "El gestor no debe precipitarse marcando como terminado un motor que aún trabaja");
    }

    // --- Test getSimulationStatus & startSimulation -----------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Estado en progreso: Mantener estado RUNNING durante cargas computacionales pesadas")
    void integration_given_runningSimulationAndRealEngineNotDone_when_getSimulationStatus_then_returnsRunning() {
        // Arrange (Given)
        Grid grid = new Grid(10, 0.35);
        // Ponemos muchos ticks (100.000) para forzar que el hilo asíncrono no termine instantáneamente
        SimulationEngine engine = new SimulationEngine(grid, 100000);
        SimulationManager manager = new SimulationManager(engine);

        // Act (When)
        manager.startSimulation();
        SimulationStatus status = manager.getSimulationStatus();

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, status, "La simulación real está retenida procesando turnos masivos, debe exponerse como ejecutándose");
    }

    // --- Test getToken & getSimulationStatus & startSimulation -------------------------------------------------------

    @Test
    @DisplayName("Integración Flujo de arranque: Inicia y registra los estados correctamente con clases reales")
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
        assertTrue(token >= 0, "El token expedido en el flujo real debe ser un entero válido");
        assertEquals(token, storedToken, "El gestor real asegura el registro interno de su token asignado");
        // Puede que ya esté COMPLETED si el PC es muy rápido, pero la transición RUNNING ocurrió.
        assertTrue(status == SimulationStatus.RUNNING || status == SimulationStatus.COMPLETED, "El flujo paralelo debe encontrarse ejecutándose en RUNNING o haber terminado rápidamente");
    }

    // --- Test getSimulationStatus & getSimulationResult & startSimulation ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Resultado final: Rescata el historial real cuando el hilo acaba de procesar el tablero")
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
        assertEquals(SimulationStatus.COMPLETED, status, "El monitor del hilo secundario debió captar la finalización de los cálculos reales");
        assertNotNull(result, "Al acabar los cálculos reales, el historial no puede ser nulo");
        // Un tick = estado inicial (sec 0) + paso tras 1 tick (sec 1) = 2 pasos totales
        assertEquals(2, result.getSeconds(), "El historial del tablero debe abarcar la posición de inicio y el fin del único turno exigido");
    }
}