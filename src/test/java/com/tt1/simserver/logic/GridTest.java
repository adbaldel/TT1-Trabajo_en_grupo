package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.CreatureFake;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;
import com.tt1.simserver.model.creatures.MobileCreature;
import com.tt1.simserver.model.creatures.StaticCreature;
import com.tt1.simserver.model.creatures.StaticRabbit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private int numberOfCreatures;
    private double occupancy;
    private Position topLeft;
    private Position center;
    private CreatureFake creatureFake;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        numberOfCreatures = 5;
        occupancy = 0.2;
        topLeft = new Position(0, 0);
        center = new Position(2, 2);
        creatureFake = new CreatureFake();
    }

    @AfterEach
    public void tearDown() {
        numberOfCreatures = 0;
        occupancy = 0.0;
        topLeft = null;
        center = null;
        creatureFake = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test Constructors -------------------------------------------------------------------------------------------

    @ParameterizedTest(name = "Test {0} creatures {1}% occupancy grid size")
    @CsvSource({
            "10, 0.35",
            "10, 0.75",
            "10, 0.90"
    })
    void given_numberOfCreaturesAndOccupancy_when_constructor_then_gridSizeIsCalculatedCorrectly(int numberOfCreatures, double occupancy) {
        int size = (int) Math.ceil(Math.sqrt(numberOfCreatures / occupancy));

        // Arrange (Given)
        // Parameters

        // Act (When)
        Grid newGrid = new Grid(numberOfCreatures, occupancy);

        // Assert (Then)
        // size = sqrt(10 / 0.1) = sqrt(100) = 10
        assertEquals(size, newGrid.getSize());
    }

    @Test
    void given_creaturesListAndOccupancy_when_constructor_then_creaturesAreAddedAndSizeIsCalculated() {
        // Arrange (Given)
        Position p1 = topLeft;
        Position p2 = new Position(1, 1);
        CreatureFake c1 = creatureFake;
        c1.setPosition(p1);
        CreatureFake c2 = new CreatureFake();
        c2.setPosition(p2);
        List<CreatureInterface> creatures = List.of(c1, c2);
        double occupancy = 0.5;

        // Act (When)
        int size = (int) Math.ceil(Math.sqrt(creatures.size() / occupancy));
        Grid newGrid = new Grid(creatures, occupancy);

        // Assert (Then)
        assertEquals(size, newGrid.getSize());
        assertEquals(c1, newGrid.getCreature(p1));
        assertEquals(c2, newGrid.getCreature(p2));
    }


    // --- Test tick ---------------------------------------------------------------------------------------------------

    @Test
    void given_gridWithCreatureThatDoesNotMoveNorMultiply_when_tick_then_creatureStaysInPlace() {
        // Arrange (Given)
        Position initialPos = center;
        creatureFake.setPosition(initialPos);

        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(creatureFake);

        // Act (When)
        grid.tick();

        // Assert (Then)
        assertEquals(creatureFake, grid.getCreature(initialPos));
        assertTrue(creatureFake.isMoveCalled());
        assertTrue(creatureFake.isMultiplyCalled());
        assertEquals(1, creatureFake.getTimesMoveCalled());
        assertEquals(1, creatureFake.getTimesMultiplyCalled());
        assertTrue(creatureFake.getMoveCalledTime() <= creatureFake.getMultiplyCalledTime());
    }

    @Test
    void given_gridWithCreatureThatMoves_when_tick_then_creatureIsMovedToNewPosition() {
        // Arrange (Given)
        Position initialPos = new Position(1, 1);
        Position pos = new Position(initialPos.getX(), initialPos.getY());
        Position newPos = new Position(2, 1);
        creatureFake.setPosition(pos);
        creatureFake.setPositionToReturnOnMove(newPos);

        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(creatureFake);

        // Act (When)
        grid.tick();

        // Assert (Then)
        assertNull(grid.getCreature(initialPos));
        assertEquals(creatureFake, grid.getCreature(newPos));
        assertTrue(creatureFake.isMoveCalled());
        assertTrue(creatureFake.isMultiplyCalled());
        assertEquals(1, creatureFake.getTimesMoveCalled());
        assertEquals(1, creatureFake.getTimesMultiplyCalled());
        assertTrue(creatureFake.getMoveCalledTime() <= creatureFake.getMultiplyCalledTime());
    }

    @Test
    void given_gridWithCreatureThatMultiplies_when_tick_then_childIsAddedToGrid() {
        // Arrange (Given)
        Position parentPos = new Position(1, 1);
        Position childPos = new Position(1, 2);

        CreatureFake parent = creatureFake;
        parent.setPosition(parentPos);
        CreatureFake child = new CreatureFake();
        child.setPosition(childPos);

        Grid grid = new Grid(numberOfCreatures, occupancy);

        parent.setCreatureToReturnOnMultiply(child);
        grid.addCreature(parent);

        // Act (When)
        grid.tick();

        // Assert (Then)
        assertEquals(parent, grid.getCreature(parentPos));
        assertEquals(child, grid.getCreature(childPos));
        assertTrue(parent.isMoveCalled());
        assertTrue(parent.isMultiplyCalled());
        assertEquals(1, parent.getTimesMoveCalled());
        assertEquals(1, parent.getTimesMultiplyCalled());
        assertTrue(parent.getMoveCalledTime() <= parent.getMultiplyCalledTime());
        assertFalse(child.isMoveCalled());
        assertFalse(child.isMultiplyCalled());
        assertEquals(0, child.getTimesMoveCalled());
        assertEquals(0, child.getTimesMultiplyCalled());
    }

    // --- Test getCreature --------------------------------------------------------------------------------------------

    @Test
    void given_gridWithCreature_when_getCreature_then_returnsCreature() {
        // Arrange (Given)
        Position pos = new Position(3, 3);
        creatureFake.setPosition(pos);

        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(creatureFake);

        // Act (When)
        CreatureInterface retrieved = grid.getCreature(pos);

        // Assert (Then)
        assertEquals(creatureFake, retrieved);
    }

    @Test
    void given_emptyGridCell_when_getCreature_then_returnsNull() {
        // Arrange (Given)
        Position pos = new Position(3, 3);
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        CreatureInterface retrieved = grid.getCreature(pos);

        // Assert (Then)
        assertNull(retrieved);
    }

    // --- Test getSize ------------------------------------------------------------------------------------------------

    @ParameterizedTest(name = "Test {0} creatures {1}% occupancy grid size")
    @CsvSource({
            "10, 0.35",
            "10, 0.75",
            "10, 0.90"
    })
    void given_grid_when_getSize_then_returnsCorrectSize(int numberOfCreatures, double occupancy) {
        int expectedSize = (int) Math.ceil(Math.sqrt(numberOfCreatures / occupancy));

        // Arrange (Given)
        Grid localGrid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        int size = localGrid.getSize();

        // Assert (Then)
        assertEquals(expectedSize, size);
    }

    // --- Test isEmpty ------------------------------------------------------------------------------------------------

    @Test
    void given_emptyCell_when_isEmpty_then_returnsTrue() {
        // Arrange (Given)
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act & Assert (When & Then)
        assertTrue(grid.isEmpty(new Position(2, 2)));
    }

    @Test
    void given_occupiedCell_when_isEmpty_then_returnsFalse() {
        // Arrange (Given)
        Position pos = new Position(2, 2);
        creatureFake.setPosition(pos);

        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(creatureFake);

        // Act & Assert (When & Then)
        assertFalse(grid.isEmpty(pos));
    }

    @Test
    void given_outOfBoundsPositions_when_isEmpty_then_returnsFalse() {
        // Arrange (Given)
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act & Assert (When & Then)
        assertFalse(grid.isEmpty(new Position(-1, 0))); // Fuera por la izquierda
        assertFalse(grid.isEmpty(new Position(0, -1))); // Fuera por abajo (negativo)
        assertFalse(grid.isEmpty(new Position(5, 0)));  // Fuera por la derecha (tamaño es 5, índice máx es 4)
        assertFalse(grid.isEmpty(new Position(0, 5)));  // Fuera por arriba
    }

    // --- Test addCreature --------------------------------------------------------------------------------------------

    @Test
    void given_emptyGridAndCreature_when_addCreature_then_creatureIsPlacedInGrid() {
        // Arrange (Given)
        Position pos = new Position(4, 4);
        creatureFake.setPosition(pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        grid.addCreature(creatureFake);

        // Assert (Then)
        assertEquals(creatureFake, grid.getCreature(pos));
        assertFalse(grid.isEmpty(pos));
    }

    // --- Test getAdjacentEmptyCells ----------------------------------------------------------------------------------

    @Test
    void given_gridWithMiddleCellAndEmptyAdjacents_when_getAdjacentEmptyCells_then_returnsAllFourPositions() {
        // Arrange (Given)
        Position up = new Position(center.getX(), center.getY());
        up.moveUp();
        Position left = new Position(center.getX(), center.getY());
        left.moveLeft();
        Position right = new Position(center.getX(), center.getY());
        right.moveRight();
        Position down = new Position(center.getX(), center.getY());
        down.moveDown();

        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        List<Position> adjacents = grid.getAdjacentEmptyCells(center);

        // Assert (Then)
        assertEquals(4, adjacents.size());
        assertTrue(adjacents.contains(right)); // Derecha
        assertTrue(adjacents.contains(up)); // Arriba
        assertTrue(adjacents.contains(left)); // Izquierda
        assertTrue(adjacents.contains(down)); // Abajo
    }

    @Test
    void given_gridWithMiddleCellAndSomeOccupiedAdjacents_when_getAdjacentEmptyCells_then_returnsOnlyEmptyPositions() {
        // Arrange (Given)
        Position up = new Position(center.getX(), center.getY());
        up.moveUp();
        Position left = new Position(center.getX(), center.getY());
        left.moveLeft();
        Position right = new Position(center.getX(), center.getY());
        right.moveRight();
        Position down = new Position(center.getX(), center.getY());
        down.moveDown();

        CreatureFake fakeUp = creatureFake;
        fakeUp.setPosition(up);
        CreatureFake fakeLeft = new CreatureFake();
        fakeLeft.setPosition(left);

        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Ocupamos la celda de arriba y de la izquierda
        grid.addCreature(fakeUp);
        grid.addCreature(fakeLeft);

        // Act (When)
        List<Position> adjacents = grid.getAdjacentEmptyCells(center);

        // Assert (Then)
        assertEquals(2, adjacents.size());
        assertTrue(adjacents.contains(right)); // Derecha (vacía)
        assertTrue(adjacents.contains(down)); // Abajo (vacía)
        assertFalse(adjacents.contains(up)); // Arriba (ocupada)
        assertFalse(adjacents.contains(left)); // Izquierda (ocupada)
    }

    @Test
    void given_gridWithCornerCell_when_getAdjacentEmptyCells_then_returnsOnlyInBoundsPositions() {
        // Arrange (Given)
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        List<Position> adjacents = grid.getAdjacentEmptyCells(topLeft);

        // Assert (Then)
        assertEquals(2, adjacents.size());
        // Solo puede devolver arriba y a la derecha, porque izq y abajo son out of bounds
        assertTrue(adjacents.contains(new Position(1, 0))); // Derecha
        assertTrue(adjacents.contains(new Position(0, 1))); // Arriba
    }


    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test tick ---------------------------------------------------------------------------------------------------

    @Test
    void integration_given_gridWithStaticCreature_when_tick_then_creatureStaysInPlace() {
        // Arrange (Given)
        Position initialPos = center;
        StaticCreature perezoso = new StaticCreature("perezoso", "green", initialPos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(perezoso);

        // Act (When)
        grid.tick();

        // Assert (Then)
        // Verificamos que el perezoso no se haya movido tras el tick
        assertEquals(perezoso, grid.getCreature(initialPos));
    }

    @Test
    void integration_given_gridWithMobileCreature_when_tick_then_creatureIsMovedToNewPosition() {
        // Arrange (Given)
        Position initialPos = new Position(1, 1);
        Position pos = new Position(initialPos.getX(), initialPos.getY());
        // Le asignamos probabilidad de 1.0 para forzar el movimiento en este test
        MobileCreature gato = new MobileCreature("gato", "red", 1.0, pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(gato);

        // Act (When)
        grid.tick();

        // Assert (Then)
        assertNull(grid.getCreature(initialPos), "La celda inicial debería estar vacía tras el movimiento.");
        Position newPos = gato.getPosition();
        assertNotEquals(initialPos, newPos, "La criatura debería tener una posición distinta a la inicial.");
        assertEquals(gato, grid.getCreature(newPos), "La criatura debería encontrarse en la nueva posición de la matriz.");
    }

    @Test
    void integration_given_gridWithStaticRabbit_when_tick_then_childIsAddedToGrid() {
        // Arrange (Given)
        Position parentPos = new Position(1, 1);
        // Le asignamos probabilidad de 1.0 para forzar la multiplicación en el test
        StaticRabbit parent = new StaticRabbit("conejo", "blue", 1.0, parentPos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(parent);

        // Act (When)
        grid.tick();

        // Assert (Then)
        assertEquals(parent, grid.getCreature(parentPos), "El padre no debería haberse movido.");

        // Verificamos que se haya añadido exactamente 1 hijo en alguna de las celdas adyacentes
        int childrenCount = 0;
        List<Position> adjacents = List.of(
                new Position(2, 1), new Position(0, 1),
                new Position(1, 2), new Position(1, 0)
        );

        for (Position p : adjacents) {
            if (grid.getCreature(p) != null && grid.getCreature(p) instanceof StaticRabbit) {
                childrenCount++;
            }
        }

        assertEquals(1, childrenCount, "Debería haberse generado exactamente una copia adyacente del conejo.");
    }

    // --- Test getCreature --------------------------------------------------------------------------------------------

    @Test
    void integration_given_gridWithRealCreature_when_getCreature_then_returnsCreature() {
        // Arrange (Given)
        Position pos = new Position(3, 3);
        StaticCreature realCreature = new StaticCreature("perezoso", "green", pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(realCreature);

        // Act (When)
        CreatureInterface retrieved = grid.getCreature(pos);

        // Assert (Then)
        assertEquals(realCreature, retrieved);
    }

    // --- Test getSize ------------------------------------------------------------------------------------------------

    @Test
    void integration_given_grid_when_getSize_then_returnsCorrectSizeCalculated() {
        // Arrange (Given)
        // 15 criaturas, ocupación 0.35 -> Math.ceil(Math.sqrt(15 / 0.35)) = ceil(sqrt(42.85)) = ceil(6.54) = 7
        Grid localGrid = new Grid(15, 0.35);

        // Act (When)
        int size = localGrid.getSize();

        // Assert (Then)
        assertEquals(7, size);
    }

    // --- Test isEmpty ------------------------------------------------------------------------------------------------

    @Test
    void integration_given_occupiedCellByRealCreature_when_isEmpty_then_returnsFalse() {
        // Arrange (Given)
        Position pos = center;
        StaticCreature realCreature = new StaticCreature("perezoso", "green", pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(realCreature);

        // Act & Assert (When & Then)
        assertFalse(grid.isEmpty(pos));
    }

    // --- Test addCreature --------------------------------------------------------------------------------------------

    @Test
    void integration_given_emptyGridAndRealCreature_when_addCreature_then_creatureIsPlacedInGrid() {
        // Arrange (Given)
        Position pos = new Position(4, 4);
        StaticCreature realCreature = new StaticCreature("perezoso", "green", pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        grid.addCreature(realCreature);

        // Assert (Then)
        assertEquals(realCreature, grid.getCreature(pos));
        assertFalse(grid.isEmpty(pos));
    }

    // --- Test getAdjacentEmptyCells ----------------------------------------------------------------------------------

    @Test
    void integration_given_gridWithMiddleCellAndSomeOccupiedAdjacents_when_getAdjacentEmptyCells_then_returnsOnlyEmptyPositions() {
        // Arrange (Given)
        Position up = new Position(center.getX(), center.getY());
        up.moveUp();
        Position left = new Position(center.getX(), center.getY());
        left.moveLeft();
        Position right = new Position(center.getX(), center.getY());
        right.moveRight();
        Position down = new Position(center.getX(), center.getY());
        down.moveDown();

        StaticCreature creatureUp = new StaticCreature("perezoso", "green", up);
        StaticCreature creatureLeft = new StaticCreature("perezoso", "green", left);

        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Ocupamos la celda de arriba y de la izquierda con criaturas reales
        grid.addCreature(creatureUp);
        grid.addCreature(creatureLeft);

        // Act (When)
        List<Position> adjacents = grid.getAdjacentEmptyCells(center);

        // Assert (Then)
        assertEquals(2, adjacents.size());
        assertTrue(adjacents.contains(right)); // Derecha (vacía)
        assertTrue(adjacents.contains(down));  // Abajo (vacía)
        assertFalse(adjacents.contains(up));   // Arriba (ocupada)
        assertFalse(adjacents.contains(left)); // Izquierda (ocupada)
    }
}