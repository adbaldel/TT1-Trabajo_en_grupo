package com.tt1.simserver.model.creatures;

import com.tt1.simserver.logic.Grid;
import com.tt1.simserver.model.Position;

/**
 * Modela a las entidades de comportamiento estático, es decir, aquellas que por regla de
 * negocio se mantienen inactivas, no modifican su ubicación inicial y no se reproducen en su forma base.
 */
public class StaticCreature extends Creature {

    /**
     * Construye una entidad de naturaleza estática en un punto designado.
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...), color es un color reconocido por CSS, position es no nula.
     *
     * @param name nombre indicativo de la especie.
     * @param color color representativo asignado en su renderizado.
     * @param position las coordenadas definitivas donde habitará.
     */
    public StaticCreature(String name, String color, Position position) {
        super(name, color, position);
    }


    /**
     * Regla que cancela el comportamiento dinámico. Nunca intenta mudarse a otras celdas.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid escenario de despliegue.
     * @return siempre devuelve null indicando que no requiere reasignación en el tablero subyacente.
     */
    @Override
    public Position move(Grid grid) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Evita la multiplicación. Ningún hijo se genera durante este paso.
     * Precondición: grid es no nulo y contiene a esta criatura en la posición correspondiente a la posición de la criatura.
     *
     * @param grid escenario de despliegue.
     * @return siempre null.
     */
    @Override
    public Creature multiply(Grid grid) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}