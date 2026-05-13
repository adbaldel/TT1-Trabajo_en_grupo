package com.tt1.simserver.api;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Define el contrato de la API para el envío de correos electrónicos.
 */
public interface EmailApi {

    /**
     * Procesa el envío de un correo electrónico a {@code emailAddress} con el mensaje {@code message}. Si la dirección
     * de correo electrónico no es válida devuelve un detalle de problema.
     *
     * @param emailAddress la dirección de destino.
     * @param message      el contenido del mensaje.
     * @return una respuesta HTTP indicando el éxito del envío y estado 201 CREATED; o con un detalle del problema y
     * estado 400 BAD_REQUEST.
     */
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response emailPost(@QueryParam("emailAddress") String emailAddress, @QueryParam("message") String message);
}