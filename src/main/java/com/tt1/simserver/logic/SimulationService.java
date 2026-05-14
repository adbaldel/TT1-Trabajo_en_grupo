package com.tt1.simserver.logic;

import com.tt1.simserver.database.DBManagerInterface;
import com.tt1.simserver.model.*;

import java.util.Collection;

/**
 * Implementación de la lógica de simulaciones (Fachada/DAO). Centraliza y gestiona toda la funcionalidad relacionada
 * con las simulaciones.
 */
public class SimulationService implements SimulationServiceInterface {
    private final DBManagerInterface dbManager;
    private final SimulationRequestManagerInterface simulationRequestManager;
    private final SimulationEngineManagerInterface simulationManager;

    /**
     * Construye un servicio de simulación asociado al gestor de solicitudes de simulaciones, al gestor de simulaciones
     * y al gestor de base de datos pasados como parámetros. Asume que el gestor de solicitudes de simulaciones, el
     * gestor de simulaciones y el gestor de base de datos son no nulos.
     *
     * @param simulationRequestManager el gestor de solicitudes de simulaciones.
     * @param simulationManager        el gestor de simulaciones.
     * @param dbManager                el gestor de base de datos.
     */
    public SimulationService(SimulationRequestManagerInterface simulationRequestManager,
                             SimulationEngineManagerInterface simulationManager,
                             DBManagerInterface dbManager) {
        this.simulationRequestManager = simulationRequestManager;
        this.simulationManager = simulationManager;
        this.dbManager = dbManager;
    }

    @Override
    public Collection<String> getCreatures() {
        return CreatureFactory.getCreaturesNames();
    }

    @Override
    public boolean existsSimulation(Simulation simulation) {
        Simulation storedSimulation = dbManager.getSimulation(simulation);
        return storedSimulation != null && storedSimulation.getUser().equals(simulation.getUser());
    }

    @Override
    public SimulationStatus getSimulationStatus(Simulation simulation) {
        return dbManager.getSimulation(simulation).getStatus();
    }

    @Override
    public SimulationData getSimulationResult(Simulation simulation) {
        return dbManager.getSimulationResult(simulation);
    }

    @Override
    public Collection<Integer> getUserTokens(User user) {
        return dbManager.getUserTokens(user);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Notas sobre implementación: comprueba si el usuario de la solicitud de simulación está almacenado y si no
     * lo está lo guarda. Después crea y almacena una simulación llamando a
     * {@link SimulationRequestManager#createSimulation(SimulationRequest)} con la una solicitud de simulación igual a
     * la pasada, pero con el usuario almacenado (si no estaba almacenado envía la solicitud de simulación pasada, ya
     * que su usuario ha sido el almacenado). Finalmente, manda correr la simulación llamando a
     * {@link SimulationEngineManager#startSimulation(LogicSimulation)}.</p>
     */
    @Override
    public Simulation requestSimulation(SimulationRequest simulationRequest) {
        // Si se añade un registrar usuario a la presentación esto iría en métodos a parte cómo existe usuario y guardar
        // usuario que la presentación llamaría. Aquí va implicito porque la presentación no conoce que haya usuarios
        // registrados.
        User storedUser = dbManager.getUser(simulationRequest.getUser());
        if (storedUser == null) {
            dbManager.saveUser(simulationRequest.getUser());
        } else {
            new SimulationRequest(storedUser, simulationRequest);
        }

        LogicSimulation simulation = simulationRequestManager.createSimulation(simulationRequest);
        simulationManager.startSimulation(simulation);

        return simulation;
    }

    @Override
    public void shutdown() {
        simulationManager.shutdown();
        dbManager.close();
    }
}