package com.tt1.simserver.presentation;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al servicio de consulta de resultados.
 */
@Path("/Resultados")
public class ResultsController implements ResultsApi {

    /**
     * Endpoint POST: Recupera y envía los resultados finales y pasos de una simulación completada.
     *
     * <p>Precondición: {@code username} no es nulo y {@code tok} contiene un identificador válido de una simulación registrada.
     *
     * <p>Postcondición: Lanza siempre una excepción de operación no soportada, ya que la funcionalidad no ha sido implementada todavía.
     *
     * @param username el nombre del usuario al cual pertenecen los resultados.
     * @param tok el token identificador de la simulación.
     * @return la respuesta HTTP con los datos históricos del tablero en cada turno.
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response resultadosPost(String username, Integer tok) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}