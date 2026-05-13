package com.tt1.simserver.mocks.database;

import com.tt1.simserver.database.DBManagerInterface;
import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationData;
import com.tt1.simserver.model.User;

import java.util.List;

/**
 * Implementa la funcionalidad de un gestor de base de datos devolviendo datos preconfigurados y registra tiempos de
 * llamadas a métodos.
 */
public class DBManagerFake implements DBManagerInterface {
    private User userToReturn;
    private Simulation simulationToReturn;
    private List<Integer> userTokensToReturn;
    private SimulationData simulationResultToReturn;
    private long getUserCalledTime;
    private long saveUserCalledTime;
    private long getSimulationCalledTime;
    private long getUserTokensCalledTime;
    private long saveSimulationCalledTime;
    private long updateSimulationCalledTime;
    private long getSimulationResultCalledTime;
    private long saveSimulationResultCalledTime;


    /**
     * Construye un gestor de bases de datos falso sin configurar.
     */
    public DBManagerFake() {
        userToReturn = null;
        simulationToReturn = null;
        userTokensToReturn = null;
        simulationResultToReturn = null;
        getUserCalledTime = 0;
        saveUserCalledTime = 0;
        getSimulationCalledTime = 0;
        getUserTokensCalledTime = 0;
        saveSimulationCalledTime = 0;
        updateSimulationCalledTime = 0;
        getSimulationResultCalledTime = 0;
        saveSimulationResultCalledTime = 0;
    }

    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setUserToReturn(User userToReturn) {
        this.userToReturn = userToReturn;
    }

    public void setSimulationToReturn(Simulation simulationToReturn) {
        this.simulationToReturn = simulationToReturn;
    }

    public void setUserTokensToReturn(List<Integer> userTokensToReturn) {
        this.userTokensToReturn = userTokensToReturn;
    }

    public void setSimulationResultToReturn(SimulationData simulationResultToReturn) {
        this.simulationResultToReturn = simulationResultToReturn;
    }

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    public long getGetUserCalledTime() {
        return getUserCalledTime;
    }

    public long getSaveUserCalledTime() {
        return saveUserCalledTime;
    }

    public long getGetSimulationCalledTime() {
        return getSimulationCalledTime;
    }

    public long getGetUserTokensCalledTime() {
        return getUserTokensCalledTime;
    }

    public long getSaveSimulationCalledTime() {
        return saveSimulationCalledTime;
    }

    public long getUpdateSimulationCalledTime() {
        return updateSimulationCalledTime;
    }

    public long getGetSimulationResultCalledTime() {
        return getSimulationResultCalledTime;
    }

    public long getSaveSimulationResultCalledTime() {
        return saveSimulationResultCalledTime;
    }

    public boolean isGetUserCalled() {
        return getUserCalledTime > 0;
    }

    public boolean isSaveUserCalled() {
        return saveUserCalledTime > 0;
    }

    public boolean isGetSimulationCalled() {
        return getSimulationCalledTime > 0;
    }

    public boolean isGetUserTokensCalled() {
        return getUserTokensCalledTime > 0;
    }

    public boolean isSaveSimulationCalled() {
        return saveSimulationCalledTime > 0;
    }

    public boolean isUpdateSimulationCalled() {
        return updateSimulationCalledTime > 0;
    }

    public boolean isGetSimulationResultCalled() {
        return getSimulationResultCalledTime > 0;
    }

    public boolean isSaveSimulationResultCalled() {
        return saveSimulationResultCalledTime > 0;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public User getUser(User user) {
        getUserCalledTime = System.currentTimeMillis();
        return userToReturn;
    }

    @Override
    public void saveUser(User user) {
        saveUserCalledTime = System.currentTimeMillis();
    }

    @Override
    public Simulation getSimulation(Simulation simulation) {
        getSimulationCalledTime = System.currentTimeMillis();
        return simulationToReturn;
    }

    @Override
    public List<Integer> getUserTokens(User user) {
        getUserTokensCalledTime = System.currentTimeMillis();
        return userTokensToReturn;
    }

    @Override
    public void saveSimulation(Simulation simulation) {
        saveSimulationCalledTime = System.currentTimeMillis();
    }

    @Override
    public void updateSimulationStatus(Simulation simulation) {
        updateSimulationCalledTime = System.currentTimeMillis();
    }

    @Override
    public SimulationData getSimulationResult(Simulation simulation) {
        getSimulationResultCalledTime = System.currentTimeMillis();
        return simulationResultToReturn;
    }

    @Override
    public void saveSimulationResult(Simulation simulation) {
        saveSimulationResultCalledTime = System.currentTimeMillis();
    }

    @Override
    public void close() {
        // No hay recursos que cerrar en el fake
    }
}
