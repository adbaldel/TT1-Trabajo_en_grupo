package com.tt1.simserver.model;

import com.tt1.simserver.logic.GridInterface;

import java.util.Map;

import static com.tt1.simserver.logic.utils.GridManipulation.copyGridToMap;

/**
 * Representa una "fotografía" o captura de los datos de las posiciones de la cuadrícula
 * en un determinado "tick" temporal del motor de simulación.
 */
public class SimulationStep {
    private final Map<Position, String> step;


    /**
     * Construye y guarda el instante del tablero extrayendo su estado mediante utilidades del Grid.
     * Precondición: grid es no nulo.
     *
     * @param grid el tablero que se desea capturar en este instante.
     */
    public SimulationStep(GridInterface grid) {
        step = copyGridToMap(grid);
    }

    /**
     *
     * Recupera el color identificativo de la entidad localizada en las coordenadas provistas en ese instante.
     * Precondición: position es no nulo.
     *
     * @param position coordenadas de la cuadrícula consultadas.
     * @return una cadena con el color del habitante, o null si la casilla estaba vacía.
     */
    public String getColor(Position position) {
        return step.get(position);
    }

    /**
     * Compara este objeto con otro para comprobar si son iguales basándose en sus atributos.
     *
     * @param o el objeto de referencia con el cual comparar.
     * @return cierto si los objetos son pasos de simulación con los mismos colores en las mismas posiciones, falso en caso contrario.
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