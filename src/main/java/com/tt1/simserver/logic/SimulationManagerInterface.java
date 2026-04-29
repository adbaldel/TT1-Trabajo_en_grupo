package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;

/**
 * Define los controles para gestionar el ciclo de vida y el estado de ejecución de una simulación.
 */
public interface SimulationManagerInterface {

    /**
     * Obtiene el identificador numérico único asignado a esta ejecución.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el token asignado durante el arranque. Si la simulación aún no ha sido arrancada, devuelve -1 de forma predeterminada.
     *
     * @return el token de la simulación.
     */
    int getToken();

    /**
     * Consulta y devuelve el estado actual de la simulación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Fuerza una actualización interna para revisar si el motor ha terminado y devuelve el estado resultante (pendiente, en progreso o completado).
     *
     * @return el estado de ejecución de la simulación.
     */
    SimulationStatus getSimulationStatus();

    /**
     * Refresca el estado interno verificando la finalización de los cálculos en el motor.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Si el motor de simulación confirma que ha procesado todos sus turnos, actualiza el estado de la simulación a completado. En caso contrario, se mantiene sin cambios.
     */
    void updateSimulationStatus();

    /**
     * Recupera el historial de resultados generado por el motor de simulación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Traspasa directamente el objeto de resultado devuelto por el motor interno solo si el estado actual es completado. Bloquea la entrega y devuelve nulo si la simulación todavía está pendiente o procesando turnos.
     *
     * @return el historial de resultados, o nulo si no ha concluido.
     */
    SimulationResult getSimulationResult();

    /**
     * Inicia asíncronamente el procesamiento matemático de la simulación en un hilo.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Encola el motor de simulación en un hilo secundario para procesar los turnos de manera paralela. Cambia el estado a en progreso y emite un nuevo token mayor o igual a cero. Si el gestor ya fue arrancado, ignora la orden y devuelve el mismo token original para prevenir repeticiones.
     *
     * @return el token numérico identificador emitido.
     */
    int startSimulation();
}
