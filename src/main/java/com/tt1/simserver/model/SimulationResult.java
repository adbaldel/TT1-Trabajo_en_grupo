package com.tt1.simserver.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Estructura de datos que encapsula el histórico completo de los estados por los
 * que ha pasado la simulación desde su inicio hasta su fin.
 */
public class SimulationResult {
    private List<SimulationStep> simulationSteps;


    /**
     * Construye un resultado de simulación sin pasos en su inicio.
     */
    public SimulationResult() {
        simulationSteps = new ArrayList<>();
    }


    /**
     * Obtiene el estado del tablero en un determinado segundo (tick).
     * Precondición: second >= 0, second < getSeconds().
     *
     * @param second el segundo temporal de la simulación requerido.
     * @return el instante guardado correspondiente a ese segundo.
     */
    public SimulationStep getSimulationStep(int second) {
        return simulationSteps.get(second);
    }

    /**
     * Devuelve el total de segundos simulados hasta el momento.
     *
     * @return cantidad de pasos almacenados.
     */
    public int getSeconds() {
        return simulationSteps.size();
    }

    /**
     * Registra un nuevo instante de simulación (un segundo / paso / tick) en el histórico general.
     * Precondición: simulationStep no nulo.
     *
     * @param simulationStep el instante a almacenar.
     * @return cierto en caso de que se haya añadido de forma satisfactoria a la lista interna.
     */
    public boolean addStep(SimulationStep simulationStep) {
        return simulationSteps.add(simulationStep);
    }
}