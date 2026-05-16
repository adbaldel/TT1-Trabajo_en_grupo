package com.tt1.simserver.model;

import com.tt1.simserver.logic.creatures.LogicCreatureInterface;

import java.util.Objects;

/**
 * Representa una criatura (inmutable).
 */
public class Creature {
    private final String id;
    private final String name;

    /**
     * Construye una criatura con el id y nombre pasados como parámetros. Asume que el id es no nulo y único para esta
     * criatura; y que el nombre no es nulo ni blank.
     *
     * @param id   el id de la criatura.
     * @param name el nombre de la criatura.
     */
    public Creature(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Construye una criatura con el mismo id y nombre que la criatura {@code creature}. Asume que la criatura no es
     * nula.
     *
     * @param creature la criatura a clonar.
     */
    public Creature(Creature creature) {
        this.id = creature.getId();
        this.name = creature.getName();
    }

    /**
     * Construye una criatura con el mismo id y color que la criatura {@code logicCreature}. Asume que la criatura no es
     * nula.
     *
     * @param logicCreature la criatura a clonar.
     */
    public Creature(LogicCreatureInterface logicCreature) {
        this.id = logicCreature.getId();
        this.name = logicCreature.getName();
    }

    /**
     * Obtiene el id de esta criatura.
     *
     * @return el id de esta criatura.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre de esta criatura.
     *
     * @return el nombre de esta criatura.
     */
    public String getName() {
        return name;
    }

    /**
     * Comprueba si esta criatura es igual al objeto {@code o}. Devuelve cierto si el objeto es una criatura con el
     * mismo id.
     *
     * @param o el objeto a comparar.
     * @return cierto si esta criatura es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return id.equals(creature.id);
    }

    /**
     * Obtiene el código hash de esta criatura.
     *
     * @return el código hash de esta criatura.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}