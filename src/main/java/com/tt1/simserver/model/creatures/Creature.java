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
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...), color es un color reconocido por CSS, position es no nula.
     *
     * @param name     nombre de la especie o de la criatura.
     * @param color    el color que la representa visualmente dentro del tablero.
     * @param position sus coordenadas 2D iniciales dentro de la simulación.
     */
    public Creature(String name, String color, Position position) {
        this.name = name;
        this.color = color;
        this.position = position;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return new Position(position.getX(), position.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Position move(GridInterface grid);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Creature multiply(GridInterface grid);
}
