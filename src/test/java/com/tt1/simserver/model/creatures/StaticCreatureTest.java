package com.tt1.simserver.model.creatures;

import com.tt1.simserver.mocks.GridFake;
import com.tt1.simserver.model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StaticCreatureTest {

    private String name;
    private String color;
    private Position position;
    private Position emptyAdjacentCell1;
    private Position emptyAdjacentCell2;
    private GridFake gridFake;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        name = "static";
        color = "red";
        position = new Position(0, 0);
        emptyAdjacentCell1 = new Position(1, 0);
        emptyAdjacentCell2 = new Position(0, 1);
        gridFake = new GridFake();
    }

    @AfterEach
    public void tearDown() {
        name = null;
        color = null;
        position = null;
        emptyAdjacentCell1 = null;
        emptyAdjacentCell2 = null;
        gridFake = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test move ---------------------------------------------------------------------------------------------------

    @Test
    public void given_staticCreatureAndNoEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Position newPosition = staticCreature.move(gridFake);

        // Assert (Then)
        assertNull(newPosition);
        assertEquals(position, staticCreature.getPosition());
    }

    @Test
    public void given_staticCreatureAndEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition = staticCreature.move(gridFake);

        // Assert (Then)
        assertNull(newPosition);
        assertEquals(position, staticCreature.getPosition());
    }

    // --- Test multiply -----------------------------------------------------------------------------------------------

    @Test
    public void given_staticCreatureAndNoEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Creature child = staticCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child);
    }

    @Test
    public void given_staticCreatureAndEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Creature child = staticCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child);
    }
}