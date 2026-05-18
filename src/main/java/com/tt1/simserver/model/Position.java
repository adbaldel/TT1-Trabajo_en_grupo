package com.tt1.simserver.model;

/**
 * Representa una posición bidimensional (inmutable).
 *
 * @param x la coordenada x u horizontal.
 * @param y la coordenada y o vertical.
 */
public record Position(int x, int y) {

    /**
     * Construye una posición con las coordenadas x e y pasadas como parámetro.
     *
     * @param x la coordenada x.
     * @param y la coordenada y.
     */
    public Position {
    }

    /**
     * Obtiene la coordenada x.
     *
     * @return la coordenada x.
     */
    @Override
    public int x() {
        return x;
    }

    /**
     * Obtiene la coordenada y.
     *
     * @return la coordenada y.
     */
    @Override
    public int y() {
        return y;
    }

    /**
     * Obtiene la posición a la derecha de esta posición.
     *
     * @return la posición a la derecha de esta posición.
     */
    public Position getRight() {
        return new Position(x() + 1, y());
    }

    /**
     * Obtiene la posición a la izquierda de esta posición.
     *
     * @return la posición a la izquierda de esta posición.
     */
    public Position getLeft() {
        return new Position(x() - 1, y());
    }

    /**
     * Obtiene la posición encima de esta posición.
     *
     * @return la posición encima de esta posición.
     */
    public Position getUp() {
        return new Position(x(), y() + 1);
    }

    /**
     * Obtiene la posición debajo de esta posición.
     *
     * @return la posición debajo de esta posición.
     */
    public Position getDown() {
        return new Position(x(), y() - 1);
    }
}