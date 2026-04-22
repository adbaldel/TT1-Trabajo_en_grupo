package com.tt1.simserver.logic;

import org.junit.Test;

/**
 * Pruebas para el gestor de simulaciones asíncronas.
 */
public class SimulationManagerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testConstructor() {
        new SimulationManager(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetToken() {
        SimulationManager manager = createManagerProxy();
        manager.getToken();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetSimulationStatus() {
        SimulationManager manager = createManagerProxy();
        manager.getSimulationStatus();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdateSimulationStatus() {
        SimulationManager manager = createManagerProxy();
        manager.updateSimulationStatus();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetSimulationResult() {
        SimulationManager manager = createManagerProxy();
        manager.getSimulationResult();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testStartSimulation() {
        SimulationManager manager = createManagerProxy();
        manager.startSimulation();
    }

    private SimulationManager createManagerProxy() {
        return new SimulationManager(null);
    }
}