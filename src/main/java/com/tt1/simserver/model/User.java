package com.tt1.simserver.model;

import com.tt1.simserver.logic.SimulationManagerInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Identifica a un cliente dentro del servidor y agrupa todas las solicitudes de simulación que ha creado.
 */
public class User {
    private final String username;
    private final Map<Integer, SimulationManagerInterface> requestedSimulations;

    /**
     * Registra un nuevo perfil de usuario en el sistema.
     *
     * <p>Precondición: {@code username} no es nulo, no está vacío y contiene caracteres válidos (no solo espacios o saltos de línea).
     *
     * <p>Postcondición: El usuario queda inicializado con el nombre indicado y con un historial de simulaciones vacío.
     *
     * @param username el nombre identificador para esta cuenta.
     */
    public User(String username) {
        this.username = username;
        requestedSimulations = new HashMap<>();
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto exacta con el nombre de cuenta asignado en su registro.
     *
     * @return el nombre del usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Comprueba si una simulación fue pedida por este usuario.
     *
     * <p>Precondición: {@code token} es un identificador numérico mayor o igual a cero.
     *
     * <p>Postcondición: Devuelve verdadero si el token se encuentra en el historial del usuario. Devuelve falso si el token no existe o pertenece a otro cliente.
     *
     * @param token el identificador de la simulación.
     * @return verdadero si el usuario es dueño del token, falso en caso contrario.
     */
    public boolean existsSimulation(int token) {
        return requestedSimulations.containsKey(token);
    }

    /**
     * Recupera el estado actual de una simulación de este usuario.
     *
     * <p>Precondición: El usuario es el dueño de la simulación ({@code existsSimulation(token)} es verdadero).
     *
     * <p>Postcondición: Devuelve el estado de ejecución (pendiente, en ejecución o completado) extraído de su gestor interno.
     *
     * @param token el identificador de la simulación consultada.
     * @return el estado de la simulación.
     */
    public SimulationStatus getSimulationStatus(int token) {
        return requestedSimulations.get(token).getSimulationStatus();
    }

    /**
     * Recupera el historial de un tablero tras finalizar su simulación.
     *
     * <p>Precondición: El usuario es el dueño de la simulación ({@code existsSimulation(token)} es verdadero).
     *
     * <p>Postcondición: Devuelve el objeto con los pasos generados por la simulación. Puede ser nulo si la simulación todavía no ha terminado sus turnos.
     *
     * @param token el identificador de la simulación.
     * @return los resultados del tablero asociado al token.
     */
    public SimulationResult getSimulationResult(int token) {
        return requestedSimulations.get(token).getSimulationResult();
    }

    /**
     * Obtiene la lista con todos los identificadores de simulación de este usuario.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una colección que contiene todos los tokens generados para este usuario. La colección está vacía si el usuario no ha creado ninguna petición.
     *
     * @return una colección con los números de los tokens.
     */
    public Collection<Integer> getTokens() {
        return requestedSimulations.keySet();
    }

    /**
     * Vincula una nueva solicitud de simulación al historial del usuario.
     *
     * <p>Precondición: {@code simulationManager} no es nulo y tiene un token válido asignado (mayor o igual a cero).
     *
     * <p>Postcondición: El gestor de la simulación se archiva en la cuenta de este usuario y estará accesible usando su token.
     *
     * @param simulationManager el gestor de la tarea iniciada.
     */
    public void addRequest(SimulationManagerInterface simulationManager) {
        requestedSimulations.put(simulationManager.getToken(), simulationManager);
    }

    /**
     * Compara este usuario con otro perfil basándose en su nombre.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero solo si el objeto proporcionado es un usuario y posee exactamente la misma cadena de texto en su nombre (incluyendo mayúsculas). Devuelve falso en caso contrario.
     *
     * @param other el objeto a comparar con este usuario.
     * @return verdadero si comparten el mismo nombre, falso si son distintos.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        User otherUser = (User) other;

        return username.equals(otherUser.username);
    }
}