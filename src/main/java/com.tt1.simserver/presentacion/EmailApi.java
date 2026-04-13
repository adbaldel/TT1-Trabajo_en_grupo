package com.tt1.simserver.presentacion;

import com.tt1.simserver.modelo.EmailResponse;
import com.tt1.simserver.modelo.ProblemDetails;
import io.swagger.annotations.*;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
* Represents a collection of functions to interact with the API endpoints.
*/
@Path("/Email")
@Api(description = "the Email API")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2026-04-13T15:30:42.005536+02:00[Europe/Madrid]", comments = "Generator version: 7.4.0")
public interface EmailApi {

    /**
     * 
     *
     * @param emailAddress 
     * @param message 
     * @return Created
     * @return Bad Request
     */
    @POST
    @Produces({ "text/plain", "application/json", "text/json" })
    @ApiOperation(value = "", notes = "", tags={ "Email" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = EmailResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = ProblemDetails.class) })
    Response emailPost(@QueryParam("emailAddress")   String emailAddress,@QueryParam("message")   String message);

}
