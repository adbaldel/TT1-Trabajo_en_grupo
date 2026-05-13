package com.tt1.simserver.mocks.logic;

import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.logic.creatures.LogicCreatureInterface;
import com.tt1.simserver.model.Position;

import java.util.Collection;
import java.util.Map;

/**
 * Implementa la funcionalidad de un tablero de simulaciones devolviendo datos preconfigurados y contando los ticks que
 * ejecuta.
 */
public class SimulationGridFake implements SimulationGridInterface {
    private Map<Position, LogicCreatureInterface> grid;
    private Collection<Position> foodPositions;
    private Collection<Position> emptyAdjacentCells;
    private int tickCount;
    private int size;

    /**
     * Construye un tablero de simulaciones falso sin configurar.
     */
    public SimulationGridFake() {
        grid = null;
        emptyAdjacentCells = null;
        tickCount = 0;
        size = 0;
    }


    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setGrid(Map<Position, LogicCreatureInterface> grid) {
        this.grid = grid;
    }

    public void setFoodPositions(Collection<Position> foodPositions) {
        this.foodPositions = foodPositions;
    }

    public void setEmptyAdjacentCells(Collection<Position> emptyAdjacentCells) {
        this.emptyAdjacentCells = emptyAdjacentCells;
    }

    public int getTickCount() {
        return tickCount;
    }

    // --- Getters de control para los tests ---------------------------------------------------------------------------

    @Override
    public void tick() {
        tickCount++;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public LogicCreatureInterface getCreatureAt(Position position) {
        return grid.get(position);
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void addCreature(LogicCreatureInterface creature) {
        grid.put(creature.getPosition(), creature);
    }

    @Override
    public boolean isEmpty(Position position) {
        return grid.get(position) == null;
    }

    @Override
    public boolean hasFood(Position position) {
        return foodPositions.contains(position);
    }

    @Override
    public Collection<Position> getEmptyAdjacentCells(Position position) {
        return emptyAdjacentCells;
    }

    @Override
    public Collection<Position> getNonEmptyPositions() {
        return grid.keySet();
    }
}
