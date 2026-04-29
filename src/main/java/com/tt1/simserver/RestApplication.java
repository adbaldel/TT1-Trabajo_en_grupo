package com.tt1.simserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Clase principal encargada de configurar e iniciar el servidor HTTP para procesar peticiones web.
 */
public class RestApplication {
    /**
     * Define la ruta base y el puerto local donde el servidor aceptará conexiones.
     */
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Punto de entrada principal de la aplicación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Arranca el servidor HTTP, muestra mensajes de registro por consola y bloquea el hilo principal a la espera de un salto de línea (tecla ENTER) del usuario para detener y cerrar el proceso.
     *
     * @param args argumentos de la línea de comandos (ignorados en esta aplicación).
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
     * Configura e instancia el servidor HTTP vinculando los controladores al enrutador.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una instancia activa del servidor Grizzly escuchando en la URI base, con todos los endpoints del paquete de presentación registrados correctamente.
     *
     * @return la instancia del servidor HTTP en ejecución.
     */
    private static HttpServer startServer() {
        // Le decimos a Jersey que busque las clases @Path (controladores) en este paquete
        final ResourceConfig rc = new ResourceConfig().packages("com.tt1.simserver.presentacion");

        // Crea e inicia el servidor HTTP Grizzly
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}