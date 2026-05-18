package com.tt1.simserver.logic.creatures.abilities;

import com.tt1.simserver.model.Position;

/**
 * Define el comportamiento de movimiento de las criaturas.
 */
public interface MoveBehaviour {

    /**
     * Implementa un comportamiento de movimiento. Si se produce un cambio de posición devuelve la nueva posición, en
     * caso contrario devuelve {@code null}.
     *
     * @return la nueva posición si se ha producido un movimiento, {@code null} en caso contrario.
     */
    Position move();
}