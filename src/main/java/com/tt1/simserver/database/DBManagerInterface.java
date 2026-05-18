package com.tt1.simserver.database;

import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationData;
import com.tt1.simserver.model.User;

import java.util.List;

/**
 * Define el contrato de la capa de persistencia (Fachada/DAO) para la interacción con la base de datos.
 */
public interface DBManagerInterface {

    /**
     * Obtiene el usuario almacenado que tiene la misma clave que el usuario de consulta ({@code user}). Asume que el
     * usuario pasado de consulta es no nulo.
     *
     * @param user el usuario de consulta.
     * @return el usuario almacenado con la misma clave que el usuario de consulta, o {@code null} si no existe tal
     * usuario almacenado.
     */
    User getUser(User user);

    /**
     * Guarda en la base de datos del usuario ({@code user}). Asume que no existe ningún usuario almacenado con la misma
     * clave que el usuario a guardar y que el usuario a guardar tiene un nombre de usuario válido según
     * {@link User#User(String)}.
     *
     * @param user el usuario a guardar.
     */
    void saveUser(User user);

    /**
     * Obtiene la simulación almacenada que tiene la misma clave que la simulación de consulta ({@code simulation}).
     * Asume que la simulación pasada de consulta es no nula y tiene un token asignado.
     *
     * @param simulation la simulación de consulta.
     * @return la simulación almacenada con la misma clave que la simulación de consulta, o {@code null} si no existe
     * tal simulación almacenada.
     */
    Simulation getSimulation(Simulation simulation);

    /**
     * Obtiene los tokens de todas las simulaciones almacenadas asociadas al usuario de consulta ({@code user}). Asume
     * que el usuario de consulta es no nulo.
     *
     * @param user el usuario de consulta.
     * @return una lista con los tokens de todas las simulaciones almacenadas asociadas al usuario de consulta.
     */
    List<Integer> getUserTokens(User user);

    /**
     * Guarda en la base de datos la simulación ({@code simulation}). Asume que la simulación a guardar no es nula,
     * tiene un token, usuario, estado y tamaño de tablero asignados; que no existe ninguna simulación almacenada con la
     * misma clave que la simulación a guardar y que hay un usuario almacenado con la misma clave que el usuario de la
     * simulación. Nunca se guarda el resultado de la simulación, ni aunque la simulación haya acabado.
     *
     * @param simulation la simulación a guardar.
     */
    void saveSimulation(Simulation simulation);

    /**
     * Actualiza el estado de una simulación almacenada con el estado de la simulación actualizada ({@code simulation}).
     * Asume que la simulación actualizada no es nula, tiene un token y estado; y que existe una simulación almacenada
     * con la misma clave que la simulación actualizada.
     *
     * @param simulation la simulación actualizada.
     */
    void updateSimulationStatus(Simulation simulation);

    /**
     * Obtiene el resultado de una simulación almacenada que tiene la misma clave que la simulación de consulta
     * ({@code simulation}). Asume que la simulación de consulta es no nula y tiene un token asignado.
     *
     * @param simulation la simulación de consulta.
     * @return el resultado almacenado de la simulación almacenada con la misma clave que la simulación de consulta, o
     * {@code null} si no existe tal resultado de simulación almacenado.
     */
    SimulationData getSimulationResult(Simulation simulation);

    /**
     * Guarda en la base de datos el resultado de la simulación ({@code simulation}) y actualiza el estado de la
     * simulación almacenada con el estado de la simulación a guardar su resultado (acabada). Asume que la simulación a
     * guardar su resultado es no nula, tiene token asignado, estado completado y datos de simulación no nulos; que hay
     * una simulación almacenada con la misma clave que la simulación a guardar su resultado y que no hay un resultado
     * almacenado para la simulación almacenada con la misma clave que la simulación a guardar su resultado.
     *
     * @param simulation la simulación a guardar su resultado.
     */
    void saveSimulationResult(Simulation simulation);

    /**
     * Cierra los recursos utilizados por este gestor de base de datos.
     */
    void close();
}