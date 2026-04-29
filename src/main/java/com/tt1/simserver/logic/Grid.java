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
     * Inicializa un tablero vacío calculando su tamaño a partir del número de criaturas y la ocupación.
     *
     * <p>Precondición: {@code numberOfCreatures} es mayor que 0. {@code occupancy} es mayor que 0.0 y menor o igual a 1.0.
     *
     * <p>Postcondición: Crea un tablero cuadrado. El tamaño se calcula como la raíz cuadrada de (criaturas / ocupación), redondeado hacia arriba. Todas las casillas están vacías.
     *
     * @param numberOfCreatures el número total de criaturas.
     * @param occupancy la fracción de ocupación esperada en el tablero.
     */
    public Grid(int numberOfCreatures, double occupancy) {
        size = (int) Math.ceil(Math.sqrt(numberOfCreatures / occupancy));

        grid = new CreatureInterface[size][size];
    }

    /**
     * Inicializa un tablero y ubica en él una lista de criaturas.
     *
     * <p>Precondición: {@code creatures} no es nulo ni está vacío. {@code occupancy} es mayor que 0.0 y menor o igual a 1.0. Todas las criaturas tienen una posición única que cae dentro del tamaño calculado para el tablero.
     *
     * <p>Postcondición: Crea un tablero con el tamaño calculado. Cada criatura se coloca en la casilla que indica su posición inicial.
     *
     * @param creatures la lista de criaturas a colocar en el tablero.
     * @param occupancy la fracción de ocupación esperada en el tablero.
     */
    public Grid(List<CreatureInterface> creatures, double occupancy) {
        this(creatures.size(), occupancy);

        for (CreatureInterface creature : creatures) {
            addCreature(creature);
        }
    }

    /**
     * Avanza el estado del tablero en un turno temporal completo.
     *
     * <p>Precondición: El tablero está inicializado.
     *
     * <p>Postcondición: Recorre todas las posiciones en orden, de izquierda a derecha y de arriba a abajo. Para cada criatura, procesa su movimiento y luego su reproducción. Si la criatura decide moverse, su casilla origen queda vacía y ocupa la nueva posición. Si decide reproducirse, la nueva criatura aparece en su casilla de destino. Las criaturas actúan solo una vez por turno. Las criaturas recién nacidas no actúan en el turno que aparecen.
     */
    @Override
    public void tick() {
        Position position;
        CreatureInterface creature;
        Position updatedCreaturePosition;
        CreatureInterface creatureChild;
        List<Position> skipPositions;

        skipPositions = new ArrayList<>();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (skipPositions.contains(new Position(x, y))) {
                    continue;
                }

                position = new Position(x, y);
                creature = getCreature(position);

                if (creature != null) {
                    updatedCreaturePosition = creature.move(this);

                    if (updatedCreaturePosition != null) {
                        grid[position.getY()][position.getX()] = null;
                        grid[updatedCreaturePosition.getY()][updatedCreaturePosition.getX()] = creature;
                        skipPositions.add(updatedCreaturePosition);
                    }

                    creatureChild = creature.multiply(this);

                    if (creatureChild != null) {
                        addCreature(creatureChild);
                        skipPositions.add(creatureChild.getPosition());
                    }
                }
            }
        }
    }

    /**
     * Obtiene la criatura situada en una posición concreta.
     *
     * <p>Precondición: {@code position} no es nulo y sus coordenadas están dentro de los límites del tablero.
     *
     * <p>Postcondición: Devuelve la criatura que ocupa la casilla indicada. Devuelve nulo si la casilla está vacía.
     *
     * @param position la posición exacta de la casilla a consultar.
     * @return la criatura en la casilla, o nulo si está vacía.
     */
    @Override
    public CreatureInterface getCreature(Position position) {
        return grid[position.getY()][position.getX()];
    }

    /**
     * Obtiene el tamaño del lado del tablero.
     *
     * <p>Precondición: El tablero está inicializado.
     *
     * <p>Postcondición: Devuelve el número de filas (o columnas) del tablero.
     *
     * @return el tamaño del tablero.
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Comprueba si una posición es válida y su casilla está libre.
     *
     * <p>Precondición: {@code position} no es nulo.
     *
     * <p>Postcondición: Devuelve verdadero si la posición está dentro del tablero y su casilla no contiene ninguna criatura. Devuelve falso si la casilla tiene una criatura o si la posición sale de los límites del tablero.
     *
     * @param position la posición a evaluar.
     * @return verdadero si la casilla está libre y es válida, falso en caso contrario.
     */
    @Override
    public boolean isEmpty(Position position) {
        if (position.getX() < 0 || position.getY() < 0 || position.getX() >= size || position.getY() >= size) {
            return false;
        }

        return grid[position.getY()][position.getX()] == null;
    }

    /**
     * Añade una criatura al tablero usando su posición interna.
     *
     * <p>Precondición: {@code creature} no es nulo. Su posición está dentro de los límites del tablero y apunta a una casilla vacía.
     *
     * <p>Postcondición: La criatura se almacena en el tablero, ocupando su casilla correspondiente.
     *
     * @param creature la criatura a registrar en el tablero.
     */
    @Override
    public final void addCreature(CreatureInterface creature) {
        grid[creature.getPosition().getY()][creature.getPosition().getX()] = creature;
    }

    /**
     * Obtiene las posiciones adyacentes (derecha, arriba, izquierda, abajo) cuyas casillas están libres.
     *
     * <p>Precondición: {@code position} no es nulo.
     *
     * <p>Postcondición: Devuelve una lista con las posiciones adyacentes que están dentro del tablero y cuyas casillas no tienen ninguna criatura. Las posiciones fuera del tablero o con criaturas se descartan.
     *
     * @param position la posición central para evaluar las adyacentes.
     * @return una lista de posiciones adyacentes con casillas vacías.
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