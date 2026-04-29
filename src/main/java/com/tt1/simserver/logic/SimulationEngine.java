package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStep;

/**
 * Motor encargado de ejecutar la lógica interna de una simulación.
 * Implementa {@link Runnable} para permitir su ejecución en un hilo de procesamiento independiente.
 */
public class SimulationEngine implements SimulationEngineInterface {
    private final GridInterface grid;
    private final int maxSteps;
    private final SimulationResult result;
    private boolean done;


    /**
     * Inicializa el motor de simulación con su tablero y su límite temporal.
     * Precondición: initialGrid no es nulo, maxSteps > 0.
     *
     * @param initialGrid el estado inicial del tablero a simular.
     * @param maxSteps    el número máximo de pasos (segundos) que dura la simulación.
     */
    public SimulationEngine(GridInterface initialGrid, int maxSteps) {
        this.grid = initialGrid;
        this.maxSteps = maxSteps;
        result = new SimulationResult();
        done = false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean isDone() {
        return done;
    }

    /**
     * Establece de forma sincronizada el estado de finalización de la simulación.
     *
     * @param done cierto para marcar la simulación como completada.
     */
    private synchronized void setDone(boolean done) {
        this.done = done;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimulationResult getResult() {
        if (!isDone()) {
            return null;
        }

        return result;
    }

    /**
     * Lógica principal del hilo. Ejecuta iterativamente los "ticks" de la simulación
     * hasta agotar el límite temporal, y guarda el estado del tablero de cada instante.
     */
    @Override
    public void run() { // Cuando se le llama, token > 0
        result.addStep(new SimulationStep(grid));
        for (int second = 0; second < maxSteps; second++) {
            grid.tick();
            result.addStep(new SimulationStep(grid));
        }

        setDone(true);
    }
}
