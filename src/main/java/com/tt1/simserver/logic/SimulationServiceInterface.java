package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.model.jsonrepresentations.Request;

import java.util.Collection;

public interface SimulationServiceInterface {

    /**
     * Obtiene el usuario del almacenamiento interno o lo registra si no existía.
     * Precondición: user no es nulo.
     *
     * @param user objeto usuario transitorio a buscar.
     * @return la instancia del usuario administrada en el servicio.
     */
    User getUser(User user);

    /**
     * Comprueba si una simulación concreta existe para un usuario indicado.
     * Precondición: user no es nulo, token >= 0.
     *
     * @param user  el usuario propietario.
     * @param token identificador de la simulación.
     * @return cierto si existe la simulación solicitada para ese usuario, falso en caso contrario.
     */
    boolean existsSimulation(User user, int token);

    /**
     * Devuelve el estado actual de una simulación solicitada por un usuario.
     * Precondición: Existe la simulación para dicho usuario.
     *
     * @param user  el usuario propietario de la simulación.
     * @param token identificador de la simulación.
     * @return el estado de progreso (PENDING, RUNNING, COMPLETED).
     */
    SimulationStatus getSimulationStatus(User user, int token);

    /**
     * Obtiene el resultado final de una simulación para el usuario especificado.
     * Precondición: Existe la simulación para dicho usuario.
     *
     * @param user  el usuario propietario de la simulación.
     * @param token identificador de la simulación.
     * @return la colección de pasos procesados de la simulación.
     */
    SimulationResult getSimulationResult(User user, int token);

    /**
     * Recupera todos los tokens de simulaciones solicitadas históricamente por un usuario.
     * Precondición: user no es nulo.
     *
     * @param user el usuario a consultar.
     * @return colección con los números de los tokens asociados al usuario.
     */
    Collection<Integer> getUserTokens(User user);

    /**
     * Inicia una nueva solicitud de simulación para un usuario determinado basándose en su request JSON.
     * Precondición: user no es nulo, request no es nula, los nombres de criaturas de request son no nulas y son algunas
     * de las registradas en el sistema, las cantidades iniciales de las criaturas de request son no nulas y mayores que
     * cero, los nombres de las entidades y las cantidades iniciales de las criaturas se corresponden en orden.
     *
     * @param user    el usuario que realiza la petición HTTP.
     * @param request los datos (entidades y cantidades iniciales) recibidos.
     * @return el token numérico asignado a la nueva simulación que procesa esta solicitud.
     */
    int requestSimulation(User user, Request request);
}
