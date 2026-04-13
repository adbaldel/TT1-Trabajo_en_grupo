package com.tt1.simserver.presentacion;

import com.tt1.simserver.modelo.EmailResponse;
import jakarta.ws.rs.core.Response;

public class EmailController implements EmailApi {

    @Override
    public Response emailPost(String emailAddress, String message) {
        // 1. Call your 'logica' (Service) here to send the email
        // 2. Return the generated model wrapped in a JAX-RS Response

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setDone(true);

        // 201 Created is the status code defined in your swagger.json
        return Response.status(Response.Status.CREATED).entity(emailResponse).build();
    }
}