package com.tt1.simserver.mocks;

import com.tt1.simserver.logic.GridInterface;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.creatures.CreatureInterface;

import java.util.List;

public class GridFake implements GridInterface {
    private List<Position> adjacentEmptyCells;
    private int tickCount;
    private int size;
    private final List<CreatureInterface> creatures;


    public GridFake() {
        adjacentEmptyCells = null;
        tickCount = 0;
        size = -1;
        creatures = null;
    }


    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public void setAdjacentEmptyCells(List<Position> adjacentEmptyCells) {
        this.adjacentEmptyCells = adjacentEmptyCells;
    }

    public int getTickCount() {
        return tickCount;
    }

    @Override
    public void tick() {
        tickCount++;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    @Override
    public CreatureInterface getCreature(Position position) {
        for (CreatureInterface c : creatures) {
            if (c.getPosition().equals(position)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void addCreature(CreatureInterface creature) {
        creatures.add(creature);
    }

    @Override
    public boolean isEmpty(Position position) {
        return false;
    }

    @Override
    public List<Position> getAdjacentEmptyCells(Position position) {
        return adjacentEmptyCells;
    }
}
