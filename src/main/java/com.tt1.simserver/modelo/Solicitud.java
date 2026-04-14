package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Representa los datos iniciales necesarios que envía el cliente para crear una nueva simulación.
 */
@JsonTypeName("Solicitud")
public class Solicitud {
    private List<Integer> cantidadesIniciales;
    private List<String> nombreEntidades;

    /**
     * Establece la lista con las cantidades iniciales de las entidades y devuelve la instancia actual.
     *
     * @param cantidadesIniciales lista de enteros con el número de elementos de cada entidad a simular.
     * @return la instancia actual para encadenar llamadas.
     */
    public Solicitud cantidadesIniciales(List<Integer> cantidadesIniciales) {
        this.cantidadesIniciales = cantidadesIniciales;
        return this;
    }


    /**
     * Devuelve las cantidades iniciales de cada entidad que participará en la simulación.
     *
     * @return lista de enteros con dichas cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public List<Integer> getCantidadesIniciales() {
        return cantidadesIniciales;
    }

    /**
     * Asigna la lista de cantidades iniciales de las entidades.
     *
     * @param cantidadesIniciales la lista de las cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public void setCantidadesIniciales(List<Integer> cantidadesIniciales) {
        this.cantidadesIniciales = cantidadesIniciales;
    }

    /**
     * Añade una nueva cantidad inicial a la lista existente. Si la lista no existe, la crea.
     *
     * @param cantidadesInicialesItem la cantidad de la nueva entidad a añadir.
     * @return la instancia actual de la solicitud.
     */
    public Solicitud addCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (this.cantidadesIniciales == null) {
            this.cantidadesIniciales = new ArrayList<>();
        }

        this.cantidadesIniciales.add(cantidadesInicialesItem);
        return this;
    }

    /**
     * Elimina una cantidad específica de la lista de cantidades iniciales.
     *
     * @param cantidadesInicialesItem la cantidad a remover.
     * @return la instancia actual de la solicitud.
     */
    public Solicitud removeCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (cantidadesInicialesItem != null && this.cantidadesIniciales != null) {
            this.cantidadesIniciales.remove(cantidadesInicialesItem);
        }

        return this;
    }

    /**
     * Establece la lista de nombres de las entidades y devuelve la instancia actual.
     *
     * @param nombreEntidades lista con los nombres de las entidades que coinciden con las cantidades.
     * @return la instancia actual de la solicitud.
     */
    public Solicitud nombreEntidades(List<String> nombreEntidades) {
        this.nombreEntidades = nombreEntidades;
        return this;
    }

    /**
     * Devuelve la lista con los nombres de las entidades a simular.
     *
     * @return lista de nombres de las entidades.
     */
    @JsonProperty("nombreEntidades")
    public List<String> getNombreEntidades() {
        return nombreEntidades;
    }

    /**
     * Asigna la lista de los nombres correspondientes a las entidades de la simulación.
     *
     * @param nombreEntidades la lista de nombres.
     */
    @JsonProperty("nombreEntidades")
    public void setNombreEntidades(List<String> nombreEntidades) {
        this.nombreEntidades = nombreEntidades;
    }

    /**
     * Añade un nuevo nombre de entidad a la lista de nombres.
     *
     * @param nombreEntidadesItem el nombre de la entidad a añadir.
     * @return la instancia actual de la solicitud.
     */
    public Solicitud addNombreEntidadesItem(String nombreEntidadesItem) {
        if (this.nombreEntidades == null) {
            this.nombreEntidades = new ArrayList<>();
        }

        this.nombreEntidades.add(nombreEntidadesItem);
        return this;
    }

    /**
     * Elimina el nombre de una entidad de la lista existente.
     *
     * @param nombreEntidadesItem el nombre a remover.
     * @return la instancia actual de la solicitud.
     */
    public Solicitud removeNombreEntidadesItem(String nombreEntidadesItem) {
        if (nombreEntidadesItem != null && this.nombreEntidades != null) {
            this.nombreEntidades.remove(nombreEntidadesItem);
        }

        return this;
    }

    /**
     * Verifica la igualdad de los atributos de esta solicitud frente a otro objeto.
     *
     * @param o el objeto con el que comparar.
     * @return cierto si tienen las mismas listas de cantidades y entidades.
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
     * Calcula y devuelve el código hash para el objeto Solicitud.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cantidadesIniciales, nombreEntidades);
    }

    /**
     * Devuelve una cadena de texto descriptiva de las listas contenidas en la solicitud.
     *
     * @return una representación en formato string del objeto.
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
     * Método auxiliar de formato para indentar de forma amigable los datos en el toString.
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}