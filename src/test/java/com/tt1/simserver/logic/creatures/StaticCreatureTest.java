package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.mocks.logic.SimulationGridFake;
import com.tt1.simserver.model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaticCreatureTest {
    private String id;
    private String name;
    private String color;
    private Position position;
    private int starvationThreshold;
    private Position emptyAdjacentCell1;
    private Position emptyAdjacentCell2;
    private SimulationGridFake gridFake;
    private StaticCreature staticCreature;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        id = "id-static_test";
        name = "static_test";
        color = "static_color";
        position = new Position(0, 0);
        starvationThreshold = 5;
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
        emptyAdjacentCell1 = null;
        emptyAdjacentCell2 = null;
        gridFake = null;
        staticCreature = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test performEat() -------------------------------------------------------------------------------------------

    @DisplayName("performEat: sobrevive 0 ticks sin comida")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {0, 1, 5, 10})
    public void given_newStaticCreatureAndNoFood_when_performEat_then_survivesZeroTicks() {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        // Do nothing

        // Assert (Then)
        assertTrue(staticCreature.isAlive(), String.format("La criatura estática con starvationThreshold %d no ha " +
                "sobrevivido %d ticks sin comida.", starvationThreshold, 0));
    }

    @DisplayName("performEat: sobrevive starvationThreshold ticks sin comida")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {0, 1, 5, 10})
    public void given_newStaticCreatureAndNoFood_when_performEat_then_isAliveInStarvationThresholdTicks(int starvationThreshold) {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        for (int i = 0; i < starvationThreshold; i++) {
            staticCreature.performEat();
        }

        // Assert (Then)
        assertTrue(staticCreature.isAlive(), String.format("La criatura estática con starvationThreshold %d no ha " +
                "sobrevivido %d ticks sin comida.", starvationThreshold, starvationThreshold));
    }

    @DisplayName("performEat: muere en starvationThreshold+1 ticks sin comida")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {0, 1, 5, 10})
    public void given_newStaticCreatureAndNoFood_when_performEat_then_isNotAliveInStarvationThresholdTicksPlusOne(int starvationThreshold) {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        for (int i = 0; i < starvationThreshold; i++) {
            staticCreature.performEat();
        }
        // Muere en el siguiente tick
        staticCreature.performEat();

        // Assert (Then)
        assertFalse(staticCreature.isAlive(), String.format("La criatura estática con starvationThreshold %d ha " +
                "sobrevivido %d ticks sin comida.", starvationThreshold, starvationThreshold + 1));
    }

    @DisplayName("performEat: sobrevive starvationThreshold ticks sin comida después de comer")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {1, 5, 10})
    public void given_newStaticCreatureAndFood_when_performEat_then_isAliveInStarvationThresholdTicksAfterEating(int starvationThreshold) {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        // Un tick sin comida
        staticCreature.performEat();
        // Siguiente tick recibe comida
        gridFake.setFoodPositions(List.of(position));
        staticCreature.performEat();
        // Aguanta sin comer el máximo
        gridFake.setFoodPositions(List.of());
        for (int i = 0; i < starvationThreshold; i++) {
            staticCreature.performEat();
        }

        // Assert (Then)
        assertTrue(staticCreature.isAlive(), String.format("La criatura estática con starvationThreshold %d no ha " +
                "sobrevivido %d ticks sin comida después de comer.", starvationThreshold, starvationThreshold));
    }

    @DisplayName("performEat: muere en starvationThreshold+1 ticks sin comida después de comer")
    @ParameterizedTest(name = "{displayName} - aguanta {0} turnos sin comer")
    @ValueSource(ints = {1, 5, 10})
    public void given_newStaticCreatureAndFood_when_performEat_then_isNotAliveInStarvationThresholdTicksPlusOneAfterEating(int starvationThreshold) {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setFoodPositions(List.of());

        // Act (When)
        // Un tick sin comida
        staticCreature.performEat();
        // Siguiente tick recibe comida
        gridFake.setFoodPositions(List.of(position));
        staticCreature.performEat();
        // Aguanta sin comer el máximo
        gridFake.setFoodPositions(List.of());
        for (int i = 0; i < starvationThreshold; i++) {
            staticCreature.performEat();
        }
        // Muere en el siguiente tick
        staticCreature.performEat();

        // Assert (Then)
        assertFalse(staticCreature.isAlive(), String.format("La criatura estática con starvationThreshold %d ha " +
                "sobrevivido %d ticks sin comida después de comer.", starvationThreshold, starvationThreshold + 1));
    }

    // --- Test performMove() ------------------------------------------------------------------------------------------

    @Test
    @DisplayName("performMove: sin casillas adyacentes vacías no se mueve")
    public void given_staticCreatureAndNoEmptyAdjacentCells_when_performMove_then_returnsNull() {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of());

        // Act (When)
        Position newPosition = staticCreature.performMove();

        // Assert (Then)
        assertNull(newPosition, "La criatura estática se ha movido sin tener casillas adyacentes vacías.");
    }

    @Test
    @DisplayName("performMove: con casillas adyacentes vacías no se mueve")
    public void given_staticCreatureThatAlwaysMovesAndEmptyAdjacentCells_when_performMove_then_returnsNull() {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition = staticCreature.performMove();

        // Assert (Then)
        assertNull(newPosition, "La criatura estática se ha movido teniendo casillas adyacentes vacías.");
    }

    // --- Test performMultiply() --------------------------------------------------------------------------------------

    @Test
    @DisplayName("performMultiply: sin casillas adyacentes vacías no se reproduce")
    public void given_staticCreatureAndNoEmptyAdjacentCells_when_performMultiply_then_returnsNull() {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of());

        // Act (When)
        LogicCreatureInterface child = staticCreature.performMultiply();

        // Assert (Then)
        assertNull(child, "La criatura estática se ha reproducido sin tener casillas adyacentes vacías.");
    }

    @Test
    @DisplayName("performMultiply: con casillas adyacentes vacías no se reproduce")
    public void given_staticCreatureAndEmptyAdjacentCells_when_performMultiply_then_returnsNull() {
        // Arrange (Given)
        staticCreature = new StaticCreature(id, name, color, starvationThreshold, position);
        staticCreature.setSimulationGrid(gridFake);
        gridFake.setEmptyAdjacentCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        LogicCreatureInterface child = staticCreature.performMultiply();

        // Assert (Then)
        assertNull(child, "La criatura estática se ha reproducido teniendo casillas adyacentes vacías.");
    }
}