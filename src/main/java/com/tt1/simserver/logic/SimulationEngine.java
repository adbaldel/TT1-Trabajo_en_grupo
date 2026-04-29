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
     * Inicializa el motor de simulación definiendo su tablero y su límite de duración.
     *
     * <p>Precondición: {@code initialGrid} no es nulo y {@code maxSteps} es mayor que cero.
     *
     * <p>Postcondición: Prepara la ejecución reteniendo el tablero inicial y fijando el total de turnos a procesar. El estado inicial se marca como no terminado.
     *
     * @param initialGrid el estado inicial del tablero configurado a simular.
     * @param maxSteps    el número máximo de turnos de vida de la simulación.
     */
    public SimulationEngine(GridInterface initialGrid, int maxSteps) {
        this.grid = initialGrid;
        this.maxSteps = maxSteps;
        result = new SimulationResult();
        done = false;
    }

    /**
     * Comprueba de forma segura y sincronizada si el motor de la simulación ha terminado.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si la simulación completó la ejecución de todos los turnos preestablecidos. Devuelve falso mientras no ha arrancado o sigue procesando.
     *
     * @return el estado actual de finalización.
     */
    @Override
    public synchronized boolean isDone() {
        return done;
    }

    /**
     * Establece de forma sincronizada el estado de finalización del motor.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Actualiza internamente la bandera de finalización al valor indicado.
     *
     * @param done verdadero para registrar que la simulación ha concluido.
     */
    private synchronized void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Extrae el objeto que empaqueta los historiales del proceso simulado.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el contenedor histórico poblado con las disposiciones del tablero solo si la simulación ya ha terminado. Devuelve nulo para evitar accesos tempranos si no ha finalizado.
     *
     * @return los datos con el resultado de la simulación o nulo si sigue en ejecución.
     */
    @Override
    public SimulationResult getResult() {
        if (!isDone()) {
            return null;
        }

        return result;
    }

    /**
     * Lógica principal del cálculo continuo a lanzar desde un hilo.
     *
     * <p>Precondición: El tablero asociado en el constructor está disponible y es válido.
     *
     * <p>Postcondición: Captura y empuja al historial el tablero de origen. A continuación obliga iterativamente al tablero a avanzar exactamente los turnos solicitados en {@code maxSteps}, almacenando el estado interno tras resolver cada turno. Al agotar el límite temporal, se auto marca de forma sincronizada como terminado.
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