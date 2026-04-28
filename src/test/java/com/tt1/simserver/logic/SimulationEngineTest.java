package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.GridFake;
import com.tt1.simserver.model.SimulationResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    void given_newSimulationEngine_when_isDone_then_returnsFalse() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        boolean done = engine.isDone();

        // Assert (Then)
        assertFalse(done, "Una simulación recién creada no debería estar terminada.");
    }

    // --- Test getResult ----------------------------------------------------------------------------------------------

    @Test
    void given_newSimulationEngine_when_getResult_then_returnsNull() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        SimulationResult result = engine.getResult();

        // Assert (Then)
        assertNull(result, "El resultado debe ser null si la simulación no ha terminado.");
    }

    // --- Test run ----------------------------------------------------------------------------------------------------

    @Test
    void given_simulationEngine_when_run_then_gridTickIsCalledMaxStepsTimes() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        engine.run();

        // Assert (Then)
        assertEquals(maxSteps, gridFake.getTickCount(), "El método tick() del grid debe llamarse tantas veces como maxSteps.");
    }

    // --- Test run & isDone -------------------------------------------------------------------------------------------

    @Test
    void given_simulationEngine_when_run_then_isDoneReturnsTrue() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        engine.run();
        boolean done = engine.isDone();

        // Assert (Then)
        assertTrue(done, "La simulación debe estar marcada como terminada tras ejecutar run().");
    }

    // --- Test run & getResult ----------------------------------------------------------------------------------------

    @Test
    void given_simulationEngine_when_run_then_getResultReturnsSimulationResultWithCorrectNumberOfSteps() {
        // Arrange (Given)
        SimulationEngine engine = new SimulationEngine(gridFake, maxSteps);

        // Act (When)
        engine.run();
        SimulationResult result = engine.getResult();

        // Assert (Then)
        assertNotNull(result, "El resultado ya no debe ser null tras finalizar.");
        // maxSteps + 1 porque guarda el estado inicial (segundo 0) y el estado tras cada tick
        assertEquals(maxSteps + 1, engine.getResult().getSeconds(), "Debe haber guardado maxSteps + 1 pasos (incluyendo el inicial).");
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test run with real Grid -------------------------------------------------------------------------------------

    @Test
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
        assertTrue(done, "La simulación real debe terminar correctamente.");
        assertNotNull(result, "El resultado debe haberse generado.");
        assertEquals(maxSteps + 1, engine.getResult().getSeconds(), "Deben haberse guardado los pasos correctos del tablero real.");
    }
}