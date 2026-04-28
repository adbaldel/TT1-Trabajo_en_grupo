package com.tt1.simserver.mocks;

import com.tt1.simserver.logic.SimulationManagerInterface;
import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;

/**
 * Doble de pruebas (Fake) para simular el gestor de la simulación.
 * Nos permite inyectar estados y tokens predecibles para testear el SimulationService.
 */
public class SimulationManagerFake implements SimulationManagerInterface {
    private int token;
    private SimulationStatus status;
    private SimulationResult result;
    private boolean updateCalled;
    private boolean startCalled;


    public SimulationManagerFake() {
        token = -1;
        status = SimulationStatus.PENDING;
        result = null;
        updateCalled = false;
        startCalled = false;
    }


    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public boolean isUpdateCalled() {
        return updateCalled;
    }

    public boolean isStartCalled() {
        return startCalled;
    }

    @Override
    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public SimulationStatus getSimulationStatus() {
        return status;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    public void setSimulationStatus(SimulationStatus status) {
        this.status = status;
    }

    @Override
    public void updateSimulationStatus() {
        this.updateCalled = true;
    }

    @Override
    public SimulationResult getSimulationResult() {
        return result;
    }

    public void setSimulationResult(SimulationResult result) {
        this.result = result;
    }

    @Override
    public int startSimulation() {
        this.startCalled = true;
        this.status = SimulationStatus.RUNNING;
        return this.token == -1 ? 999 : this.token; // 999 por defecto si no se configura
    }
}