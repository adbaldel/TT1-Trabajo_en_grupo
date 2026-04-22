package com.tt1.simserver.model.creature;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.MobileCreature;
import org.junit.Assert;
import org.junit.Test;

/**
 * Pruebas de integración para MobileCreature.
 */
public class MobileCreatureTest {

    @Test
    public void testConstructorAndProbability() {
        Position pos = new Position(0, 0);
        MobileCreature c = new MobileCreature("Lobo", "grey", 0.75, pos);

        Assert.assertEquals(0.75, c.getMoveProbability(), 0.001);
        Assert.assertEquals("Lobo", c.getName());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMoveThrowsException() {
        MobileCreature c = new MobileCreature("Lobo", "grey", 0.5, new Position(0, 0));
        c.move(null); // Según precondición grid es no nulo, pero aquí validamos el lanzamiento de la excepción
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMultiplyThrowsException() {
        MobileCreature c = new MobileCreature("Lobo", "grey", 0.5, new Position(0, 0));
        c.multiply(null);
    }
}