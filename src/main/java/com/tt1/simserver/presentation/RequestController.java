package com.tt1.simserver.presentation;

import com.tt1.simserver.logic.SimulationService;
import com.tt1.simserver.logic.SimulationServiceInterface;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.presentation.jsonobjects.ProblemDetails;
import com.tt1.simserver.presentation.jsonobjects.Request;
import com.tt1.simserver.presentation.jsonobjects.RequestResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.Collection;

/**
 * Controlador que implementa las acciones y reglas de negocio conectadas al servicio de solicitudes de simulación.
 */
@Path("/Solicitud")
public class RequestController implements RequestApi {

    // Recuperamos la instancia global del servicio
    private final SimulationServiceInterface service = SimulationService.getInstance();

    /**
     * Endpoint GET: Comprueba el estado actual de una solicitud de simulación existente.
     *
     * <p>Precondición: {@code username} no es nulo y {@code token} contiene un identificador válido.
     *
     * <p>Postcondición: TODO
     *
     * @param username el nombre de la cuenta del usuario.
     * @param token    el identificador numérico de la simulación.
     * @return la respuesta HTTP con el estado de la simulación.
     */
    @Override
    public Response solicitudComprobarSolicitudGet(String username, Integer token) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Endpoint POST: Recupera todas las solicitudes de simulación asociadas a un usuario en el sistema.
     *
     * <p>Precondición: {@code username} no es nulo.
     *
     * <p>Postcondición: TODO
     *
     * @param username el nombre de cuenta del usuario a consultar.
     * @return la respuesta HTTP con la lista de identificadores.
     */
    @Override
    public Response solicitudGetSolicitudesUsuarioGet(String username) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }

    /**
     * Endpoint POST: Crea y registra una nueva solicitud de simulación para el usuario rellenando un nuevo tablero.
     *
     * <p>Precondición: {@code username} no es nulo y {@code request} contiene los datos iniciales válidos de las criaturas.
     *
     * <p>Postcondición: TODO
     *
     * @param username el identificador del usuario que hace la solicitud.
     * @param request  el objeto con la especificación y cantidades de criaturas a incluir en el tablero.
     * @return la respuesta HTTP informando del token asignado a la simulación.
     */
    @Override
    public Response solicitudSolicitarPost(String username, Request request) {
        throw new UnsupportedOperationException("Clase aún no implementada.");
    }
}