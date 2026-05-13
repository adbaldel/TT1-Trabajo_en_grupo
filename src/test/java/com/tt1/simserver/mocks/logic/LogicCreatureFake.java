package com.tt1.simserver.mocks.logic;

import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.logic.creatures.LogicCreatureInterface;
import com.tt1.simserver.model.Creature;
import com.tt1.simserver.model.Position;

/**
 * Implementa la funcionalidad de una criatura devolviendo datos preconfigurados y registra tiempos de llamadas a
 * métodos y cantidad de llamadas a métodos.
 */
public class LogicCreatureFake extends Creature implements LogicCreatureInterface {
    private Position position;
    private Position previousPosition;
    private SimulationGridInterface simulationGrid;
    private int starvationThreshold;
    private boolean alive;
    private Position positionToReturnOnMove;
    private LogicCreatureInterface creatureToReturnOnMultiply;
    private long moveCalledTime;
    private long multiplyCalledTime;
    private long eatCalledTime;
    private long fedTime;
    private int timesMoveCalled;
    private int timesMultiplyCalled;
    private int timesEatCalled;
    private int timesFed;


    /**
     * Construye una criatura lógica falsa sin configurar.
     */
    public LogicCreatureFake(String id) {
        super(id, "fake", "color-fake");
        position = null;
        alive = false;
        positionToReturnOnMove = null;
        creatureToReturnOnMultiply = null;
        moveCalledTime = 0;
        multiplyCalledTime = 0;
        eatCalledTime = 0;
        fedTime = 0;
        timesMoveCalled = 0;
        timesMultiplyCalled = 0;
        timesEatCalled = 0;
        timesFed = 0;
    }


    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setPositionToReturnOnMove(Position position) {
        this.positionToReturnOnMove = position;
    }

    public void setCreatureToReturnOnMultiply(LogicCreatureInterface creature) {
        this.creatureToReturnOnMultiply = creature;
    }

    public int getTimesMoveCalled() {
        return timesMoveCalled;
    }

    public int getTimesMultiplyCalled() {
        return timesMultiplyCalled;
    }

    public int getTimesEatCalled() {
        return timesEatCalled;
    }

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    public int getTimesFed() {
        return timesFed;
    }

    public long getMoveCalledTime() {
        return moveCalledTime;
    }

    public long getMultiplyCalledTime() {
        return multiplyCalledTime;
    }

    public long getEatCalledTime() {
        return eatCalledTime;
    }

    public long getFedTime() {
        return fedTime;
    }

    public boolean isMoveCalled() {
        return moveCalledTime > 0;
    }

    public boolean isMultiplyCalled() {
        return multiplyCalledTime > 0;
    }

    public boolean isEatCalled() {
        return eatCalledTime > 0;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        previousPosition = position;
    }

    @Override
    public SimulationGridInterface getSimulationGrid() {
        return simulationGrid;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public void setSimulationGrid(SimulationGridInterface simulationGrid) {
        this.simulationGrid = simulationGrid;
    }

    @Override
    public int getStarvationThreshold() {
        return starvationThreshold;
    }

    public void setStarvationThreshold(int starvationThreshold) {
        this.starvationThreshold = starvationThreshold;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void performEat() {
        timesEatCalled++;
        eatCalledTime = System.currentTimeMillis();

        if (simulationGrid.hasFood(previousPosition) || simulationGrid.hasFood(getPosition())) {
            timesFed++;
            fedTime = System.currentTimeMillis();
        }
    }

    @Override
    public Position performMove() {
        timesMoveCalled++;
        moveCalledTime = System.currentTimeMillis();

        previousPosition = getPosition();

        if (positionToReturnOnMove != null) {
            // Simulamos el comportamiento real de actualizar la posición interna
            this.position = positionToReturnOnMove;
        }
        return positionToReturnOnMove;
    }

    @Override
    public LogicCreatureInterface performMultiply() {
        timesMultiplyCalled++;
        multiplyCalledTime = System.currentTimeMillis();
        return creatureToReturnOnMultiply;
    }
}