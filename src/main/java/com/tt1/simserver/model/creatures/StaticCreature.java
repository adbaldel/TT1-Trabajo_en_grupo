package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;

/**
 * Modela a las entidades de comportamiento estático, es decir, aquellas que por regla de
 * negocio se mantienen inactivas, no modifican su ubicación inicial y no se reproducen en su forma base.
 */
public class StaticCreature extends Creature {

    /**
     * Construye una criatura estática en un punto designado.
     *
     * <p>Precondición: {@code name} no es nulo, no está vacío ni solo contiene caracteres invisibles. {@code color} es un color reconocido por CSS. {@code position} no es nulo.
     *
     * <p>Postcondición: Inicializa la criatura asegurando sus propiedades visuales y espaciales definitivas en el tablero.
     *
     * @param name     nombre indicativo de la especie.
     * @param color    color representativo asignado en su renderizado.
     * @param position la posición definitiva donde habitará.
     */
    public StaticCreature(String name, String color, Position position) {
        super(name, color, position);
    }

    /**
     * Bloquea la acción de movimiento para esta especie concreta.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura en su posición correspondiente.
     *
     * <p>Postcondición: Devuelve siempre nulo sin modificar nunca las coordenadas internas de la criatura. Ignora deliberadamente cualquier casilla libre adyacente en el tablero.
     *
     * @param grid el tablero sobre el que opera en este turno.
     * @return siempre devuelve nulo.
     */
    @Override
    public Position move(GridInterface grid) {
        return null;
    }

    /**
     * Cancela mecánicamente la reproducción para evitar la generación de crías.
     *
     * <p>Precondición: {@code grid} no es nulo y contiene a esta criatura en su posición correspondiente.
     *
     * <p>Postcondición: Devuelve siempre nulo. La criatura estática base no implementa reproducción, por lo que aborta el intento independientemente de las casillas vacías a su alrededor.
     *
     * @param grid el tablero sobre el que opera en este turno.
     * @return siempre devuelve nulo.
     */
    @Override
    public Creature multiply(GridInterface grid) {
        return null;
    }
}