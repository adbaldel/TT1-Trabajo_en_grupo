package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;

/**
 * Define la capacidad de una criatura para moverse entre las casillas del tablero.
 */
public interface Movable {

    /**
     * Calcula e intenta realizar un movimiento hacia una nueva casilla del tablero durante este turno.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura apuntando a la misma posición que tiene guardada.
     *
     * <p>Postcondición: Devuelve la nueva posición si la criatura logra moverse con éxito, o nulo si decide o se ve forzada a quedarse quieta.
     *
     * @param grid el tablero que expone las casillas adyacentes.
     * @return la posición de destino, o nulo si no se mueve.
     */
    Position move(GridInterface grid);
}
