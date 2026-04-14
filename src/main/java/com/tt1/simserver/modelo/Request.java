package com.tt1.simserver.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;


/**
 * Representa los datos iniciales necesarios que envía el cliente para crear una nueva simulación.
 */
@JsonTypeName("Solicitud")
public class Request {
    private List<Integer> initialQuantities;
    private List<String> entityNames;


    /**
     * Devuelve las cantidades iniciales de cada entidad que participará en la simulación.
     *
     * @return lista de enteros con dichas cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public List<Integer> getInitialQuantities() {
        return initialQuantities;
    }

    /**
     * Asigna la lista de cantidades iniciales de las entidades.
     *
     * @param initialQuantities la lista de las cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public void setInitialQuantities(List<Integer> initialQuantities) {
        this.initialQuantities = initialQuantities;
    }

    /**
     * Añade una nueva cantidad inicial a la lista existente. Si la lista no existe, la crea.
     *
     * @param cantidadesInicialesItem la cantidad de la nueva entidad a añadir.
     * @return la instancia actual de la solicitud.
     */
    public Request addCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (this.initialQuantities == null) {
            this.initialQuantities = new ArrayList<>();
        }

        this.initialQuantities.add(cantidadesInicialesItem);
        return this;
    }

    /**
     * Elimina una cantidad específica de la lista de cantidades iniciales.
     *
     * @param cantidadesInicialesItem la cantidad a remover.
     * @return la instancia actual de la solicitud.
     */
    public Request removeCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (cantidadesInicialesItem != null && this.initialQuantities != null) {
            this.initialQuantities.remove(cantidadesInicialesItem);
        }

        return this;
    }

    /**
     * Devuelve la lista con los nombres de las entidades a simular.
     *
     * @return lista de nombres de las entidades.
     */
    @JsonProperty("nombreEntidades")
    public List<String> getEntityNames() {
        return entityNames;
    }

    /**
     * Asigna la lista de los nombres correspondientes a las entidades de la simulación.
     *
     * @param entityNames la lista de nombres.
     */
    @JsonProperty("nombreEntidades")
    public void setEntityNames(List<String> entityNames) {
        this.entityNames = entityNames;
    }

    /**
     * Añade un nuevo nombre de entidad a la lista de nombres.
     *
     * @param nombreEntidadesItem el nombre de la entidad a añadir.
     * @return la instancia actual de la solicitud.
     */
    public Request addNombreEntidadesItem(String nombreEntidadesItem) {
        if (this.entityNames == null) {
            this.entityNames = new ArrayList<>();
        }

        this.entityNames.add(nombreEntidadesItem);
        return this;
    }

    /**
     * Elimina el nombre de una entidad de la lista existente.
     *
     * @param nombreEntidadesItem el nombre a remover.
     * @return la instancia actual de la solicitud.
     */
    public Request removeNombreEntidadesItem(String nombreEntidadesItem) {
        if (nombreEntidadesItem != null && this.entityNames != null) {
            this.entityNames.remove(nombreEntidadesItem);
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
        Request request = (Request) o;
        return Objects.equals(this.initialQuantities, request.initialQuantities) &&
                Objects.equals(this.entityNames, request.entityNames);
    }

    /**
     * Calcula y devuelve el código hash para el objeto Solicitud.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(initialQuantities, entityNames);
    }

    /**
     * Devuelve una cadena de texto descriptiva de las listas contenidas en la solicitud.
     *
     * @return una representación en formato string del objeto.
     */
    @Override
    public String toString() {
        return "class Solicitud {\n" +
                "\tcantidadesIniciales: " + toIndentedString(initialQuantities) + "\n" +
                "\tnombreEntidades: " + toIndentedString(entityNames) + "\n" +
                "}";
    }
}