package com.tt1.simserver.api;

import com.tt1.simserver.logic.SimulationService;
import com.tt1.simserver.logic.SimulationServiceInterface;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.api.jsonobjects.ProblemDetails;
import com.tt1.simserver.api.jsonobjects.Request;
import com.tt1.simserver.api.jsonobjects.RequestResponse;
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
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response solicitudComprobarSolicitudGet(String username, Integer token) {
        User user = new User(username);

        // 1. Validar que la simulación existe y pertenece al usuario
        if (!service.existsSimulation(user, token)) {
            ProblemDetails problem = new ProblemDetails();
            problem.setType("https://api.simserver.com/errors/not-found");
            problem.setTitle("Simulación no encontrada o acceso denegado");
            problem.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
            problem.setDetail("El token " + token + " no pertenece al usuario '" + username + "' o no existe.");
            problem.setInstance("/Solicitud/ComprobarSolicitud");

            return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
        }

        // 2. Obtener el estado
        SimulationStatus status = service.getSimulationStatus(user, token);

        // Devolvemos 201 Created y el texto del estado de la simulación
        return Response.status(Response.Status.CREATED).entity(status.name()).build();
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
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response solicitudGetSolicitudesUsuarioGet(String username) {
        User user = new User(username);

        // Obtenemos los tokens históricos del usuario
        Collection<Integer> tokens = service.getUserTokens(user);

        // Devolvemos la lista de enteros como especifica el Swagger
        return Response.status(Response.Status.CREATED).entity(tokens).build();
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
     * @throws UnsupportedOperationException siempre, porque el método aún no está programado.
     */
    @Override
    public Response solicitudSolicitarPost(String username, Request request) {
        User user = new User(username);

        // 1. Iniciar la simulación en el motor asíncrono
        int token = service.requestSimulation(user, request);

        // 2. Empaquetar la respuesta JSON
        RequestResponse response = new RequestResponse();
        response.setDone(true);
        response.setRequestToken(token);
        response.setData(true); // Flag adicional positivo

        // 3. Devolver 201 Created
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
}