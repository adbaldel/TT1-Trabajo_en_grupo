package com.tt1.simserver.model;

import java.util.Objects;

/**
 * Representa una simulación.
 */
public class Simulation {
    private final int token;
    private final User user;
    private final SimulationData simulationData;
    private SimulationStatus status;

    /**
     * Construye una simulación acabada con el token, usuario y datos de simulación pasados como parámetros. Asume que
     * el token es único para esta simulación, y que el usuario y los datos de simulación son no nulos.
     *
     * @param token          el token de la simulación.
     * @param user           el usuario asociado a la simulación.
     * @param simulationData los datos de la simulación acabada.
     */
    public Simulation(int token, User user, SimulationData simulationData) {
        this.token = token;
        this.user = user;
        status = SimulationStatus.COMPLETED;
        this.simulationData = simulationData;
    }

    /**
     * Construye una simulación que no ha sido corrida con el token, usuario y tamaño de tablero pasados como
     * parámetros. Asume que el token es único para esta simulación; que el usuario es no nulo; y que gridSize > 0.
     *
     * @param token    el token de la simulación.
     * @param user     el usuario asociado a la simulación.
     * @param gridSize el tamaño del tablero de la simulación.
     */
    public Simulation(int token, User user, int gridSize) {
        this.token = token;
        this.user = user;
        status = SimulationStatus.PENDING;
        simulationData = new SimulationData(gridSize);
    }

    /**
     * Obtiene el token de esta simulación.
     *
     * @return el token de esta simulación.
     */
    public int getToken() {
        return token;
    }

    /**
     * Obtiene el usuario de esta simulación.
     *
     * @return el usuario de esta simulación.
     */
    public User getUser() {
        return user;
    }

    /**
     * Obtiene el tamaño del tablero de esta simulación.
     *
     * @return el tamaño del tablero de esta simulación.
     */
    public int getGridSize() {
        return simulationData.getGridSize();
    }

    /**
     * Obtiene el estado de esta simulación.
     *
     * @return el estado de esta simulación.
     */
    public SimulationStatus getStatus() {
        return status;
    }

    /**
     * Obtiene los datos de esta simulación.
     *
     * @return los datos de esta simulación.
     */
    public SimulationData getSimulationData() {
        return simulationData;
    }

    /**
     * Cambia el estado de esta simulación a corriendo.
     */
    public void startSimulation() {
        this.status = SimulationStatus.RUNNING;
    }

    /**
     * Cambia el estado de esta simulación a acabada.
     */
    public void completeSimulation() {
        this.status = SimulationStatus.COMPLETED;
    }

    /**
     * Cambia el estado de esta simulación a interrumpida.
     */
    public void interruptSimulation() {
        this.status = SimulationStatus.INTERRUPTED;
    }

    /**
     * Añade el paso dado como último paso en los datos de esta simulación.
     *
     * @param simulationStep el paso de simulación a añadir.
     */
    public void addStep(SimulationStep simulationStep) {
        simulationData.addStep(simulationStep);
    }

    /**
     * Comprueba si esta simulación es igual al objeto {@code o}. Devuelve cierto si el objeto es una simulación con el
     * mismo token.
     *
     * @param o el objeto a comparar.
     * @return cierto si esta simulación es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simulation simulation = (Simulation) o;
        return token == simulation.token;
    }

    /**
     * Obtiene el código hash de esta simulación.
     *
     * @return el código hash de esta simulación.
     */
    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
