package com.tt1.simserver.modelo;

/**
 * Define los estados posibles por los que puede transitar una solicitud.
 * El flujo típico sigue el orden: PENDIENTE -> PROCESANDO -> COMPLETADA o FALLIDA.
 */
public enum EstadoSolicitud {
    /** La solicitud ha sido creada y está a la espera de ser tomada por el motor de procesamiento. */
    PENDIENTE,

    /** El proceso de cálculo o lógica de negocio está actualmente en ejecución. */
    PROCESANDO,

    /** El proceso ha finalizado con éxito y los resultados están disponibles. */
    COMPLETADA,

    /** El proceso se ha detenido debido a un error crítico o una validación fallida. */
    FALLIDA
}
