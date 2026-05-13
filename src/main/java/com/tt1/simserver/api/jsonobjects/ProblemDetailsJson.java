package com.tt1.simserver.api.jsonobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;

/**
 * Representa un objeto JSON para reportar detalles estructurados de errores en la API.
 */
@JsonTypeName("ProblemDetails")
public class ProblemDetailsJson extends HashMap<String, Object> {
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;

    /**
     * Construye un detalle de problema vacío.
     */
    public ProblemDetailsJson() {
        type = null;
        title = null;
        status = null;
        detail = null;
        instance = null;
    }

    /**
     * Obtiene el tipo del problema.
     *
     * @return el tipo del problema.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Cambia el tipo del problema por {@code type}.
     *
     * @param type el nuevo tipo.
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtiene el título del problema.
     *
     * @return el título del problema.
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Cambia el título del problema por {@code title}.
     *
     * @param title el nuevo título.
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene el código de estado HTTP.
     *
     * @return el estado HTTP.
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     * Cambia el código de estado HTTP por {@code status}.
     *
     * @param status el nuevo código de estado.
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Obtiene el detalle explicativo del error.
     *
     * @return el detalle del error.
     */
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    /**
     * Cambia el detalle explicativo del error por {@code detail}.
     *
     * @param detail el nuevo detalle.
     */
    @JsonProperty("detail")
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Obtiene la instancia que originó el error.
     *
     * @return la instancia del error.
     */
    @JsonProperty("instance")
    public String getInstance() {
        return instance;
    }

    /**
     * Cambia la instancia que originó el error por {@code instance}.
     *
     * @param instance la nueva instancia.
     */
    @JsonProperty("instance")
    public void setInstance(String instance) {
        this.instance = instance;
    }

    /**
     * Comprueba si este detalle de problema es igual al objeto {@code o}. Devuelve cierto si el objeto es un detalle de
     * problema con el tipo, título, estado, detalle e instancia iguales, además de coincidir en las propiedades de su
     * clase padre.
     *
     * @param o el objeto a comparar.
     * @return cierto si este detalle de problema es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProblemDetailsJson problemDetails = (ProblemDetailsJson) o;
        return Objects.equals(this.type, problemDetails.type) &&
                Objects.equals(this.title, problemDetails.title) &&
                Objects.equals(this.status, problemDetails.status) &&
                Objects.equals(this.detail, problemDetails.detail) &&
                Objects.equals(this.instance, problemDetails.instance) &&
                super.equals(o);
    }

    /**
     * Obtiene el código hash del detalle de problema.
     *
     * @return el código hash del detalle de problema.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, title, status, detail, instance, super.hashCode());
    }

    /**
     * Obtiene una representación en cadena de texto del objeto.
     *
     * @return la cadena de texto con los atributos.
     */
    @Override
    public String toString() {
        return "class ProblemDetails {\n" +
                "    " + toIndentedString(super.toString()) + "\n" +
                "    type: " + toIndentedString(type) + "\n" +
                "    title: " + toIndentedString(title) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
                "    detail: " + toIndentedString(detail) + "\n" +
                "    instance: " + toIndentedString(instance) + "\n" +
                "}";
    }
}