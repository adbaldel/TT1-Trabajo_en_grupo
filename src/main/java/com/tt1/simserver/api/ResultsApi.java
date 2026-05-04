package com.tt1.simserver.api;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Interfaz de la API enfocada en la consulta y obtención de resultados generados por simulaciones ya finalizadas.
 */
public interface ResultsApi {

    /**
     * Endpoint POST: Recupera y envía los resultados finales y pasos de una simulación completada.
     *
     * <p>Precondición: {@code username} no es nulo y {@code tok} contiene un identificador válido de una simulación registrada.
     *
     * <p>Postcondición: TODO
     *
     * @param username el nombre del usuario al cual pertenecen los resultados.
     * @param tok      el token identificador de la simulación.
     * @return la respuesta HTTP con los datos históricos del tablero en cada turno.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @POST
    @Produces({"text/plain", "application/json", "text/json"})
    Response resultadosPost(@QueryParam("nombreUsuario") String username, @QueryParam("tok") Integer tok);
}