package com.tt1.simserver.logic;

import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.presentation.jsonobjects.Request;

import java.util.Collection;

/**
 * Define las operaciones de negocio para administrar usuarios, coordinar tableros y gestionar solicitudes de simulación.
 */
public interface SimulationServiceInterface {

    /**
     * Recupera un usuario en memoria o lo crea si no existe.
     *
     * <p>Precondición: {@code user} no es nulo y tiene un nombre asignado.
     *
     * <p>Postcondición: Funciona como caché. Si el usuario ya está registrado, devuelve la misma referencia en memoria. Si no existe, lo instancia, lo guarda en la colección y lo devuelve conservando su nombre intacto.
     *
     * @param user el objeto usuario usado para buscar o registrar.
     * @return la instancia persistente del usuario en el sistema.
     */
    User getUser(User user);

    /**
     * Comprueba si una simulación pertenece a un usuario concreto.
     *
     * <p>Precondición: {@code user} no es nulo.
     *
     * <p>Postcondición: Devuelve verdadero si el sistema certifica que el token consta en el registro de peticiones del usuario. Devuelve falso si el usuario no es propietario de dicho token.
     *
     * @param user  el usuario que hace la solicitud.
     * @param token el identificador de la simulación a verificar.
     * @return verdadero si el token pertenece al usuario, falso en caso contrario.
     */
    boolean existsSimulation(User user, int token);

    /**
     * Consulta el estado de una simulación vinculada a un usuario.
     *
     * <p>Precondición: {@code user} no es nulo y es el propietario real del token indicado.
     *
     * <p>Postcondición: Delega la consulta y retorna el estado exacto extraído directamente desde el gestor interno de esa simulación.
     *
     * @param user  el usuario propietario de la simulación.
     * @param token el identificador de la simulación.
     * @return el estado de ejecución actual de la simulación solicitada.
     */
    SimulationStatus getSimulationStatus(User user, int token);

    /**
     * Recupera el resultado histórico de una simulación específica de un usuario.
     *
     * <p>Precondición: {@code user} no es nulo y es el propietario real del token indicado.
     *
     * <p>Postcondición: Extrae y devuelve el historial completo del tablero expuesto por el gestor subyacente de la simulación.
     *
     * @param user  el usuario propietario de la simulación.
     * @param token el identificador de la simulación.
     * @return el objeto con el historial de resultados devuelto por el gestor.
     */
    SimulationResult getSimulationResult(User user, int token);

    /**
     * Lista todos los identificadores de simulación registrados a nombre de un usuario.
     *
     * <p>Precondición: {@code user} no es nulo.
     *
     * <p>Postcondición: Devuelve una colección que agrupa todos los tokens solicitados históricamente por el usuario. La colección nunca es nula, aunque el usuario no tenga simulaciones.
     *
     * @param user el usuario a consultar.
     * @return una colección con los tokens pertenecientes al usuario.
     */
    Collection<Integer> getUserTokens(User user);

    /**
     * Orquesta la creación, configuración y arranque de una nueva simulación para un usuario.
     *
     * <p>Precondición: {@code user} y {@code request} no son nulos. Las cantidades iniciales en la solicitud son enteros no negativos.
     *
     * <p>Postcondición: Construye un tablero de tamaño dinámico, lo puebla aleatoriamente con las criaturas solicitadas, arranca asíncronamente los cálculos de turnos y amarra la petición a la cuenta del usuario. Devuelve un token válido superior o igual a cero.
     *
     * @param user    el usuario que solicita crear la simulación.
     * @param request el objeto con la especificación de criaturas a incluir.
     * @return el nuevo token numérico asignado a la simulación.
     */
    int requestSimulation(User user, Request request);
}
