package com.tt1.simserver.model.creature;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.StaticCreature;
import org.junit.Test;

/**
 * Pruebas de integración para StaticCreature.
 */
public class StaticCreatureTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testMoveThrowsException() {
        StaticCreature c = new StaticCreature("Roca", "black", new Position(10, 10));
        c.move(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMultiplyThrowsException() {
        StaticCreature c = new StaticCreature("Roca", "black", new Position(10, 10));
        c.multiply(null);
    }
}