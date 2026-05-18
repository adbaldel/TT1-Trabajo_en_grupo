package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.model.Creature;
import com.tt1.simserver.model.Position;

/**
 * Define la funcionalidad de una criatura.
 */
public interface LogicCreatureInterface {

    /**
     * Ver descripción {@link Creature#getId()}.
     *
     * @return ver return {@link Creature#getId()}.
     */
    String getId();

    /**
     * Ver descripción {@link Creature#getName()}.
     *
     * @return ver return {@link Creature#getName()}.
     */
    String getName();

    /**
     * Obtiene la posición de esta criatura.
     *
     * @return la posición de esta criatura.
     */
    Position getPosition();

    /**
     * Obtiene el tablero de simulación en el que está esta criatura.
     *
     * @return el tablero de simulación en el que está esta criatura.
     */
    SimulationGridInterface getSimulationGrid();

    /**
     * Cambia el tablero de simulación en el que está esta criatura por {@code simulationGrid}. Asume que la criatura no
     * está asociada a ningún tablero; y que el nuevo tablero es no nulo y contiene a la criatura en la posición de la
     * criatura.
     *
     * @param simulationGrid el nuevo tablero de la criatura.
     */
    void setSimulationGrid(SimulationGridInterface simulationGrid);

    /**
     * Obtiene el número de ticks que esta criatura aguanta sin comer antes de morirse.
     *
     * @return el número de ticks que esta criatura aguanta sin comer antes de morirse.
     */
    int getStarvationThreshold();

    /**
     * Comprueba si esta criatura está viva o no.
     *
     * @return cierto si esta criatura está viva, falso en caso contrario.
     */
    boolean isAlive();

    /**
     * Alimenta a esta criatura si en el tick actual de simulación del tablero asociado pasa por una casilla con comida.
     * Si la criatura supera su número de ticks máximos sin comer se muere. Asume que la criatura está asociada a un
     * tablero de simulación y que esta función se ejecuta después de moverse.
     */
    void performEat();

    /**
     * Esta criatura se mueve según su comportamiento de movimiento. Si cambia de posición devuelve la nueva posición,
     * en caso contrario devuelve {@code null}. Asume que la criatura está asociada a un tablero de simulación.
     *
     * @return la nueva posición de esta criatura si se ha movido, {@code null} en caso contrario.
     */
    Position performMove();

    /**
     * Esta criatura se multiplica/reproduce según su comportamiento de multiplicación/reproducción. Si tiene una cría
     * devuelve la nueva cría, en caso contrario devuelve {@code null}. Asume que la criatura está asociada a un tablero
     * de simulación.
     *
     * @return la nueva cría de esta criatura si se ha reproducido, o {@code null} en caso contrario.
     */
    LogicCreatureInterface performMultiply();
}
