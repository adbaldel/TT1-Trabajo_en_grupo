package com.tt1.simserver.presentation;

import com.tt1.simserver.logic.SimulationService;
import com.tt1.simserver.logic.SimulationServiceInterface;
import com.tt1.simserver.model.*;
import com.tt1.simserver.presentation.jsonobjects.ProblemDetails;
import com.tt1.simserver.presentation.jsonobjects.ResultsResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al servicio de consulta de resultados.
 */
@Path("/Resultados")
public class ResultsController implements ResultsApi {

    // Recuperamos la instancia global del servicio
    private final SimulationServiceInterface service = SimulationService.getInstance();

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
     */
    @Override
    public Response resultadosPost(String username, Integer tok) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}