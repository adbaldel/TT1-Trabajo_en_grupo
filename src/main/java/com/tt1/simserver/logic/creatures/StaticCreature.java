package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.model.Position;

/**
 * Implementa la funcionalidad de una criatura que nunca se mueve ni se reproduce.
 */
public class StaticCreature extends LogicCreature {

    /**
     * Construye una criatura estática viva con el id, nombre, color, aguante sin comida y posición pasados como
     * parámetros; y asociada al tablero {@code simulationGrid}.. Asume que id es no nulo y único para esta criatura;
     * que el nombre y color no son nulos y no blanks; que el número de ticks que aguanta sin comer antes de morirse de
     * hambre {@code starvationThreshold} es mayor o igual a cero; que la posición es no nula; y que el tablero es no
     * nulo y contiene a la criatura en la posición de la criatura.
     *
     * @param id                  el id de la criatura.
     * @param name                el nombre de la criatura.
     * @param color               el color de la criatura.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param position            la posición de la criatura.
     * @param simulationGrid      el tablero de simulación en el que está la criatura.
     */
    public StaticCreature(String id, String name, String color, int starvationThreshold, Position position,
                          SimulationGridInterface simulationGrid) {
        super(id, name, color, starvationThreshold, position, simulationGrid);
        moveBehaviour = new MoveNever();
        multiplyBehaviour = new MultiplyNever();
    }

    /**
     * Construye una criatura estática viva con el id, nombre, color, aguante sin comida y posición pasados como
     * parámetros; y asociada a ningún tablero. Asume que id es no nulo y único para esta criatura; que el nombre y
     * color no son nulos y no blanks; que el número de ticks que aguanta sin comer antes de morirse de hambre
     * {@code starvationThreshold} es mayor o igual a cero; y que la posición es no nula.
     *
     * @param id                  el id de la criatura.
     * @param name                el nombre de la criatura.
     * @param color               el color de la criatura.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param position            la posición de la criatura.
     */
    public StaticCreature(String id, String name, String color, int starvationThreshold, Position position) {
        super(id, name, color, starvationThreshold, position);
        moveBehaviour = new MoveNever();
        multiplyBehaviour = new MultiplyNever();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Esta se mueve según el comportamiento {@link MoveNever#move()}.</p>
     */
    @Override
    public Position performMove() {
        return super.performMove();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Esta criatura se reproduce según el comportamiento {@link MultiplyNever#multiply()}.</p>
     */
    @Override
    public LogicCreatureInterface performMultiply() {
        return super.performMultiply();
    }
}