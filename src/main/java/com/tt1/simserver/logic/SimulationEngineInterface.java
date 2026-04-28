package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;

public interface SimulationEngineInterface extends Runnable {

    /**
     * Verifica de forma sincronizada (thread-safe) si la simulación ha terminado.
     *
     * @return cierto si la simulación completó todos sus pasos, falso en caso contrario.
     */
    boolean isDone();

    /**
     * Devuelve el resultado final almacenando todos los pasos calculados, o null si todavía sigue en curso.
     *
     * @return el resultado si la simulación ha finalizado, o null si todavía sigue en curso.
     */
    SimulationResult getResult();
}
