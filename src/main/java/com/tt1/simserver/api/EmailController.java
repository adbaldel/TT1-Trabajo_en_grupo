package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.EmailResponseJson;
import com.tt1.simserver.api.jsonobjects.ProblemDetailsJson;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Controlador REST que implementa la API de correos electrónicos.
 */
@Path("/Email")
public class EmailController implements EmailApi {

    @Override
    public Response emailPost(String emailAddress, String message) {

        if (emailAddress == null || !emailAddress.matches("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$") || message == null) {
            ProblemDetailsJson problemDetailsJson = new ProblemDetailsJson();
            problemDetailsJson.setType("https://api.simserver.com/errors/invalid-email-address");
            problemDetailsJson.setTitle("Dirección de correo no válida");
            problemDetailsJson.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problemDetailsJson.setDetail("La dirección " + emailAddress + " no es una dirección de correo válida");
            problemDetailsJson.setInstance("/Email");

            return Response.status(Response.Status.BAD_REQUEST).entity(problemDetailsJson).build();
        }

        EmailResponseJson emailResponseJson = new EmailResponseJson();
        emailResponseJson.setDone(true);
        emailResponseJson.setErrorMessage(null);

        return Response.status(Response.Status.CREATED).entity(emailResponseJson).build();
    }
}