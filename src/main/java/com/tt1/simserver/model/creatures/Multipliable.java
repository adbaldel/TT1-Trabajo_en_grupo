package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;

/**
 * Define la capacidad de una criatura para reproducirse y generar nuevas crías en el tablero.
 */
public interface Multipliable {

    /**
     * Intenta ejecutar la reproducción de la especie creando una cría en una casilla contigua durante este turno.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura apuntando a la misma posición que tiene guardada.
     *
     * <p>Postcondición: Devuelve una nueva instancia de criatura (cría) posicionada en el tablero si la reproducción tiene éxito, o nulo si fracasa.
     *
     * @param grid el tablero para evaluar el espacio y depositar la cría.
     * @return la nueva cría generada, o nulo si no se reproduce.
     */
    CreatureInterface multiply(GridInterface grid);
}
