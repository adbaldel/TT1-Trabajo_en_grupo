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
    @DisplayName("Movimiento: El conejo estático no puede moverse al no hallar casillas contiguas sin ocupar")
    public void given_staticRabbitAndNoEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Position newPosition = staticRabbit.move(gridFake);

        // Assert (Then)
        assertNull(newPosition, "Un conejo atrapado entre casilleros ocupados bloquea en seco sus acciones de movimiento");
        assertEquals(position, staticRabbit.getPosition(), "Al anularse el movimiento, el conejo perpetúa en las mismas coordenadas espaciales");
    }

    @Test
    @DisplayName("Movimiento: El conejo estático rechaza moverse a pesar de estar rodeado de casillas libres")
    public void given_staticRabbitAndEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition = staticRabbit.move(gridFake);

        // Assert (Then)
        assertNull(newPosition, "El atributo inamovible de esta especie estática fuerza un nulo en cada intento de desplazamiento");
        assertEquals(position, staticRabbit.getPosition(), "La inmovilidad de la especie mantiene cristalizada su ubicación en el tablero a perpetuidad");
    }

    // --- Test multiply -----------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Reproducción: El proceso reproductivo del conejo se frustra si su contorno está invadido")
    public void given_staticRabbitAndNoEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Creature child = staticRabbit.multiply(gridFake);

        // Assert (Then)
        assertNull(child, "La cría no nace ni se añade al ecosistema al faltarle una casilla limpia para brotar");
    }

    @Test
    @DisplayName("Reproducción: Gestar y colocar una cría calcada en la única casilla libre colindante")
    public void given_staticRabbitThatAlwaysMultipliesAndOneEmptyAdjacentCell_when_multiply_then_returnsChildInEmptyAdjacentCell() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1));

        // Act (When)
        Creature child = staticRabbit.multiply(gridFake);

        // Assert (Then)
        assertNotNull(child, "La reproducción con probabilidad 1 y disponibilidad de terreno está forzada a producir un resultado tangible");
        assertEquals(staticRabbit.getName(), child.getName(), "Para preservar la especie, la cría adopta la cadena de texto con el mismo nombre");
        assertEquals(staticRabbit.getColor(), child.getColor(), "La transmisión genética traslada el identificador de color a la cría");
        assertEquals(staticRabbit.getClass(), child.getClass(), "El espécimen alumbrado se construye como la misma subclase biológica de código");
        assertEquals(staticRabbit.getMultiplyProbability(), ((StaticRabbit) child).getMultiplyProbability(), "Los descendientes calcan íntegra la tasa probabilística de reproducción");
        assertEquals(emptyAdjacentCell1, child.getPosition(), "A falta de opciones, la criatura se instanciará empadronada en la única casilla contigua despejada");
    }

    @Test
    @DisplayName("Reproducción: Depositar a la nueva cría clonada sorteando una de entre varias casillas libres")
    public void given_staticRabbitThatAlwaysMultipliesAndEmptyAdjacentCells_when_multiply_then_returnsChildInEmptyAdjacentCells() {
        // Arrange (Given)
        StaticRabbit staticRabbit = new StaticRabbit(name, color, 1.0, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Creature child = staticRabbit.multiply(gridFake);

        // Assert (Then)
        assertNotNull(child, "El turno fructifica dando a luz un elemento en forma de criatura del tablero");
        assertEquals(staticRabbit.getName(), child.getName(), "El hijo refleja la onomástica del padre tras la reproducción");
        assertEquals(staticRabbit.getColor(), child.getColor(), "El tintado cromático salta una generación intacto desde su matriz");
        assertEquals(staticRabbit.getClass(), child.getClass(), "El software genera la descendencia clonando el tipo de clase subyacente");
        assertEquals(staticRabbit.getMultiplyProbability(), ((StaticRabbit) child).getMultiplyProbability(), "La fertilidad numérica salta sin degradarse hacia la siguiente generación de la especie");
        assertTrue(List.of(emptyAdjacentCell1, emptyAdjacentCell2).contains(child.getPosition()), "El motor empareja de modo aleatorio la instancia recién engendrada con uno de los recovecos disponibles");
    }

    @DisplayName("Reproducción: Validar estadísticamente que las crías emitidas sigan la probabilidad configurada")
    @ParameterizedTest(name = "Test {0} distribución en turno")
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
        assertTrue(childCount >= lowerBound && childCount <= upperBound, "A lo largo de iteraciones masivas, los conejos engendrados no lograron cuadrar con los rangos teóricos marcados por su propia variable p");
    }
}