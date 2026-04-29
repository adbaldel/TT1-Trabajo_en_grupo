package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gestor encargado de coordinar la ejecución asíncrona de una simulación específica.
 * Proporciona hilos desde un pull para realizar el cálculo del motor sin bloquear el servidor web,
 * a la vez que gestiona el estado y el token identificador de esta ejecución.
 */
public class SimulationManager implements SimulationManagerInterface {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    /**
     * Contador estático para asignar los identificadores de token globales.
     * ¡CUIDADO! Varios métodos accediendo y modificando esta variable a la vez en varios hilos
     * podría generar problemas de condición de carrera. Requiere AtomicInteger o bloqueo sincronizado.
     */
    private static int numberOfSimulations = 0;
    private final SimulationEngineInterface simulationEngine;
    private int token;
    private SimulationStatus status;

    /**
     * Inicializa el gestor y prepara el motor de simulación para ser lanzado.
     *
     * <p>Precondición: {@code simulationEngine} no es nulo.
     *
     * <p>Postcondición: Crea el gestor almacenando el motor de ejecución. El estado inicial se establece como pendiente y el token como -1.
     *
     * @param simulationEngine el motor lógico encargado de procesar los turnos del tablero.
     */
    public SimulationManager(SimulationEngineInterface simulationEngine) {
        this.token = -1;
        this.simulationEngine = simulationEngine;
        this.status = SimulationStatus.PENDING;
    }

    /**
     * Obtiene el identificador numérico único asignado a esta ejecución.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el token asignado durante el arranque. Si la simulación aún no ha sido arrancada, devuelve -1 de forma predeterminada.
     *
     * @return el token de la simulación.
     */
    @Override
    public int getToken() {
        return token;
    }

    /**
     * Consulta y devuelve el estado actual de la simulación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Fuerza una actualización interna para revisar si el motor ha terminado y devuelve el estado resultante (pendiente, en progreso o completado).
     *
     * @return el estado de ejecución de la simulación.
     */
    @Override
    public SimulationStatus getSimulationStatus() {
        updateSimulationStatus();

        return status;
    }

    /**
     * Refresca el estado interno verificando la finalización de los cálculos en el motor.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Si el motor de simulación confirma que ha procesado todos sus turnos, actualiza el estado de la simulación a completado. En caso contrario, se mantiene sin cambios.
     */
    @Override
    public void updateSimulationStatus() {
        if (simulationEngine.isDone()) {
            status = SimulationStatus.COMPLETED;
        }
    }

    /**
     * Recupera el historial de resultados generado por el motor de simulación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Traspasa directamente el objeto de resultado devuelto por el motor interno solo si el estado actual es completado. Bloquea la entrega y devuelve nulo si la simulación todavía está pendiente o procesando turnos.
     *
     * @return el historial de resultados, o nulo si no ha concluido.
     */
    @Override
    public SimulationResult getSimulationResult() {
        return getSimulationStatus() == SimulationStatus.COMPLETED ? simulationEngine.getResult() : null;
    }

    /**
     * Inicia asíncronamente el procesamiento matemático de la simulación en un hilo.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Encola el motor de simulación en un hilo secundario para procesar los turnos de manera paralela. Cambia el estado a en progreso y emite un nuevo token mayor o igual a cero. Si el gestor ya fue arrancado, ignora la orden y devuelve el mismo token original para prevenir repeticiones.
     *
     * @return el token numérico identificador emitido.
     */
    @Override
    public int startSimulation() {
        if (getToken() >= 0) {
            return getToken();
        }

        token = numberOfSimulations++;

        pool.submit(simulationEngine);

        status = SimulationStatus.RUNNING;

        return token;
    }
}