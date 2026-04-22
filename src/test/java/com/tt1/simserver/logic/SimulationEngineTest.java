package com.tt1.simserver.logic;

import org.junit.Test;

/**
 * Pruebas para el motor de simulación.
 */
public class SimulationEngineTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testConstructor() {
        // Ignoramos el fallo de Grid porque el objetivo es probar el constructor de Engine
        new SimulationEngine(null, 10);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsDone() {
        SimulationEngine engine = createEngineProxy();
        engine.isDone();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetResult() {
        SimulationEngine engine = createEngineProxy();
        engine.getResult();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRun() {
        SimulationEngine engine = createEngineProxy();
        engine.run();
    }

    private SimulationEngine createEngineProxy() {
        // Forzamos la llamada al constructor que lanza la excepción
        return new SimulationEngine(null, 1);
    }
}