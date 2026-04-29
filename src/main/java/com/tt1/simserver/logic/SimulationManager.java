package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gestor encargado de coordinar la ejecución asíncrona de una simulación específica.
 * Proporciona hilos desde un pull para realizar el cálculo del motor sin bloquear el servidor web,
 * a la vez que gestiona el estado y el token identificador de esta ejecución.
 */
public class SimulationManager implements SimulationManagerInterface {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    /**
     * Contador estático para asignar los identificadores de token globales.
     * ¡CUIDADO! Varios métodos accediendo y modificando esta variable a la vez en varios hilos
     * podría generar problemas de condición de carrera. Requiere AtomicInteger o bloqueo sincronizado.
     */
    private static int numberOfSimulations = 0;
    private final SimulationEngineInterface simulationEngine;
    private int token;
    private SimulationStatus status;


    /**
     * Inicializa el gestor, preparando un motor de simulación para ser lanzado.
     * Precondición: simulationEngine no es nulo.
     *
     * @param simulationEngine el motor lógico con el tablero a simular.
     */
    public SimulationManager(SimulationEngineInterface simulationEngine) {
        this.token = -1;
        this.simulationEngine = simulationEngine;
        this.status = SimulationStatus.PENDING;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getToken() {
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimulationStatus getSimulationStatus() {
        updateSimulationStatus();

        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSimulationStatus() {
        if (simulationEngine.isDone()) {
            status = SimulationStatus.COMPLETED;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SimulationResult getSimulationResult() {
        return getSimulationStatus() == SimulationStatus.COMPLETED ? simulationEngine.getResult() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int startSimulation() {
        if (getToken() >= 0) {
            return getToken();
        }

        token = numberOfSimulations++;

        pool.submit(simulationEngine);

        status = SimulationStatus.RUNNING;

        return token;
    }
}
