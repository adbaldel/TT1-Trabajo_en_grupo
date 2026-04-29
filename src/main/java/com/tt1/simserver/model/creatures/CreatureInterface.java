package com.tt1.simserver.model.creatures;

import com.tt1.simserver.model.Position;

/**
 * Define los atributos básicos y comportamientos que toda criatura debe implementar para existir en el tablero.
 */
public interface CreatureInterface extends Movable, Multipliable {

    /**
     * Obtiene el nombre de la especie a la que pertenece la criatura.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto exacta con el nombre asignado en su creación.
     *
     * @return el nombre de la criatura.
     */
    String getName();

    /**
     * Obtiene el color que representa a la criatura.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto con el identificador CSS del color.
     *
     * @return el color de la criatura.
     */
    String getColor();

    /**
     * Recupera las coordenadas actuales de la criatura en el tablero.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una copia nueva del objeto posición actual para evitar manipulaciones externas del estado interno de la criatura.
     *
     * @return una nueva instancia con la posición actual.
     */
    Position getPosition();
}
