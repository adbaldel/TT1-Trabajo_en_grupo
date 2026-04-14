package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 */
@JsonTypeName("Solicitud")
public class Solicitud {
    private List<Integer> cantidadesIniciales;
    private List<String> nombreEntidades;

    /**
     *
     * @param cantidadesIniciales
     * @return
     */
    public Solicitud cantidadesIniciales(List<Integer> cantidadesIniciales) {
        this.cantidadesIniciales = cantidadesIniciales;
        return this;
    }


    /**
     *
     * @return
     */
    @JsonProperty("cantidadesIniciales")
    public List<Integer> getCantidadesIniciales() {
        return cantidadesIniciales;
    }

    /**
     *
     * @param cantidadesIniciales
     */
    @JsonProperty("cantidadesIniciales")
    public void setCantidadesIniciales(List<Integer> cantidadesIniciales) {
        this.cantidadesIniciales = cantidadesIniciales;
    }

    /**
     *
     * @param cantidadesInicialesItem
     * @return
     */
    public Solicitud addCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (this.cantidadesIniciales == null) {
            this.cantidadesIniciales = new ArrayList<>();
        }

        this.cantidadesIniciales.add(cantidadesInicialesItem);
        return this;
    }

    /**
     *
     * @param cantidadesInicialesItem
     * @return
     */
    public Solicitud removeCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (cantidadesInicialesItem != null && this.cantidadesIniciales != null) {
            this.cantidadesIniciales.remove(cantidadesInicialesItem);
        }

        return this;
    }

    /**
     *
     * @param nombreEntidades
     * @return
     */
    public Solicitud nombreEntidades(List<String> nombreEntidades) {
        this.nombreEntidades = nombreEntidades;
        return this;
    }

    /**
     *
     * @return
     */
    @JsonProperty("nombreEntidades")
    public List<String> getNombreEntidades() {
        return nombreEntidades;
    }

    /**
     *
     * @param nombreEntidades
     */
    @JsonProperty("nombreEntidades")
    public void setNombreEntidades(List<String> nombreEntidades) {
        this.nombreEntidades = nombreEntidades;
    }

    /**
     *
     * @param nombreEntidadesItem
     * @return
     */
    public Solicitud addNombreEntidadesItem(String nombreEntidadesItem) {
        if (this.nombreEntidades == null) {
            this.nombreEntidades = new ArrayList<>();
        }

        this.nombreEntidades.add(nombreEntidadesItem);
        return this;
    }

    /**
     *
     * @param nombreEntidadesItem
     * @return
     */
    public Solicitud removeNombreEntidadesItem(String nombreEntidadesItem) {
        if (nombreEntidadesItem != null && this.nombreEntidades != null) {
            this.nombreEntidades.remove(nombreEntidadesItem);
        }

        return this;
    }

    /**
     *
     * @param o   the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Solicitud solicitud = (Solicitud) o;
        return Objects.equals(this.cantidadesIniciales, solicitud.cantidadesIniciales) &&
                Objects.equals(this.nombreEntidades, solicitud.nombreEntidades);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(cantidadesIniciales, nombreEntidades);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {

        String sb = "class Solicitud {\n" +
                "    cantidadesIniciales: " + toIndentedString(cantidadesIniciales) + "\n" +
                "    nombreEntidades: " + toIndentedString(nombreEntidades) + "\n" +
                "}";
        return sb;
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

