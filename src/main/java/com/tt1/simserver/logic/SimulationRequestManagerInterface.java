package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationRequest;

/**
 * Define la funcionalidad de un gestor de solicitudes de simulaciones.
 */
public interface SimulationRequestManagerInterface {

    /**
     * Crea una simulación lógica asociada a un tablero de simulación a partir de la solicitud de simulación
     * {@code simulationRequest} y la almacena. Asume que la solicitud de simulación es no nula.
     *
     * @param simulationRequest la solicitud de simulación.
     * @return la simulación lógica generada a partir de la solicitud de simulación.
     */
    LogicSimulation createSimulation(SimulationRequest simulationRequest);
}
