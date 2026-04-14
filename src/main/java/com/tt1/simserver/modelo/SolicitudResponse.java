package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tt1.simserver.utils.StringManipulation;

import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;


/**
 * Representa la respuesta del servidor tras procesar la creación de una nueva solicitud de simulación.
 */
@JsonTypeName("SolicitudResponse")
public class SolicitudResponse {
    private Boolean done;
    private Integer tokenSolicitud;
    private String errorMessage;
    private Boolean data;


    /**
     * Indica si se pudo llevar a cabo la operación con éxito.
     *
     * @return cierto si fue exitosa, de lo contrario falso.
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     * Asigna el estado de la operación (éxito o fracaso).
     *
     * @param done estado de la operación.
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }


    /**
     * Devuelve el token que identifica unívocamente a la solicitud recién creada.
     *
     * @return el token de la solicitud.
     */
    @JsonProperty("tokenSolicitud")
    public Integer getTokenSolicitud() {
        return tokenSolicitud;
    }

    /**
     * Asigna el identificador token de la solicitud generada.
     *
     * @param tokenSolicitud el valor numérico del token.
     */
    @JsonProperty("tokenSolicitud")
    public void setTokenSolicitud(Integer tokenSolicitud) {
        this.tokenSolicitud = tokenSolicitud;
    }

    /**
     * Devuelve el mensaje de error en caso de fallo, o nulo si no hubo ninguno.
     *
     * @return texto del error.
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Asigna un texto descriptivo del error al generar la solicitud.
     *
     * @param errorMessage mensaje de error a guardar.
     */
    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Devuelve un flag adicional adjunto en la respuesta.
     *
     * @return flag adicional.
     */
    @JsonProperty("data")
    public Boolean getData() {
        return data;
    }

    /**
     * Asigna un valor booleano a los datos extra devueltos por el servidor.
     *
     * @param data valor booleano adicional.
     */
    @JsonProperty("data")
    public void setData(Boolean data) {
        this.data = data;
    }

    /**
     * Compara los campos de esta respuesta frente a otro objeto verificando su igualdad.
     *
     * @param o el objeto a contrastar.
     * @return cierto si tienen la misma información, falso de lo contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SolicitudResponse solicitudResponse = (SolicitudResponse) o;
        return Objects.equals(this.done, solicitudResponse.done) &&
                Objects.equals(this.tokenSolicitud, solicitudResponse.tokenSolicitud) &&
                Objects.equals(this.errorMessage, solicitudResponse.errorMessage) &&
                Objects.equals(this.data, solicitudResponse.data);
    }

    /**
     * Devuelve el código hash para el objeto actual.
     *
     * @return valor hash numérico.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, tokenSolicitud, errorMessage, data);
    }

    /**
     * Obtiene una representación textual, multilínea y legible, de los datos en este objeto.
     *
     * @return los datos en formato cadena de texto.
     */
    @Override
    public String toString() {
        return "class SolicitudResponse {\n" +
                "\tdone: " + toIndentedString(done) + "\n" +
                "\ttokenSolicitud: " + toIndentedString(tokenSolicitud) + "\n" +
                "\terrorMessage: " + toIndentedString(errorMessage) + "\n" +
                "\tdata: " + toIndentedString(data) + "\n" +
                "}";
    }
}