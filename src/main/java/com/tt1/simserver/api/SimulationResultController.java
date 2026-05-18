package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.ProblemDetailsJson;
import com.tt1.simserver.api.jsonobjects.SimulationResultResponseJson;
import com.tt1.simserver.logic.SimulationServiceInterface;
import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationData;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Controlador REST que implementa la API de resultados de simulaciones.
 */
@Path("/Resultados")
public class SimulationResultController implements SimulationResultApi {

    private final SimulationServiceInterface service;

    /**
     * Construye un controlador de resultados. Asume que {@code service} es una instancia válida y no nula que provee la
     * lógica de simulación.
     *
     * @param service el servicio de simulación a utilizar.
     */
    @Inject
    public SimulationResultController(SimulationServiceInterface service) {
        this.service = service;
    }

    @Override
    public Response resultadosPost(String username, Integer token) {
        User queryUser = new User(username);
        Simulation querySimulation = new Simulation(token, queryUser, 0);

        if (!service.existsSimulation(querySimulation)) {
            ProblemDetailsJson problemDetailsJson = new ProblemDetailsJson();
            problemDetailsJson.setType("https://api.simserver.com/errors/simulation-not-found");
            problemDetailsJson.setTitle("Simulación no encontrada");
            problemDetailsJson.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problemDetailsJson.setDetail("El token " + token + " no pertenece al usuario '" + username + "' o no " +
                    "existe.");
            problemDetailsJson.setInstance("/Resultados");

            return Response.status(Response.Status.BAD_REQUEST).entity(problemDetailsJson).build();
        }

        SimulationStatus status = service.getSimulationStatus(querySimulation);
        if (status != SimulationStatus.COMPLETED) {
            ProblemDetailsJson problemDetailsJson = new ProblemDetailsJson();
            problemDetailsJson.setType("https://api.simserver.com/errors/simulation-not-completed");
            problemDetailsJson.setTitle("Simulación no acabada");
            problemDetailsJson.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problemDetailsJson.setDetail("La simulación con token " + token + " todavía no ha terminado (Estado " +
                    "actual: " + status.name() + ").");
            problemDetailsJson.setInstance("/Resultados");

            return Response.status(Response.Status.BAD_REQUEST).entity(problemDetailsJson).build();
        }

        SimulationData simulationResult = service.getSimulationResult(querySimulation);
        SimulationResultResponseJson simulationResultResponseJson = new SimulationResultResponseJson();
        simulationResultResponseJson.setDone(true);
        simulationResultResponseJson.setRequestToken(token);
        simulationResultResponseJson.setData(simulationResult.toCsvStringUsingCreatureName());
        simulationResultResponseJson.setErrorMessage(null);

        return Response.status(Response.Status.CREATED).entity(simulationResultResponseJson).build();
    }
}