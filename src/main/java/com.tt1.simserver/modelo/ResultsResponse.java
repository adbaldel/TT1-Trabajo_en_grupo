package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;


/**
 * Representa la respuesta devuelta por el servidor al consultar los resultados de una simulación.
 */
@JsonTypeName("ResultsResponse")
public class ResultsResponse {
    private Boolean done;
    private Integer tokenSolicitud;
    private String errorMessage;
    private String data;


    /**
     * Establece si los resultados se han recuperado con éxito y devuelve la instancia actual.
     *
     * @param done cierto si la operación fue exitosa, falso en caso contrario.
     * @return la instancia actual para encadenar métodos.
     */
    public ResultsResponse done(Boolean done) {
        this.done = done;
        return this;
    }


    /**
     * Devuelve si la operación de obtener resultados fue exitosa.
     *
     * @return cierto si se obtuvieron los datos, falso si hubo error.
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     * Establece el estado de éxito al solicitar los resultados.
     *
     * @param done cierto si la petición fue exitosa.
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * Establece el token de la solicitud original asociada y devuelve la instancia actual.
     *
     * @param tokenSolicitud el número de token que identifica a la simulación.
     * @return la instancia actual para encadenar métodos.
     */
    public ResultsResponse tokenSolicitud(Integer tokenSolicitud) {
        this.tokenSolicitud = tokenSolicitud;
        return this;
    }

    /**
     * Devuelve el token de la solicitud de simulación solicitada.
     *
     * @return el token identificador numérico.
     */
    @JsonProperty("tokenSolicitud")
    public Integer getTokenSolicitud() {
        return tokenSolicitud;
    }

    /**
     * Establece el token identificador de la solicitud de la simulación.
     *
     * @param tokenSolicitud el token numérico.
     */
    @JsonProperty("tokenSolicitud")
    public void setTokenSolicitud(Integer tokenSolicitud) {
        this.tokenSolicitud = tokenSolicitud;
    }

    /**
     * Establece el mensaje de error y devuelve la instancia actual.
     *
     * @param errorMessage mensaje descriptivo en caso de fallo.
     * @return la instancia actual para encadenar métodos.
     */
    public ResultsResponse errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    /**
     * Devuelve el mensaje de error de la solicitud, si lo hubiera.
     *
     * @return el texto del error, o nulo si no ha habido errores.
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece el mensaje de error de la solicitud.
     *
     * @param errorMessage texto con el error ocurrido.
     */
    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Establece los datos con los resultados de la simulación y devuelve la instancia actual.
     *
     * @param data una cadena de texto representando los resultados.
     * @return la instancia actual para encadenar métodos.
     */
    public ResultsResponse data(String data) {
        this.data = data;
        return this;
    }

    /**
     * Devuelve los datos de la simulación completada.
     *
     * @return los datos generados por la simulación.
     */
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    /**
     * Establece los datos resultantes de la simulación.
     *
     * @param data la información de la simulación.
     */
    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Compara los atributos de esta instancia con otro objeto para verificar la igualdad.
     *
     * @param o el objeto de referencia a comparar.
     * @return cierto si son idénticos, falso si no.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultsResponse resultsResponse = (ResultsResponse) o;
        return Objects.equals(this.done, resultsResponse.done) &&
                Objects.equals(this.tokenSolicitud, resultsResponse.tokenSolicitud) &&
                Objects.equals(this.errorMessage, resultsResponse.errorMessage) &&
                Objects.equals(this.data, resultsResponse.data);
    }

    /**
     * Genera el código hash para el objeto.
     *
     * @return el valor numérico del hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, tokenSolicitud, errorMessage, data);
    }

    /**
     * Convierte el objeto a una representación en forma de cadena de texto.
     *
     * @return cadena descriptiva de los resultados.
     */
    @Override
    public String toString() {

        String sb = "class ResultsResponse {\n" +
                "    done: " + toIndentedString(done) + "\n" +
                "    tokenSolicitud: " + toIndentedString(tokenSolicitud) + "\n" +
                "    errorMessage: " + toIndentedString(errorMessage) + "\n" +
                "    data: " + toIndentedString(data) + "\n" +
                "}";
        return sb;
    }

    /**
     * Método interno para la correcta visualización del toString.
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}