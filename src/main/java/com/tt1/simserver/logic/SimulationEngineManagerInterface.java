package com.tt1.simserver.logic;

import com.tt1.simserver.model.Simulation;

/**
 * Define la funcionalidad de un gestor de simulaciones.
 */
public interface SimulationEngineManagerInterface {

    /**
     * Inicia la simulación lógica {@code simulation} mandándola a un motor de simulaciones para que se corra la
     * simulación. Añade la simulación a la colección de simulaciones gestionadas por este gestor. Asume que la
     * simulación no es nula.
     *
     * @param simulation la simulación lógica a correr.
     */
    void startSimulation(LogicSimulation simulation);

    /**
     * Elimina de este gestor de simulaciones la simulación pasada. Si la simulación no estaba siendo gestionada por
     * este gestor, no hace nada. Asume que la simulación es no nula.
     *
     * @param simulation la simulación a eliminar.
     */
    void removeSimulation(Simulation simulation);

    /**
     * Apaga de forma limpia los recursos utilizados por el gestor de motores de simulación.
     */
    void shutdown();
}