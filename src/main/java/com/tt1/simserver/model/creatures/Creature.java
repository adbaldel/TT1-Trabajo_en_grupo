package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;

/**
 * Clase abstracta base que modela a las entidades genéricas dentro de la simulación.
 * Las implementaciones de esta clase definen los diferentes tipos (móviles, estáticos, reproductivos, etc.).
 */
public abstract class Creature implements CreatureInterface {
    protected final Position position;
    private final String name;
    private final String color;

    /**
     * Construye una nueva criatura base configurando sus atributos visuales e iniciales.
     *
     * <p>Precondición: {@code name} no es nulo, no está vacío ni solo contiene caracteres invisibles. {@code color} es un color reconocido por CSS. {@code position} no es nulo.
     *
     * <p>Postcondición: La criatura queda instanciada en memoria guardando su especie, representación visual y su posición de origen.
     *
     * @param name     nombre de la especie o de la criatura.
     * @param color    el color que la representa visualmente dentro del tablero.
     * @param position su posición inicial dentro de la simulación.
     */
    public Creature(String name, String color, Position position) {
        this.name = name;
        this.color = color;
        this.position = position;
    }

    /**
     * Obtiene el nombre de la especie a la que pertenece la criatura.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto exacta con el nombre asignado en su creación.
     *
     * @return el nombre de la criatura.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Obtiene el color que representa a la criatura.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto con el identificador CSS del color.
     *
     * @return el color de la criatura.
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * Recupera las coordenadas actuales de la criatura en el tablero.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una copia nueva del objeto posición actual para evitar manipulaciones externas del estado interno de la criatura.
     *
     * @return una nueva instancia con la posición actual.
     */
    @Override
    public Position getPosition() {
        return new Position(position.getX(), position.getY());
    }

    /**
     * Calcula e intenta realizar un movimiento hacia una nueva casilla del tablero durante este turno.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura apuntando a la misma posición que tiene guardada.
     *
     * <p>Postcondición: Devuelve la nueva posición si la criatura logra moverse con éxito, o nulo si decide o se ve forzada a quedarse quieta.
     *
     * @param grid el tablero que expone las casillas adyacentes.
     * @return la posición de destino, o nulo si no se mueve.
     */
    @Override
    public abstract Position move(GridInterface grid);

    /**
     * Intenta ejecutar la reproducción de la especie creando una cría en una casilla contigua durante este turno.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura apuntando a la misma posición que tiene guardada.
     *
     * <p>Postcondición: Devuelve una nueva instancia de criatura (cría) posicionada en el tablero si la reproducción tiene éxito, o nulo si fracasa.
     *
     * @param grid el tablero para evaluar el espacio y depositar la cría.
     * @return la nueva cría generada, o nulo si no se reproduce.
     */
    @Override
    public abstract Creature multiply(GridInterface grid);
}