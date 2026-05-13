package com.tt1.simserver.model;

import com.tt1.simserver.logic.SimulationGridInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa un paso de simulación.
 */
public class SimulationStep {
    private final Map<Position, Creature> step;
    private final int gridSize;

    /**
     * Crea un paso de simulación vacío (sin criaturas) para una simulación con tamaño de tablero {@code gridSize}.
     * Asume que el tamaño del tablero es mayor que cero.
     *
     * @param gridSize el tamaño del tablero.
     */
    public SimulationStep(int gridSize) {
        step = new HashMap<>();
        this.gridSize = gridSize;
    }

    /**
     * Construye un paso de simulación con las criaturas en las posiciones dadas por el mapa {@code simulationStep} para
     * una simulación con tamaño de tablero {@code gridSize}. Asume que el tamaño del tablero es mayor que cero; que el
     * mapa es no nulo; y que el tablero es suficientemente grande como para alojar a todas las criaturas dadas en el
     * mapa en las posiciones indicadas en el mapa. Cuidado, el paso de simulación pasa a estar representada por el mapa
     * pasado, cualquier modificación del mapa pasado afectará al paso de simulación y viceversa.
     *
     * @param gridSize       el tamaño del tablero.
     * @param simulationStep el mapa que da las posiciones y sus criaturas.
     */
    public SimulationStep(int gridSize, Map<Position, Creature> simulationStep) {
        this.step = new HashMap<>(simulationStep);
        this.gridSize = gridSize;
    }

    /**
     * Crea un paso de simulación que representa el estado actual de un tablero de simulación. Asume que el tablero no
     * es nulo y que las criaturas no se mueven, reproducen, ni mueren durante la ejecución de esta función.
     *
     * @param grid el tablero a copiar.
     * @return el paso de simulación que representa el estado actual del tablero.
     */
    public static SimulationStep convertToSimulationStep(SimulationGridInterface grid) {
        SimulationStep simulationStep = new SimulationStep(grid.getSize());

        for (Position position : grid.getNonEmptyPositions()) {
            simulationStep.step.put(position, new Creature(grid.getCreatureAt(position)));
        }

        return simulationStep;
    }

    /**
     * Obtiene el tamaño del tablero de la simulación asociada a este paso.
     *
     * @return el tamaño del tablero.
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Obtiene la criatura en la posición {@code position} en este paso de simulación.
     *
     * @param position la posición de la criatura.
     * @return la criatura en la posición indicada, o {@code null} si no hay una criatura en dicha posición.
     */
    public Creature getCreatureAt(Position position) {
        return step.get(position);
    }

    /**
     * Obtiene el número de criaturas en este paso de simulación.
     *
     * @return el número de criaturas en este paso de simulación.
     */
    public int getNumberOfCreatures() {
        return step.size();
    }

    /**
     * Obtiene las posiciones no vacías (con criaturas) en este paso de simulación.
     *
     * @return las posiciones no vacías en este paso de simulación.
     */
    public Collection<Position> getNonEmptyPositions() {
        return step.keySet();
    }

    /**
     * Obtiene las criaturas en este paso de simulación.
     *
     * @return las criaturas en este paso de simulación.
     */
    public Collection<Creature> getCreatures() {
        return step.values();
    }

    /**
     * Genera una representación de este paso de simulación como una cadena en formato csv siguiendo el siguiente
     * patrón: por cada posición no vacía se añade una línea con los datos tick,x,y,id siendo tick {@code tick}, x e y
     * las componentes x e y de la posición, e id el id de la criatura que se encuentra en dicha posición en este paso.
     *
     * @param tick el tick que representa este paso de simulación.
     * @return la representación en formato csv de este paso de simulación.
     */
    public String toCsvStringUsingCreatureId(int tick) {
        StringBuilder dataBuilder = new StringBuilder();

        for (Position p : getNonEmptyPositions()) {
            dataBuilder.append(tick).append(",")
                    .append(p.y()).append(",")
                    .append(p.x()).append(",")
                    .append(getCreatureAt(p).getId()).append("\n");
        }
        return dataBuilder.toString().trim();
    }

    /**
     * Genera una representación de este paso de simulación como una cadena en formato csv siguiendo el siguiente
     * patrón: por cada posición no vacía se añade una línea con los datos tick,x,y,name siendo tick {@code tick}, x e y
     * las componentes x e y de la posición, y name el nombre de la criatura que se encuentra en dicha posición en este
     * paso.
     *
     * @param tick el tick que representa este paso de simulación.
     * @return la representación en formato csv de este paso de simulación.
     */
    public String toCsvStringUsingCreatureName(int tick) {
        StringBuilder dataBuilder = new StringBuilder();

        for (Position p : getNonEmptyPositions()) {
            dataBuilder.append(tick).append(",")
                    .append(p.y()).append(",")
                    .append(p.x()).append(",")
                    .append(getCreatureAt(p).getName()).append("\n");
        }
        return dataBuilder.toString().trim();
    }

    /**
     * Genera una representación de este paso de simulación como una cadena en formato csv siguiendo el siguiente
     * patrón: por cada posición no vacía se añade una línea con los datos tick,x,y,color siendo tick {@code tick}, x e
     * y las componentes x e y de la posición, y color el color de la criatura que se encuentra en dicha posición en
     * este paso.
     *
     * @param tick el tick que representa este paso de simulación.
     * @return la representación en formato csv de este paso de simulación.
     */
    public String toCsvStringUsingCreatureColor(int tick) {
        StringBuilder dataBuilder = new StringBuilder();

        for (Position p : getNonEmptyPositions()) {
            dataBuilder.append(tick).append(",")
                    .append(p.y()).append(",")
                    .append(p.x()).append(",")
                    .append(getCreatureAt(p).getColor()).append("\n");
        }
        return dataBuilder.toString().trim();
    }

    /**
     * Comprueba si este paso de simulación es igual al objeto {@code o}. Devuelve cierto si el objeto es un paso de
     * simulación con criaturas iguales asociados a las mismas posiciones.
     *
     * @param o el objeto a comparar.
     * @return cierto si este paso de simulación es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationStep simulationStep = (SimulationStep) o;

        if (step.size() != simulationStep.step.size()) return false;

        for (Map.Entry<Position, Creature> entry : step.entrySet()) {
            if (!getCreatureAt(entry.getKey()).equals(simulationStep.getCreatureAt(entry.getKey()))) return false;
        }

        return true;
    }
}