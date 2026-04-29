package com.tt1.simserver.model.jsonrepresentations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

import static com.tt1.simserver.logic.utils.StringManipulation.toIndentedString;

/**
 * Representa la respuesta del servidor tras procesar la creación de una nueva solicitud de simulación.
 */
@JsonTypeName("SolicitudResponse")
public class RequestResponse {
    private Boolean done;
    private Integer requestToken;
    private String errorMessage;
    private Boolean data;

    /**
     * Indica si la simulación se registró y arrancó correctamente.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si la solicitud fue exitosa, o falso si ocurrió algún error al crearla.
     *
     * @return el estado de éxito de la operación.
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     * Actualiza el estado de éxito de la petición.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El estado de éxito interno se sobreescribe con el valor proporcionado.
     *
     * @param done verdadero si fue exitosa, falso en caso contrario.
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * Obtiene el identificador numérico de la solicitud de simulación generada.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el token único asignado a la nueva simulación en el servidor.
     *
     * @return el token numérico de la simulación.
     */
    @JsonProperty("tokenSolicitud")
    public Integer getRequestToken() {
        return requestToken;
    }

    /**
     * Establece el identificador numérico de la solicitud de simulación generada.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El token interno de la petición se sobreescribe con el valor proporcionado.
     *
     * @param requestToken el valor numérico del token.
     */
    @JsonProperty("requestToken")
    public void setRequestToken(Integer requestToken) {
        this.requestToken = requestToken;
    }

    /**
     * Obtiene el mensaje descriptivo en caso de error durante la creación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el texto del error, o nulo si la solicitud fue registrada con éxito.
     *
     * @return el texto del mensaje de error, o nulo si no hubo error.
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece el mensaje de error de la operación.
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
     * Obtiene los datos adicionales adjuntos en la respuesta.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve un flag booleano con datos adicionales del servidor.
     *
     * @return un indicador booleano.
     */
    @JsonProperty("data")
    public Boolean getData() {
        return data;
    }

    /**
     * Establece los datos adicionales de la respuesta.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El flag de datos adicionales se actualiza con el valor indicado.
     *
     * @param data el valor booleano extra.
     */
    @JsonProperty("data")
    public void setData(Boolean data) {
        this.data = data;
    }

    /**
     * Compara esta respuesta con otro objeto para verificar si contienen los mismos datos de registro.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si el otro objeto es una respuesta idéntica. Devuelve falso en caso contrario.
     *
     * @param o el objeto a comparar.
     * @return verdadero si tienen la misma información, falso si difieren.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestResponse requestResponse = (RequestResponse) o;
        return Objects.equals(this.done, requestResponse.done) &&
                Objects.equals(this.requestToken, requestResponse.requestToken) &&
                Objects.equals(this.errorMessage, requestResponse.errorMessage) &&
                Objects.equals(this.data, requestResponse.data);
    }

    /**
     * Calcula el código numérico para usar esta respuesta en colecciones basadas en hash.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve un número entero generado a partir del estado de la operación y el token asignado.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, requestToken, errorMessage, data);
    }

    /**
     * Genera una representación en texto con los datos de esta respuesta.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una cadena de texto multilínea mostrando el resultado de la solicitud y el token emitido.
     *
     * @return una representación en formato de texto.
     */
    @Override
    public String toString() {
        return "class SolicitudResponse {\n" +
                "\tdone: " + toIndentedString(done) + "\n" +
                "\ttokenSolicitud: " + toIndentedString(requestToken) + "\n" +
                "\terrorMessage: " + toIndentedString(errorMessage) + "\n" +
                "\tdata: " + toIndentedString(data) + "\n" +
                "}";
    }
}