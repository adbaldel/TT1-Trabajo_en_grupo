package com.tt1.simserver.model;

import com.tt1.simserver.logic.GridInterface;

import java.util.Map;

import static com.tt1.simserver.logic.utils.GridManipulation.copyGridToMap;

/**
 * Almacena una captura o fotografía de todas las posiciones y criaturas del tablero en un único paso de la simulación.
 */
public class SimulationStep {
    private final Map<Position, String> step;

    /**
     * Construye una captura extrayendo los datos del tablero en este instante.
     *
     * <p>Precondición: {@code grid} no es nulo.
     *
     * <p>Postcondición: El estado actual del tablero es copiado en memoria, asociando de forma inmutable el color de cada criatura con la casilla exacta que ocupa.
     *
     * @param grid el tablero que se va a fotografiar.
     */
    public SimulationStep(GridInterface grid) {
        step = copyGridToMap(grid);
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
}