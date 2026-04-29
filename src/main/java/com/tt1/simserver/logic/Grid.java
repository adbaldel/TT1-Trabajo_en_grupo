package com.tt1.simserver.logic;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el tablero bidimensional donde interactúan y se sitúan las criaturas de la simulación.
 * El tablero se modela como una matriz cuadrada.
 */
public class Grid implements GridInterface {
    private final CreatureInterface[][] grid;
    private final int size;


    /**
     * Constructor que inicializa un tablero vacío calculando su tamaño en base
     * al número total de criaturas y el porcentaje de ocupación deseado.
     * Precondición: numberOfCreatures > 0, occupancy entre 0.0 y 1.0 (0.0 no incluido).
     *
     * @param numberOfCreatures el número total inicial de criaturas.
     * @param occupancy         la fracción de ocupación esperada en el tablero (ej. 0.35 para 35%).
     */
    public Grid(int numberOfCreatures, double occupancy) {
        size = (int) Math.ceil(Math.sqrt(numberOfCreatures / occupancy));

        grid = new CreatureInterface[size][size];
    }

    /**
     * Constructor que inicializa el tablero y lo puebla con una lista de criaturas.
     * Calcula su tamaño en base al número total de criaturas y el porcentaje de ocupación deseado.
     * Precondición: creatures no es nulo ni vacío, occupancy entre 0.0 y 1.0 (0.0 no incluido) y todas las criaturas
     * tienen posiciones diferentes y están dentro del tamaño calculado del tablero.
     *
     * @param creatures la lista de criaturas a ubicar en el tablero.
     * @param occupancy la fracción de ocupación esperada en el tablero.
     */
    public Grid(List<CreatureInterface> creatures, double occupancy) {
        this(creatures.size(), occupancy);

        for (CreatureInterface creature : creatures) {
            addCreature(creature);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void tick() {
        Position position;
        CreatureInterface creature;
        Position updatedCreaturePosition;
        CreatureInterface creatureChild;
        List<Position> newCreaturePositions;

        newCreaturePositions = new ArrayList<>();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (newCreaturePositions.contains(new Position(x, y))) {
                    continue;
                }

                position = new Position(x, y);
                creature = getCreature(position);

                if (creature != null) {
                    updatedCreaturePosition = creature.move(this);

                    if (updatedCreaturePosition != null) {
                        grid[position.getY()][position.getX()] = null;
                        grid[updatedCreaturePosition.getY()][updatedCreaturePosition.getX()] = creature;
                    }

                    creatureChild = creature.multiply(this);

                    if (creatureChild != null) {
                        addCreature(creatureChild);
                        newCreaturePositions.add(creatureChild.getPosition());
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CreatureInterface getCreature(Position position) {
        return grid[position.getY()][position.getX()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty(Position position) {
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= size || position.getY() >= size) {
            return false;
        }

        return grid[position.getY()][position.getX()] == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addCreature(CreatureInterface creature) {
        grid[creature.getPosition().getY()][creature.getPosition().getX()] = creature;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Position> getAdjacentEmptyCells(Position position) {
        List<Position> positions = new ArrayList<>();
        Position right, left, up, down;

        right = new Position(position.getX(), position.getY());
        right.moveRight();
        if (isEmpty(right)) {
            positions.add(right);
        }

        up = new Position(position.getX(), position.getY());
        up.moveUp();
        if (isEmpty(up)) {
            positions.add(up);
        }

        left = new Position(position.getX(), position.getY());
        left.moveLeft();
        if (isEmpty(left)) {
            positions.add(left);
        }
        down = new Position(position.getX(), position.getY());
        down.moveDown();
        if (isEmpty(down)) {
            positions.add(down);
        }

        return positions;
    }
}
