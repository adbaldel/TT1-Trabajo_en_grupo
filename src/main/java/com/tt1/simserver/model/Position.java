package com.tt1.simserver.model;

import java.util.Objects;

/**
 * Modela las coordenadas 2D relativas de una casilla dentro del tablero de simulación.
 */
public class Position {
    private int x;
    private int y;


    /**
     * Constructor para inicializar una coordenada XY.
     *
     * @param x la columna en el tablero (eje horizontal).
     * @param y la fila en el tablero (eje vertical).
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Obtiene el valor horizontal X.
     *
     * @return la coordenada x actual.
     */
    public int getX() {
        return x;
    }

    /**
     * Establece el valor horizontal X.
     *
     * @param x la nueva coordenada x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtiene el valor vertical Y.
     *
     * @return la coordenada y actual.
     */
    public int getY() {
        return y;
    }

    /**
     * Establece el valor vertical Y.
     *
     * @param y la nueva coordenada y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Desplaza la coordenada una casilla hacia la derecha en el eje horizontal.
     */
    public void moveRight() {
        x += 1;
    }

    /**
     * Desplaza la coordenada una casilla hacia la izquierda en el eje horizontal.
     */
    public void moveLeft() {
        x -= 1;
    }

    /**
     * Desplaza la coordenada una casilla hacia arriba (positiva) en el eje vertical.
     */
    public void moveUp() {
        y += 1;
    }

    /**
     * Desplaza la coordenada una casilla hacia abajo (negativa) en el eje vertical.
     */
    public void moveDown() {
        y -= 1;
    }

    /**
     * Compara este objeto con otro para comprobar si son iguales basándose en sus atributos.
     *
     * @param o el objeto de referencia con el cual comparar.
     * @return cierto si los objetos son posiciones con la misma x e y, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return x == position.x &&
                y == position.y;
    }

    /**
     * Calcula y devuelve el código hash para el objeto Position.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
