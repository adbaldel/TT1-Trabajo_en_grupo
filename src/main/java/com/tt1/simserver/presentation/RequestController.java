package com.tt1.simserver.presentation;

import com.tt1.simserver.logic.SimulationService;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.model.jsonrepresentations.ProblemDetails;
import com.tt1.simserver.model.jsonrepresentations.Request;
import com.tt1.simserver.model.jsonrepresentations.RequestResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al servicio de solicitudes definidos por la interfaz SolicitudApi.
 */
@Path("/Solicitud")
public class RequestController implements RequestApi {

    /**
     * {@inheritDoc}
     */
    @Override
    public Response solicitudComprobarSolicitudGet(String username, Integer token) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response solicitudGetSolicitudesUsuarioGet(String username) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response solicitudSolicitarPost(String username, Request request) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}
