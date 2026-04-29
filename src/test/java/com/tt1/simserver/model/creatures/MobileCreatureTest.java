package com.tt1.simserver.model.creatures;

import com.tt1.simserver.mocks.GridFake;
import com.tt1.simserver.model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Movimiento: Falla y no se mueve si carece de casillas adyacentes libres en el tablero")
    public void given_mobileCreatureAndNoEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Position newPosition = mobileCreature.move(gridFake);

        // Assert (Then)
        assertNull(newPosition, "Una criatura móvil no puede desplazarse y devuelve null si todas las casillas de su perímetro están bloqueadas u ocupadas");
    }

    @Test
    @DisplayName("Movimiento: Asume la única casilla libre adyacente si su probabilidad asegura el movimiento")
    public void given_mobileCreatureThatAlwaysMovesAndOneEmptyAdjacentCell_when_move_then_returnsEmptyAdjacentCell() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1));

        // Act (When)
        Position newPosition = mobileCreature.move(gridFake);

        // Assert (Then)
        assertNotNull(newPosition, "La criatura obligatoriamente debe calcular y devolver su nueva posición de destino");
        assertEquals(mobileCreature.getPosition(), newPosition, "Tras completar el movimiento internamente en la clase, su propiedad posicional debe actualizarse");
        assertEquals(emptyAdjacentCell1, newPosition, "El destino escogido al moverse tiene que ser ineludiblemente la única casilla libre que tenía a su alrededor");
    }

    @Test
    @DisplayName("Movimiento: Selecciona una de entre varias casillas libres cuando el movimiento está asegurado")
    public void given_mobileCreatureThatAlwaysMovesAndEmptyAdjacentCells_when_move_then_returnsOneOfTheEmptyAdjacentCells() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition = mobileCreature.move(gridFake);

        // Assert (Then)
        assertNotNull(newPosition, "La criatura debe lograr desplazarse cuando hay casillas de sobra y probabilidad alta");
        assertEquals(mobileCreature.getPosition(), newPosition, "El desplazamiento debe mutar los datos de ubicación internos de la propia criatura");
        assertTrue(List.of(emptyAdjacentCell1, emptyAdjacentCell2).contains(newPosition), "El nuevo terreno ocupado solo puede elegirse de entre la lista de casillas vacías dictada por el tablero");
    }

    @DisplayName("Movimiento: Realiza una cantidad de desplazamientos acorde a su distribución de probabilidad asignada")
    @ParameterizedTest(name = "Test {0} distribución en turno")
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
        assertTrue(moveCount >= lowerBound && moveCount <= upperBound, "El motor pseudoaleatorio del movimiento de la criatura falla en cumplir con los márgenes del intervalo de confianza matemático estipulado para su probabilidad");
    }

    // --- Test multiply -----------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Reproducción: Falla y no genera crías al estar acorralada sin casillas libres")
    public void given_mobileCreatureAndNoEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Creature child = mobileCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child, "La falta de espacio físico cancela las posibilidades biológicas de reproducción, resultando en nulo");
    }

    @Test
    @DisplayName("Reproducción: Una criatura móvil estándar no hereda genéticamente la habilidad de reproducirse")
    public void given_mobileCreatureAndEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        MobileCreature mobileCreature = new MobileCreature(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Creature child = mobileCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child, "Las criaturas móviles genéricas por regla de negocio nunca se reproducen independientemente del espacio, debiendo siempre rechazar la operación con null");
    }
}