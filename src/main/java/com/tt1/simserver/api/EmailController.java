package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.EmailResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al envío de correos electrónicos.
 */
@Path("/Email")
public class EmailController implements EmailApi {

    /**
     * Endpoint POST: Procesa la solicitud para enviar un correo electrónico a un destinatario.
     *
     * <p>Precondición: {@code emailAddress} y {@code message} no son nulos.
     *
     * <p>Postcondición: Crea un objeto de respuesta indicando que la operación fue un éxito y devuelve una respuesta HTTP 201 (Created) lista para ser enviada al cliente.
     *
     * @param emailAddress la dirección de correo del destinatario.
     * @param message      el contenido del correo a enviar.
     * @return la respuesta HTTP empaquetada con el estado de la operación.
     */
    @Override
    public Response emailPost(String emailAddress, String message) {

        // Validaciones preventivas básicas por si faltan los parámetros
        if (emailAddress == null || emailAddress.isEmpty() || message == null || message.isEmpty()) {
            EmailResponse errorResponse = new EmailResponse();
            errorResponse.setDone(false);
            errorResponse.setErrorMessage("La dirección de correo y el mensaje son obligatorios.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        }

        // [Futuros Sprints]: Integración con el servicio real de envío de correos.

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setDone(true);

        return Response.status(Response.Status.CREATED)
                .entity(emailResponse)
                .build();
    }
}