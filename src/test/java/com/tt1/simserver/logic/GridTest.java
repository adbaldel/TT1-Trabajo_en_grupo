package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.CreatureFake;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;
import com.tt1.simserver.model.creatures.MobileCreature;
import com.tt1.simserver.model.creatures.StaticCreature;
import com.tt1.simserver.model.creatures.StaticRabbit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("Constructor: Calcula el tamaño inicial del tablero según las criaturas y la ocupación")
    @ParameterizedTest(name = "Tablero con {0} criaturas al {1} de ocupación")
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
        assertEquals(size, newGrid.getSize(), "El tamaño del tablero debe ser la raíz cuadrada de las criaturas divididas por la ocupación (redondeado hacia arriba)");
    }

    @Test
    @DisplayName("Constructor: Inicializa el tablero con una lista de criaturas dadas")
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
        assertEquals(size, newGrid.getSize(), "El tamaño del tablero debe basarse en el total de la lista de criaturas inyectada");
        assertEquals(c1, newGrid.getCreature(p1), "La primera criatura debe colocarse en su posición asignada durante la creación del tablero");
        assertEquals(c2, newGrid.getCreature(p2), "La segunda criatura debe colocarse en su posición asignada durante la creación del tablero");
    }

    // --- Test tick ---------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Turno: Una criatura que no se mueve ni se reproduce conserva su posición")
    void given_gridWithCreatureThatDoesNotMoveNorMultiply_when_tick_then_creatureStaysInPlace() {
        // Arrange (Given)
        Position initialPos = center;
        creatureFake.setPosition(initialPos);

        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(creatureFake);

        // Act (When)
        grid.tick();

        // Assert (Then)
        assertEquals(creatureFake, grid.getCreature(initialPos), "La criatura no debe cambiar de casilla si no realiza ningún movimiento");
        assertTrue(creatureFake.isMoveCalled(), "El turno siempre debe evaluar si la criatura quiere moverse");
        assertTrue(creatureFake.isMultiplyCalled(), "El turno siempre debe evaluar si la criatura quiere reproducirse");
        assertEquals(1, creatureFake.getTimesMoveCalled(), "Solo se debe evaluar el movimiento una vez por turno");
        assertEquals(1, creatureFake.getTimesMultiplyCalled(), "Solo se debe evaluar la reproducción una vez por turno");
        assertTrue(creatureFake.getMoveCalledTime() <= creatureFake.getMultiplyCalledTime(), "La regla de negocio dicta que el movimiento ocurre antes que la reproducción");
    }

    @Test
    @DisplayName("Turno: Una criatura que se mueve ocupa su nueva posición y deja la anterior vacía")
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
        assertNull(grid.getCreature(initialPos), "La casilla de origen debe quedar libre tras completarse el movimiento");
        assertEquals(creatureFake, grid.getCreature(newPos), "La criatura debe estar físicamente en la nueva casilla de destino tras moverse");
        assertTrue(creatureFake.isMoveCalled(), "El turno debe procesar la orden de movimiento de la criatura");
        assertTrue(creatureFake.isMultiplyCalled(), "El turno debe procesar la orden de reproducción incluso tras moverse");
        assertEquals(1, creatureFake.getTimesMoveCalled(), "El desplazamiento debe ocurrir exactamente una vez por turno");
        assertEquals(1, creatureFake.getTimesMultiplyCalled(), "El intento de reproducción debe ocurrir exactamente una vez por turno");
        assertTrue(creatureFake.getMoveCalledTime() <= creatureFake.getMultiplyCalledTime(), "El desplazamiento de la criatura tiene prioridad cronológica sobre su reproducción");
    }

    @Test
    @DisplayName("Turno: Una criatura que se reproduce añade su cría al tablero")
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
        assertEquals(parent, grid.getCreature(parentPos), "El progenitor debe mantener su posición actual tras la reproducción");
        assertEquals(child, grid.getCreature(childPos), "La cría recién nacida debe aparecer en la casilla de destino de la reproducción");
        assertTrue(parent.isMoveCalled(), "Se debe procesar el movimiento del progenitor en su turno");
        assertTrue(parent.isMultiplyCalled(), "Se debe ejecutar el evento de reproducción del progenitor");
        assertEquals(1, parent.getTimesMoveCalled(), "El progenitor solo intenta moverse una vez");
        assertEquals(1, parent.getTimesMultiplyCalled(), "El progenitor solo intenta reproducirse una vez");
        assertTrue(parent.getMoveCalledTime() <= parent.getMultiplyCalledTime(), "El progenitor se mueve antes de reproducirse");
        assertFalse(child.isMoveCalled(), "Las criaturas recién nacidas no pueden moverse en el turno en el que aparecen");
        assertFalse(child.isMultiplyCalled(), "Las criaturas recién nacidas no pueden reproducirse en el turno en el que aparecen");
        assertEquals(0, child.getTimesMoveCalled(), "La cría no debe acumular intentos de movimiento en su primer turno");
        assertEquals(0, child.getTimesMultiplyCalled(), "La cría no debe acumular intentos de reproducción en su primer turno");
    }

    // --- Test getCreature --------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Obtener criatura: Devuelve la criatura que ocupa la posición solicitada")
    void given_gridWithCreature_when_getCreature_then_returnsCreature() {
        // Arrange (Given)
        Position pos = new Position(3, 3);
        creatureFake.setPosition(pos);

        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(creatureFake);

        // Act (When)
        CreatureInterface retrieved = grid.getCreature(pos);

        // Assert (Then)
        assertEquals(creatureFake, retrieved, "Debe recuperar exactamente la criatura que fue colocada en esa casilla");
    }

    @Test
    @DisplayName("Obtener criatura: Devuelve null si se consulta una casilla libre")
    void given_emptyGridCell_when_getCreature_then_returnsNull() {
        // Arrange (Given)
        Position pos = new Position(3, 3);
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        CreatureInterface retrieved = grid.getCreature(pos);

        // Assert (Then)
        assertNull(retrieved, "Una casilla sin criaturas debe devolver null");
    }

    // --- Test getSize ------------------------------------------------------------------------------------------------

    @DisplayName("Tamaño: Devuelve la dimensión correcta del lado del tablero")
    @ParameterizedTest(name = "Tablero con {0} criaturas al {1} de ocupación")
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
        assertEquals(expectedSize, size, "El tamaño devuelto por el tablero debe coincidir con su tamaño matemático original");
    }

    // --- Test isEmpty ------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Casilla libre: Detecta que una posición sin criaturas está vacía")
    void given_emptyCell_when_isEmpty_then_returnsTrue() {
        // Arrange (Given)
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act & Assert (When & Then)
        assertTrue(grid.isEmpty(new Position(2, 2)), "El tablero debe indicar que no hay ninguna criatura en la casilla");
    }

    @Test
    @DisplayName("Casilla libre: Detecta que una posición con una criatura no está vacía")
    void given_occupiedCell_when_isEmpty_then_returnsFalse() {
        // Arrange (Given)
        Position pos = new Position(2, 2);
        creatureFake.setPosition(pos);

        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(creatureFake);

        // Act & Assert (When & Then)
        assertFalse(grid.isEmpty(pos), "El tablero debe indicar que la casilla está ocupada");
    }

    @Test
    @DisplayName("Casilla libre: Las posiciones fuera de los límites del tablero nunca están libres")
    void given_outOfBoundsPositions_when_isEmpty_then_returnsFalse() {
        // Arrange (Given)
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act & Assert (When & Then)
        assertFalse(grid.isEmpty(new Position(-1, 0)), "Una posición con eje X negativo es inválida y no puede usarse");
        assertFalse(grid.isEmpty(new Position(0, -1)), "Una posición con eje Y negativo es inválida y no puede usarse");
        assertFalse(grid.isEmpty(new Position(5, 0)), "Una posición más allá del tamaño máximo en X no es válida");
        assertFalse(grid.isEmpty(new Position(0, 5)), "Una posición más allá del tamaño máximo en Y no es válida");
    }

    // --- Test addCreature --------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Añadir criatura: Registra correctamente la nueva criatura en la casilla solicitada")
    void given_emptyGridAndCreature_when_addCreature_then_creatureIsPlacedInGrid() {
        // Arrange (Given)
        Position pos = new Position(4, 4);
        creatureFake.setPosition(pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        grid.addCreature(creatureFake);

        // Assert (Then)
        assertEquals(creatureFake, grid.getCreature(pos), "El tablero debe almacenar la criatura en las coordenadas exactas de su posición");
        assertFalse(grid.isEmpty(pos), "La casilla ya no puede constar como libre tras añadir la criatura");
    }

    // --- Test getAdjacentEmptyCells ----------------------------------------------------------------------------------

    @Test
    @DisplayName("Casillas adyacentes: Devuelve las 4 posiciones si están libres")
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
        assertEquals(4, adjacents.size(), "Una casilla central sin vecinos debe tener 4 casillas adyacentes libres");
        assertTrue(adjacents.contains(right), "La casilla de la derecha debe considerarse libre");
        assertTrue(adjacents.contains(up), "La casilla superior debe considerarse libre");
        assertTrue(adjacents.contains(left), "La casilla de la izquierda debe considerarse libre");
        assertTrue(adjacents.contains(down), "La casilla inferior debe considerarse libre");
    }

    @Test
    @DisplayName("Casillas adyacentes: Filtra las posiciones ocupadas por otras criaturas")
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
        assertEquals(2, adjacents.size(), "Solo deben devolverse las casillas que no contienen a otra criatura");
        assertTrue(adjacents.contains(right), "La casilla de la derecha sigue libre y debe incluirse");
        assertTrue(adjacents.contains(down), "La casilla inferior sigue libre y debe incluirse");
        assertFalse(adjacents.contains(up), "La casilla superior tiene una criatura y debe excluirse de la lista");
        assertFalse(adjacents.contains(left), "La casilla izquierda tiene una criatura y debe excluirse de la lista");
    }

    @Test
    @DisplayName("Casillas adyacentes: Excluye posiciones que caen fuera de los bordes del tablero")
    void given_gridWithCornerCell_when_getAdjacentEmptyCells_then_returnsOnlyInBoundsPositions() {
        // Arrange (Given)
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        List<Position> adjacents = grid.getAdjacentEmptyCells(topLeft);

        // Assert (Then)
        assertEquals(2, adjacents.size(), "Una casilla en la esquina solo tiene un máximo de 2 casillas adyacentes dentro del tablero");
        assertTrue(adjacents.contains(new Position(1, 0)), "La casilla derecha es válida porque está dentro del tablero");
        assertTrue(adjacents.contains(new Position(0, 1)), "La casilla inferior (o superior dependiendo del sistema) es válida porque está dentro del tablero");
    }


    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test tick ---------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Turno: Las criaturas reales estáticas conservan su posición intacta")
    void integration_given_gridWithStaticCreature_when_tick_then_creatureStaysInPlace() {
        // Arrange (Given)
        Position initialPos = center;
        StaticCreature perezoso = new StaticCreature("perezoso", "green", initialPos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(perezoso);

        // Act (When)
        grid.tick();

        // Assert (Then)
        assertEquals(perezoso, grid.getCreature(initialPos), "El tipo StaticCreature carece de comportamiento de movimiento y debe seguir en su casilla original");
    }

    @Test
    @DisplayName("Integración Turno: Las criaturas reales móviles actualizan su casilla tras el desplazamiento")
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
        assertNull(grid.getCreature(initialPos), "La casilla de origen de la criatura móvil debe quedar vacía");
        Position newPos = gato.getPosition();
        assertNotEquals(initialPos, newPos, "Las coordenadas de la criatura deben ser distintas a las de origen");
        assertEquals(gato, grid.getCreature(newPos), "El tablero debe ubicar a la criatura móvil en su nueva casilla destino");
    }

    @Test
    @DisplayName("Integración Turno: La reproducción real añade una nueva instancia de cría al tablero")
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
        assertEquals(parent, grid.getCreature(parentPos), "El conejo original no debe cambiar de casilla durante la reproducción");

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

        assertEquals(1, childrenCount, "Debe existir exactamente una casilla adyacente ocupada por la nueva cría generada");
    }

    // --- Test getCreature --------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Obtener criatura: Recupera instancias de criaturas reales guardadas")
    void integration_given_gridWithRealCreature_when_getCreature_then_returnsCreature() {
        // Arrange (Given)
        Position pos = new Position(3, 3);
        StaticCreature realCreature = new StaticCreature("perezoso", "green", pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(realCreature);

        // Act (When)
        CreatureInterface retrieved = grid.getCreature(pos);

        // Assert (Then)
        assertEquals(realCreature, retrieved, "El tablero debe devolver la instancia original de la criatura colocada en esa casilla");
    }

    // --- Test getSize ------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Tamaño: Resuelve matemáticamente el área para una población y ocupación")
    void integration_given_grid_when_getSize_then_returnsCorrectSizeCalculated() {
        // Arrange (Given)
        // 15 criaturas, ocupación 0.35 -> Math.ceil(Math.sqrt(15 / 0.35)) = ceil(sqrt(42.85)) = ceil(6.54) = 7
        Grid localGrid = new Grid(15, 0.35);

        // Act (When)
        int size = localGrid.getSize();

        // Assert (Then)
        assertEquals(7, size, "El tamaño del tablero calculado dinámicamente debe ser 7 para 15 criaturas al 35% de ocupación");
    }

    // --- Test isEmpty ------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Casilla libre: Bloquea casillas utilizadas por criaturas reales")
    void integration_given_occupiedCellByRealCreature_when_isEmpty_then_returnsFalse() {
        // Arrange (Given)
        Position pos = center;
        StaticCreature realCreature = new StaticCreature("perezoso", "green", pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);
        grid.addCreature(realCreature);

        // Act & Assert (When & Then)
        assertFalse(grid.isEmpty(pos), "El tablero debe reconocer que la casilla está ocupada por una criatura real");
    }

    // --- Test addCreature --------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Añadir criatura: Inserta criaturas reales directamente al tablero")
    void integration_given_emptyGridAndRealCreature_when_addCreature_then_creatureIsPlacedInGrid() {
        // Arrange (Given)
        Position pos = new Position(4, 4);
        StaticCreature realCreature = new StaticCreature("perezoso", "green", pos);
        Grid grid = new Grid(numberOfCreatures, occupancy);

        // Act (When)
        grid.addCreature(realCreature);

        // Assert (Then)
        assertEquals(realCreature, grid.getCreature(pos), "La criatura real debe quedar vinculada a la posición en el mapa del tablero");
        assertFalse(grid.isEmpty(pos), "La casilla que recibe a la criatura real debe dejar de estar libre");
    }

    // --- Test getAdjacentEmptyCells ----------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Casillas adyacentes: Discrimina vecinos según estén libres u ocupados por criaturas reales")
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
        assertEquals(2, adjacents.size(), "Solo las casillas sin criaturas reales deben incluirse como adyacentes disponibles");
        assertTrue(adjacents.contains(right), "La casilla derecha no tiene criaturas reales y es válida");
        assertTrue(adjacents.contains(down), "La casilla inferior no tiene criaturas reales y es válida");
        assertFalse(adjacents.contains(up), "La casilla superior choca con una criatura real y debe descartarse");
        assertFalse(adjacents.contains(left), "La casilla izquierda choca con una criatura real y debe descartarse");
    }
}