package com.tt1.simserver.model.creatures;

import com.tt1.simserver.mocks.GridFake;
import com.tt1.simserver.model.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Movimiento: Falla en moverse al no contar con casillas contiguas vacías")
    public void given_staticCreatureAndNoEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Position newPosition = staticCreature.move(gridFake);

        // Assert (Then)
        assertNull(newPosition, "Un sujeto estático no devuelve posición destino de movimiento nunca");
        assertEquals(position, staticCreature.getPosition(), "Al carecer de casillas libres, el intento de movimiento no modifica las coordenadas de la criatura");
    }

    @Test
    @DisplayName("Movimiento: Una criatura estática ignora las casillas contiguas libres quedándose en el sitio")
    public void given_staticCreatureAndEmptyAdjacentCells_when_move_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Position newPosition = staticCreature.move(gridFake);

        // Assert (Then)
        assertNull(newPosition, "Incluso existiendo casillas libres, la naturaleza de la criatura le impide desplazarse en su turno");
        assertEquals(position, staticCreature.getPosition(), "Ante una orden de movimiento, un ente estático mantiene intactas sus coordenadas originales en el tablero");
    }

    // --- Test multiply -----------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Reproducción: Carece de espacio en las casillas contiguas y cancela su reproducción")
    public void given_staticCreatureAndNoEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of());

        // Act (When)
        Creature child = staticCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child, "La carencia total de casillas limítrofes vacías veta la gestación biológica en el tablero");
    }

    @Test
    @DisplayName("Reproducción: Las criaturas estáticas genéricas rehúsan generar copias pese a tener espacio")
    public void given_staticCreatureAndEmptyAdjacentCells_when_multiply_then_returnsNull() {
        // Arrange (Given)
        StaticCreature staticCreature = new StaticCreature(name, color, position);
        gridFake.setAdjacentEmptyCells(List.of(emptyAdjacentCell1, emptyAdjacentCell2));

        // Act (When)
        Creature child = staticCreature.multiply(gridFake);

        // Assert (Then)
        assertNull(child, "Por reglas del simulador, el espécimen estático base carece del gen reproductivo devolviendo nulo");
    }
}