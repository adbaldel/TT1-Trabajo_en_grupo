package com.tt1.simserver.mocks.logic;

import com.tt1.simserver.logic.LogicSimulation;
import com.tt1.simserver.logic.SimulationEngineManagerInterface;
import com.tt1.simserver.model.Simulation;

import java.util.Collection;
import java.util.HashSet;

/**
 * Implementa un gestor de motor de simulaciones devolviendo datos preconfigurados.
 */
public class SimulationEngineManagerFake implements SimulationEngineManagerInterface {
    private final Collection<LogicSimulation> runningSimulations;

    /**
     * Construye
     */
    public SimulationEngineManagerFake() {
        runningSimulations = new HashSet<>();
    }

    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void completeSimulation(Simulation simulation) {
        for (Simulation managedSimulation : runningSimulations) {
            if (managedSimulation.equals(simulation)) {
                managedSimulation.completeSimulation();
                break;
            }
        }
    }

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    public Collection<LogicSimulation> getRunningSimulations() {
        return runningSimulations;
    }

    public boolean isRunningSimulation(Simulation simulation) {
        return runningSimulations.contains(simulation);
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public void startSimulation(LogicSimulation simulation) {
        simulation.startSimulation();
        runningSimulations.add(simulation);
    }

    @Override
    public void removeSimulation(Simulation simulation) {
        runningSimulations.remove(simulation);
    }

    @Override
    public void shutdown() {
        runningSimulations.clear();
    }
}