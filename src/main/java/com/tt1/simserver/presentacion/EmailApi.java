package com.tt1.simserver.presentacion;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Interfaz de la API encargada de definir los endpoints para el envío de correos electrónicos.
 */
public interface EmailApi {

    /**
     * Endpoint POST para realizar el envío de un correo a una dirección de email indicada.
     *
     * @param emailAddress la dirección de correo del destinatario donde se debe enviar el mensaje.
     * @param message      el cuerpo o contenido textual del correo.
     * @return un objeto Response que contiene el estado HTTP (como 201 Created) y el cuerpo con la estructura EmailResponse, o en su defecto ProblemDetails si la operación falla (por ejemplo 400 Bad Request).
     */
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response emailPost(@QueryParam("emailAddress") String emailAddress, @QueryParam("message") String message);
}