package com.tt1.simserver.api.jsonobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;

/**
 * Representa un objeto JSON para retornar los resultados finales de una simulación.
 */
@JsonTypeName("ResultsResponse")
public class SimulationResultResponseJson {
    private Boolean done;
    private Integer requestToken;
    private String errorMessage;
    private String data;

    /**
     * Construye una respuesta de resultados vacía.
     */
    public SimulationResultResponseJson() {
        done = null;
        requestToken = null;
        errorMessage = null;
        data = null;
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
     * Obtiene el token de la solicitud de simulación.
     *
     * @return el identificador de la simulación.
     */
    @JsonProperty("tokenSolicitud")
    public Integer getRequestToken() {
        return requestToken;
    }

    /**
     * Cambia el token de la solicitud por {@code requestToken}.
     *
     * @param requestToken el nuevo token de solicitud.
     */
    @JsonProperty("tokenSolicitud")
    public void setRequestToken(Integer requestToken) {
        this.requestToken = requestToken;
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
     * Obtiene los datos procesados en la simulación.
     *
     * @return los datos en formato CSV de la simulación.
     */
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    /**
     * Cambia los datos de la respuesta por {@code data}.
     *
     * @param data los nuevos datos.
     */
    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Comprueba si esta respuesta de resultados es igual al objeto {@code o}. Devuelve cierto si el objeto es una
     * respuesta de resultados con el estado, token de solicitud, mensaje de error y datos iguales.
     *
     * @param o el objeto a comparar.
     * @return cierto si esta respuesta de resultados es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimulationResultResponseJson resultsResponse = (SimulationResultResponseJson) o;
        return Objects.equals(this.done, resultsResponse.done) &&
                Objects.equals(this.requestToken, resultsResponse.requestToken) &&
                Objects.equals(this.errorMessage, resultsResponse.errorMessage) &&
                Objects.equals(this.data, resultsResponse.data);
    }

    /**
     * Obtiene el código hash de la respuesta de resultados.
     *
     * @return el código hash de la respuesta de resultados.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, requestToken, errorMessage, data);
    }

    /**
     * Obtiene una representación en cadena de texto del objeto.
     *
     * @return la cadena de texto con los atributos.
     */
    @Override
    public String toString() {
        return "class ResultsResponse {\n" +
                "\tdone: " + toIndentedString(done) + "\n" +
                "\ttokenSolicitud: " + toIndentedString(requestToken) + "\n" +
                "\terrorMessage: " + toIndentedString(errorMessage) + "\n" +
                "\tdata: " + toIndentedString(data) + "\n" +
                "}";
    }
}