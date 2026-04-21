package com.tt1.simserver.presentation;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al servicio de resultados definidos por la interfaz ResultadosApi.
 */
@Path("/Resultados")
public class ResultsController implements ResultsApi {

    /**
     * {@inheritDoc}
     */
    @Override
    public Response resultadosPost(String username, Integer tok) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
