package com.tt1.simserver.model.creatures;

import com.tt1.simserver.mocks.GridFake;
import com.tt1.simserver.model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MobileCreatureTest {

    private String name;
    private String color;
    private Position position;
    private Position emptyAdjacentCell1;
    private Position emptyAdjacentCell2;
    private GridFake gridFake;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        name = "mobile";
        color = "green";
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
    public void given_mobileCreatureAndNoEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Position newPosition = mobileCreature.move(gridFake);

        // Assert (Then)
        assertNull(newPosition);
    }

    @Test
    public void given_mobileCreatureThatAlwaysMovesAndOneEmptyAdjacentCell_when_move_then_returnsEmptyAdjacentCell() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1));

        // Act (When)
        Position newPosition = mobileCreature.move(gridFake);

        // Assert (Then)
        assertNotNull(newPosition);
        assertEquals(mobileCreature.getPosition(), newPosition);
        assertEquals(emptyAdjacentCell1, newPosition);
    }

    @Test
    public void given_mobileCreatureThatAlwaysMovesAndEmptyAdjacentCells_when_move_then_returnsOneOfTheEmptyAdjacentCells() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition = mobileCreature.move(gridFake);

        // Assert (Then)
        assertNotNull(newPosition);
        assertEquals(mobileCreature.getPosition(), newPosition);
        assertTrue(List.of(emptyAdjacentCell1, emptyAdjacentCell2).contains(newPosition));
    }

    @ParameterizedTest(name = "Test {0}% distribution")
    @CsvSource({
            "0.50", // 50/50 case
            "0.75", // 75/25 case
            "0.90"  // 90/10 case
    })
    public void given_mobileCreatureThatMovesSomeTimesAndEmptyAdjacentCells_when_move_then_returnsOneOfTheEmptyAdjacentCellsSomeTimes(double p) {
        int moveCount = 0;
        int n = 1000;
        // Statistical boundaries for 99.99% confidence interval
        // 1. Calculate Mean (μ)
        double mean = n * p;
        // 2. Calculate Standard Deviation (σ) = sqrt(n * p * (1-p))
        double sigma = Math.sqrt(n * p * (1 - p));
        // 3. Define 99.99% confidence interval (3.891 standard deviations)
        double margin = 3.891 * sigma;
        double lowerBound = mean - margin;
        double upperBound = mean + margin;

        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, p, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        for (int i = 0; i < n; i++) {
            if (mobileCreature.move(gridFake) != null) {
                moveCount++;
            }
        }

        // Assert (Then)
        assertTrue(moveCount >= lowerBound && moveCount <= upperBound);
    }

    // --- Test multiply -----------------------------------------------------------------------------------------------

    @Test
    public void given_mobileCreatureAndNoEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Creature child = mobileCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child);
    }

    @Test
    public void given_mobileCreatureAndEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Creature child = mobileCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child);
    }
}