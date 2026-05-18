package com.tt1.simserver.logic;

import com.tt1.simserver.database.DBManagerInterface;
import com.tt1.simserver.model.Simulation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementa la funcionalidad de un gestor de simulaciones.
 */
public class SimulationEngineManager implements SimulationEngineManagerInterface {
    private final ExecutorService executorService;
    private final Map<LogicSimulation, SimulationEngine> activeSimulations;
    private final DBManagerInterface dbManager;

    /**
     * Construye un gestor de simulaciones vacío (sin simulaciones) asociado al gestor de base de datos
     * {@code dbManager}. El gestor de base de datos se encargará de la actualización de las simulaciones almacenadas y
     * de la persistencia sus resultados. Asume que el gestor de base de datos es no nulo.
     *
     * <p>Nota sobre implementación: cuidado, reserva un pool de hilos nuevo cada vez que se construye un nuevo
     * gestor de simulaciones ({@link Executors#newCachedThreadPool()}).</p>
     *
     * @param dbManager el gestor de base de datos.
     */
    public SimulationEngineManager(DBManagerInterface dbManager) {
        this.executorService = Executors.newCachedThreadPool();
        this.activeSimulations = new ConcurrentHashMap<>();
        this.dbManager = dbManager;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Notas sobre implementación: en cuanto a la concurrencia de simulaciones, corre todas las
     * simulaciones enviando un {@link SimulationEngine} a un pool de hilos reservado con
     * {@link Executors#newCachedThreadPool()}; y la función es {@code synchronized} para evitar problemas que puedan
     * surgir por estar empezar dos simulaciones a la vez.</p>
     */
    @Override
    public synchronized void startSimulation(LogicSimulation simulation) {
        // 1. Mandar correr la simulación
        SimulationEngine engine = new SimulationEngine(simulation, this, dbManager);
        executorService.submit(engine);

        // 2. Guardar la simulación en memoria
        activeSimulations.put(simulation, engine);
    }

    @Override
    public void removeSimulation(Simulation simulation) {
        activeSimulations.remove(simulation);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Notas sobre implementación: Apaga de forma limpia el pool de hilos. Los motores de simulación quedan
     * interrumpidos liberando los hilos y se eliminan las simulaciones activas.</p>
     */
    @Override
    public void shutdown() {
        executorService.shutdown();
        activeSimulations.clear();
    }
}