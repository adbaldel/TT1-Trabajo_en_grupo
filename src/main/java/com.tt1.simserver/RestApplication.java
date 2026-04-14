package com.tt1.simserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 *
 */
public class RestApplication {
    // Define la ruta base donde escuchará el servidor
    /**
     *
     */
    public static final String BASE_URI = "http://localhost:8080/";


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            final HttpServer server = startServer();
            System.out.println("Servidor Grizzly de Simulaciones iniciado.");
            System.out.println("Escuchando en: " + BASE_URI);
            System.out.println("Presiona ENTER para detener el servidor...");

            // Mantiene la aplicación viva hasta que presiones Enter
            System.in.read();
            server.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @return
     */
    private static HttpServer startServer() {
        // Le decimos a Jersey que busque las clases @Path (controladores) en este paquete
        final ResourceConfig rc = new ResourceConfig().packages("com.tt1.simserver.presentacion");

        // Crea e inicia el servidor HTTP Grizzly
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}