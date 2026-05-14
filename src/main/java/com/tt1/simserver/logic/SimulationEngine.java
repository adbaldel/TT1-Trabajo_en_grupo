package com.tt1.simserver.logic;

import com.tt1.simserver.database.DBManagerInterface;

/**
 * Implementa la funcionalidad de un motor de simulaciones.
 */
public class SimulationEngine implements SimulationEngineInterface {
    private final LogicSimulation simulation;
    private final SimulationEngineManagerInterface manager;
    private final DBManagerInterface dbManager;

    /**
     * Construye un motor de simulación con la simulación lógica, gestor de simulaciones y gestor de base de datos
     * pasados cómo parámetros. Asume que la simulación lógica es no nula; el gestor de motor de simulaciones es no nulo
     * y gestiona la simulación pasada; el gestor de base de datos es no nulo y gestiona una base de datos en la que la
     * simulación está almacenada.
     *
     * @param simulation la simulación lógica sobre la que se van a correr los ticks.
     * @param manager    el gestor de simulaciones que gestiona la simulación.
     * @param dbManager  el gestor de base de datos que almacena la simulación.
     */
    public SimulationEngine(LogicSimulation simulation, SimulationEngineManagerInterface manager,
                            DBManagerInterface dbManager) {
        this.simulation = simulation;
        this.manager = manager;
        this.dbManager = dbManager;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Notas sobre implementación: primero actualiza el estado de la simulación a corriendo, tanto en memoria con
     * en la persistencia; después corre {@code ticksToRun} ticks de simulación partiendo del paso final almacenado en
     * la simulación, añadiendo cada nuevo paso a la simulación; y finalmente actualiza el estado de la simulación a
     * acabada, tanto en memoria como en la base de datos, guarda el resultado en la base de datos y elimina la
     * simulación del gestor de simulaciones. Si en algún momento el hilo es interrumpido se para la simulación en el
     * siguiente tick, se actualiza el estado de la simulación a interrumpido, tanto en memoria como en la base de
     * datos, y se elimina la simulación del gestor de simulaciones.</p>
     */
    @Override
    public void run() {
        boolean interrupted = false;
        // Update simulation status to running
        simulation.startSimulation();
        dbManager.updateSimulationStatus(simulation);

        // Run simulation
        // NOTE: Simulation already has the initial state added.
        for (int i = 0; i < simulation.getTicksToRun(); i++) {
            if (Thread.interrupted()) {
                interrupted = true;
                break;
            }
            simulation.tick();
        }

        if (!interrupted) {
            // Update simulation status to complete
            simulation.completeSimulation();
            dbManager.saveSimulationResult(simulation);

            // Remove simulation from memory
            manager.removeSimulation(simulation);
        } else {
            // Update simulation status to interrupted
            simulation.interruptSimulation();
            dbManager.updateSimulationStatus(simulation);

            // Remove simulation from memory
            manager.removeSimulation(simulation);
        }
    }
}