package com.tt1.simserver.presentation;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Interfaz de la API enfocada en la consulta y obtención de resultados generados por simulaciones ya finalizadas.
 */
public interface ResultsApi {

    /**
     * Endpoint POST que recupera y envía los resultados de una simulación para el usuario indicado.
     *
     * @param username el nombre del usuario al cual pertenecen los resultados.
     * @param tok      el token identificador asociado unívocamente a esa simulación específica.
     * @return la respuesta HTTP informando de la correcta obtención de los datos (ej. 201 Created y cuerpo ResultsResponse) o detallando un error si ha fallado.
     */
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response resultadosPost(@QueryParam("nombreUsuario") String username, @QueryParam("tok") Integer tok);
}