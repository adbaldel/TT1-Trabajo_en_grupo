package com.tt1.simserver.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Configuración principal de JAX-RS.
 * Define la ruta base de todos los servicios del servidor.
 * Al estar vacía, el servidor busca automáticamente clases con la anotación @Path.
 */
@ApplicationPath("/api")
public class RestConfig extends Application {
    // No se requiere código adicional aquí.
}