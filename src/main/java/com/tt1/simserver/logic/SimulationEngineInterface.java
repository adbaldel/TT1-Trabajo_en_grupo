package com.tt1.simserver.logic;

/**
 * Define la funcionalidad de un motor de simulaciones.
 */
public interface SimulationEngineInterface extends Runnable {

    /**
     * Ejecuta una simulación completa.
     */
    void run();
}