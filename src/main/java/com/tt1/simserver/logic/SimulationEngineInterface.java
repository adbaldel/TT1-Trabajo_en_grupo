package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;

/**
 * Define el contrato del motor encargado de procesar los turnos de la simulación de forma asíncrona.
 */
public interface SimulationEngineInterface extends Runnable {

    /**
     * Comprueba de forma segura y sincronizada si el motor de la simulación ha terminado.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si la simulación completó la ejecución de todos los turnos preestablecidos. Devuelve falso mientras no ha arrancado o sigue procesando.
     *
     * @return el estado actual de finalización.
     */
    boolean isDone();

    /**
     * Extrae el objeto que empaqueta los historiales del proceso simulado.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el contenedor histórico poblado con las disposiciones del tablero solo si la simulación ya ha terminado. Devuelve nulo para evitar accesos tempranos si no ha finalizado.
     *
     * @return los datos con el resultado de la simulación o nulo si sigue en ejecución.
     */
    SimulationResult getResult();
}
