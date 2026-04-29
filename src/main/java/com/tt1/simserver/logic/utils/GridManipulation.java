package com.tt1.simserver.logic.utils;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para manejar la extracción y manipulación de datos sobre el tablero.
 */
public class GridManipulation {
    /**
     * Extrae una captura del estado actual del tablero y lo convierte en un diccionario asociativo.
     *
     * <p>Precondición: {@code grid} no es nulo y se encuentra correctamente inicializado.
     *
     * <p>Postcondición: Devuelve un mapa asociativo donde cada posición ocupada del tablero actúa como clave y el color de la criatura alojada actúa como valor. Las casillas vacías son ignoradas por completo en el mapa final.
     *
     * @param grid el tablero que se desea copiar o fotografiar.
     * @return un diccionario mapeando posiciones a los colores de las criaturas.
     */
    public static Map<Position, String> copyGridToMap(GridInterface grid) {
        Map<Position, String> map = new HashMap<>();
        Position position;
        CreatureInterface creature;

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