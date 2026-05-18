package com.tt1.simserver.model;

/**
 * Representa los posibles estados de una simulación.
 */
public enum SimulationStatus {
    /**
     * Aún no ha empezado a correr la simulación.
     */
    PENDING,

    /**
     * La simulación está corriendo.
     */
    RUNNING,

    /**
     * La simulación a acabado.
     */
    COMPLETED,

    /**
     * La simulación ha sido interrumpida mientras estaba corriendo.
     */
    INTERRUPTED
}