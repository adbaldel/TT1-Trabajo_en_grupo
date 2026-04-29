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
    private long moveCalledTime;
    private long multiplyCalledTime;
    private int timesMoveCalled;
    private int timesMultiplyCalled;


    public CreatureFake() {
        name = null;
        color = null;
        position = null;
        positionToReturnOnMove = null;
        creatureToReturnOnMultiply = null;
        moveCalledTime = 0;
        multiplyCalledTime = 0;
        timesMoveCalled = 0;
        timesMultiplyCalled = 0;
    }


    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPositionToReturnOnMove(Position position) {
        this.positionToReturnOnMove = position;
    }

    public void setCreatureToReturnOnMultiply(CreatureInterface creature) {
        this.creatureToReturnOnMultiply = creature;
    }

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    public long getMoveCalledTime() {
        return moveCalledTime;
    }

    public long getMultiplyCalledTime() {
        return multiplyCalledTime;
    }

    public int getTimesMoveCalled() {
        return timesMoveCalled;
    }

    public int getTimesMultiplyCalled() {
        return timesMultiplyCalled;
    }

    public boolean isMoveCalled() {
        return moveCalledTime > 0;
    }

    public boolean isMultiplyCalled() {
        return multiplyCalledTime > 0;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public Position getPosition() {
        return new Position(position.getX(), position.getY());
    }

    @Override
    public Position move(GridInterface grid) {
        timesMoveCalled++;
        moveCalledTime = System.currentTimeMillis();
        if (positionToReturnOnMove != null) {
            // Simulamos el comportamiento real de actualizar la posición interna
            position.setX(positionToReturnOnMove.getX());
            position.setY(positionToReturnOnMove.getY());
        }
        return positionToReturnOnMove;
    }

    @Override
    public CreatureInterface multiply(GridInterface grid) {
        timesMultiplyCalled++;
        multiplyCalledTime = System.currentTimeMillis();
        return creatureToReturnOnMultiply;
    }
}