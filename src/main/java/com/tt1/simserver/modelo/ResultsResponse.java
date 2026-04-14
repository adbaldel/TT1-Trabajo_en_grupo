package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;


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
        return "class ResultsResponse {\n" +
                "\tdone: " + toIndentedString(done) + "\n" +
                "\ttokenSolicitud: " + toIndentedString(tokenSolicitud) + "\n" +
                "\terrorMessage: " + toIndentedString(errorMessage) + "\n" +
                "\tdata: " + toIndentedString(data) + "\n" +
                "}";
    }
}