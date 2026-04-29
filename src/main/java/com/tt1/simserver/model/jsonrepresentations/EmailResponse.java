package com.tt1.simserver.model.jsonrepresentations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

import static com.tt1.simserver.logic.utils.StringManipulation.toIndentedString;

/**
 * Representa la respuesta devuelta por el servidor tras intentar enviar un correo electrónico.
 */
@JsonTypeName("EmailResponse")
public class EmailResponse {
    private Boolean done;
    private String errorMessage;

    /**
     * Indica si el envío del correo electrónico finalizó con éxito.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si el correo se envió, o falso si ocurrió algún fallo.
     *
     * @return el estado de éxito de la operación.
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     * Actualiza el estado de éxito del envío del correo.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El estado de éxito interno se sobreescribe con el valor proporcionado.
     *
     * @param done verdadero si fue exitoso, falso en caso contrario.
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * Obtiene el mensaje descriptivo en caso de error durante el envío.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el texto del error, o nulo si el envío fue exitoso.
     *
     * @return el texto del mensaje de error, o nulo si no hubo error.
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece el mensaje de error de la operación de envío.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El mensaje de error interno se actualiza con el texto indicado.
     *
     * @param errorMessage el texto del mensaje de error.
     */
    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Compara esta respuesta con otro objeto para verificar si contienen los mismos datos.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si el otro objeto es una respuesta con idéntico estado de éxito y mensaje de error. Devuelve falso en caso contrario.
     *
     * @param o el objeto a comparar.
     * @return verdadero si los objetos son iguales, falso si no.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailResponse emailResponse = (EmailResponse) o;
        return Objects.equals(this.done, emailResponse.done) &&
                Objects.equals(this.errorMessage, emailResponse.errorMessage);
    }

    /**
     * Calcula el código numérico para usar esta respuesta en colecciones basadas en hash.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve un número entero generado a partir del estado de éxito y el mensaje de error.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, errorMessage);
    }

    /**
     * Genera una representación en texto con los datos de esta respuesta.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una cadena de texto multilínea mostrando el estado de éxito y el mensaje de error actual.
     *
     * @return una cadena que representa este objeto.
     */
    @Override
    public String toString() {
        return "class EmailResponse {\n" +
                "\tdone: " + toIndentedString(done) + "\n" +
                "\terrorMessage: " + toIndentedString(errorMessage) + "\n" +
                "}";
    }
}