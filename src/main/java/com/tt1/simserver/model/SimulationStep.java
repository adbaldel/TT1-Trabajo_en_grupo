package com.tt1.simserver.model;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.creatures.CreatureInterface;

import java.util.*;

/**
 * Almacena una captura o fotografía de todas las posiciones y criaturas del tablero en un único paso de la simulación.
 */
public class SimulationStep {
    private final Map<Position, String> step;
    private final int size;

    /**
     * Construye un paso de simulación vacío, sin criaturas.
     *
     * <p>Precondición: El tamaño pasado es mayor que cero y se corresponde con el tamaño del tablero que se va a representar.
     *
     * <p>Postcondición: Se crea un paso de simulación vacío preparado para ser una captura de un tablero de tamaño {@code size}.
     */
    private SimulationStep(int size) {
        step = new HashMap<>();
        this.size = size;
    }


    /**
     * Obtiene el tamaño del lado del tablero cuyo paso se representa.
     *
     * <p>Precondición: El paso de simulación está inicializado.
     *
     * <p>Postcondición: Devuelve el número de filas (o columnas) del tablero cuyo paso se representa.
     *
     * @return el tamaño del tablero cuyo paso se representa.
     */
    public int getSize() {
        return size;
    }

    /**
     * Consulta el color de la criatura que ocupaba una casilla específica durante este paso.
     *
     * <p>Precondición: {@code position} no es nula.
     *
     * <p>Postcondición: Devuelve la cadena de texto con el color de la criatura alojada en esa casilla. Devuelve nulo si la casilla estaba vacía en este turno.
     *
     * @param position las coordenadas de la casilla a consultar.
     * @return el color de la criatura, o nulo si no había ninguna.
     */
    public String getColor(Position position) {
        return step.get(position);
    }

    /**
     * Obtiene el número de criaturas que hay en la simulación en este paso.
     *
     * <p>Precondición: El paso de simulación está inicializado.
     *
     * <p>Postcondición: Devuelve el número de criaturas que hay en la simulación en este paso.
     *
     * @return número de criaturas que hay en la simulación en este paso.
     */
    public int getNumberOfCreatures() {
        return step.size();
    }

    /**
     * Obtiene una colección con las posiciones de todas las criaturas que hay en la simulación en este paso.
     *
     * <p>Precondición: El paso de simulación está inicializado.
     *
     * <p>Postcondición: Devuelve una colección con las posiciones de todas las criaturas que hay en la simulación en este paso.
     *
     * @return una colección con las posiciones de todas las criaturas que hay en la simulación en este paso.
     */
    public Collection<Position> getNonEmptyPositions() {
        return step.keySet();
    }

    /**
     * Compara este paso con otro para comprobar si el tablero tenía la misma disposición.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero solo si ambas capturas tienen la misma cantidad de criaturas, y todas ocupan exactamente las mismas casillas con los mismos colores. Devuelve falso en caso contrario.
     *
     * @param o el objeto a comparar con esta captura.
     * @return verdadero si tienen los mismos colores en las mismas posiciones, falso si difieren.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimulationStep simulationStep = (SimulationStep) o;

        if (step.size() != simulationStep.step.size()) return false;

        for (Map.Entry<Position, String> entry : step.entrySet()) {
            if (!entry.getValue().equals(simulationStep.getColor(entry.getKey()))) return false;
        }

        return true;
    }

    /**
     * Construye una captura extrayendo los datos del tablero en este instante.
     *
     * <p>Precondición: {@code grid} no es nulo.
     *
     * <p>Postcondición: El estado actual del tablero es copiado en memoria, asociando de forma inmutable el color de cada criatura con la casilla exacta que ocupa.
     *
     * @param grid el tablero que se va a fotografiar.
     * @return una copia del estado actual del tablero en forma de paso de simulación.
     */
    public static SimulationStep convertToSimulationStep(GridInterface grid) {
        SimulationStep simulationStep = new SimulationStep(grid.getSize());
        Position position;
        CreatureInterface creature;

        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                position = new Position(x, y);

                creature = grid.getCreature(position);

                if (creature != null) {
                    simulationStep.step.put(position, creature.getColor());
                }
            }
        }

        return simulationStep;
    }
}