package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;

public interface SimulationManagerInterface {

    /**
     * Obtiene el token unívoco que identifica a esta simulación concreta.
     *
     * @return el valor numérico del token, o -1 si aún no ha comenzado.
     */
    int getToken();

    /**
     * Obtiene el estado actual de esta simulación, actualizando posibles cambios subyacentes.
     *
     * @return el estado de la simulación (ej. PENDING, RUNNING, COMPLETED).
     */
    SimulationStatus getSimulationStatus();

    /**
     * Interroga al motor de simulación para averiguar si ha terminado,
     * cambiando el estado a COMPLETED en caso afirmativo.
     */
    void updateSimulationStatus();

    /**
     * Solicita y devuelve los resultados definitivos al motor.
     *
     * @return los datos del resultado paso por paso, o null si no se han completado aún.
     */
    SimulationResult getSimulationResult();

    /**
     * Asigna un token, encola el motor de simulación en el pool de hilos para su ejecución asíncrona
     * y transita el estado a RUNNING.
     * Postcondición: token >= 0.
     *
     * @return el token numérico asignado a la simulación recién iniciada.
     */
    int startSimulation();
}
