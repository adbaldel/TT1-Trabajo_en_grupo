package com.tt1.simserver.api;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Define el contrato de la API para la obtención de resultados de simulaciones.
 */
public interface SimulationResultApi {

    /**
     * Obtiene los resultados de la simulación del usuario {@code username} y token {@code token}. Si no existen
     * resultados de simulación para el usuario y token dados devuelve un detalle de problema.
     *
     * @param username el nombre del usuario.
     * @param token    el token de la solicitud.
     * @return una respuesta HTTP con los resultados de la simulación y estado 201 CREATED; o con un detalle del
     * problema y estado 400 BAD_REQUEST.
     */
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response resultadosPost(@QueryParam("nombreUsuario") String username, @QueryParam("tok") Integer token);
}