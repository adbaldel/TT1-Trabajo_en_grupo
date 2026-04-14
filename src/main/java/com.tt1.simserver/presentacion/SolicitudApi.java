package com.tt1.simserver.presentacion;

import com.tt1.simserver.modelo.Solicitud;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Interfaz que define los endpoints de la API necesarios para crear nuevas peticiones de simulación y consultar el estado o el conjunto de solicitudes de un usuario.
 */
@Path("/Solicitud")
public interface SolicitudApi {

    /**
     * Endpoint GET para comprobar el estado de una simulación a través de su token.
     *
     * @param nombreUsuario el nombre de la cuenta del usuario que hizo la petición.
     * @param tok           el token con el cual se puede buscar la simulación solicitada.
     * @return un objeto Response que representa el resultado de la comprobación (ej. array con estado o similar).
     */
    @GET
    @Path("/ComprobarSolicitud")
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudComprobarSolicitudGet(@QueryParam("nombreUsuario") String nombreUsuario, @QueryParam("tok") Integer tok);

    /**
     * Endpoint GET para recuperar los tokens de las simulaciones vinculadas a un usuario concreto.
     *
     * @param nombreUsuario el nombre de cuenta del que se extraen todas sus solicitudes pasadas.
     * @return la respuesta conteniendo la lista de identificadores numéricos de las simulaciones solicitadas por este cliente.
     */
    @GET
    @Path("/GetSolicitudesUsuario")
    Response solicitudGetSolicitudesUsuarioGet(@QueryParam("nombreUsuario") String nombreUsuario);

    /**
     * Endpoint POST encargado de crear y registrar una nueva simulación con sus condiciones iniciales provistas.
     *
     * @param nombreUsuario el identificador del usuario que hace la solicitud de simulación.
     * @param solicitud     un JSON mapeado a objeto que contiene listas con cantidades y nombres de las entidades a participar.
     * @return respuesta HTTP que, de ir bien, contendrá un SolicitudResponse detallando el token que le ha sido asignado a la nueva simulación (201 Created).
     */
    @POST
    @Path("/Solicitar")
    @Consumes({"application/json", "text/json", "application/*+json"})
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudSolicitarPost(@QueryParam("nombreUsuario") String nombreUsuario, Solicitud solicitud);
}