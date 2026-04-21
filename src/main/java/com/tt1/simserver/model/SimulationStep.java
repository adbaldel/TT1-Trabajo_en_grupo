package com.tt1.simserver.model;

import com.tt1.simserver.logic.Grid;

import java.util.Map;

import static com.tt1.simserver.logic.utils.GridManipulation.copyGridToMap;

/**
 * Representa una "fotografía" o captura de los datos de las posiciones de la cuadrícula
 * en un determinado "tick" temporal del motor de simulación.
 */
public class SimulationStep {
    private Map<Position, String> step;


    /**
     * Construye y guarda el instante del tablero extrayendo su estado mediante utilidades del Grid.
     * Precondición: grid es no nulo.
     *
     * @param grid el tablero que se desea capturar en este instante.
     */
    public SimulationStep(Grid grid) {
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
}