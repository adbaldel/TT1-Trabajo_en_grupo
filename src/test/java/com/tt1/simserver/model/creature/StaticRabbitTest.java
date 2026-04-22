package com.tt1.simserver.model.creature;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.StaticRabbit;
import org.junit.Assert;
import org.junit.Test;

/**
 * Pruebas de integración para StaticRabbit.
 */
public class StaticRabbitTest {

    @Test
    public void testConstructorAndMultiplyProbability() {
        Position pos = new Position(2, 2);
        StaticRabbit rabbit = new StaticRabbit("ConejoEstatico", "white", 0.3, pos);

        Assert.assertEquals(0.3, rabbit.getMultiplyProbability(), 0.001);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMultiplyThrowsException() {
        StaticRabbit rabbit = new StaticRabbit("ConejoEstatico", "white", 0.3, new Position(0, 0));
        rabbit.multiply(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testInheritedMoveThrowsException() {
        // Aunque es un conejo, hereda el comportamiento estático de no moverse
        StaticRabbit rabbit = new StaticRabbit("Conejo", "white", 0.1, new Position(0, 0));
        rabbit.move(null);
    }
}