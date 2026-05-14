package com.tt1.simserver;

import com.sun.tools.javac.Main;
import com.tt1.simserver.config.AppBinder;
import com.tt1.simserver.config.ConfigManager;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Punto de entrada a la aplicación.
 */
public class RestApplication {

    /**
     * Inicia la aplicación lanzando un servidor grizzly embebido en la dirección especificada en application
     * .properties; que responde a las peticiones HTTP definidas en los endpoints de com.tt1.simserver.api; que
     * transforma clases anotadas con jackson a JSON; y que utiliza el servicio de simulación configurado por
     * {@link AppBinder}.
     *
     * @param args argumentos de la aplicación.
     */
    public static void main(String[] args) {
        try {
            String baseUri = ConfigManager.getInstance().getString("server.base_uri", "http://localhost:8081/");
            AppBinder appBinder = new AppBinder();

            final HttpServer server = startServer(baseUri, appBinder);

            server.getServerConfiguration().addHttpHandler(
                    new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger-ui/"), "/swagger"
            );

            System.out.println("Servidor Grizzly de Simulaciones iniciado.");
            System.out.println("Escuchando en: " + baseUri);
            // NO DOCKER
//            System.out.println("Presiona ENTER para detener el servidor...");

//            System.in.read();
//            appBinder.getSimulationService().shutdown();
//            server.shutdown();
            // FIN NO DOCKER

            // DOCKER
            // 1. Registramos un "Shutdown Hook" para apagar el servidor limpiamente
            // cuando Docker mande la señal de parada (docker-compose down / ctrl+c)
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Apagando el servidor Grizzly...");
                appBinder.getSimulationService().shutdown();
                server.shutdownNow();
            }));

            // 2. Bloqueamos el hilo principal para siempre para que el servidor no se cierre
            Thread.currentThread().join();
            // FIN DOCKER
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Configura la conexión entre Grizzly y Jersey para responder a las peticiones HTTP definidas en los endpoints de
     * com.tt1.simserver.api; transformar clases anotadas con jackson a JSON; y utilizar el servicio de simulación
     * configurado por {@link AppBinder}.
     *
     * @param baseUri la URI del servidor.
     * @return el servidor Grizzly configurado sin arrancar.
     */
    private static HttpServer startServer(String baseUri, AppBinder appBinder) {
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.tt1.simserver.api") // Scan for endpoints
                .register(JacksonFeature.class)    // Register Jackson for JSON
                .register(appBinder);        // Register SimulationService

        addOpenAPISpecification(rc);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), rc);
    }

    private static void addOpenAPISpecification(ResourceConfig rc) {
        try {
            // This tells Swagger to look for the configuration file
            new JaxrsOpenApiContextBuilder()
                    .configLocation("openapi-configuration.yaml") // Path relative to classpath
                    .buildContext(true);
        } catch (OpenApiConfigurationException e) {
            e.printStackTrace();
        }

        // Register the OpenAPI endpoint
        rc.register(OpenApiResource.class);

    }
}