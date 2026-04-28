package com.tt1.simserver.logic;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;

import java.util.List;

public interface GridInterface {

    /**
     * Avanza el estado del tablero en un tick temporal (un segundo en la simulación).
     * Recorre la matriz de izquierda a derecha (eje X) y de arriba a abajo (eje Y).
     * Durante el recorrido, invoca las reglas de movimiento y multiplicación de cada criatura
     * en ese orden.
     */
    void tick();

    /**
     * Obtiene la criatura situada en una posición concreta del tablero.
     * Precondición: la posición debe estar dentro del tablero.
     *
     * @param position las coordenadas donde buscar la criatura.
     * @return la criatura en dicha posición, o null si está vacía.
     */
    CreatureInterface getCreature(Position position);

    /**
     * Devuelve el tamaño del lado del tablero (número de filas/columnas).
     *
     * @return el tamaño del tablero.
     */
    int getSize();

    /**
     * Inserta o actualiza una criatura en el tablero utilizando las coordenadas internas de la propia criatura.
     * Precondición: la criatura debe ocupar una posición vacía del tablero.
     *
     * @param creature la criatura a añadir al tablero.
     */
    void addCreature(CreatureInterface creature);

    /**
     * Verifica si una posición se encuentra vacía y dentro de los límites del tablero.
     *
     * @param position la posición a evaluar.
     * @return cierto si la posición está dentro del tablero y no contiene ninguna criatura, falso en caso contrario.
     */
    boolean isEmpty(Position position);

    /**
     * Calcula y devuelve las posiciones adyacentes vacías (arriba, abajo, izquierda, derecha)
     * a partir de una posición dada, verificando los límites del tablero.
     * Precondición: la posición debe estar dentro del tablero.
     *
     * @param position la coordenada central desde la cual verificar celdas adyacentes.
     * @return una lista con las posiciones adyacentes que se encuentran actualmente vacías.
     */
    List<Position> getAdjacentEmptyCells(Position position);
}
