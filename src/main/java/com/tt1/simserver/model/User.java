package com.tt1.simserver.model;

import com.tt1.simserver.logic.SimulationManagerInterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Modela un cliente o usuario del sistema y sirve de nexo entre su perfil
 * y las distintas peticiones de simulación que ha efectuado.
 */
public class User {
    private final String username;
    private final Map<Integer, SimulationManagerInterface> requestedSimulations;


    /**
     * Constructor base de usuario identificándolo mediante su nombre.
     * Precondición: username no es nulo, user name no está vacío ni solo contiene carácteres invisibles (espacios,
     * saltos de línea, tabuladores, ...).
     *
     * @param username cadena identificadora de la cuenta de usuario.
     */
    public User(String username) {
        this.username = username;
        requestedSimulations = new HashMap<>();
    }


    /**
     * Obtiene el nombre con el cual se identifica este usuario.
     *
     * @return el nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Comprueba si el usuario tiene asociada alguna simulación en memoria con el token dado.
     * Precondición: token > 0.
     *
     * @param token el identificador numérico generado para la simulación consultada.
     * @return cierto si le pertenece dicha petición, falso de lo contrario.
     */
    public boolean existsSimulation(int token) {
        return requestedSimulations.containsKey(token);
    }

    /**
     * Devuelve el estado de una simulación basándose en el token.
     * Precondición: Existe una simulación con ese token {@link #existsSimulation(int)}.
     *
     * @param token identificador de la petición de simulación.
     * @return un enum con el estado de progreso (ej. RUNNING o COMPLETED).
     */
    public SimulationStatus getSimulationStatus(int token) {
        return requestedSimulations.get(token).getSimulationStatus();
    }

    /**
     * Devuelve el objeto que guarda el histórico de toda la simulación solicitada.
     * Precondición: Existe una simulación con ese token {@link #existsSimulation(int)}.
     *
     * @param token identificador numérico de la petición de simulación.
     * @return el resultado histórico asociado a ese token.
     */
    public SimulationResult getSimulationResult(int token) {
        return requestedSimulations.get(token).getSimulationResult();
    }

    /**
     * Recupera la colección completa de todos los tokens de simulaciones pedidas por el usuario.
     *
     * @return colección de valores numéricos de los tokens.
     */
    public Collection<Integer> getTokens() {
        return requestedSimulations.keySet();
    }

    /**
     * Añade un nuevo gestor de simulación recién creado al historial y mapa de simulaciones de la cuenta de este usuario.
     * Precondición: simulationManager es no nulo y está iniciado.
     *
     * @param simulationManager la instancia administradora de la tarea.
     */
    public void addRequest(SimulationManagerInterface simulationManager) {
        requestedSimulations.put(simulationManager.getToken(), simulationManager);
    }

    /**
     * Evalúa si un usuario es igual a otro analizando únicamente la cadena de texto de su nombre de usuario.
     * Dos usuarios son iguales si su nombre es exactamente igual (importa la capitalización).
     *
     * @param other objeto de contraste para verificar igualdad.
     * @return cierto si los dos usuarios tienen idéntico username.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        User otherUser = (User) other;

        return username.equals(otherUser.username);
    }
}
