package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;

public interface Multipliable {

    /**
     * Regla ejecutada para evaluar una posible clonación o procreación en casillas aledañas.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid el tablero para evaluar la disponibilidad de casillas cercanas de nacimiento.
     * @return una nueva instancia derivada de esta criatura de tener éxito, o null en caso contrario.
     */
    CreatureInterface multiply(GridInterface grid);
}
