package com.tt1.simserver.api;

import com.tt1.simserver.logic.SimulationService;
import com.tt1.simserver.logic.SimulationServiceInterface;
import com.tt1.simserver.model.*;
import com.tt1.simserver.api.jsonobjects.ProblemDetails;
import com.tt1.simserver.api.jsonobjects.ResultsResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

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
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response resultadosPost(String username, Integer tok) {
        User user = new User(username);

        // 1. Validar que existe una simulación de dicho usuario con el token dado
        if (!service.existsSimulation(user, tok)) {
            ProblemDetails problem = new ProblemDetails();
            problem.setType("https://api.simserver.com/errors/forbidden");
            problem.setTitle("Acceso denegado o simulación inexistente");
            problem.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problem.setDetail("El token " + tok + " no pertenece al usuario '" + username + "' o no existe.");
            problem.setInstance("/Resultados");

            return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
        }

        // 2. Verificar que la simulación haya terminado completamente
        SimulationStatus status = service.getSimulationStatus(user, tok);
        if (status != SimulationStatus.COMPLETED) {
            ProblemDetails problem = new ProblemDetails();
            problem.setType("https://api.simserver.com/errors/conflict");
            problem.setTitle("Simulación en curso");
            problem.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problem.setDetail("La simulación con token " + tok + " todavía no ha terminado (Estado actual: " + status.name() + ").");
            problem.setInstance("/Resultados");

            return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
        }

        // 3. Recuperar el historial y formatearlo al texto CSV plano solicitado
        SimulationResult result = service.getSimulationResult(user, tok);
        ResultsResponse response = new ResultsResponse();
        response.setDone(true);
        response.setRequestToken(tok);
        response.setData(ResultsResponse.convertToResultResponseData(result));

        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}