package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa la respuesta devuelta por el servidor tras intentar enviar un correo electrónico.
 */
@JsonTypeName("EmailResponse")
public class EmailResponse {
    private Boolean done;
    private String errorMessage;


    /**
     * Establece si la operación se realizó con éxito y devuelve la instancia actual.
     *
     * @param done cierto si la operación fue exitosa, falso en caso contrario.
     * @return la instancia actual de EmailResponse para permitir el encadenamiento de métodos.
     */
    public EmailResponse done(Boolean done) {
        this.done = done;
        return this;
    }


    /**
     * Devuelve si la operación de envío se completó correctamente.
     *
     * @return cierto si se envió el correo, falso en caso de error.
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     * Establece el estado de éxito del envío del correo.
     *
     * @param done cierto si fue exitoso, falso en caso contrario.
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * Establece el mensaje de error y devuelve la instancia actual.
     *
     * @param errorMessage el mensaje que describe el error, si hubo alguno.
     * @return la instancia actual de EmailResponse para encadenar métodos.
     */
    public EmailResponse errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    /**
     * Devuelve el mensaje de error de la operación, si la hubo.
     *
     * @return el texto del mensaje de error, o nulo si no hubo error.
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece el mensaje descriptivo del error en caso de que falle el envío.
     *
     * @param errorMessage el texto del mensaje de error.
     */
    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Compara este objeto con otro para comprobar si son iguales basándose en sus atributos.
     *
     * @param o el objeto de referencia con el cual comparar.
     * @return cierto si los objetos son iguales, falso en caso contrario.
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
     * Calcula y devuelve el código hash de este objeto.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, errorMessage);
    }

    /**
     * Genera una representación en forma de cadena (string) de los datos de este objeto.
     *
     * @return una cadena que representa este objeto.
     */
    @Override
    public String toString() {

        String sb = "class EmailResponse {\n" +
                "    done: " + toIndentedString(done) + "\n" +
                "    errorMessage: " + toIndentedString(errorMessage) + "\n" +
                "}";
        return sb;
    }

    /**
     * Convierte el objeto dado a una cadena, indentando cada línea con 4 espacios
     * (excepto la primera línea). Método auxiliar para toString.
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}