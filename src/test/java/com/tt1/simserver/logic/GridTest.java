package com.tt1.simserver.logic;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.Creature;
import com.tt1.simserver.model.creatures.StaticCreature;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas de integración para la clase Grid.
 * Verifica que todos los métodos definidos lancen UnsupportedOperationException.
 */
public class GridTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testConstructorWithQuantities() {
        new Grid(10, 0.5);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testConstructorWithList() {
        List<Creature> creatures = new ArrayList<>();
        creatures.add(new StaticCreature("Piedra", "#808080", new Position(0, 0)));
        new Grid(creatures, 0.5);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTick() {
        Grid grid = createGridProxy();
        grid.tick();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCreature() {
        Grid grid = createGridProxy();
        grid.getCreature(new Position(0, 0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetSize() {
        Grid grid = createGridProxy();
        grid.getSize();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsEmpty() {
        Grid grid = createGridProxy();
        grid.isEmpty(new Position(0, 0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddCreature() {
        Grid grid = createGridProxy();
        Creature c = new StaticCreature("Piedra", "#808080", new Position(0, 0));
        grid.addCreature(c);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetAdjacentEmptyCells() {
        Grid grid = createGridProxy();
        grid.getAdjacentEmptyCells(new Position(1, 1));
    }

    /**
     * Utilidad para intentar instanciar un Grid cumpliendo precondiciones mínimas,
     * aunque sabemos que el constructor fallará.
     */
    private Grid createGridProxy() {
        return new Grid(1, 0.1);
    }
}