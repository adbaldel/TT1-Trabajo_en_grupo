package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStep;

/**
 * Motor encargado de ejecutar la lógica interna de una simulación.
 * Implementa {@link Runnable} para permitir su ejecución en un hilo de procesamiento independiente.
 */
public class SimulationEngine implements Runnable {

    /**
     * Inicializa el motor de simulación con su tablero y su límite temporal.
     * Precondición: initialGrid no es nulo, maxSteps > 0.
     *
     * @param initialGrid el estado inicial del tablero a simular.
     * @param maxSteps el número máximo de pasos (segundos) que dura la simulación.
     */
    public SimulationEngine(Grid initialGrid, int maxSteps) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }


    /**
     * Verifica de forma sincronizada (thread-safe) si la simulación ha terminado.
     *
     * @return cierto si la simulación completó todos sus pasos, falso en caso contrario.
     */
    public synchronized boolean isDone() {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Establece de forma sincronizada el estado de finalización de la simulación.
     *
     * @param done cierto para marcar la simulación como completada.
     */
    private synchronized void setDone(boolean done) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Devuelve el resultado final almacenando todos los pasos calculados, o null si todavía sigue en curso.
     *
     * @return el resultado si la simulación ha finalizado, o null si todavía sigue en curso.
     */
    public SimulationResult getResult() {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Lógica principal del hilo. Ejecuta iterativamente los "ticks" de la simulación
     * hasta agotar el límite temporal, y guarda el estado del tablero de cada instante.
     */
    @Override
    public void run() { // Cuando se le llama, token > 0
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
