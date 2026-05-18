package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.model.Creature;
import com.tt1.simserver.model.Position;

/**
 * Implementa la funcionalidad de una criatura que nunca se mueve ni se reproduce.
 */
public class StaticCreature extends LogicCreature {

    /**
     * Construye una criatura estática viva con la criatura base, aguante sin comida y posición pasados como parámetros;
     * y asociada al tablero {@code simulationGrid}.. Asume que la criatura base es no nula; que el número de ticks que
     * aguanta sin comer antes de morirse de hambre {@code starvationThreshold} es mayor o igual a cero; que la posición
     * es no nula; y que el tablero es no nulo y contiene a la criatura en la posición de la criatura.
     *
     * @param creature            la criatura base.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param position            la posición de la criatura.
     * @param simulationGrid      el tablero de simulación en el que está la criatura.
     */
    public StaticCreature(Creature creature, int starvationThreshold, Position position,
                          SimulationGridInterface simulationGrid) {
        super(creature, starvationThreshold, position, simulationGrid);
        moveBehaviour = new MoveNever();
        multiplyBehaviour = new MultiplyNever();
    }

    /**
     * Construye una criatura estática viva con la criatura base, aguante sin comida y posición pasados como parámetros;
     * y asociada a ningún tablero. Asume que la criatura base es no nula; que el número de ticks que aguanta sin comer
     * antes de morirse de hambre {@code starvationThreshold} es mayor o igual a cero; y que la posición es no nula.
     *
     * @param creature            la criatura base.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param position            la posición de la criatura.
     */
    public StaticCreature(Creature creature, int starvationThreshold, Position position) {
        super(creature, starvationThreshold, position);
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