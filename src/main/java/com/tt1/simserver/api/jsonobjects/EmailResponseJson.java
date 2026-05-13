package com.tt1.simserver.api.jsonobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;

/**
 * Representa un objeto JSON para encapsular la respuesta de una solicitud de correo electrónico.
 */
@JsonTypeName("EmailResponse")
public class EmailResponseJson {
    private Boolean done;
    private String errorMessage;

    /**
     * Construye una respuesta de email vacía.
     */
    public EmailResponseJson() {
    }

    /**
     * Obtiene el estado de finalización.
     *
     * @return el estado de finalización.
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     * Cambia el estado de finalización por {@code done}.
     *
     * @param done el nuevo estado de finalización.
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * Obtiene el mensaje de error.
     *
     * @return el mensaje de error.
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Cambia el mensaje de error por {@code errorMessage}.
     *
     * @param errorMessage el nuevo mensaje de error.
     */
    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Comprueba si esta respuesta de email es igual al objeto {@code o}. Devuelve cierto si el objeto es una respuesta
     * de email con el estado y mensaje de error.
     *
     * @param o el objeto a comparar.
     * @return cierto si esta respuesta de email es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailResponseJson emailResponseJSON = (EmailResponseJson) o;
        return Objects.equals(this.done, emailResponseJSON.done) &&
                Objects.equals(this.errorMessage, emailResponseJSON.errorMessage);
    }

    /**
     * Obtiene el código hash de la respuesta de email.
     *
     * @return el código hash de la respuesta de email.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, errorMessage);
    }

    /**
     * Obtiene una representación en cadena de texto del objeto.
     *
     * @return la cadena de texto con los atributos.
     */
    @Override
    public String toString() {
        return "class EmailResponse {\n" +
                "\tdone: " + toIndentedString(done) + "\n" +
                "\terrorMessage: " + toIndentedString(errorMessage) + "\n" +
                "}";
    }
}