package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.logic.creatures.abilities.MoveBehaviour;
import com.tt1.simserver.logic.creatures.abilities.MultiplyBehaviour;
import com.tt1.simserver.model.Creature;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.utils.RandomUtil;

import java.util.Collection;
import java.util.Random;
import java.util.UUID;

/**
 * Implementa la funcionalidad de una criatura.
 */
public abstract class LogicCreature extends Creature implements LogicCreatureInterface {
    private final int starvationThreshold;
    /**
     * El número de ticks que esta criatura aguanta sin comer antes de morirse.
     */
    protected int ticksUntilStarvation;
    /**
     * Estado de vida de esta criatura.
     */
    protected boolean alive;
    /**
     * Comportamiento de movimiento de esta criatura.
     */
    protected MoveBehaviour moveBehaviour;
    /**
     * Comportamiento de reproducción de esta criatura.
     */
    protected MultiplyBehaviour multiplyBehaviour;
    private Position position;
    private Position previousPosition;
    private SimulationGridInterface simulationGrid;

    /**
     * Define cómo se construye la parte común de las criaturas con funcionalidad. La criatura se construye viva, con la
     * criatura base, aguante sin comida y posición pasadas como parámetros; sin comportamiento de movimiento ni de
     * reproducción; y asociada al tablero {@code simulationGrid}. Asume que la criatura base es no nula; que el número
     * de ticks que aguanta sin comer antes de morirse de hambre {@code starvationThreshold} es mayor o igual a cero;
     * que la posición es no nula; y que el tablero es no nulo y contiene a la criatura en la posición de la criatura.
     *
     * @param creature            la criatura base.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param position            la posición de la criatura.
     * @param simulationGrid      el tablero de simulación en el que está la criatura.
     */
    protected LogicCreature(Creature creature, int starvationThreshold, Position position,
                            SimulationGridInterface simulationGrid) {
        super(creature);
        this.starvationThreshold = starvationThreshold;
        ticksUntilStarvation = starvationThreshold;
        alive = true;
        moveBehaviour = null;
        multiplyBehaviour = null;
        this.position = position;
        this.previousPosition = position;
        this.simulationGrid = simulationGrid;
    }

    /**
     * Define cómo se construye la parte común de las criaturas con funcionalidad. La criatura se construye viva, con la
     * criatura base, aguante sin comida y posición pasadas como parámetros; sin comportamiento de movimiento ni
     * reproducción; y asociada a ningún tablero. Asume que la criatura base es no nula; que el número de ticks que
     * aguanta sin comer antes de morirse de hambre {@code starvationThreshold} es mayor o igual a cero; y que la
     * posición es no nula.
     *
     * @param creature            la criatura base.
     * @param starvationThreshold el número de ticks que la criatura aguanta sin comer antes de morirse.
     * @param position            la posición de la criatura.
     */
    protected LogicCreature(Creature creature, int starvationThreshold, Position position) {
        this(creature, starvationThreshold, position, null);
    }

    @Override
    public Position getPosition() {
        return new Position(position.x(), position.y());
    }

    @Override
    public SimulationGridInterface getSimulationGrid() {
        return simulationGrid;
    }

    @Override
    public void setSimulationGrid(SimulationGridInterface simulationGrid) {
        this.simulationGrid = simulationGrid;
    }

    @Override
    public int getStarvationThreshold() {
        return starvationThreshold;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void performEat() {
        if (getSimulationGrid().hasFood(previousPosition) || getSimulationGrid().hasFood(getPosition())) {
            ticksUntilStarvation = starvationThreshold;
        } else {
            ticksUntilStarvation--;

            if (ticksUntilStarvation < 0) {
                alive = false;
            }
        }
    }

    @Override
    public Position performMove() {
        previousPosition = getPosition();
        return moveBehaviour.move();
    }

    @Override
    public LogicCreatureInterface performMultiply() {
        return multiplyBehaviour.multiply();
    }

    /**
     * Comprueba si esta criatura lógica es igual al objeto {@code o}. Devuelve cierto si el objeto es una criatura
     * lógica con el mismo id.
     *
     * @param o el objeto a comparar.
     * @return cierto si esta criatura lógica es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogicCreature creature = (LogicCreature) o;
        return getId().equals(creature.getId());
    }

    /**
     * Implementa el comportamiento de movimiento de las criaturas que nunca se mueven.
     */
    protected class MoveNever implements MoveBehaviour {

        /**
         * {@inheritDoc}
         *
         * <p>Notas sobre implementación: nunca se mueve, siempre devuelve {@code null}.</p>
         */
        @Override
        public Position move() {
            return null;
        }
    }

    /**
     * Implementa el comportamiento de movimiento de las criaturas que se mueven a una casilla adyacente con una
     * determinada probabilidad.
     */
    protected class MoveToEmptyAdjacentRandomly implements MoveBehaviour {
        private final double moveProbability;
        private final Random random;

        /**
         * Construye una implementación del comportamiento de movimiento pseudo-aleatorio a casillas adyacentes asociada
         * a esta criatura con la probabilidad de moverse {@code moveProbability} y el generador de números
         * pseudo-aleatorios {@code random}. Asume que la probabilidad de moverse está entre 0.0 y 1.0 (1.0 incluido) y
         * que el generador de números pseudo-aleatorios es no nulo.
         *
         * @param moveProbability la probabilidad de moverse.
         * @param random          el generador de números pseudo-aleatorios.
         */
        public MoveToEmptyAdjacentRandomly(double moveProbability, Random random) {
            this.moveProbability = moveProbability;
            this.random = random;
        }

        /**
         * {@inheritDoc}
         *
         * <p>Notas sobre implementación: si hay alguna casilla adyacente libre se mueve con la probabilidad definida
         * para esta criatura a una de las casillas adyacentes libres. Todas las casillas adyacentes libres tienen la
         * misma probabilidad de ser elegidas.</p>
         */
        @Override
        public Position move() {
            Collection<Position> availablePositions;
            Position newPosition = null;

            if (random.nextDouble() <= moveProbability) {
                availablePositions = getSimulationGrid().getEmptyAdjacentCells(getPosition());

                if (!availablePositions.isEmpty()) {
                    newPosition = RandomUtil.getRandomElement(availablePositions, random);
                    position = newPosition;
                }
            }

            return newPosition;
        }
    }

    /**
     * Implementa el comportamiento de reproducción de las criaturas que nunca se reproducen.
     */
    protected class MultiplyNever implements MultiplyBehaviour {

        /**
         * {@inheritDoc}
         *
         * <p>Notas sobre implementación: nunca se reproduce, siempre devuelve {@code null}.</p>
         */
        @Override
        public LogicCreatureInterface multiply() {
            return null;
        }
    }

    /**
     * Implementa el comportamiento de reproducción de las criaturas que se reproducen a una casilla adyacente con una
     * determinada probabilidad.
     */
    protected class MultiplyToEmptyAdjacentRandomly implements MultiplyBehaviour {
        private final double multiplyProbability;
        private final Random random;

        /**
         * Construye una implementación del comportamiento de reproducción pseudo-aleatorio a casillas adyacentes
         * asociada a esta criatura con la probabilidad de reproducirse {@code multiplyProbability} y el generador de
         * números pseudo-aleatorios {@code random}. Asume que la probabilidad de reproducirse está entre 0.0 y 1.0 (1.0
         * incluido) y que el generador de números pseudo-aleatorios es no nulo.
         *
         * @param multiplyProbability la probabilidad de reproducirse.
         * @param random              el generador de números pseudo-aleatorios.
         */
        public MultiplyToEmptyAdjacentRandomly(double multiplyProbability, Random random) {
            this.multiplyProbability = multiplyProbability;
            this.random = random;
        }

        /**
         * {@inheritDoc}
         *
         * <p>Notas sobre implementación: si hay alguna casilla adyacente libre se reproduce con la probabilidad
         * definida para esta criatura en una de las casillas adyacentes libres. Todas las casillas adyacentes libres
         * tienen la misma probabilidad de ser elegidas. Si se reproduce la cría tiene un id único, pero el mismo
         * nombre, aguante sin comida, probabilidad de multiplicarse y generador de números pseudo-aleatorios que el
         * padre; y está asociado al mismo tablero de simulación que el padre.</p>
         */
        @Override
        public LogicCreatureInterface multiply() {
            LogicCreature child = null;
            Collection<Position> availablePositions;
            Position childPosition;

            if (random.nextDouble() <= multiplyProbability) {
                availablePositions = getSimulationGrid().getEmptyAdjacentCells(getPosition());

                if (!availablePositions.isEmpty()) {
                    childPosition = RandomUtil.getRandomElement(availablePositions, random);
                    child = new StaticRabbit(new Creature(UUID.randomUUID().toString(), getName()),
                            getStarvationThreshold(), multiplyProbability, childPosition, random);
                    child.setSimulationGrid(simulationGrid);
                }
            }

            return child;
        }
    }
}