package com.tt1.simserver.presentation;

import com.tt1.simserver.model.jsonrepresentations.EmailResponse;
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
     * @param message el contenido del correo a enviar.
     * @return la respuesta HTTP empaquetada con el estado de la operación.
     */
    @Override
    public Response emailPost(String emailAddress, String message) {

        // 1. Aquí llamaremos a las clases del paquete 'logica'
        // para realizar la acción (ej. enviar el email real).
        // De momento nada, se implementará en futuros sprints

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