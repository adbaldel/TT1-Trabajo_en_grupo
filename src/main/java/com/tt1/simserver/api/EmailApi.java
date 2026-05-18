package com.tt1.simserver.api;

import com.tt1.simserver.api.jsonobjects.EmailResponseJson;
import com.tt1.simserver.api.jsonobjects.ProblemDetailsJson;
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
 * Define el contrato de la API para el envío de correos electrónicos.
 */
public interface EmailApi {

    /**
     * Procesa el envío de un correo electrónico a {@code emailAddress} con el mensaje {@code message}. Si la dirección
     * de correo electrónico no es válida devuelve un detalle de problema.
     *
     * @param emailAddress la dirección de destino.
     * @param message      el contenido del mensaje.
     * @return una respuesta HTTP indicando el éxito del envío y estado 201 CREATED; o con un detalle del problema y
     * estado 400 BAD_REQUEST.
     */
    @POST
    @Produces({"application/json", "text/json"})
    @Operation(
            summary = "Enviar mensaje por correo electrónico",
            description = "Simula el envío de un correo electrónico. Valida que el formato de la dirección de correo " +
                    "sea correcto.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Correo procesado con éxito",
                            content = @Content(schema = @Schema(implementation = EmailResponseJson.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Dirección de correo no válida o parámetros faltantes",
                            content = @Content(schema = @Schema(implementation = ProblemDetailsJson.class))
                    )
            }
    )
    Response emailPost(
            @Parameter(description = "Dirección de correo electrónico de destino", required = true, example =
                    "usuario@ejemplo.com")
            @QueryParam("emailAddress") String emailAddress,

            @Parameter(description = "Contenido del mensaje a enviar", required = true, example = "La simulación ha " +
                    "terminado.")
            @QueryParam("message") String message
    );
}