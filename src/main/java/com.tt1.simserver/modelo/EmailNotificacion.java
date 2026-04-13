package com.tt1.simserver.modelo;

/**
 * Representa el modelo de una notificación por correo electrónico.
 * Se utiliza para encapsular la información necesaria que será enviada
 * al usuario tras finalizar un proceso o para comunicaciones directas.
 */
public class EmailNotificacion {
    /** Dirección de correo electrónico del destinatario. */
    private final String emailAddress;

    /** Contenido del mensaje a enviar. */
    private final String message;

    /** Marca de tiempo en milisegundos que indica cuándo se generó la notificación. */
    private final long timestamp;

    /**
     * Construye una nueva notificación validando que la dirección tenga un formato básico.
     * * @param emailAddress Dirección de destino (debe contener el carácter '@').
     * @param message Cuerpo del mensaje.
     * @throws IllegalArgumentException Si la dirección es nula o no contiene una '@'.
     */
    public EmailNotificacion(String emailAddress, String message) {
        if (emailAddress == null || !emailAddress.contains("@")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        this.emailAddress = emailAddress;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Obtiene la dirección de correo del destinatario.
     * @return String con el email.
     */
    public String getEmailAddress() { return emailAddress; }

    /**
     * Obtiene el contenido del mensaje.
     * @return String con el cuerpo de la notificación.
     */
    public String getMessage() { return message; }

    /**
     * Obtiene el momento exacto en el que se creó el objeto.
     * @return Long con el timestamp en milisegundos.
     */
    public long getTimestamp() { return timestamp; }
}
