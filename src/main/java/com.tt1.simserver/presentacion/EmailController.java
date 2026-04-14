package com.tt1.simserver.presentacion;

import com.tt1.simserver.modelo.EmailResponse;
import jakarta.ws.rs.core.Response;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al envío de correos electrónicos definidos por la interfaz EmailApi.
 */
public class EmailController implements EmailApi {

    /**
     * Recibe una solicitud HTTP para el envío de un correo y procesa la petición de forma interna devolviendo una respuesta.
     *
     * @param emailAddress la dirección de correo a la cual enviar la información.
     * @param message      texto que se enviará en el cuerpo del correo.
     * @return un objeto Response indicando la creación (201) de la orden y un EmailResponse adjunto.
     */
    @Override
    public Response emailPost(String emailAddress, String message) {

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