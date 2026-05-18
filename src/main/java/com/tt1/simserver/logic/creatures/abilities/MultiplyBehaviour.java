package com.tt1.simserver.logic.creatures.abilities;

import com.tt1.simserver.logic.creatures.LogicCreatureInterface;

/**
 * Define el comportamiento de reproducción de las criaturas.
 */
public interface MultiplyBehaviour {

    /**
     * Implementa un comportamiento de multiplicación/reproducción. Si se produce una reproducción devuelve la nueva
     * cría, en caso contrario devuelve {@code null}.
     *
     * @return la nueva cría si se ha producido una reproducción, o {@code null} en caso contrario.
     */
    LogicCreatureInterface multiply();
}