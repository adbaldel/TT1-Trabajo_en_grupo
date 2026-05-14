package com.tt1.simserver.logic;

import com.tt1.simserver.logic.creatures.LogicCreatureInterface;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.SimulationStep;

import java.util.*;

/**
 * Representa un tablero de simulación como un mapa de posiciones a criaturas, que representa todas las criaturas del
 * tablero con su posición, y un entero que especifica el tamaño.
 *
 * <p>La forma en la que asigna comida es pseudo-aleatoria dando la misma probabilidad de obtener comida en cada tick
 * a todas las celdas de la matriz y asignando un número total de unidades de comida fijo ({@code foodPerTick}). La
 * forma de obtener la pseudo-aleatoriedad viene definida por {@link Random}.</p>
 */
public class SimulationGrid implements SimulationGridInterface {
    private final Map<Position, LogicCreatureInterface> grid;
    private final int size;
    private final int foodPerTick;
    private final Random random;
    private Collection<Position> foodPositions;

    /**
     * Construye un tablero de simulación vacío con el tamaño, cantidad de comida por tick y generador de números
     * pseudo-aleatorios pasados como parámetros. Asume que el tamaño es mayor que cero; que la cantidad de comida por
     * tick está entre 0 y el tamaño al cuadrado (ambos incluidos); y que el generador de números pseudo-aleatorios es
     * no nulo.
     *
     * @param size        el tamaño del tablero.
     * @param foodPerTick la cantidad de unidades de comida por tick.
     * @param random      el generador de números pseudo-aleatorios.
     */
    public SimulationGrid(int size, int foodPerTick, Random random) {
        this.size = size;
        grid = new HashMap<>();
        this.foodPerTick = foodPerTick;
        this.random = random;
    }

    /**
     * Construye un tablero de simulación poblado con el tamaño y las criaturas en las posiciones dadas por el estado
     * inicial ({@code initialStep}), y la cantidad de comida por tick y generador de números pseudo-aleatorios pasados
     * como parámetros. Asume que el estado inicial es no nulo y sus criaturas implementan la interfaz
     * {@link LogicCreatureInterface}; que la cantidad de comida por tick está entre 0 y el tamaño al cuadrado (ambos
     * incluidos) y que el generador de números pseudo-aleatorios es no nulo. Asocia las criaturas al tablero.
     *
     * @param initialStep el estado inicial de la simulación.
     * @param foodPerTick la cantidad de unidades de comida por tick.
     * @param random      el generador de números aleatorios.
     */
    public SimulationGrid(SimulationStep initialStep, int foodPerTick, Random random) {
        this(initialStep.getGridSize(), foodPerTick, random);

        LogicCreatureInterface logicCreature;
        for (Position position : initialStep.getNonEmptyPositions()) {
            logicCreature = (LogicCreatureInterface) initialStep.getCreatureAt(position);
            addCreature(logicCreature);
            logicCreature.setSimulationGrid(this);
        }
    }

    /**
     * Devuelve el tamaño que tendría que tener el tablero para alojar el número de criaturas especificadas y que estas
     * ocuparan el porcentaje de tablero especificado. Debido a que no se puede garantizar que con el número de
     * criaturas dado exista un tamaño de tablero tal que las criaturas ocupen exactamente el porcentaje de tablero
     * especificado, se devolverá el tamaño más pequeño tal que dicho número de criaturas ocupe como máximo el
     * porcentaje especificado.
     *
     * @param numberOfCreatures el número de criaturas que habría en el tablero.
     * @param occupancy         el porcentaje del tablero que ocuparían las criaturas.
     * @return el tamaño del tablero que mejor cumpla estos requisitos sin tener un porcentaje de ocupación mayor al
     * especificado.
     */
    public static int calculateSize(int numberOfCreatures, double occupancy) {
        return (int) Math.ceil(Math.sqrt(numberOfCreatures / occupancy));
    }

    /**
     * Devuelve el número de unidades de comida que un tablero del tamaño especificado debería repartir uniformemente
     * por tick para que cada celda tenga la probabilidad especificada de recibir comida cada tick. Debido a que no se
     * puede garantizar que con el tamaño dado haya un número de unidades de comida tal que cada celda tenga exactamente
     * la probabilidad especificada de recibir comida, se devolverá el número de unidades de comida más pequeño tal que
     * todas las celdas tengan al menos la probabilidad especificada de recibir comida.
     *
     * @param size            el tamaño que tendría tablero.
     * @param foodProbability la probabilidad que cada celda tendría de recibir comida.
     * @return la cantidad de comida que se debería repartir uniformemente cada tick que mejor cumpla los requisitos sin
     * conseguir una probabilidad menor a la especificada.
     */
    public static int calculateFoodPerTick(int size, double foodProbability) {
        return (int) Math.ceil(foodProbability * size * size);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Notas sobre implementación: La forma en la que asigna comida es pseudo-aleatoria dando la misma
     * probabilidad de obtener comida en cada tick a todas las celdas de la matriz y asignando un número total de
     * unidades de comida fijo ({@code foodPerTick}). La forma de obtener la pseudo-aleatoriedad viene definida por
     * {@link Random}.</p>
     */
    @Override
    public void tick() {
        Position position;
        LogicCreatureInterface creature;
        Collection<Position> skipPositions;

        skipPositions = new HashSet<>();
        foodPositions = generateFoodPositions();

        for (int y = 0; y < getSize(); y++) {
            for (int x = 0; x < getSize(); x++) {
                if (skipPositions.contains(new Position(x, y))) {
                    continue;
                }

                position = new Position(x, y);
                creature = getCreatureAt(position);

                if (creature != null) {
                    tickCreature(creature, position, skipPositions);
                }
            }
        }
    }

    @Override
    public LogicCreatureInterface getCreatureAt(Position position) {
        return grid.get(position);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty(Position position) {
        if (position.x() < 0 || position.y() < 0 || position.x() >= size || position.y() >= size) {
            return false;
        }

        return grid.get(position) == null;
    }

    @Override
    public boolean hasFood(Position position) {
        return foodPositions.contains(position);
    }

    @Override
    public final void addCreature(LogicCreatureInterface creature) {
        grid.put(creature.getPosition(), creature);
        creature.setSimulationGrid(this);
    }

    @Override
    public Collection<Position> getEmptyAdjacentCells(Position position) {
        Collection<Position> positions = new HashSet<>();
        Position right, left, up, down;

        right = position.getRight();
        if (isEmpty(right)) {
            positions.add(right);
        }

        up = position.getUp();
        if (isEmpty(up)) {
            positions.add(up);
        }

        left = position.getLeft();
        if (isEmpty(left)) {
            positions.add(left);
        }

        down = position.getDown();
        if (isEmpty(down)) {
            positions.add(down);
        }

        return positions;
    }

    @Override
    public Collection<Position> getNonEmptyPositions() {
        return grid.keySet();
    }

    /**
     * Genera una colección de exactamente {@code foodPerTick} posiciones distintas que están dentro de los límites de
     * este tablero de manera pseudo-aleatoria teniendo todas las posiciones la misma probabilidad de estar en la
     * colección. La forma de obtener la pseudo-aleatoriedad viene definida por {@link Random}.
     *
     * @return la colección con las posiciones generadas.
     */
    private Collection<Position> generateFoodPositions() {
        List<Position> positions = new ArrayList<>();
        Collection<Position> foodPositions = new HashSet<>();

        for (int y = 0; y < getSize(); y++) {
            for (int x = 0; x < getSize(); x++) {
                positions.add(new Position(x, y));
            }
        }
        Collections.shuffle(positions, random);

        for (int i = 0; i < foodPerTick; i++) {
            foodPositions.add(positions.get(i));
        }

        return foodPositions;
    }

    /**
     * Ejecuta las acciones de una criatura; si la criatura se mueve, añade a la colección de posiciones a saltar la
     * nueva posición de la criatura; y si la criatura se reproduce, añade a la colección de posiciones a saltar la
     * posición de la nueva criatura. Estas acciones son primero moverse, después reproducirse, y finalmente comer. Si
     * la criatura se muere es eliminada del tablero. Asume que se ha generado comida en este tick de esta simulación;
     * que la criatura es no nula y está en la posición del tablero que se corresponde con su posición; que la posición
     * {@code creaturePosition} es no nula y se corresponde con la posición de la criatura; y que la colección de
     * posiciones a saltar es no nula y no contiene la posición de la criatura. No elimina de las posiciones con comida
     * ninguna posición.
     *
     * @param creature         la criatura que va a realizar sus acciones.
     * @param creaturePosition la posición de la criatura.
     * @param skipPositions    las posiciones a saltar del tablero.
     */
    private void tickCreature(LogicCreatureInterface creature, Position creaturePosition,
                              Collection<Position> skipPositions) {
        Position updatedCreaturePosition;
        LogicCreatureInterface creatureChild;

        // Mandar la criatura moverse
        updatedCreaturePosition = creature.performMove();
        if (updatedCreaturePosition != null) {
            // Actualizar posición de la criatura en el tablero
            grid.remove(creaturePosition);
            addCreature(creature);
            // Añadir a la colección de posiciones a saltar la nueva posición
            skipPositions.add(updatedCreaturePosition);
        }

        // Mandar la criatura reproducirse
        creatureChild = creature.performMultiply();
        if (creatureChild != null) {
            // Añadir la nueva criatura al tablero
            addCreature(creatureChild);
            // Añadir a la colección de posiciones a saltar la posición de la nueva criatura
            skipPositions.add(creatureChild.getPosition());
        }

        // Mandar la criatura comer
        creature.performEat();

        // Si muere, se retira del tablero
        if (!creature.isAlive()) {
            creature.setSimulationGrid(null);
            grid.remove(creature.getPosition());
        }
    }
}