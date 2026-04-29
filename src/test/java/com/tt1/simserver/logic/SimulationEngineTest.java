package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.GridFake;
import com.tt1.simserver.model.SimulationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationEngineTest {

    private GridFake gridFake;
    private int maxSteps;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    void setUp() {
        gridFake = new GridFake();
        maxSteps = 10;
    }

    @AfterEach
    void tearDown() {
        gridFake = null;
        maxSteps = 0;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test isDone -------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Estado final: Una simulación nueva arranca sin estar terminada")
    void given_newSimulationEngine_when_isDone_then_returnsFalse() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        boolean done = engine.isDone();

        // Assert (Then)
        assertFalse(done, "Una simulación recién instanciada debe iniciar con su estado de finalización en falso");
    }

    // --- Test getResult ----------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Resultado: Devuelve null si se consulta antes de concluir la simulación")
    void given_newSimulationEngine_when_getResult_then_returnsNull() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        SimulationResult result = engine.getResult();

        // Assert (Then)
        assertNull(result, "No se puede obtener un resultado definitivo hasta que el motor procese todos sus turnos");
    }

    // --- Test run ----------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Ejecución: El motor delega al tablero exactamente la cantidad de turnos configurada")
    void given_simulationEngine_when_run_then_gridTickIsCalledMaxStepsTimes() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        engine.run();

        // Assert (Then)
        assertEquals(maxSteps, gridFake.getTickCount(), "El motor debe solicitar al tablero que avance un turno por cada paso máximo definido");
    }

    // --- Test run & isDone -------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Ejecución: Marca la simulación como terminada tras procesar el último turno")
    void given_simulationEngine_when_run_then_isDoneReturnsTrue() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        engine.run();
        boolean done = engine.isDone();

        // Assert (Then)
        assertTrue(done, "El motor debe cambiar internamente su bandera a terminada al concluir todos los turnos");
    }

    // --- Test run & getResult ----------------------------------------------------------------------------------------

    @Test
    @DisplayName("Ejecución: Construye un historial completo que incluye el tablero inicial más cada turno procesado")
    void given_simulationEngine_when_run_then_getResultReturnsSimulationResultWithCorrectNumberOfSteps() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        engine.run();
        SimulationResult result = engine.getResult();

        // Assert (Then)
        assertNotNull(result, "El resultado debe estar generado y disponible tras finalizar la ejecución");
        // maxSteps + 1 porque guarda el estado inicial (segundo 0) y el estado tras cada tick
        assertEquals(maxSteps + 1, engine.getResult().getSeconds(), "El historial del resultado debe contener el estado inicial (paso 0) sumado a un estado por cada turno procesado");
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test run with real Grid -------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Ejecución: Procesa correctamente un tablero real hasta el final y genera su historial")
    void integration_given_simulationEngineWithRealGrid_when_run_then_simulationCompletesAndReturnsResult() {
        // Arrange (Given)
        Grid grid = new Grid(5, 0.2);
        int maxSteps = 5;
        SimulationEngine engine = new SimulationEngine(grid, maxSteps);

        // Act (When)
        engine.run();
        boolean done = engine.isDone();
        SimulationResult result = engine.getResult();

        // Assert (Then)
        assertTrue(done, "La ejecución con un tablero real debe llegar hasta el final y marcarse como concluida");
        assertNotNull(result, "El motor debe haber empaquetado los datos del tablero real en un objeto de resultado válido");
        assertEquals(maxSteps + 1, engine.getResult().getSeconds(), "El historial real exportado debe sumar exactamente los turnos procesados más la disposición inicial de las criaturas");
    }
}