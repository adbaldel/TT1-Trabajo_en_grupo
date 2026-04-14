package com.tt1.simserver.presentacion;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Represents a collection of functions to interact with the API endpoints.
 */
@Path("/Resultados")
public interface ResultadosApi {

    /**
     *
     *
     * @param nombreUsuario
     * @param tok
     * @return Created
     * @return Bad Request
     */
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response resultadosPost(@QueryParam("nombreUsuario") String nombreUsuario, @QueryParam("tok") Integer tok);

}
