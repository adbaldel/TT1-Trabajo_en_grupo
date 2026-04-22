package com.tt1.simserver.model.creature;

import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.Creature;
import com.tt1.simserver.model.creatures.StaticCreature;
import org.junit.Assert;
import org.junit.Test;

/**
 * Pruebas para la clase base abstracta Creature.
 * Al ser abstracta, utilizamos una implementación concreta (StaticCreature)
 * para validar el comportamiento de los métodos comunes definidos en la base.
 */
public class CreatureTest {

    @Test
    public void testGettersBase() {
        Position pos = new Position(5, 10);
        // Usamos StaticCreature para probar la funcionalidad de la clase base
        Creature c = new StaticCreature("EntidadBase", "blue", pos);

        Assert.assertEquals("EntidadBase", c.getName());
        Assert.assertEquals("blue", c.getColor());
        Assert.assertEquals(5, c.getPosition().getX());
        Assert.assertEquals(10, c.getPosition().getY());
    }

    @Test
    public void testGetPositionCloning() {
        Position pos = new Position(1, 1);
        Creature c = new StaticCreature("Test", "red", pos);

        Position posCopy = c.getPosition();
        posCopy.setX(99); // Modificamos la copia

        // La posición interna no debería haber cambiado (defensive copying)
        Assert.assertEquals(1, c.getPosition().getX());
    }
}