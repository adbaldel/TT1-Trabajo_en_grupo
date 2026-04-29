package com.tt1.simserver.presentation;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Interfaz de la API encargada de definir los endpoints para el envío de correos electrónicos.
 */
public interface EmailApi {

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
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response emailPost(@QueryParam("emailAddress") String emailAddress, @QueryParam("message") String message);
}