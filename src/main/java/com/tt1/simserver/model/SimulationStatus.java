package com.tt1.simserver.model;

/**
 * Enum que representa los posibles estados de una simulación en el sistema.
 */
public enum SimulationStatus {
    /**
     * La solicitud ha sido recibida, pero aún no se ha empezado la simulación.
     */
    PENDING,

    /**
     * El motor de simulación está procesando los datos actualmente.
     */
    RUNNING,

    /**
     * La simulación ha finalizado con éxito y los resultados están disponibles.
     */
    COMPLETED
}