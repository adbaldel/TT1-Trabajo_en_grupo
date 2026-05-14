package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.ProblemDetailsJson;
import com.tt1.simserver.api.jsonobjects.SimulationResultResponseJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * Define el contrato de la API para la obtención de resultados de simulaciones.
 */
public interface SimulationResultApi {

    /**
     * Obtiene los resultados de la simulación del usuario {@code username} y token {@code token}. Si no existen
     * resultados de simulación para el usuario y token dados devuelve un detalle de problema.
     *
     * @param username el nombre del usuario.
     * @param token    el token de la solicitud.
     * @return una respuesta HTTP con los resultados de la simulación y estado 201 CREATED; o con un detalle del
     * problema y estado 400 BAD_REQUEST.
     */
    @POST
    @Produces({"application/json", "text/json"})
    @Operation(
            summary = "Obtener resultados",
            description = "Recupera los datos en formato CSV de una simulación. Esta operación solo tendrá éxito si " +
                    "la simulación ha finalizado completamente (Estado COMPLETED).",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resultados obtenidos con éxito",
                            content = @Content(schema = @Schema(implementation = SimulationResultResponseJson.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "La simulación no existe, no pertenece al usuario, o aún no ha terminado",
                            content = @Content(schema = @Schema(implementation = ProblemDetailsJson.class))
                    )
            }
    )
    Response resultadosPost(
            @Parameter(description = "Nombre del usuario propietario", required = true)
            @QueryParam("nombreUsuario") String username,

            @Parameter(description = "Token de la simulación finalizada", required = true)
            @QueryParam("tok") Integer token
    );
}