package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gestor encargado de coordinar la ejecución asíncrona de una simulación específica.
 * Proporciona hilos desde un pull para realizar el cálculo del motor sin bloquear el servidor web,
 * a la vez que gestiona el estado y el token identificador de esta ejecución.
 */
public class SimulationManager implements SimulationManagerInterface {
    // Aprovechamos Java 21: Usamos Virtual Threads. Son extremadamente ligeros y perfectos
    // para ejecutar miles de simulaciones concurrentes sin agotar la RAM del servidor.
    private static final ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();

    // AtomicInteger garantiza que múltiples peticiones HTTP concurrentes
    // nunca obtengan el mismo token (Thread-safe).
    private static final AtomicInteger tokenGenerator = new AtomicInteger(0);

    private final SimulationEngineInterface simulationEngine;
    private int token;

    // 'volatile' asegura que si el hilo de la simulación cambia este valor,
    // los hilos HTTP de Jersey vean el cambio instantáneamente.
    private volatile SimulationStatus status;

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
        if (status != SimulationStatus.COMPLETED && simulationEngine.isDone()) {
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
    public synchronized int startSimulation() {
        if (token >= 0) {
            return token; // Evita que se inicie dos veces
        }

        // getAndIncrement() es una operación atómica y 100% segura entre hilos
        token = tokenGenerator.getAndIncrement();
        status = SimulationStatus.RUNNING;

        pool.submit(simulationEngine);

        return token;
    }
}