package com.tt1.simserver.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Estructura de datos que encapsula el histórico completo de los estados por los
 * que ha pasado la simulación desde su inicio hasta su fin.
 */
public class SimulationResult {
    private final List<SimulationStep> simulationSteps;


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

    /**
     * Compara este objeto con otro para comprobar si son iguales basándose en sus atributos.
     *
     * @param o el objeto de referencia con el cual comparar.
     * @return cierto si los objetos son resultados de simulación con los mismos pasos de simulación en el mismo orden, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimulationResult simulationResult = (SimulationResult) o;

        if (simulationSteps.size() != simulationResult.simulationSteps.size()) return false;

        for (int i = 0; i < simulationSteps.size(); i++) {
            if (!simulationSteps.get(i).equals(simulationResult.simulationSteps.get(i))) return false;
        }

        return true;
    }
}