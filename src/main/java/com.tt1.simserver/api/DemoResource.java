package com.tt1.simserver.api;

import com.tt1.simserver.modelo.Solicitud;
import com.tt1.simserver.modelo.ItemSolicitud;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;

/**
 * Recurso REST de prueba para validar el funcionamiento del servidor.
 * Permite comprobar la integración entre la capa de presentación y el modelo de dominio.
 */
@Path("/demo")
public class DemoResource {

    /**
     * Prueba la creación de una solicitud.
     * Acceso: GET http://localhost:8080/simserver/api/demo/saludo/{nombre}
     * * @param nombre Nombre del usuario enviado en la URL.
     * @return Una respuesta con el objeto Solicitud en formato JSON.
     */
    @GET
    @Path("/saludo/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saludar(@PathParam("nombre") String nombre) {
        try {
            // Creamos datos ficticios para probar tu clase Solicitud
            Solicitud prueba = new Solicitud(
                    nombre,
                    Arrays.asList(100, 200),
                    Arrays.asList("Entidad_Alfa", "Entidad_Beta")
            );

            // Si llegamos aquí, la validación del dominio fue exitosa
            return Response.ok(prueba).build();

        } catch (Exception e) {
            // Si el dominio lanza una excepción (ej. datos inválidos), devolvemos error 400
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error en el dominio: " + e.getMessage())
                    .build();
        }
    }
}