package com.tt1.simserver.mocks.logic;

import com.tt1.simserver.logic.SimulationServiceInterface;
import com.tt1.simserver.model.*;

import java.util.Collection;
import java.util.List;

/**
 * Implementa la funcionalidad de un servicio de simulaciones devolviendo datos preconfigurados.
 */
public class SimulationServiceFake implements SimulationServiceInterface {
    private boolean existsSimulationToReturn;
    private SimulationStatus simulationStatusToReturn;
    private SimulationData simulationResultToReturn;
    private List<Integer> userTokensToReturn;
    private Simulation simulationToReturn;

    /**
     * Construye un servicio de simulaciones falso sin configurar.
     */
    public SimulationServiceFake() {
        existsSimulationToReturn = false;
        simulationStatusToReturn = null;
        simulationResultToReturn = null;
        userTokensToReturn = null;
        simulationToReturn = null;
    }

    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setExistsSimulationToReturn(boolean existsSimulationToReturn) {
        this.existsSimulationToReturn = existsSimulationToReturn;
    }

    public void setSimulationStatusToReturn(SimulationStatus simulationStatusToReturn) {
        this.simulationStatusToReturn = simulationStatusToReturn;
    }

    public void setSimulationResultToReturn(SimulationData simulationResultToReturn) {
        this.simulationResultToReturn = simulationResultToReturn;
    }

    public void setUserTokensToReturn(List<Integer> userTokensToReturn) {
        this.userTokensToReturn = userTokensToReturn;
    }

    public void setSimulationToReturn(Simulation simulationToReturn) {
        this.simulationToReturn = simulationToReturn;
    }

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public boolean existsSimulation(Simulation simulation) {
        return existsSimulationToReturn;
    }

    @Override
    public SimulationStatus getSimulationStatus(Simulation simulation) {
        return simulationStatusToReturn;
    }

    @Override
    public SimulationData getSimulationResult(Simulation simulation) {
        return simulationResultToReturn;
    }

    @Override
    public Collection<Integer> getUserTokens(User user) {
        return userTokensToReturn;
    }

    @Override
    public Simulation requestSimulation(SimulationRequest simulationRequest) {
        return simulationToReturn;
    }

    @Override
    public void shutdown() {
        // No hay recursos a apagar en el fake
    }
}
