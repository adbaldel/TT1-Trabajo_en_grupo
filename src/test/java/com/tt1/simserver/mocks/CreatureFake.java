package com.tt1.simserver.mocks;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;

/**
 * Doble de pruebas (Fake/Stub/Spy) para simular de forma determinista
 * el comportamiento de una criatura en los tests unitarios del Grid.
 */
public class CreatureFake implements CreatureInterface {
    private String name;
    private String color;
    private Position position;
    private Position positionToReturnOnMove;
    private CreatureInterface creatureToReturnOnMultiply;
    private boolean moveCalled;
    private boolean multiplyCalled;


    public CreatureFake() {
        name = null;
        color = null;
        position = null;
        positionToReturnOnMove = null;
        creatureToReturnOnMultiply = null;
        this.moveCalled = false;
        this.multiplyCalled = false;
    }


    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setPositionToReturnOnMove(Position position) {
        this.positionToReturnOnMove = position;
    }

    public void setCreatureToReturnOnMultiply(CreatureInterface creature) {
        this.creatureToReturnOnMultiply = creature;
    }

    public boolean isMoveCalled() {
        return moveCalled;
    }

    public boolean isMultiplyCalled() {
        return multiplyCalled;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getColor() {
        return color;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public Position getPosition() {
        return new Position(position.getX(), position.getY());
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position move(GridInterface grid) {
        moveCalled = true;
        if (positionToReturnOnMove != null) {
            // Simulamos el comportamiento real de actualizar la posición interna
            position.setX(positionToReturnOnMove.getX());
            position.setY(positionToReturnOnMove.getY());
        }
        return positionToReturnOnMove;
    }

    @Override
    public CreatureInterface multiply(GridInterface grid) {
        multiplyCalled = true;
        return creatureToReturnOnMultiply;
    }
}