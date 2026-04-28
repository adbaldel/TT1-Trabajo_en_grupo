package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;

public interface Movable {

    /**
     * Regla ejecutada durante cada "tick" de la simulación encargada del desplazamiento.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     * Postcondición: la posición devuelta es la posición de la criatura tras moverse y es una de las casillas adyacentes
     * del tablero, o es null si la criatura no se ha movido.
     *
     * @param grid el tablero y sus casillas vacías adyacentes para evaluar destinos.
     * @return nueva posición de la entidad post-movimiento, o null si decide quedarse quieta o no puede moverse.
     */
    Position move(GridInterface grid);
}
