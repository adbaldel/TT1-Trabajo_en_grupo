package com.tt1.jersey.test;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class ServerApp {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "https://localhost:8080/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // Create a resource config that scans for JAX-RS resources and providers in the package
        final ResourceConfig rc = new ResourceConfig().packages("com.tt1.jersey.test");

        // Create and start a new instance of the Grizzly HTTP server
        // Exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        // Start the server
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started at %s%s", BASE_URI, "message"));
        System.out.println("Hit enter to stop it...");
        System.in.read();
        server.shutdownNow();
    }
}