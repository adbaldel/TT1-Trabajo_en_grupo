package com.tt1.simserver.presentation.jsonobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.Objects;

import static com.tt1.simserver.presentation.jsonobjects.utils.StringManipulation.toIndentedString;

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
     * Obtiene la referencia que identifica el tipo de problema.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto con el identificador del tipo de error.
     *
     * @return el tipo del problema.
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Establece la referencia que identifica el tipo de problema.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El tipo interno se sobreescribe con el valor proporcionado.
     *
     * @param type una cadena con la referencia al tipo del problema.
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtiene el título descriptivo corto del error.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto con el título del problema.
     *
     * @return el título del problema.
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título descriptivo corto del error.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El título interno se sobreescribe con el valor proporcionado.
     *
     * @param title el título descriptivo corto.
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene el código de estado HTTP asociado al error.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el número entero que representa el código HTTP.
     *
     * @return el número del código de estado.
     */
    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    /**
     * Establece el código de estado HTTP asociado al error.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El código de estado interno se sobreescribe con el valor proporcionado.
     *
     * @param status el número del código de estado.
     */
    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Obtiene la explicación detallada del problema.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto con los detalles del error.
     *
     * @return los detalles del problema.
     */
    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    /**
     * Establece la explicación detallada del problema.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El detalle interno se sobreescribe con el valor proporcionado.
     *
     * @param detail una cadena con la explicación detallada.
     */
    @JsonProperty("detail")
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Obtiene el identificador exacto de la petición donde ocurrió el problema.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la cadena de texto con el origen de la instancia del problema.
     *
     * @return el URI de la instancia del problema.
     */
    @JsonProperty("instance")
    public String getInstance() {
        return instance;
    }

    /**
     * Establece el identificador exacto de la petición donde ocurrió el problema.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: La instancia interna se sobreescribe con el valor proporcionado.
     *
     * @param instance el identificador de la instancia.
     */
    @JsonProperty("instance")
    public void setInstance(String instance) {
        this.instance = instance;
    }

    /**
     * Compara este objeto con otro para verificar si representan el mismo error exacto.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero solo si ambos tienen los mismos atributos fijos y el mismo contenido en el mapa base. Falso en caso contrario.
     *
     * @param o el objeto a comparar.
     * @return verdadero si los objetos son idénticos, falso si no.
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
     * Calcula el código numérico para usar esta respuesta en colecciones basadas en hash.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve un número entero generado a partir de todos los detalles del problema.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, title, status, detail, instance, super.hashCode());
    }

    /**
     * Genera una representación en texto con los datos completos del problema.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una cadena de texto multilínea mostrando todas las propiedades asignadas al error.
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