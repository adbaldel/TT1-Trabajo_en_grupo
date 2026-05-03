package com.tt1.simserver.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Almacena el historial completo de los pasos procesados durante una simulación.
 */
public class SimulationResult {
    private final List<SimulationStep> simulationSteps;
    private final int size;

    /**
     * Inicializa un nuevo historial de resultados vacío.
     *
     * <p>Precondición: El tamaño pasado es mayor que cero.
     *
     * <p>Postcondición: El objeto queda preparado para recibir capturas de un tablero de tamaño {@code size}. El contador de pasos comienza en cero.
     *
     * @param size el tamaño del tablero de la simulación de la que se representa el resultado.
     */
    public SimulationResult(int size) {
        simulationSteps = new ArrayList<>();
        this.size = size;
    }

    /**
     * Obtiene la captura del tablero en un turno determinado.
     *
     * <p>Precondición: {@code second} es mayor o igual a cero y estrictamente menor que el total devuelto por {@code getSeconds()}.
     *
     * <p>Postcondición: Devuelve el estado exacto del tablero guardado para ese paso de la simulación.
     *
     * @param second el índice del turno solicitado.
     * @return la captura del tablero en ese paso.
     */
    public SimulationStep getSimulationStep(int second) {
        return simulationSteps.get(second);
    }

    /**
     * Obtiene el tamaño del lado del tablero cuyo resultado se representa.
     *
     * <p>Precondición: El resultado de simulación está inicializado.
     *
     * <p>Postcondición: Devuelve el número de filas (o columnas) del tablero cuyo resultado se representa.
     *
     * @return el tamaño del tablero cuyo resultado se representa.
     */
    public int getSize() {
        return size;
    }

    /**
     * Obtiene la cantidad total de turnos registrados en el historial.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el número exacto de pasos guardados, que incluye el estado inicial del tablero.
     *
     * @return el total de pasos almacenados.
     */
    public int getSeconds() {
        return simulationSteps.size();
    }

    /**
     * Añade una nueva captura del tablero al final del historial.
     *
     * <p>Precondición: {@code simulationStep} no es nulo y el tamaño de la simulación que representa es el mismo que el del resultado de simulación.
     *
     * <p>Postcondición: La captura se añade a la lista, incrementando en uno el total de turnos almacenados. Devuelve verdadero si la inserción tuvo éxito.
     *
     * @param simulationStep el instante a almacenar.
     * @return verdadero si se guardó correctamente.
     */
    public boolean addStep(SimulationStep simulationStep) {
        return simulationSteps.add(simulationStep);
    }

    /**
     * Compara este historial con otro para verificar si son idénticos.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero solo si el otro objeto es un historial con la misma cantidad de turnos y las capturas del tablero son idénticas en el mismo orden. Devuelve falso en caso contrario.
     *
     * @param o el objeto a comparar.
     * @return verdadero si los resultados tienen los mismos pasos en el mismo orden, falso si no.
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