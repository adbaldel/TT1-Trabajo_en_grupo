package com.tt1.simserver.logic;

import com.tt1.simserver.logic.creatures.LogicCreatureInterface;
import com.tt1.simserver.model.Position;

import java.util.Collection;

/**
 * Define la funcionalidad de un tablero de simulación.
 */
public interface SimulationGridInterface {

    /**
     * Ejecuta un tick de simulación sobre este tablero. Primero se asigna comida a algunas casillas de este tablero y
     * después cada criatura realiza sus acciones. Estas acciones son primero moverse, después reproducirse, y
     * finalmente comer. Si la criatura se mueve se actualiza su posición; si la criatura se reproduce se añade su cría
     * al tablero; y si la criatura se muere es eliminada del tablero y se desasocia la criatura del tablero. El orden
     * es de izquierda a derecha y de arriba abajo (recorriendo fila a fila todas las columnas).
     */
    void tick();

    /**
     * Obtiene la criatura de este tablero de simulación que está en la posición {@code position}. Asume que la posición
     * es no nula y se encuentra dentro de los límites de este tablero; y que las criaturas no se mueven, reproducen, ni
     * mueren durante la ejecución de esta función.
     *
     * @param position la posición de la criatura a encontrar.
     * @return a criatura de este tablero de simulación que está en la posición indicada, o null si no hay criatura en
     * dicha posición.
     */
    LogicCreatureInterface getCreatureAt(Position position);

    /**
     * Devuelve el tamaño de este tablero (el tamaño se refiere al lado del cuadrado que define este tablero).
     *
     * @return el tamaño de este tablero.
     */
    int getSize();

    /**
     * Añade o ubica la criatura {@code creature} en este tablero y asocia este tablero a la criatura. Asume que la
     * criatura es no nula, no tiene ningún tablero asociado (o está asociada a este tablero) y su posición está dentro
     * de los límites de este tablero; que las criaturas no se mueven, reproducen, ni mueren durante la ejecución de
     * esta función; y que la posición de la criatura se encuentra vacía en el tablero.
     *
     * @param creature la criatura a añadir.
     */
    void addCreature(LogicCreatureInterface creature);

    /**
     * Comprueba si la posición {@code position} está vacía en este tablero o no. Si la posición está fuera de los
     * límites del tablero se considera ocupada. Asume que la posición es no nula y que las criaturas no se mueven,
     * reproducen, ni mueren durante la ejecución de esta función.
     *
     * @param position la posición a comprobar.
     * @return cierto si la posición está vacía, falso en caso contrario.
     */
    boolean isEmpty(Position position);

    /**
     * Comprueba si la posición {@code position} tiene comida en el tick actual de este tablero, o no. Asume que la
     * posición es no nula y se encuentra dentro de los límites del tablero; y que se ha generado comida para este
     * tick.
     *
     * @param position la posición a comprobar.
     * @return cierto si la posición tiene comida, falso en caso contrario.
     */
    boolean hasFood(Position position);

    /**
     * Obtiene una lista con las posiciones vacías de este tablero adyacentes a la posición {@code position}. Las
     * posiciones adyacentes son las que se corresponden a la derecha, arriba, izquierda y abajo. Asume que la posición
     * es no nula y que las criaturas no se mueven, reproducen, ni mueren durante la ejecución de esta función.
     *
     * @param position la posición a obtener sus adyacentes.
     * @return una lista con las posiciones vacías adyacentes a la posición dada.
     */
    Collection<Position> getEmptyAdjacentCells(Position position);

    /**
     * Obtiene una colección con las posiciones no vacías (posiciones con criatura) de este tablero. Asume que las
     * criaturas no se mueven, reproducen, ni mueren durante la ejecución de esta función.
     *
     * @return una colección con las posiciones no vacías de este tablero.
     */
    Collection<Position> getNonEmptyPositions();
}