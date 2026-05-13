package com.tt1.simserver.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa los datos de una simulación.
 */
public class SimulationData {
    private final List<SimulationStep> simulationSteps;
    private final int gridSize;

    /**
     * Construye unos datos vacíos para una simulación con un tamaño de tablero {@code gridSize}. Asume que el tamaño
     * del tablero es mayor que cero.
     *
     * @param gridSize el tamaño del tablero.
     */
    public SimulationData(int gridSize) {
        simulationSteps = new ArrayList<>();
        this.gridSize = gridSize;
    }

    /**
     * Obtiene el paso de simulación, de la simulación que representan estos datos, en el tick {@code tick}. Asume que
     * tick es mayor o igual a cero y que es menor estricto que el número de ticks en los datos de esta simulación.
     *
     * @param tick el tick correspondiente al paso solicitado.
     * @return el paso de simulación en dicho tick.
     */
    public SimulationStep getSimulationStepAt(int tick) {
        return simulationSteps.get(tick);
    }

    /**
     * Obtiene el tamaño del tablero de la simulación que representan estos datos.
     *
     * @return el tamaño del tablero.
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Obtiene el número de ticks de la simulación que representan estos datos.
     *
     * @return el número de ticks.
     */
    public int getTicks() {
        return simulationSteps.size();
    }

    /**
     * Añade el paso dado como último paso en estos datos de simulación.
     *
     * @param simulationStep el paso de simulación a añadir.
     */
    public void addStep(SimulationStep simulationStep) {
        simulationSteps.add(simulationStep);
    }

    /**
     * Genera una representación de estos datos de simulación como una cadena en formato csv siguiendo el siguiente
     * patrón: la primera línea indica el tamaño del tablero, y después por cada tick se añade el paso de simulación
     * correspondiente representado en formato csv según {@link SimulationStep#toCsvStringUsingCreatureId(int)}.
     *
     * @return la representación en formato csv de estos datos de simulación.
     */
    public String toCsvStringUsingCreatureId() {
        StringBuilder dataBuilder = new StringBuilder();
        SimulationStep step;

        // Guardamos la primera línea con el tamaño base inferido del tablero
        dataBuilder.append(gridSize).append("\n");

        // Construimos las líneas formato "tick,x,y,id"
        for (int tick = 0; tick < getTicks(); tick++) {
            step = getSimulationStepAt(tick);
            dataBuilder.append(step.toCsvStringUsingCreatureId(tick)).append("\n");
        }
        return dataBuilder.toString().trim();
    }

    /**
     * Genera una representación de estos datos de simulación como una cadena en formato csv siguiendo el siguiente
     * patrón: la primera línea indica el tamaño del tablero, y después por cada tick se añade el paso de simulación
     * correspondiente representado en formato csv según {@link SimulationStep#toCsvStringUsingCreatureName(int)}.
     *
     * @return la representación en formato csv de estos datos de simulación.
     */
    public String toCsvStringUsingCreatureName() {
        StringBuilder dataBuilder = new StringBuilder();
        SimulationStep step;

        // Guardamos la primera línea con el tamaño base inferido del tablero
        dataBuilder.append(gridSize).append("\n");

        // Construimos las líneas formato "tick,x,y,name"
        for (int tick = 0; tick < getTicks(); tick++) {
            step = getSimulationStepAt(tick);
            dataBuilder.append(step.toCsvStringUsingCreatureName(tick)).append("\n");
        }
        return dataBuilder.toString().trim();
    }

    /**
     * Genera una representación de estos datos de simulación como una cadena en formato csv siguiendo el siguiente
     * patrón: la primera línea indica el tamaño del tablero, y después por cada tick se añade el paso de simulación
     * correspondiente representado en formato csv según {@link SimulationStep#toCsvStringUsingCreatureColor(int)}.
     *
     * @return la representación en formato csv de estos datos de simulación.
     */
    public String toCsvStringUsingCreatureColor() {
        StringBuilder dataBuilder = new StringBuilder();
        SimulationStep step;

        // Guardamos la primera línea con el tamaño base inferido del tablero
        dataBuilder.append(gridSize).append("\n");

        // Construimos las líneas formato "tick,x,y,color"
        for (int tick = 0; tick < getTicks(); tick++) {
            step = getSimulationStepAt(tick);
            dataBuilder.append(step.toCsvStringUsingCreatureColor(tick)).append("\n");
        }
        return dataBuilder.toString().trim();
    }

    /**
     * Comprueba si estos datos de simulación son iguales al objeto {@code o}. Devuelve cierto si el objeto son datos de
     * simulación con pasos iguales asociados a los mismos ticks.
     *
     * @param o el objeto a comparar.
     * @return cierto si estos datos de simulación son iguales al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationData simulationData = (SimulationData) o;

        if (getTicks() != simulationData.getTicks()) return false;

        for (int tick = 0; tick < getTicks(); tick++) {
            if (!getSimulationStepAt(tick).equals(simulationData.getSimulationStepAt(tick))) return false;
        }

        return true;
    }
}