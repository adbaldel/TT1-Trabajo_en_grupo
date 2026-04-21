package com.tt1.simserver.logic;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.Creature;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el tablero bidimensional donde interactúan y se sitúan las criaturas de la simulación.
 * El tablero se modela como una matriz cuadrada.
 */
public class Grid {

    /**
     * Constructor que inicializa un tablero vacío calculando su tamaño en base
     * al número total de criaturas y el porcentaje de ocupación deseado.
     * Precondición: numberOfCreatures > 0, occupancy entre 0.0 y 1.0 (0.0 no incluido).
     *
     * @param numberOfCreatures el número total inicial de criaturas.
     * @param occupancy la fracción de ocupación esperada en el tablero (ej. 0.35 para 35%).
     */
    public Grid(int numberOfCreatures, double occupancy) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Constructor que inicializa el tablero y lo puebla con una lista de criaturas.
     * Calcula su tamaño en base al número total de criaturas y el porcentaje de ocupación deseado.
     * Precondición: creatures no es nulo ni vacío, occupancy entre 0.0 y 1.0 (0.0 no incluido).
     *
     * @param creatures la lista de criaturas a ubicar en el tablero.
     * @param occupancy la fracción de ocupación esperada en el tablero.
     */
    public Grid(List<Creature> creatures, double occupancy) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }


    /**
     * Avanza el estado del tablero en un tick temporal (un segundo en la simulación).
     * Recorre la matriz de izquierda a derecha (eje X) y de arriba a abajo (eje Y).
     * Durante el recorrido, invoca las reglas de movimiento y multiplicación de cada criatura
     * en ese orden.
     */
    public void tick() {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Obtiene la criatura situada en una posición concreta del tablero.
     * Precondición: la posición debe estar dentro del tablero.
     *
     * @param position las coordenadas donde buscar la criatura.
     * @return la criatura en dicha posición, o null si está vacía.
     */
    public Creature getCreature(Position position) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Devuelve el tamaño del lado del tablero (número de filas/columnas).
     *
     * @return el tamaño del tablero.
     */
    public int getSize() {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Verifica si una posición se encuentra vacía y dentro de los límites del tablero.
     *
     * @param position la posición a evaluar.
     * @return cierto si la posición está dentro del tablero y no contiene ninguna criatura, falso en caso contrario.
     */
    public boolean isEmpty(Position position) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Inserta o actualiza una criatura en el tablero utilizando las coordenadas internas de la propia criatura.
     * Precondición: la criatura debe ocupar una posición vacía del tablero.
     *
     * @param creature la criatura a añadir al tablero.
     */
    public final void addCreature(Creature creature) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Calcula y devuelve las posiciones adyacentes vacías (arriba, abajo, izquierda, derecha)
     * a partir de una posición dada, verificando los límites del tablero.
     * Precondición: la posición debe estar dentro del tablero.
     *
     * @param position la coordenada central desde la cual verificar celdas adyacentes.
     * @return un arreglo con las posiciones adyacentes que se encuentran actualmente vacías.
     */
    public Position[] getAdjacentEmptyCells(Position position) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
