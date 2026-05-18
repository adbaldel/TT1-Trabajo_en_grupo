package com.tt1.simserver.mocks.logic;

import com.tt1.simserver.logic.LogicSimulation;
import com.tt1.simserver.logic.SimulationRequestManagerInterface;
import com.tt1.simserver.model.SimulationRequest;

/**
 * Implementa la funcionalidad de un gestor de solicitudes de simulaciones devolviendo datos preconfigurados.
 */
public class SimulationRequestManagerFake implements SimulationRequestManagerInterface {
    private LogicSimulation simulationToReturn;

    /**
     * Construye un gestor de solicitudes de simulaciones falso sin configurar.
     */
    public SimulationRequestManagerFake() {
        simulationToReturn = null;
    }

    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setSimulationToReturn(LogicSimulation simulationToReturn) {
        this.simulationToReturn = simulationToReturn;
    }

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public LogicSimulation createSimulation(SimulationRequest simulationRequest) {
        return simulationToReturn;
    }
}
