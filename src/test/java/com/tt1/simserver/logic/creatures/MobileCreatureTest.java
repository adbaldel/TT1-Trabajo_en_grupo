package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.mocks.logic.SimulationGridFake;
import com.tt1.simserver.model.Creature;
import com.tt1.simserver.model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MobileCreatureTest {
    private String id;
    private String name;
    private String color;
    private Position position;
    private int starvationThreshold;
    private Random random;
    private Position emptyAdjacentCell1;
    private Position emptyAdjacentCell2;
    private SimulationGridFake gridFake;
    private MobileCreature mobileCreature;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        id = "id-mobile_test";
        name = "mobile_test";
        color = "mobile_color";
        position = new Position(0, 0);
        starvationThreshold = 5;
        random = new Random();
        emptyAdjacentCell1 = new Position(1, 0);
        emptyAdjacentCell2 = new Position(0, 1);
        gridFake = new SimulationGridFake();
    }

    @AfterEach
    public void tearDown() {
        id = null;
        name = null;
        color = null;
        position = null;
        starvationThreshold = 0;
        random = null;
        emptyAdjacentCell1 = null;
        emptyAdjacentCell2 = null;
        gridFake = null;
        mobileCreature = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test performEat() -------------------------------------------------------------------------------------------

    @DisplayName("performEat: sobrevive 0 ticks sin comida")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {0, 1, 5, 10})
    public void given_newMobileCreatureAndNoFood_when_performEat_then_survivesZeroTicks() {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        // Do nothing

        // Assert (Then)
        assertTrue(mobileCreature.isAlive(), String.format("La criatura móvil con starvationThreshold %d no ha " +
                "sobrevivido %d ticks sin comida.", starvationThreshold, 0));
    }

    @DisplayName("performEat: sobrevive starvationThreshold ticks sin comida")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {0, 1, 5, 10})
    public void given_newMobileCreatureAndNoFood_when_performEat_then_isAliveInStarvationThresholdTicks(int starvationThreshold) {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        for (int i = 0; i < starvationThreshold; i++) {
            mobileCreature.performEat();
        }

        // Assert (Then)
        assertTrue(mobileCreature.isAlive(), String.format("La criatura móvil con starvationThreshold %d no ha " +
                "sobrevivido %d ticks sin comida.", starvationThreshold, starvationThreshold));
    }

    @DisplayName("performEat: muere en starvationThreshold+1 ticks sin comida")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {0, 1, 5, 10})
    public void given_newMobileCreatureAndNoFood_when_performEat_then_isNotAliveInStarvationThresholdTicksPlusOne(int starvationThreshold) {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        for (int i = 0; i < starvationThreshold; i++) {
            mobileCreature.performEat();
        }
        // Muere en el siguiente tick
        mobileCreature.performEat();

        // Assert (Then)
        assertFalse(mobileCreature.isAlive(), String.format("La criatura móvil con starvationThreshold %d ha " +
                "sobrevivido %d ticks sin comida.", starvationThreshold, starvationThreshold + 1));
    }

    @DisplayName("performEat: sobrevive starvationThreshold ticks sin comida después de comer")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {1, 5, 10})
    public void given_newMobileCreatureAndFood_when_performEat_then_isAliveInStarvationThresholdTicksAfterEating(int starvationThreshold) {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        // Un tick sin comida
        mobileCreature.performEat();
        // Siguiente tick recibe comida
        gridFake.setFoodPositions(List.of(position));
        mobileCreature.performEat();
        // Aguanta sin comer el máximo
        gridFake.setFoodPositions(List.of());
        for (int i = 0; i < starvationThreshold; i++) {
            mobileCreature.performEat();
        }

        // Assert (Then)
        assertTrue(mobileCreature.isAlive(), String.format("La criatura móvil con starvationThreshold %d no ha " +
                "sobrevivido %d ticks sin comida después de comer.", starvationThreshold, starvationThreshold));
    }

    @DisplayName("performEat: muere en starvationThreshold+1 ticks sin comida después de comer")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {1, 5, 10})
    public void given_newMobileCreatureAndFood_when_performEat_then_isNotAliveInStarvationThresholdTicksPlusOneAfterEating(int starvationThreshold) {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        // Un tick sin comida
        mobileCreature.performEat();
        // Siguiente tick recibe comida
        gridFake.setFoodPositions(List.of(position));
        mobileCreature.performEat();
        // Aguanta sin comer el máximo
        gridFake.setFoodPositions(List.of());
        for (int i = 0; i < starvationThreshold; i++) {
            mobileCreature.performEat();
        }
        // Muere en el siguiente tick
        mobileCreature.performEat();

        // Assert (Then)
        assertFalse(mobileCreature.isAlive(), String.format("La criatura móvil con starvationThreshold %d ha " +
                "sobrevivido %d ticks sin comida después de comer.", starvationThreshold, starvationThreshold + 1));
    }

    // --- Test performMove() ------------------------------------------------------------------------------------------

    @Test
    @DisplayName("performMove: sin casillas adyacentes vacías no se mueve")
    public void given_mobileCreatureAndNoEmptyAdjacentCells_when_performMove_then_returnsNull() {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of());

        // Act (When)
        Position newPosition = mobileCreature.performMove();

        // Assert (Then)
        assertNull(newPosition, "La criatura móvil se ha movido sin tener casillas adyacentes vacías.");
    }

    @Test
    @DisplayName("performMove: con casillas adyacentes vacías se mueve a una")
    public void given_mobileCreatureThatAlwaysMovesAndEmptyAdjacentCells_when_performMove_then_returnsOneOfTheEmptyAdjacentCells() {
        int emptyAdjacentCell1Count = 0;
        int emptyAdjacentCell2Count = 0;
        int numberOfEmptyAdjacentCells = 2;
        double p = 1.0 / numberOfEmptyAdjacentCells;
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
        boolean notMoved = false;
        boolean positionNotUpdated = false;
        boolean notEmptyAdjacentCell = false;

        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition;
        for (int i = 0; i < n; i++) {
            newPosition = mobileCreature.performMove();

            notMoved = newPosition == null;
            if (notMoved) break;

            positionNotUpdated = !newPosition.equals(mobileCreature.getPosition());
            if (positionNotUpdated) break;

            if (mobileCreature.getPosition().equals(emptyAdjacentCell1)) {
                emptyAdjacentCell1Count++;
            } else if (mobileCreature.getPosition().equals(emptyAdjacentCell2)) {
                emptyAdjacentCell2Count++;
            } else {
                notEmptyAdjacentCell = true;
                break;
            }
        }

        // Assert (Then)
        assertFalse(notMoved, "La criatura móvil con probabilidad 1 de moverse no se ha movido teniendo casillas " +
                "adyacentes vacías.");
        assertFalse(positionNotUpdated, "La criatura móvil no ha actualizado su posición después de moverse.");
        assertFalse(notEmptyAdjacentCell, "La criatura móvil se ha movido a una casilla no vacía.");
        assertTrue((emptyAdjacentCell1Count >= lowerBound && emptyAdjacentCell1Count <= upperBound)
                && (emptyAdjacentCell2Count >= lowerBound && emptyAdjacentCell2Count <= upperBound), String.format(
                "La criatura móvil no se mueve con igual probabilidad a las casillas adyacentes. Esperado valor entre" +
                        " [%.2f, %.2f], Actual: %d (casilla 1), %d (casilla 2).", lowerBound, upperBound,
                emptyAdjacentCell1Count, emptyAdjacentCell2Count));

    }

    @DisplayName("performMove: se mueve con la probabilidad definida")
    @ParameterizedTest(name = "{displayName} - {0} probabilidad de moverse")
    @ValueSource(doubles = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0})
    public void given_mobileCreatureThatMovesSomeTimesAndEmptyAdjacentCells_when_performMove_then_returnsOneOfTheEmptyAdjacentCellsSomeTimes(double p) {
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
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, p, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        for (int i = 0; i < n; i++) {
            if (mobileCreature.performMove() != null) {
                moveCount++;
            }
        }

        // Assert (Then)
        assertTrue(moveCount >= lowerBound && moveCount <= upperBound, String.format("La criatura móvil no se mueve " +
                        "con su probabilidad definida %.2f. Esperado valor entre [%.2f, %.2f], Actual: %d.", p,
                lowerBound,
                upperBound, moveCount));
    }

    // --- Test performMultiply() --------------------------------------------------------------------------------------

    @Test
    @DisplayName("performMultiply: sin casillas adyacentes vacías no se reproduce")
    public void given_mobileCreatureAndNoEmptyAdjacentCells_when_performMultiply_then_returnsNull() {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of());

        // Act (When)
        LogicCreatureInterface child = mobileCreature.performMultiply();

        // Assert (Then)
        assertNull(child, "La criatura móvil se ha reproducido sin tener casillas adyacentes vacías.");
    }

    @Test
    @DisplayName("performMultiply: con casillas adyacentes vacías no se reproduce")
    public void given_mobileCreatureAndEmptyAdjacentCells_when_performMultiply_then_returnsNull() {
        // Arrange (Given)
        mobileCreature = new MobileCreature(new Creature(id, name), starvationThreshold, 1.0, position, random);
        mobileCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        LogicCreatureInterface child = mobileCreature.performMultiply();

        // Assert (Then)
        assertNull(child, "La criatura móvil se ha reproducido teniendo casillas adyacentes vacías.");
    }
}