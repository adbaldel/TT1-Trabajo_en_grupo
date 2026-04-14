package com.tt1.simserver.presentacion;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Represents a collection of functions to interact with the API endpoints.
 */
@Path("/Email")
public interface EmailApi {

    /**
     *
     *
     * @param emailAddress
     * @param message
     * @return Created
     * @return Bad Request
     */
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response emailPost(@QueryParam("emailAddress") String emailAddress, @QueryParam("message") String message);
}
