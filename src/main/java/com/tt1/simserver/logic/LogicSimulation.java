package com.tt1.simserver.logic;

import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationStep;
import com.tt1.simserver.model.User;

/**
 * Representa una simulación y almacena la información necesaria para correrla.
 */
public class LogicSimulation extends Simulation {
    private final int ticksToRun;
    private final SimulationGridInterface simulationGrid;

    /**
     * Construye una simulación lógica con el token, usuario, ticks a correr y tablero de simulación pasados cómo
     * parámetros. Además, añade como paso inicial el paso representado por el tablero de simulación pasado. Asume que
     * el token es único para esta simulación; que el usuario es no nulo; que los ticks a correr son mayores o igual a
     * cero; y que el tablero de simulación es no nulo.
     *
     * @param token          el token de la simulación.
     * @param user           el usuario asociado a la simulación.
     * @param ticksToRun     los ticks a correr en la simulación.
     * @param simulationGrid el tablero de simulación.
     */
    public LogicSimulation(int token, User user, int ticksToRun, SimulationGridInterface simulationGrid) {
        super(token, user, simulationGrid.getSize());
        this.ticksToRun = ticksToRun;
        this.simulationGrid = simulationGrid;

        addStep(SimulationStep.convertToSimulationStep(simulationGrid));
    }

    /**
     * Obtiene los ticks a correr de la simulación.
     *
     * @return los ticks a correr de la simulación.
     */
    public int getTicksToRun() {
        return ticksToRun;
    }

    /**
     * Corre un tick de simulación en el tablero asociado y guarda el resultado en un paso de simulación.
     */
    public void tick() {
        simulationGrid.tick();
        addStep(SimulationStep.convertToSimulationStep(simulationGrid));
    }
}
