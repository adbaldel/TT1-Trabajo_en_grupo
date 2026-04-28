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

public class StaticRabbitTest {

    private String name;
    private String color;
    private Position position;
    private Position emptyAdjacentCell1;
    private Position emptyAdjacentCell2;
    private GridFake gridFake;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        name = "rabbit";
        color = "blue";
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
    public void given_staticRabbitAndNoEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Position newPosition = staticRabbit.move(gridFake);

        // Assert (Then)
        assertNull(newPosition);
        assertEquals(position, staticRabbit.getPosition());
    }

    @Test
    public void given_staticRabbitAndEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition = staticRabbit.move(gridFake);

        // Assert (Then)
        assertNull(newPosition);
        assertEquals(position, staticRabbit.getPosition());
    }

    // --- Test multiply -----------------------------------------------------------------------------------------------

    @Test
    public void given_staticRabbitAndNoEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Creature child = staticRabbit.multiply(gridFake);

        // Assert (Then)
        assertNull(child);
    }

    @Test
    public void given_staticRabbitThatAlwaysMultipliesAndOneEmptyAdjacentCell_when_multiply_then_returnsChildInEmptyAdjacentCell() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1));

        // Act (When)
        Creature child = staticRabbit.multiply(gridFake);

        // Assert (Then)
        assertNotNull(child);
        assertEquals(staticRabbit.getName(), child.getName());
        assertEquals(staticRabbit.getColor(), child.getColor());
        assertEquals(staticRabbit.getClass(), child.getClass());
        assertEquals(staticRabbit.getMultiplyProbability(), ((StaticRabbit) child).getMultiplyProbability());
        assertEquals(emptyAdjacentCell1, child.getPosition());
    }

    @Test
    public void given_staticRabbitThatAlwaysMultipliesAndEmptyAdjacentCells_when_multiply_then_returnsChildInEmptyAdjacentCells() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Creature child = staticRabbit.multiply(gridFake);

        // Assert (Then)
        assertNotNull(child);
        assertEquals(staticRabbit.getName(), child.getName());
        assertEquals(staticRabbit.getColor(), child.getColor());
        assertEquals(staticRabbit.getClass(), child.getClass());
        assertEquals(staticRabbit.getMultiplyProbability(), ((StaticRabbit) child).getMultiplyProbability());
        assertTrue(List.of(emptyAdjacentCell1, emptyAdjacentCell2).contains(child.getPosition()));
    }

    @ParameterizedTest(name = "Test {0}% distribution")
    @CsvSource({
            "0.50", // 50/50 case
            "0.75", // 75/25 case
            "0.90"  // 90/10 case
    })
    public void given_staticRabbitThatMultipliesSomeTimesAndEmptyAdjacentCells_when_multiply_then_returnsChildInEmptyAdjacentCellsSomeTimes(double p) {
        int childCount = 0;
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
        StaticRabbit staticRabbit = new StaticRabbit(name, color, p, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        for (int i = 0; i < n; i++) {
            if (staticRabbit.multiply(gridFake) != null) {
                childCount++;
            }
        }

        // Assert (Then)
        assertTrue(childCount >= lowerBound && childCount <= upperBound);
    }
}