package com.tt1.simserver.model;

/**
 * Identifica los estados por los que transita una simulación en el servidor.
 */
public enum SimulationStatus {
    /**
     * La solicitud ha sido registrada, pero el motor todavía no ha empezado a procesar sus turnos.
     */
    PENDING,

    /**
     * El motor está actualmente ejecutando cálculos y avanzando los turnos de la simulación.
     */
    RUNNING,

    /**
     * El motor ha terminado todos los pasos y el resultado final está listo para ser consultado.
     */
    COMPLETED
}