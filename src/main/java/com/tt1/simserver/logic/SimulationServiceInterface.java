package com.tt1.simserver.logic;

import com.tt1.simserver.model.*;

import java.util.Collection;

/**
 * Define el contrato de la capa de lógica de negocio (Fachada/DAO) para gestionar simulaciones.
 */
public interface SimulationServiceInterface {

    /**
     * Obtiene los nombres de todas las criaturas que el servicio sabe simular.
     *
     * @return la colección los nombres de todas las criaturas que el servicio sabe simular.
     */
    Collection<String> getCreatures();

    /**
     * Comprueba si existe una simulación almacenada con la misma clave que la simulación de consulta {@code simulation}
     * y asociada al usuario con la misma clave que el usuario de la simulación. Asume que la simulación de consulta y
     * el usuario de consulta no son nulos.
     *
     * @param simulation la simulación de consulta.
     * @return cierto hay una simulación almacenada con la misma clave, falso en caso contrario.
     */
    boolean existsSimulation(Simulation simulation);

    /**
     * Obtiene el estado de la simulación almacenada con la misma clave que la simulación de consulta {@code simulation}
     * y asociada al usuario con la misma clave que el usuario de la simulación. Asume que la simulación de consulta y
     * el usuario de consulta no son nulos; y que existe una simulación almacenada con la misma clave asociado al
     * usuario con la misma clave.
     *
     * @param simulation la simulación de consulta.
     * @return el estado de la simulación almacenada.
     */
    SimulationStatus getSimulationStatus(Simulation simulation);

    /**
     * Obtiene el resultado de la simulación almacenada con la misma clave que la simulación de consulta
     * {@code simulaiton} y asociada al usuario con la misma clave que el usuario de la simulación. Asume que la
     * simulación de consulta y el usuario de consulta no son nulos, que existe una simulación almacenada con la misma
     * clave asoicada al usuario con la misma clave y que el estado de la simulación almacenada es acabada.
     *
     * @param simulation la simulación de consulta.
     * @return el resultado de la simulación almacenado.
     */
    SimulationData getSimulationResult(Simulation simulation);

    /**
     * Obtiene los tokens de las simulaciones almacenadas asociadas al usuario de consulta {@code user}. Asume que el
     * usuario es no nulo.
     *
     * @param user el usuario de consulta.
     * @return la colección de tokens asociados a simulaciones almacenadas del usuario.
     */
    Collection<Integer> getUserTokens(User user);

    /**
     * Solicita la creación y ejecución de una simulación con los datos contenidos en {@code simulaitonRequest}. Asume
     * que la solicitud de simulación es no nula.
     *
     * @param simulationRequest la solicitud de simulación.
     * @return la simulación creada que se está ejecutando asociada a la solicitud.
     */
    Simulation requestSimulation(SimulationRequest simulationRequest);

    /**
     * Apaga de forma limpia el gestor de simulaciones y gestor de base de datos.
     */
    void shutdown();
}