package com.tt1.simserver.presentacion;

import com.tt1.simserver.modelo.ProblemDetails;
import com.tt1.simserver.modelo.Solicitud;
import com.tt1.simserver.modelo.SolicitudResponse;
import io.swagger.annotations.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
* Represents a collection of functions to interact with the API endpoints.
*/
@Path("/Solicitud")
@Api(description = "the Solicitud API")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-04-13T15:30:42.005536+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
public interface SolicitudApi {

    /**
     * 
     *
     * @param nombreUsuario 
     * @param tok 
     * @return Created
     * @return Bad Request
     */
    @GET
    @Path("/ComprobarSolicitud")
    @Produces({ "text/plain", "application/json", "text/json" })
    @ApiOperation(value = "", notes = "", tags={ "Solicitud" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = Integer.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request", response = ProblemDetails.class, responseContainer = "List") })
    Response solicitudComprobarSolicitudGet(@QueryParam("nombreUsuario")   String nombreUsuario,@QueryParam("tok")   Integer tok);


    /**
     * 
     *
     * @param nombreUsuario 
     * @return Created
     * @return Bad Request
     */
    @GET
    @Path("/GetSolicitudesUsuario")
    @Produces({ "text/plain", "application/json", "text/json" })
    @ApiOperation(value = "", notes = "", tags={ "Solicitud" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = Integer.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request", response = ProblemDetails.class, responseContainer = "List") })
    Response solicitudGetSolicitudesUsuarioGet(@QueryParam("nombreUsuario")   String nombreUsuario);


    /**
     * 
     *
     * @param nombreUsuario 
     * @param solicitud 
     * @return Created
     * @return Bad Request
     */
    @POST
    @Path("/Solicitar")
    @Consumes({ "application/json", "text/json", "application/*+json" })
    @Produces({ "text/plain", "application/json", "text/json" })
    @ApiOperation(value = "", notes = "", tags={ "Solicitud" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = SolicitudResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ProblemDetails.class) })
    Response solicitudSolicitarPost(@QueryParam("nombreUsuario")   String nombreUsuario,@Valid Solicitud solicitud);

}
