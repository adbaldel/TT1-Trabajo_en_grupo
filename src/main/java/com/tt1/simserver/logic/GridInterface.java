package com.tt1.simserver.logic;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;

import java.util.List;

/**
 * Define la estructura y las operaciones permitidas sobre el tablero de simulación.
 */
public interface GridInterface {

    /**
     * Avanza el estado del tablero en un turno temporal completo.
     *
     * <p>Precondición: El tablero está inicializado.
     *
     * <p>Postcondición: Recorre todas las posiciones en orden, de izquierda a derecha y de arriba a abajo. Para cada criatura, procesa su movimiento y luego su reproducción. Si la criatura decide moverse, su casilla origen queda vacía y ocupa la nueva posición. Si decide reproducirse, la nueva criatura aparece en su casilla de destino. Las criaturas actúan solo una vez por turno. Las criaturas recién nacidas no actúan en el turno que aparecen.
     */
    void tick();

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
    CreatureInterface getCreature(Position position);

    /**
     * Obtiene el tamaño del lado del tablero.
     *
     * <p>Precondición: El tablero está inicializado.
     *
     * <p>Postcondición: Devuelve el número de filas (o columnas) del tablero.
     *
     * @return el tamaño del tablero.
     */
    int getSize();

    /**
     * Añade una criatura al tablero usando su posición interna.
     *
     * <p>Precondición: {@code creature} no es nulo. Su posición está dentro de los límites del tablero y apunta a una casilla vacía.
     *
     * <p>Postcondición: La criatura se almacena en el tablero, ocupando su casilla correspondiente.
     *
     * @param creature la criatura a registrar en el tablero.
     */
    void addCreature(CreatureInterface creature);

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
    boolean isEmpty(Position position);

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
    List<Position> getAdjacentEmptyCells(Position position);
}
