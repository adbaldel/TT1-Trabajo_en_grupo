package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.Request;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Interfaz que define los endpoints de la API necesarios para crear nuevas peticiones de simulación y consultar el estado o el conjunto de solicitudes de un usuario.
 */
public interface RequestApi {

    /**
     * Endpoint GET: Comprueba el estado actual de una solicitud de simulación existente.
     *
     * <p>Precondición: {@code username} no es nulo y {@code token} contiene un identificador válido.
     *
     * <p>Postcondición: TODO
     *
     * @param username el nombre de la cuenta del usuario.
     * @param token    el identificador numérico de la simulación.
     * @return la respuesta HTTP con el estado de la simulación.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @GET
    @Path("/ComprobarSolicitud")
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudComprobarSolicitudGet(@QueryParam("nombreUsuario") String username, @QueryParam("tok") Integer token);

    /**
     * Endpoint POST: Recupera todas las solicitudes de simulación asociadas a un usuario en el sistema.
     *
     * <p>Precondición: {@code username} no es nulo.
     *
     * <p>Postcondición: TODO
     *
     * @param username el nombre de cuenta del usuario a consultar.
     * @return la respuesta HTTP con la lista de identificadores.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @GET
    @Path("/GetSolicitudesUsuario")
    Response solicitudGetSolicitudesUsuarioGet(@QueryParam("nombreUsuario") String username);

    /**
     * Endpoint POST: Crea y registra una nueva solicitud de simulación para el usuario rellenando un nuevo tablero.
     *
     * <p>Precondición: {@code username} no es nulo y {@code request} contiene los datos iniciales válidos de las criaturas.
     *
     * <p>Postcondición: TODO
     *
     * @param username el identificador del usuario que hace la solicitud.
     * @param request  el objeto con la especificación y cantidades de criaturas a incluir en el tablero.
     * @return la respuesta HTTP informando del token asignado a la simulación.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @POST
    @Path("/Solicitar")
    @Consumes({"application/json", "text/json", "application/*+json"})
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudSolicitarPost(@QueryParam("nombreUsuario") String username, Request request);
}