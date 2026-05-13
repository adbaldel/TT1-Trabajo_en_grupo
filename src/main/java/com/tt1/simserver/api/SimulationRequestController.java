package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.ProblemDetailsJson;
import com.tt1.simserver.api.jsonobjects.SimulationRequestJson;
import com.tt1.simserver.api.jsonobjects.SimulationRequestResponseJson;
import com.tt1.simserver.api.transformers.RequestTransformer;
import com.tt1.simserver.logic.SimulationServiceInterface;
import com.tt1.simserver.model.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.Random;

/**
 * Controlador REST que implementa la API de solicitudes de simulación.
 */
@Path("/Solicitud")
public class SimulationRequestController implements SimulationRequestApi {

    private final SimulationServiceInterface service;

    /**
     * Construye un controlador de solicitudes. Asume que {@code service} es una instancia válida y no nula que provee
     * la lógica de simulación.
     *
     * @param service el servicio de simulación a utilizar.
     */
    @Inject
    public SimulationRequestController(SimulationServiceInterface service) {
        this.service = service;
    }

    @Override
    public Response solicitudComprobarSolicitudGet(String username, Integer token) {
        User queryUser = new User(username);
        Simulation querySimulation = new Simulation(token, queryUser, 0);

        if (!service.existsSimulation(querySimulation)) {
            ProblemDetailsJson problemDetailsJson = new ProblemDetailsJson();
            problemDetailsJson.setType("https://api.simserver.com/errors/simulation-not-found");
            problemDetailsJson.setTitle("Simulación no encontrada");
            problemDetailsJson.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problemDetailsJson.setDetail("El token " + token + " no pertenece al usuario '" + username + "' o no " +
                    "existe.");
            problemDetailsJson.setInstance("/Solicitud/ComprobarSolicitud");

            return Response.status(Response.Status.BAD_REQUEST).entity(problemDetailsJson).build();
        }

        SimulationStatus status = service.getSimulationStatus(querySimulation);

        return Response.status(Response.Status.CREATED).entity(status.name()).build();
    }

    @Override
    public Response solicitudGetSolicitudesUsuarioGet(String username) {
        User queryUser = new User(username);
        Collection<Integer> tokens = service.getUserTokens(queryUser);

        return Response.status(Response.Status.CREATED).entity(tokens).build();
    }

    @Override
    public Response solicitudSolicitarPost(String username, SimulationRequestJson simulationRequestJson) {
        if (username == null || username.isBlank()) {
            ProblemDetailsJson problemDetailsJson = new ProblemDetailsJson();
            problemDetailsJson.setType("https://api.simserver.com/errors/invalid-username");
            problemDetailsJson.setTitle("Nombre de usuario no válido");
            problemDetailsJson.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problemDetailsJson.setDetail("El nombre de usuario '" + (username == null ? "null" : username) + "' es " +
                    "nulo o " +
                    "vacío.");
            problemDetailsJson.setInstance("/Solicitud/Solicitar");

            return Response.status(Response.Status.BAD_REQUEST).entity(problemDetailsJson).build();
        }

        User user = new User(username);

        boolean creatureNamesAreValid = true;
        String invalidCreatureName = null;
        for (String creatureName : simulationRequestJson.getCreatureNames()) {
            if (!CreatureFactory.existsCreatureName(creatureName)) {
                creatureNamesAreValid = false;
                invalidCreatureName = creatureName;
            }
        }

        if (!creatureNamesAreValid) {
            ProblemDetailsJson problemDetailsJson = new ProblemDetailsJson();
            problemDetailsJson.setType("https://api.simserver.com/errors/invalid-creature-name");
            problemDetailsJson.setTitle("Nombre de criatura no válido");
            problemDetailsJson.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problemDetailsJson.setDetail("El nombre de criatura '" + invalidCreatureName + "' no es reconocido por la" +
                    " aplicación" +
                    ".");
            problemDetailsJson.setInstance("/Solicitud/Solicitar");

            return Response.status(Response.Status.BAD_REQUEST).entity(problemDetailsJson).build();
        }

        boolean creatureQuantitiesAreValid = true;
        int invalidCreatureQuantity = 0;
        String invalidCreatureQuantityCreatureName = null;
        int invalidCreatureQuantityIndex = 0;
        for (Integer creatureQuantity : simulationRequestJson.getCreatureQuantities()) {
            if (creatureQuantity < 0) {
                creatureQuantitiesAreValid = false;
                invalidCreatureQuantity = creatureQuantity;
                invalidCreatureQuantityCreatureName =
                        simulationRequestJson.getCreatureNames().get(invalidCreatureQuantityIndex);
            }
            invalidCreatureQuantityIndex++;
        }

        if (!creatureQuantitiesAreValid) {
            ProblemDetailsJson problemDetailsJson = new ProblemDetailsJson();
            problemDetailsJson.setType("https://api.simserver.com/errors/invalid-creature-quantity");
            problemDetailsJson.setTitle("Cantidad de '" + invalidCreatureQuantityCreatureName + "s' no válida.");
            problemDetailsJson.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problemDetailsJson.setDetail("La cantidad " + invalidCreatureQuantity + " de '" + invalidCreatureQuantityCreatureName +
                    "s' no válida.");
            problemDetailsJson.setInstance("/Solicitud/Solicitar");

            return Response.status(Response.Status.BAD_REQUEST).entity(problemDetailsJson).build();
        }

        SimulationRequest simulationRequest = RequestTransformer.transform(simulationRequestJson, user, new Random());

        int token = service.requestSimulation(simulationRequest).getToken();

        SimulationRequestResponseJson simulationRequestResponseJson = new SimulationRequestResponseJson();
        simulationRequestResponseJson.setDone(true);
        simulationRequestResponseJson.setRequestToken(token);
        simulationRequestResponseJson.setData(null);
        simulationRequestResponseJson.setErrorMessage(null);

        return Response.status(Response.Status.CREATED).entity(simulationRequestResponseJson).build();
    }
}