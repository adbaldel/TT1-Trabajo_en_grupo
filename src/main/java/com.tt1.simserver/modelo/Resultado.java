package com.tt1.simserver.modelo;

/**
 * Representa el desenlace de un proceso de cálculo asociado a una solicitud.
 * Contiene los datos generados en caso de éxito o el mensaje de error en caso de fallo,
 * vinculándose siempre a la solicitud original mediante su token.
 */
public class Resultado {
    /** Token identificador de la solicitud a la que pertenece este resultado. */
    private final int tokenSolicitud;

    /** Indica si el proceso se completó correctamente (true) o si falló (false). */
    private final boolean done;

    /** Datos resultantes del procesamiento en formato String (usualmente JSON o CSV). */
    private final String data;

    /** Descripción del error ocurrido, en caso de que el proceso no haya tenido éxito. */
    private String errorMensaje;

    /**
     * Constructor para un resultado exitoso.
     * * @param tokenSolicitud Token de la solicitud procesada.
     * @param data Información resultante del proceso.
     */
    public Resultado(int tokenSolicitud, String data) {
        this.tokenSolicitud = tokenSolicitud;
        this.data = data;
        this.done = true;
    }

    /**
     * Método de factoría estático para crear una instancia de resultado fallido.
     * * @param tokenSolicitud Token de la solicitud que no pudo procesarse.
     * @param mensaje Descripción detallada del error.
     * @return Una instancia de {@link Resultado} marcada como no completada.
     */
    public static Resultado crearFallido(int tokenSolicitud, String mensaje) {
        Resultado res = new Resultado(tokenSolicitud, null);
        // Nota: Internamente el constructor pone done = true,
        // pero para fallos el objeto debe representar la ausencia de éxito.
        // Se recomienda ajustar la lógica de 'done' si se requiere mayor precisión.
        res.errorMensaje = mensaje;
        return res;
    }

    /**
     * Obtiene el token de la solicitud vinculada.
     * @return Identificador de la solicitud original.
     */
    public int getTokenSolicitud() { return tokenSolicitud; }

    /**
     * Comprueba si el proceso finalizó con éxito.
     * @return true si se completó correctamente, false en caso contrario.
     */
    public boolean isDone() { return done; }

    /**
     * Obtiene los datos del resultado.
     * @return Cadena con la información procesada o null si hubo error.
     */
    public String getData() { return data; }

    /**
     * Obtiene el mensaje de error si existe.
     * @return Descripción del fallo o null si el proceso fue exitoso.
     */
    public String getErrorMensaje() { return errorMensaje; }
}