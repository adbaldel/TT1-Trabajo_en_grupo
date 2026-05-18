package com.tt1.simserver.config;

import com.tt1.simserver.database.DBManager;
import com.tt1.simserver.logic.SimulationEngineManager;
import com.tt1.simserver.logic.SimulationRequestManager;
import com.tt1.simserver.logic.SimulationService;
import com.tt1.simserver.logic.SimulationServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.util.Random;

/**
 * Inyector de dependencias para inyectar un servicio de simulaciones configurado a Jersey.
 */
public class AppBinder extends AbstractBinder {
    private final SimulationService simulationService;

    /**
     * Crea el servicio de simulaciones que luego se inyecta al servidor, además de ejecutar lo su constructor padre
     * ({@link AbstractBinder#AbstractBinder()}).
     */
    public AppBinder() {
        super();

        DBManager dbManager = DBManager.getInstance();
        Random random = new Random();
        SimulationRequestManager simulationRequestManager = new SimulationRequestManager(random, dbManager);
        SimulationEngineManager simulationManager = new SimulationEngineManager(dbManager);
        simulationService = new SimulationService(simulationRequestManager, simulationManager,
                dbManager);
    }

    /**
     * Configura la inyección de un servicio de simulaciones asociado al singleton gestor de la base de datos; a un
     * gestor de solicitudes de simulaciones asociado al mismo gestor de base de datos y con un generador de
     * pseudo-aleatoriedad por defecto; y a un gestor de simulaciones asociado al mismo gestor de base de datos.
     */
    @Override
    protected void configure() {
        // Bind the simulation service
        bind(simulationService).to(SimulationServiceInterface.class);
    }

    /**
     * Obtiene el servicio de simulaciones configurado.
     *
     * @return el servicio de simulaciones configurado.
     */
    public SimulationService getSimulationService() {
        return simulationService;
    }
}