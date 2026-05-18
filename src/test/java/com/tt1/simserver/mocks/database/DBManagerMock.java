package com.tt1.simserver.mocks.database;

import com.tt1.simserver.database.entities.SimulationEntity;
import com.tt1.simserver.database.entities.SimulationResultEntity;
import com.tt1.simserver.database.entities.UserEntity;
import com.tt1.simserver.database.transformers.SimulationResultTransformer;
import com.tt1.simserver.database.transformers.SimulationTransformer;
import com.tt1.simserver.database.transformers.UserTransformer;
import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationData;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementa la funcionalidad de un gestor de base de datos almacenando los datos en memoria.
 */
public class DBManagerMock extends DBManagerFake {
    private Map<String, UserEntity> users;
    private Map<Integer, SimulationEntity> simulations;
    private Map<Integer, SimulationResultEntity> simulationResults;

    /**
     * Construye un gestor de bases de datos mock con los usuarios, simulaciones y resultados de simulaciones pasados
     * como parámetros. Asume que los usuarios, simulaciones y resultados de simulaciones no son nulos.
     *
     * @param users             los usuarios almacenados.
     * @param simulations       las simulaciones almacenadas.
     * @param simulationResults los resultados de simulación almacenados.
     */
    public DBManagerMock(Map<String, UserEntity> users, Map<Integer, SimulationEntity> simulations, Map<Integer,
            SimulationResultEntity> simulationResults) {
        this.users = users;
        this.simulations = simulations;
        this.simulationResults = simulationResults;
    }

    /**
     * Construye un gestor de bases de datos mock vacío.
     */
    public DBManagerMock() {
        this(new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    // --- Setters de control para los tests ---------------------------------------------------------------------------

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public User getUser(User user) {
        super.getUser(user); // Para registrar tiempos de llamada

        User realUser = null;
        UserEntity userEntity = users.get(user.username());
        if (userEntity != null) {
            realUser = UserTransformer.transform(userEntity);
        }
        return realUser;
    }

    @Override
    public void saveUser(User user) {
        super.saveUser(user); // Para registrar tiempos de llamada

        UserEntity userEntity = UserTransformer.transform(user);
        users.put(userEntity.getUsername(), userEntity);
    }

    @Override
    public Simulation getSimulation(Simulation simulation) {
        super.getSimulation(simulation); // Para registrar tiempos de llamada

        Simulation realSimulation = null;
        SimulationEntity simulationEntity = simulations.get(simulation.getToken());
        if (simulationEntity != null) {
            if (simulationEntity.getStatus() == SimulationStatus.COMPLETED) {
                realSimulation = SimulationTransformer.transform(simulationEntity, getSimulationResult(simulation));
            } else {
                realSimulation = SimulationTransformer.transform(simulationEntity);
            }
        }
        return realSimulation;
    }

    @Override
    public List<Integer> getUserTokens(User user) {
        super.getUserTokens(user); // Para registrar tiempos de llamada

        UserEntity userEntity = UserTransformer.transform(user);
        List<Integer> userTokens = new ArrayList<>();
        for (SimulationEntity simulationEntity : simulations.values()) {
            if (simulationEntity.getUser().equals(userEntity)) {
                userTokens.add(simulationEntity.getToken());
            }
        }
        return userTokens;
    }

    @Override
    public void saveSimulation(Simulation simulation) {
        super.saveSimulation(simulation); // Para registrar tiempos de llamada

        SimulationEntity simulationEntity = SimulationTransformer.transform(simulation);
        simulations.put(simulationEntity.getToken(), simulationEntity);
    }

    @Override
    public void updateSimulationStatus(Simulation simulation) {
        super.updateSimulationStatus(simulation); // Para registrar tiempos de llamada

        SimulationEntity simulationEntity = simulations.get(simulation.getToken());
        simulationEntity.setStatus(simulation.getStatus());
    }

    @Override
    public SimulationData getSimulationResult(Simulation simulation) {
        super.getSimulationResult(simulation); // Para registrar tiempos de llamada

        SimulationData simulationResult = null;
        SimulationResultEntity simulationResultEntity = simulationResults.get(simulation.getToken());
        if (simulationResultEntity != null) {
            simulationResult = SimulationResultTransformer.transform(simulationResultEntity);
        }
        return simulationResult;
    }

    @Override
    public void saveSimulationResult(Simulation simulation) {
        super.saveSimulationResult(simulation); // Para registrar tiempos de llamada

        SimulationEntity simulationEntity = simulations.get(simulation.getToken());
        simulationEntity.setStatus(simulation.getStatus());
        SimulationResultEntity simulationResultEntity =
                SimulationResultTransformer.transform(simulation.getSimulationData(), simulationEntity);
        simulationResults.put(simulationEntity.getToken(), simulationResultEntity);
    }

    @Override
    public void close() {
        super.close();

        users.clear();
        simulations.clear();
        simulationResults.clear();
        users = null;
        simulations = null;
        simulationResults = null;
    }
}
