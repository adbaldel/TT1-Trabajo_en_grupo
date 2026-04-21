package com.tt1.simserver.logic.utils;

import com.tt1.simserver.model.creatures.Creature;
import com.tt1.simserver.logic.Grid;
import com.tt1.simserver.model.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para manejar la extracción y manipulación de datos sobre el tablero.
 */
public class GridManipulation {
    /**
     * Extrae un volcado del tablero actual convirtiéndolo en un mapa asociativo de Coordenada -> Color.
     * Permite tomar "fotografías" del estado de la cuadrícula en un determinado segundo (tick).
     * Precondición: grid es no nulo.
     *
     * @param grid el tablero que se desea copiar.
     * @return un diccionario mapeando posiciones a los respectivos colores de las criaturas que la ocupan.
     */
    public static Map<Position, String> copyGridToMap(Grid grid) {
        Map<Position, String> map = new HashMap<>();
        Position position;
        Creature creature;

        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                position = new Position(x, y);

                creature = grid.getCreature(position);

                if (creature != null) {
                    map.put(position, creature.getColor());
                }
            }
        }

        return map;
    }
}
