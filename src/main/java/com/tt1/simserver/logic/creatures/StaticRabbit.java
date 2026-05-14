package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.model.Position;

import java.util.Random;

/**
 * Implementa la funcionalidad de una criatura que nunca se mueve, pero se reproduce con una probabilidad definida.
 */
public class StaticRabbit extends LogicCreature {

    /**
     * Construye un conejo estático vivo con el id, nombre, color, aguante sin comida, probabilidad de reproducción,
     * posición y generador de números pseudo-aleatorios pasados como parámetros; y asociado al tablero
     * {@code simulationGrid}. Asume que id es no nulo y único para esta criatura; que el nombre y color no son nulos y
     * no blanks; que el número de ticks que aguanta sin comer antes de morirse de hambre {@code starvationThreshold} es
     * mayor o igual a cero; que la probabilidad de multiplicarse está entre 0.0 y 1.0 (1.0 incluido); que la posición
     * es no nula; que el tablero es no nulo y contiene a la criatura en la posición de la criatura; y que el generador
     * de números pseudo-aleatorios es no nulo.
     *
     * @param id                  el id de la criatura.
     * @param name                el nombre de la criatura.
     * @param color               el color de la criatura.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param multiplyProbability la probabilidad de reproducirse de la criatura.
     * @param position            la posición de la criatura.
     * @param simulationGrid      el tablero de simulación en el que está la criatura.
     * @param random              el generador de números pseudo-aleatorios.
     */
    public StaticRabbit(String id, String name, String color, int starvationThreshold, double multiplyProbability,
                        Position position,
                        SimulationGridInterface simulationGrid, Random random) {
        super(id, name, color, starvationThreshold,
                position, simulationGrid);
        moveBehaviour = new MoveNever();
        multiplyBehaviour = new MultiplyToEmptyAdjacentRandomly(multiplyProbability, random);
    }

    /**
     * Construye un conejo estático vivo con el id, nombre, color, aguante sin comida, probabilidad de reproducción,
     * posición y generador de números pseudo-aleatorios pasados como parámetros; y asociado a ningún tablero. Asume que
     * id es no nulo y único para esta criatura; que el nombre y color no son nulos y no blanks; que el número de ticks
     * que aguanta sin comer antes de morirse de hambre {@code starvationThreshold} es mayor o igual a cero; que la
     * probabilidad de multiplicarse está entre 0.0 y 1.0 (1.0 incluido); que la posición es no nula; y que el generador
     * de números pseudo-aleatorios es no nulo.
     *
     * @param id                  el id de la criatura.
     * @param name                el nombre de la criatura.
     * @param color               el color de la criatura.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param multiplyProbability la probabilidad de reproducirse de la criatura.
     * @param position            la posición de la criatura.
     * @param random              el generador de números pseudo-aleatorios.
     */
    public StaticRabbit(String id, String name, String color, int starvationThreshold, double multiplyProbability,
                        Position position, Random random) {
        super(id, name, color, starvationThreshold, position);
        moveBehaviour = new MoveNever();
        multiplyBehaviour = new MultiplyToEmptyAdjacentRandomly(multiplyProbability, random);
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
     * <p>Esta criatura se reproduce según el comportamiento {@link MultiplyToEmptyAdjacentRandomly#multiply()}.</p>
     */
    @Override
    public LogicCreatureInterface performMultiply() {
        return super.performMultiply();
    }
}