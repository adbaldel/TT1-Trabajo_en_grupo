package com.tt1.simserver.presentacion;

import com.tt1.simserver.modelo.EmailResponse;
import jakarta.ws.rs.core.Response;

public class EmailController implements EmailApi
{

    @Override
    public Response emailPost(String emailAddress, String message)
    {

        // 1. Aquí llamaremos a las clases del paquete 'logica'
        // para realizar la acción (ej. enviar el email real).

        // 2. Preparamos el objeto de respuesta autogenerado (del paquete 'modelo')
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setDone(true);

        // 3. Devolvemos la respuesta empaquetada en el formato JAX-RS
        // '201 Created' coincide con lo que definimos en el swagger.json
        return Response.status(Response.Status.CREATED)
                .entity(emailResponse)
                .build();
    }
}