package com.tt1.simserver.presentacion;

import com.tt1.simserver.modelo.Solicitud;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Represents a collection of functions to interact with the API endpoints.
 */
@Path("/Solicitud")
public interface SolicitudApi {

    /**
     *
     *
     * @param nombreUsuario
     * @param tok
     * @return Created
     * @return Bad Request
     */
    @GET
    @Path("/ComprobarSolicitud")
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudComprobarSolicitudGet(@QueryParam("nombreUsuario") String nombreUsuario, @QueryParam("tok") Integer tok);

    /**
     *
     *
     * @param nombreUsuario
     * @return Created
     * @return Bad Request
     */
    @GET
    @Path("/GetSolicitudesUsuario")
    Response solicitudGetSolicitudesUsuarioGet(@QueryParam("nombreUsuario") String nombreUsuario);

    /**
     *
     *
     * @param nombreUsuario
     * @param solicitud
     * @return Created
     * @return Bad Request
     */
    @POST
    @Path("/Solicitar")
    @Consumes({"application/json", "text/json", "application/*+json"})
    @Produces({"text/plain", "application/json", "text/json"})
    Response solicitudSolicitarPost(@QueryParam("nombreUsuario") String nombreUsuario, Solicitud solicitud);

}
