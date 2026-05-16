package com.tt1.simserver.logic;

import com.tt1.simserver.logic.creatures.MobileCreature;
import com.tt1.simserver.logic.creatures.StaticCreature;
import com.tt1.simserver.logic.creatures.StaticRabbit;
import com.tt1.simserver.mocks.logic.LogicCreatureFake;
import com.tt1.simserver.model.Creature;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.SimulationStep;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SimulationGridTest {
    private int gridSize;
    private int foodPerTick;
    private int starvationThreshold;
    private Position topLeft;
    private Position center;
    private LogicCreatureFake logicCreatureFake1;
    private LogicCreatureFake logicCreatureFake2;
    private Random random;
    private SimulationStep initialStep;
    private SimulationGrid simulationGrid;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    void setUp() {
        gridSize = 10;
        foodPerTick = 20;
        starvationThreshold = 5;
        topLeft = new Position(0, 0);
        center = new Position(5, 5);
        logicCreatureFake1 = new LogicCreatureFake("id-fake-1");
        logicCreatureFake2 = new LogicCreatureFake("id-fake-2");
        random = new Random();

        Map<Position, Creature> stepMap = new HashMap<>();
        stepMap.put(topLeft, logicCreatureFake1);
        stepMap.put(center, logicCreatureFake2);
        logicCreatureFake1.setAlive(true);
        logicCreatureFake2.setAlive(true);
        logicCreatureFake1.setPosition(topLeft);
        logicCreatureFake2.setPosition(center);
        initialStep = new SimulationStep(gridSize, stepMap);
    }

    @AfterEach
    void tearDown() {
        gridSize = 0;
        foodPerTick = 0;
        starvationThreshold = 0;
        topLeft = null;
        center = null;
        logicCreatureFake1 = null;
        logicCreatureFake2 = null;
        random = null;
        initialStep = null;
        simulationGrid = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test SimulationGrid(SimulationStep initialStep, int foodPerTick, Random random) -----------------------------

    @Test
    @DisplayName("Constructor: comprueba que las criaturas están en el tablero en posiciones del step inicial")
    public void given_initialStepWithCreatures_when_newSimulationGrid_then_creaturesAreInExpectedPositions() {
        // Arrange (Given)

        // Act (When)
        simulationGrid = new SimulationGrid(initialStep, foodPerTick, random);

        // Assert (Then)
        assertFalse(simulationGrid.isEmpty(topLeft), String.format("La celda (%d, %d) debería contener una criatura " +
                "proveniente del estado inicial.", topLeft.x(), topLeft.y()));
        assertEquals(logicCreatureFake1, simulationGrid.getCreatureAt(topLeft), String.format("La criatura en (%d, " +
                "%d) no coincide con la esperada del estado inicial.", topLeft.x(), topLeft.y()));
        assertFalse(simulationGrid.isEmpty(center), String.format("La celda (%d, %d) debería contener una criatura " +
                "proveniente del estado inicial.", center.x(), center.y()));
        assertEquals(logicCreatureFake2, simulationGrid.getCreatureAt(center), String.format("La criatura en (%d, %d)" +
                " no coincide con la esperada del estado inicial.", center.x(), center.y()));
        for (int y = 0; y < simulationGrid.getSize(); y++) {
            for (int x = 0; x < simulationGrid.getSize(); x++) {
                Position position = new Position(x, y);
                if (position.equals(topLeft) || position.equals(center)) continue;
                assertTrue(simulationGrid.isEmpty(position),
                        String.format("La celda (%d, %d) no debería contener " + "ninguna criatura.", position.x(),
                                position.y()));
            }
        }
    }

    // --- Test calculateSize(int numberOfCreatures, double occupancy) -------------------------------------------------

    @DisplayName("calculateSize: devuelve el tamaño correcto según la ocupación")
    @ParameterizedTest(name = "{displayName} - {0} criaturas, ocupación {1} -> tamaño {2}")
    @CsvSource({"10, 0.5, 5", "5, 0.1, 8", "1, 1.0, 1", "100, 0.25, 20", "5, 0.35, 4"})
    public void given_numberOfCreaturesAndOccupancy_when_calculateSize_then_returnsExpectedSize(int numCreatures,
                                                                                                double occupancy,
                                                                                                int expectedSize) {
        // Act (When)
        int size = SimulationGrid.calculateSize(numCreatures, occupancy);

        // Assert (Then)
        assertEquals(expectedSize, size, String.format("Para %d criaturas y %.2f ocupación se esperaba un tablero de " +
                "tamaño %d, pero se obtuvo" + " %d.", numCreatures, occupancy, expectedSize, size));
    }

    // --- Test calculateFoodPerTick(int size, double foodProbability) -------------------------------------------------

    @DisplayName("calculateFoodPerTick: devuelve la comida por tick correcta según tamaño y probabilidad")
    @ParameterizedTest(name = "{displayName} - tamaño {0}, prob {1} -> comida {2}")
    @CsvSource({"10, 0.1, 10", "5, 0.5, 13", "10, 1.0, 100", "8, 0.0, 0", "5, 0.2, 5", "11, 0.2, 25"})
    public void given_sizeAndFoodProbability_when_calculateFoodPerTick_then_returnsExpectedFoodCount(int size,
                                                                                                     double prob,
                                                                                                     int expectedFood) {
        // Act (When)
        int food = SimulationGrid.calculateFoodPerTick(size, prob);

        // Assert (Then)
        assertEquals(expectedFood, food, String.format("Para tablero de tamaño %d y probabilidad %.2f se esperaba %d " +
                "comida, pero se obtuvo %d" + ".", size, prob, expectedFood, food));
    }

    // --- Test tick() -------------------------------------------------------------------------------------------------

    @DisplayName("tick: distribuye comida equiprobablemente y respeta foodPerTick")
    @ParameterizedTest(name = "{displayName} - tamaño 10, foodPerTick {0}}")
    @ValueSource(ints = {0, 1, 10, 20, 30, 40, 50, 60, 70, 80, 90, 99, 100})
    public void given_simulationGrid_when_tickMultipleTimes_then_foodIsDistributedEquiprobably(int foodPerTick) {
        // Arrange (Given)
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);

        int n = 1000;
        double p = (double) foodPerTick / (gridSize * gridSize);
        double mean = n * p;
        double sigma = Math.sqrt(n * p * (1 - p));
        double margin = 3.891 * sigma;
        double lowerBound = mean - margin;
        double upperBound = mean + margin;

        int hitsAtTopLeft = 0;
        int hitsAtCenter = 0;

        // Act (When)
        for (int i = 0; i < n; i++) {
            simulationGrid.tick();
            if (simulationGrid.hasFood(topLeft)) {
                hitsAtTopLeft++;
            }
            if (simulationGrid.hasFood(center)) {
                hitsAtCenter++;
            }
        }

        // Assert (Then)
        assertTrue((hitsAtTopLeft >= lowerBound && hitsAtTopLeft <= upperBound) && (hitsAtCenter >= lowerBound && hitsAtCenter <= upperBound), String.format("La comida no se distribuye equiprobablemente. Esperado en [%.2f, %.2f] hits, Actual: " + "%d (topLeft), %d (center).", lowerBound, upperBound, hitsAtTopLeft, hitsAtCenter));
    }

    @Test
    @DisplayName("tick: las criaturas actúan en orden izquierda-derecha, arriba-abajo y reciben órdenes")
    public void given_creaturesInGrid_when_tick_then_actInOrderLeftToRightTopToBottom() {
        // Arrange (Given)
        simulationGrid = new SimulationGrid(initialStep, 0, random);
        logicCreatureFake1.setAlive(true);
        logicCreatureFake2.setAlive(true);

        simulationGrid.addCreature(logicCreatureFake1);
        simulationGrid.addCreature(logicCreatureFake2);

        // Act (When)
        simulationGrid.tick();

        // Assert (Then)
        assertTrue((logicCreatureFake1.getTimesMoveCalled() == 1 && logicCreatureFake2.getTimesMoveCalled() == 1) && (logicCreatureFake1.getTimesMultiplyCalled() == 1 && logicCreatureFake2.getTimesMultiplyCalled() == 1) && (logicCreatureFake1.getTimesEatCalled() == 1 && logicCreatureFake2.getTimesEatCalled() == 1), "Las criaturas no recibieron sus órdenes de actuar (comer/mover/reproducir) o las " + "recibieron más de una vez.");
        assertTrue((logicCreatureFake1.getMoveCalledTime() <= logicCreatureFake1.getMultiplyCalledTime()) && (logicCreatureFake1.getMultiplyCalledTime() <= logicCreatureFake1.getEatCalledTime()) && (logicCreatureFake2.getMoveCalledTime() <= logicCreatureFake2.getMultiplyCalledTime()) && (logicCreatureFake2.getMultiplyCalledTime() <= logicCreatureFake2.getEatCalledTime()), "Las criaturas no recibieron sus órdenes de actuar (comer/mover/reproducir) en el orden" + " especificado mover->reproducir->comer.");
        assertTrue(logicCreatureFake1.getEatCalledTime() <= logicCreatureFake2.getMoveCalledTime(), "La criatura en " +
                "topLeft debía actuar antes que la de center, pero actuó después.");
    }

    @Test
    @DisplayName("tick: actualiza la posición si la criatura se mueve")
    public void given_mobileFakeCreature_when_tick_then_positionIsUpdatedInGrid() {
        // Arrange (Given)
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);
        Position newPosition = new Position(1, 0);
        logicCreatureFake1.setPositionToReturnOnMove(newPosition);
        simulationGrid.addCreature(logicCreatureFake1);

        // Act (When)
        simulationGrid.tick();

        // Assert (Then)
        assertTrue(simulationGrid.isEmpty(topLeft), String.format("La antigua posición (%d,%d) debía quedar vacía " +
                "tras el movimiento.", topLeft.x(), topLeft.y()));
        assertFalse(simulationGrid.isEmpty(newPosition) || !logicCreatureFake1.equals(simulationGrid.getCreatureAt(newPosition)), String.format("La nueva posición (%d,%d) debía estar ocupada tras el movimiento.", newPosition.x(), newPosition.y()));
    }

    @Test
    @DisplayName("tick: añade la cría al tablero si la criatura se reproduce")
    public void given_multiplyingFakeCreature_when_tick_then_childIsAddedToGrid() {
        // Arrange (Given)
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);
        LogicCreatureFake parent = logicCreatureFake1;
        Position childPosition = new Position(1, 0);
        LogicCreatureFake child = logicCreatureFake2;
        child.setPosition(childPosition);

        parent.setCreatureToReturnOnMultiply(child);
        simulationGrid.addCreature(parent);

        // Act (When)
        simulationGrid.tick();

        // Assert (Then)
        assertFalse(simulationGrid.isEmpty(childPosition) || !child.equals(simulationGrid.getCreatureAt(childPosition)), String.format("La cría debía haberse añadido al tablero en la posición (%d,%d).", childPosition.x(), childPosition.y()));
    }

    @Test
    @DisplayName("tick: elimina a la criatura si muere y la desasocia del tablero")
    public void given_dyingFakeCreature_when_tick_then_creatureIsRemovedAndDissociated() {
        // Arrange (Given)
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);
        logicCreatureFake1.setAlive(false); // Muere en este tick
        simulationGrid.addCreature(logicCreatureFake1);

        // Act (When)
        simulationGrid.tick();

        // Assert (Then)
        assertTrue(simulationGrid.isEmpty(topLeft), String.format("La criatura muerta debía ser removida de la celda " +
                "(%d,%d).", topLeft.x(), topLeft.y()));
        assertNull(logicCreatureFake1.getSimulationGrid(), "La criatura muerta debía ser desasociada (grid = null) " +
                "del tablero.");
    }

    // --- Test getEmptyAdjacentCells(Position position) ---------------------------------------------------------------

    @Test
    @DisplayName("getEmptyAdjacentCells: devuelve cantidad correcta según celdas ocupadas adyacentes")
    public void given_gridWithObstacles_when_getEmptyAdjacentCells_then_returnsExpectedCount() {
        // Arrange (Given)
        gridSize = 5;
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);
        center = new Position(2, 2);

        // Act & Assert (1) Todas vacías (4)
        assertEquals(4, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 0 obstáculos adyacentes, debían " +
                "devolverse 4 posiciones vacías.");

        // Arrange & Act & Assert (2) 1 ocupada (vacías 3)
        LogicCreatureFake f1 = new LogicCreatureFake("id-fake-right");
        f1.setPosition(center.getRight()); // Derecha
        simulationGrid.addCreature(f1);
        assertEquals(3, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 1 obstáculo adyacente, debían " +
                "devolverse 3 posiciones vacías.");

        // Arrange & Act & Assert (3) 2 ocupadas (vacías 2)
        LogicCreatureFake f2 = new LogicCreatureFake("id-fake-up");
        f2.setPosition(center.getUp()); // Arriba
        simulationGrid.addCreature(f2);
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 2 obstáculos adyacentes, debían " +
                "devolverse 2 posiciones vacías.");

        // Arrange & Act & Assert (4) 3 ocupadas (vacías 1)
        LogicCreatureFake f3 = new LogicCreatureFake("id-fake-left");
        f3.setPosition(center.getLeft()); // Izquierda
        simulationGrid.addCreature(f3);
        assertEquals(1, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 3 obstáculos adyacentes, debía " +
                "devolverse 1 posición vacía.");

        // Arrange & Act & Assert (5) 4 ocupadas (vacías 0)
        LogicCreatureFake f4 = new LogicCreatureFake("id-fake-down");
        f4.setPosition(center.getDown()); // Abajo
        simulationGrid.addCreature(f4);
        assertEquals(0, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 4 obstáculos adyacentes, debían " +
                "devolverse 0 posiciones vacías.");

        // Assert (6) bordes
        // Top Left
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(topLeft).size(), "Con 2 obstáculos adyacentes, debían " +
                "devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(1, 0)).size(), "Con 1 obstáculos " +
                "adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(0, 1)).size(), "Con 1 obstáculos " +
                "adyacentes, debían devolverse 3 posiciones vacías.");

        // Top Right
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, 0)).size(), "Con 2 obstáculos" +
                " adyacentes, debían devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 2, 0)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, 1)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        // Bottom Left
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(new Position(0, gridSize - 1)).size(), "Con 2 obstáculos" +
                " adyacentes, debían devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(1, gridSize - 1)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(0, gridSize - 2)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        // Bottom Right
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, gridSize - 1)).size(), "Con 2" +
                " obstáculos adyacentes, debían devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 2, gridSize - 1)).size(), "Con 1" +
                " obstáculos adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, gridSize - 2)).size(), "Con 1" +
                " obstáculos adyacentes, debían devolverse 3 posiciones vacías.");
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test SimulationGrid(SimulationStep initialStep, int foodPerTick, Random random) -----------------------------

    @Test
    @DisplayName("Constructor Integración: crea grid correctamente usando initialStep real")
    public void given_realInitialStep_when_newSimulationGrid_then_createsCorrectly() {
        // Arrange (Given)
        Map<Position, Creature> stepMap = new HashMap<>();
        Creature realCreature = new StaticCreature(new Creature("s1", "static-test"), starvationThreshold, topLeft);
        stepMap.put(topLeft, realCreature);
        SimulationStep realStep = new SimulationStep(gridSize, stepMap);

        // Act (When)
        simulationGrid = new SimulationGrid(realStep, foodPerTick, random);

        // Assert (Then)
        assertFalse(simulationGrid.isEmpty(new Position(0, 0)), String.format("La celda (%d,%d) debía estar ocupada " +
                "por la criatura cargada del estado inicial.", topLeft.x(), topLeft.y()));
    }

    // --- Test tick() -------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("tick Integración: actualiza posición, cría y limpia si muere usando criaturas reales")
    public void given_realCreatures_when_tick_then_stateIsUpdatedAppropriately() {
        // Arrange (Given)
        foodPerTick = 0;
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);

        // Criatura que se mueve (probabilidad 1.0)
        MobileCreature mobile = new MobileCreature(new Creature("m1", "mobile-test"), starvationThreshold, 1.0,
                topLeft, random);

        // Criatura que se reproduce (probabilidad 1.0)
        StaticRabbit rabbit = new StaticRabbit(new Creature("r1", "rabbit-test"), starvationThreshold, 1.0, center,
                random);

        // Criatura que morirá por falta de comida (threshold 0)
        Position dyingPosition = new Position(8, 8);
        StaticCreature dyingStatic = new StaticCreature(new Creature("s1", "static-test"), 0, dyingPosition);

        simulationGrid.addCreature(mobile);
        simulationGrid.addCreature(rabbit);
        simulationGrid.addCreature(dyingStatic);

        // Act (When)
        simulationGrid.tick(); // En el primer tick, dyingStatic gasta su threshold y muere.

        // Assert (Then)
        assertTrue(simulationGrid.isEmpty(topLeft), String.format("La MobileCreature debía haber abandonado la " +
                "posición (%d,%d) tras moverse.", topLeft.x(), topLeft.y()));
        Position newPosition1 = mobile.getPosition();
        assertEquals(mobile, simulationGrid.getCreatureAt(mobile.getPosition()), "La MobileCreature " + "debía " +
                "actualizar su posición tras moverse.");

        assertFalse(simulationGrid.isEmpty(center), String.format("El StaticRabbit debía continuar en su posición " +
                "(%d,%d).", center.x(), center.y()));
        int childrenCount = 0;
        for (Position pos : new Position[]{center.getRight(), center.getLeft(), center.getUp(), center.getDown()}) {
            if (!simulationGrid.isEmpty(pos)) childrenCount++;
        }
        assertEquals(1, childrenCount, "El StaticRabbit debía haber generado 1 cría en posiciones " + "adyacentes.");
        assertTrue(simulationGrid.isEmpty(dyingPosition), String.format("La criatura moribunda en (%d,%d) debía haber" +
                " desaparecido y ser removida del " + "grid.", dyingPosition.x(), dyingPosition.y()));

        // Act (When)
        simulationGrid.tick(); // En el segundo tick, las otras criaturas siguen actuando.

        // Assert (Then)
        assertTrue(simulationGrid.isEmpty(newPosition1), String.format("La MobileCreature debía haber abandonado la " +
                "posición (%d,%d) tras moverse.", newPosition1.x(), newPosition1.y()));
        assertEquals(mobile, simulationGrid.getCreatureAt(mobile.getPosition()), "La MobileCreature " + "debía " +
                "actualizar su posición tras moverse.");

        assertFalse(simulationGrid.isEmpty(center), String.format("El StaticRabbit debía continuar en su posición " +
                "(%d,%d).", center.x(), center.y()));
        childrenCount = 0;
        for (Position pos : new Position[]{center.getRight(), center.getLeft(), center.getUp(), center.getDown()}) {
            if (!simulationGrid.isEmpty(pos)) childrenCount++;
        }
        assertEquals(2, childrenCount, "El StaticRabbit debía haber generado 2 crías en posiciones " + "adyacentes.");
        assertTrue(simulationGrid.isEmpty(dyingPosition), String.format("La criatura moribunda en (%d,%d) debía haber" +
                " desaparecido y ser removida del grid.", dyingPosition.x(), dyingPosition.y()));
    }

    // --- Test getEmptyAdjacentCells(Position position) ---------------------------------------------------------------

    @Test
    @DisplayName("getEmptyAdjacentCells Integración: evalúa correctamente con grid interactivo")
    public void given_realGridState_when_getEmptyAdjacentCells_then_returnsAccurateCount() {
        // Arrange (Given)
        gridSize = 5;
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);
        center = new Position(2, 2);

        // Act & Assert (1) Todas vacías (4)
        assertEquals(4, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 0 obstáculos adyacentes, debían " +
                "devolverse 4 posiciones vacías.");

        // Arrange & Act & Assert (2) 1 ocupada (vacías 3)
        StaticCreature s1 = new StaticCreature(new Creature("s1-right", "static-test"), starvationThreshold,
                center.getRight());
        simulationGrid.addCreature(s1);
        assertEquals(3, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 1 obstáculo adyacente, debían " +
                "devolverse 3 posiciones vacías.");

        // Arrange & Act & Assert (3) 2 ocupadas (vacías 2)
        MobileCreature c1 = new MobileCreature(new Creature("c1-up", "mobile-test"), starvationThreshold, 1.0,
                center.getUp(), random);
        simulationGrid.addCreature(c1);
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 2 obstáculos adyacentes, debían " +
                "devolverse 2 posiciones vacías.");

        // Arrange & Act & Assert (4) 3 ocupadas (vacías 1)
        MobileCreature r1 = new MobileCreature(new Creature("r1-left", "rabbit-test"), starvationThreshold, 1.0,
                center.getLeft(), random);
        simulationGrid.addCreature(r1);
        assertEquals(1, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 3 obstáculos adyacentes, debía " +
                "devolverse 1 posición vacía.");

        // Arrange & Act & Assert (5) 4 ocupadas (vacías 0)
        StaticCreature s2 = new StaticCreature(new Creature("s2-down", "static-test"), starvationThreshold,
                center.getDown());
        simulationGrid.addCreature(s2);
        assertEquals(0, simulationGrid.getEmptyAdjacentCells(center).size(), "Con 4 obstáculos adyacentes, debían " +
                "devolverse 0 posiciones vacías.");

        // Assert (6) bordes
        // Top Left
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(topLeft).size(), "Con 2 obstáculos adyacentes, debían " +
                "devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(1, 0)).size(), "Con 1 obstáculos " +
                "adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(0, 1)).size(), "Con 1 obstáculos " +
                "adyacentes, debían devolverse 3 posiciones vacías.");

        // Top Right
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, 0)).size(), "Con 2 obstáculos" +
                " adyacentes, debían devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 2, 0)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, 1)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        // Bottom Left
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(new Position(0, gridSize - 1)).size(), "Con 2 obstáculos" +
                " adyacentes, debían devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(1, gridSize - 1)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(0, gridSize - 2)).size(), "Con 1 obstáculos" +
                " adyacentes, debían devolverse 3 posiciones vacías.");

        // Bottom Right
        assertEquals(2, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, gridSize - 1)).size(), "Con 2" +
                " obstáculos adyacentes, debían devolverse 2 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 2, gridSize - 1)).size(), "Con 1" +
                " obstáculos adyacentes, debían devolverse 3 posiciones vacías.");

        assertEquals(3, simulationGrid.getEmptyAdjacentCells(new Position(gridSize - 1, gridSize - 2)).size(), "Con 1" +
                " obstáculos adyacentes, debían devolverse 3 posiciones vacías.");

        // Arrange (7) extra
        simulationGrid = new SimulationGrid(gridSize, foodPerTick, random);
        MobileCreature c2 = new MobileCreature(new Creature("c2", "mobile-test"), starvationThreshold, 1.0, topLeft,
                random);
        Position checkPosition = new Position(1, 0);
        StaticRabbit r2 = new StaticRabbit(new Creature("r2", "rabbit-test"), starvationThreshold, 1.0, checkPosition
                , random);
        simulationGrid.addCreature(c2);
        simulationGrid.addCreature(r2);

        // Act (7)
        Collection<Position> emptyAdj = simulationGrid.getEmptyAdjacentCells(checkPosition);

        // Assert (7)
        assertEquals(2, emptyAdj.size(), String.format("Dos celda bloqueaban a (%d,%d), debían devolverse 2 " +
                "posiciones vacías, se devolvieron " + "%d.", checkPosition.x(), checkPosition.y(), emptyAdj.size()));
    }
}