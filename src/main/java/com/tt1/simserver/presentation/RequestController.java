package com.tt1.simserver.presentation;

import com.tt1.simserver.model.jsonrepresentations.Request;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al servicio de solicitudes de simulación.
 */
@Path("/Solicitud")
public class RequestController implements RequestApi {

    /**
     * Endpoint GET: Comprueba el estado actual de una solicitud de simulación existente.
     *
     * <p>Precondición: {@code username} no es nulo y {@code token} contiene un identificador válido.
     *
     * <p>Postcondición: Lanza siempre una excepción de operación no soportada, ya que la funcionalidad no ha sido implementada todavía.
     *
     * @param username el nombre de la cuenta del usuario.
     * @param token el identificador numérico de la simulación.
     * @return la respuesta HTTP con el estado de la simulación.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response solicitudComprobarSolicitudGet(String username, Integer token) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Endpoint POST: Recupera todas las solicitudes de simulación asociadas a un usuario en el sistema.
     *
     * <p>Precondición: {@code username} no es nulo.
     *
     * <p>Postcondición: Lanza siempre una excepción de operación no soportada, ya que la funcionalidad no ha sido implementada todavía.
     *
     * @param username el nombre de cuenta del usuario a consultar.
     * @return la respuesta HTTP con la lista de identificadores.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response solicitudGetSolicitudesUsuarioGet(String username) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Endpoint POST: Crea y registra una nueva solicitud de simulación para el usuario rellenando un nuevo tablero.
     *
     * <p>Precondición: {@code username} no es nulo y {@code request} contiene los datos iniciales válidos de las criaturas.
     *
     * <p>Postcondición: Lanza siempre una excepción de operación no soportada, ya que la funcionalidad no ha sido implementada todavía.
     *
     * @param username el identificador del usuario que hace la solicitud.
     * @param request el objeto con la especificación y cantidades de criaturas a incluir en el tablero.
     * @return la respuesta HTTP informando del token asignado a la simulación.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response solicitudSolicitarPost(String username, Request request) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}