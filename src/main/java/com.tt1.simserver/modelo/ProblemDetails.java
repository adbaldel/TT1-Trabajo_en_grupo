package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;

/**
 * Representa los detalles de un problema o error en una respuesta HTTP,
 * comúnmente usado para estandarizar errores (basado en RFC 7807).
 */
@JsonTypeName("ProblemDetails")
public class ProblemDetails extends HashMap<String, Object> {
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;


    /**
     * Devuelve el tipo del problema.
     *
     * @return una cadena con la referencia al tipo del problema.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Establece el tipo del problema.
     *
     * @param type una cadena con la referencia al tipo del problema.
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Devuelve el título del problema.
     *
     * @return el título descriptivo corto.
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título del problema.
     *
     * @param title el título descriptivo corto.
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Devuelve el código de estado HTTP.
     *
     * @return el número del código de estado.
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     * Establece el código de estado HTTP asociado.
     *
     * @param status el número del código de estado.
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Devuelve los detalles del problema.
     *
     * @return una cadena con la explicación detallada del error.
     */
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    /**
     * Establece los detalles del problema.
     *
     * @param detail una cadena con la explicación detallada.
     */
    @JsonProperty("detail")
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Devuelve la instancia donde ocurrió el problema.
     *
     * @return el URI de la instancia del problema.
     */
    @JsonProperty("instance")
    public String getInstance() {
        return instance;
    }

    /**
     * Establece la instancia donde ocurrió el problema.
     *
     * @param instance el URI identificador de la instancia.
     */
    @JsonProperty("instance")
    public void setInstance(String instance) {
        this.instance = instance;
    }

    /**
     * Compara este objeto con otro para comprobar si son iguales basándose en sus atributos y en los de su superclase HashMap.
     *
     * @param o el objeto a comparar.
     * @return cierto si los objetos son idénticos, falso de lo contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProblemDetails problemDetails = (ProblemDetails) o;
        return Objects.equals(this.type, problemDetails.type) &&
                Objects.equals(this.title, problemDetails.title) &&
                Objects.equals(this.status, problemDetails.status) &&
                Objects.equals(this.detail, problemDetails.detail) &&
                Objects.equals(this.instance, problemDetails.instance) &&
                super.equals(o);
    }

    /**
     * Calcula y devuelve el código hash de este objeto.
     *
     * @return el valor hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, title, status, detail, instance, super.hashCode());
    }

    /**
     * Genera una representación en forma de cadena del detalle del problema.
     *
     * @return una cadena que representa este objeto.
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