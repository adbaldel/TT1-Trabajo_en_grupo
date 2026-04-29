package com.tt1.simserver.model;

import java.util.Objects;

/**
 * Modela las coordenadas bidimensionales de una casilla dentro del tablero de simulación.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Crea una nueva posición apuntando a las coordenadas indicadas.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: La posición se inicializa con los valores exactos de x e y proporcionados.
     *
     * @param x la columna en el tablero (eje horizontal).
     * @param y la fila en el tablero (eje vertical).
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene el valor horizontal del tablero.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la coordenada X asignada a esta posición.
     *
     * @return la coordenada x actual.
     */
    public int getX() {
        return x;
    }

    /**
     * Modifica el valor horizontal del tablero.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: La coordenada X se sobreescribe con el nuevo valor.
     *
     * @param x la nueva coordenada x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtiene el valor vertical del tablero.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la coordenada Y asignada a esta posición.
     *
     * @return la coordenada y actual.
     */
    public int getY() {
        return y;
    }

    /**
     * Modifica el valor vertical del tablero.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: La coordenada Y se sobreescribe con el nuevo valor.
     *
     * @param y la nueva coordenada y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Mueve la posición una casilla hacia la derecha en el eje horizontal.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El valor de la coordenada X aumenta en uno. La coordenada Y no cambia.
     */
    public void moveRight() {
        x += 1;
    }

    /**
     * Mueve la posición una casilla hacia la izquierda en el eje horizontal.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El valor de la coordenada X disminuye en uno. La coordenada Y no cambia.
     */
    public void moveLeft() {
        x -= 1;
    }

    /**
     * Mueve la posición una casilla hacia arriba en el eje vertical.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El valor de la coordenada Y aumenta en uno. La coordenada X no cambia.
     */
    public void moveUp() {
        y += 1;
    }

    /**
     * Mueve la posición una casilla hacia abajo en el eje vertical.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El valor de la coordenada Y disminuye en uno. La coordenada X no cambia.
     */
    public void moveDown() {
        y -= 1;
    }

    /**
     * Compara esta posición con otro objeto para verificar si representan la misma casilla.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero solo si el objeto proporcionado es otra posición con las mismas coordenadas X e Y. Devuelve falso en cualquier otro caso.
     *
     * @param o el objeto a comparar con esta posición.
     * @return verdadero si apuntan a la misma casilla, falso en caso contrario.
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
     * Calcula el código numérico para usar la posición en colecciones basadas en hash.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve un entero generado a partir de las coordenadas X e Y. Posiciones en la misma casilla devuelven el mismo hash.
     *
     * @return el valor hash de la posición.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}