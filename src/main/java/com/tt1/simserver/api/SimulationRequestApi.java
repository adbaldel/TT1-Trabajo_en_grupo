package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.SimulationRequestJson;
import com.tt1.simserver.api.transformers.RequestTransformer;
import com.tt1.simserver.model.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Define el contrato de la API para la gestión de solicitudes de simulación.
 */
public interface SimulationRequestApi {

    /**
     * Obtiene el estado de la simulación del usuario {@code username} y token {@code token}. Si no existen una
     * simulación para el usuario y token dados devuelve un detalle de problema.
     *
     * @param username el nombre del usuario.
     * @param token    el token de la solicitud.
     * @return una respuesta HTTP con el estado de la simulación y estado 201 CREATED; o con un detalle del problema y
     * estado 400 BAD_REQUEST.
     */
    @GET
    @Path("/ComprobarSolicitud")
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudComprobarSolicitudGet(@QueryParam("nombreUsuario") String username,
                                            @QueryParam("tok") Integer token);

    /**
     * Obtiene los tokens de todas las simulaciones del usuario {@code username}. Si no existe el usuario o no tiene
     * simulaciones devuelve una lista vacía.
     *
     * @param username el nombre del usuario.
     * @return una respuesta HTTP con los tokens de la simulación y estado 201 CREATED.
     */
    @GET
    @Path("/GetSolicitudesUsuario")
    @Produces({"application/json", "text/json"})
    Response solicitudGetSolicitudesUsuarioGet(@QueryParam("nombreUsuario") String username);

    /**
     * Procesa una solicitud de simulación {@code request} de un usuario con nombre {@code username}. Si el usuario no
     * cumple con las precondiciones de {@link User#User(String)} o la solicitud no cumple con las precondiciones de
     * {@link RequestTransformer#transform} devuelve un detalle de problema.
     *
     * @param username              el nombre del usuario solicitante
     * @param simulationRequestJSON los datos de la solicitud
     * @return una respuesta HTTP con el identificador asignado a la nueva simulación
     */
    @POST
    @Path("/Solicitar")
    @Consumes({"application/json", "text/json", "application/*+json"})
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudSolicitarPost(@QueryParam("nombreUsuario") String username,
                                    SimulationRequestJson simulationRequestJSON);
}