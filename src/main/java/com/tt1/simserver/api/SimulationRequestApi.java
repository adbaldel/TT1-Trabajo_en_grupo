package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.ProblemDetailsJson;
import com.tt1.simserver.api.jsonobjects.SimulationRequestJson;
import com.tt1.simserver.api.jsonobjects.SimulationRequestResponseJson;
import com.tt1.simserver.api.transformers.RequestTransformer;
import com.tt1.simserver.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Define el contrato de la API para la gestión de solicitudes de simulación.
 */
public interface SimulationRequestApi {

    /**
     * Obtiene los nombres de todas las criaturas que el servidor sabe simular.
     *
     * @return una respuesta HTTP con los nombres de las criaturas y estado 201 CREATED.
     */
    @GET
    @Path("/GetCriaturas")
    @Produces({"application/json", "text/json"})
    @Operation(
            summary = "Obtener criaturas disponibles",
            description = "Devuelve una lista con los nombres de todas las criaturas registradas en el sistema que " +
                    "pueden ser simuladas.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Lista de criaturas obtenida correctamente",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class,
                                    example = "Lobo")))
                    )
            }
    )
    Response solicitudGetCriaturasGet();

    /**
     * Obtiene el estado de la simulación del usuario {@code username} y token {@code token}. Si no existen una
     * simulación para el usuario y token dados devuelve un detalle de problema.
     *
     * @param username el nombre del usuario.
     * @param token    el token de la solicitud.
     * @return una respuesta HTTP con el estado de la simulación y estado 201 CREATED; o con un detalle del problema y
     * estado 400 BAD_REQUEST.
     */
    @GET
    @Path("/ComprobarSolicitud")
    @Produces({"application/json", "text/json"})
    @Operation(
            summary = "Comprobar estado de la simulación",
            description = "Consulta el estado actual (ej. PENDING, RUNNING, COMPLETED) de una simulación específica " +
                    "usando el nombre de usuario y el token asociado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Estado de la simulación devuelto con éxito",
                            content = @Content(schema = @Schema(implementation = String.class, example = "COMPLETED"))),
                    @ApiResponse(responseCode = "400", description = "El token no pertenece al usuario o la " +
                            "simulación no existe", content = @Content(schema = @Schema(implementation =
                            ProblemDetailsJson.class)))
            }
    )
    Response solicitudComprobarSolicitudGet(
            @Parameter(description = "Nombre del usuario propietario de la simulación", required = true)
            @QueryParam("nombreUsuario") String username,

            @Parameter(description = "Token identificador de la simulación", required = true)
            @QueryParam("tok") Integer token
    );

    /**
     * Obtiene los tokens de todas las simulaciones del usuario {@code username}. Si no existe el usuario o no tiene
     * simulaciones devuelve una lista vacía.
     *
     * @param username el nombre del usuario.
     * @return una respuesta HTTP con los tokens de la simulación y estado 201 CREATED.
     */
    @GET
    @Path("/GetSolicitudesUsuario")
    @Produces({"application/json", "text/json"})
    @Operation(
            summary = "Obtener tokens del usuario",
            description = "Devuelve una lista de todos los tokens de simulación que pertenecen al usuario " +
                    "proporcionado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Lista de tokens obtenida", content =
                    @Content(array = @ArraySchema(schema = @Schema(implementation = Integer.class))))
            }
    )
    Response solicitudGetSolicitudesUsuarioGet(
            @Parameter(description = "Nombre del usuario", required = true)
            @QueryParam("nombreUsuario") String username
    );

    /**
     * Procesa una solicitud de simulación {@code request} de un usuario con nombre {@code username}. Si el usuario no
     * cumple con las precondiciones de {@link User#User(String)} o la solicitud no cumple con las precondiciones de
     * {@link RequestTransformer#transform} devuelve un detalle de problema.
     *
     * @param username              el nombre del usuario solicitante
     * @param simulationRequestJSON los datos de la solicitud
     * @return una respuesta HTTP con el identificador asignado a la nueva simulación
     */
    @POST
    @Path("/Solicitar")
    @Consumes({"application/json", "text/json"})
    @Produces({"application/json", "text/json"})
    @Operation(
            summary = "Solicitar nueva simulación",
            description = "Envía una petición para iniciar una nueva simulación. Valida que el usuario no sea nulo, " +
                    "que las criaturas existan y que las cantidades no sean negativas.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Simulación solicitada y token generado",
                            content = @Content(schema = @Schema(implementation = SimulationRequestResponseJson.class))),
                    @ApiResponse(responseCode = "400", description = "Nombre de usuario inválido, criatura " +
                            "desconocida o cantidad negativa", content = @Content(schema = @Schema(implementation =
                            ProblemDetailsJson.class)))
            }
    )
    Response solicitudSolicitarPost(
            @Parameter(description = "Nombre del usuario que solicita la simulación", required = true)
            @QueryParam("nombreUsuario") String username,

            @RequestBody(description = "Detalles de las criaturas a simular", required = true)
            SimulationRequestJson simulationRequestJSON
    );
}