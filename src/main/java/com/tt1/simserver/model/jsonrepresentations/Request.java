package com.tt1.simserver.model.jsonrepresentations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tt1.simserver.logic.utils.StringManipulation.toIndentedString;


/**
 * Representa los datos iniciales necesarios que envía el cliente para crear una nueva simulación.
 */
@JsonTypeName("Solicitud")
public class Request {
    private List<Integer> initialCreatureQuantities;
    private List<String> creatureNames;


    /**
     * Devuelve las cantidades iniciales de cada entidad que participará en la simulación.
     *
     * @return lista de enteros con dichas cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public List<Integer> getInitialCreatureQuantities() {
        return initialCreatureQuantities;
    }

    /**
     * Asigna la lista de cantidades iniciales de las entidades.
     *
     * @param initialCreatureQuantities la lista de las cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public void setInitialCreatureQuantities(List<Integer> initialCreatureQuantities) {
        this.initialCreatureQuantities = initialCreatureQuantities;
    }

    /**
     * Añade una nueva cantidad inicial a la lista existente. Si la lista no existe, la crea.
     *
     * @param cantidadesInicialesItem la cantidad de la nueva entidad a añadir.
     * @return la instancia actual de la solicitud.
     */
    public Request addCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (this.initialCreatureQuantities == null) {
            this.initialCreatureQuantities = new ArrayList<>();
        }

        this.initialCreatureQuantities.add(cantidadesInicialesItem);
        return this;
    }

    /**
     * Elimina una cantidad específica de la lista de cantidades iniciales.
     *
     * @param cantidadesInicialesItem la cantidad a remover.
     * @return la instancia actual de la solicitud.
     */
    public Request removeCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (cantidadesInicialesItem != null && this.initialCreatureQuantities != null) {
            this.initialCreatureQuantities.remove(cantidadesInicialesItem);
        }

        return this;
    }

    /**
     * Devuelve la lista con los nombres de las entidades a simular.
     *
     * @return lista de nombres de las entidades.
     */
    @JsonProperty("nombreEntidades")
    public List<String> getCreatureNames() {
        return creatureNames;
    }

    /**
     * Asigna la lista de los nombres correspondientes a las entidades de la simulación.
     *
     * @param creatureNames la lista de nombres.
     */
    @JsonProperty("nombreEntidades")
    public void setCreatureNames(List<String> creatureNames) {
        this.creatureNames = creatureNames;
    }

    /**
     * Añade un nuevo nombre de entidad a la lista de nombres.
     *
     * @param nombreEntidadesItem el nombre de la entidad a añadir.
     * @return la instancia actual de la solicitud.
     */
    public Request addNombreEntidadesItem(String nombreEntidadesItem) {
        if (this.creatureNames == null) {
            this.creatureNames = new ArrayList<>();
        }

        this.creatureNames.add(nombreEntidadesItem);
        return this;
    }

    /**
     * Elimina el nombre de una entidad de la lista existente.
     *
     * @param nombreEntidadesItem el nombre a remover.
     * @return la instancia actual de la solicitud.
     */
    public Request removeNombreEntidadesItem(String nombreEntidadesItem) {
        if (nombreEntidadesItem != null && this.creatureNames != null) {
            this.creatureNames.remove(nombreEntidadesItem);
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
        return Objects.equals(this.initialCreatureQuantities, request.initialCreatureQuantities) &&
                Objects.equals(this.creatureNames, request.creatureNames);
    }

    /**
     * Calcula y devuelve el código hash para el objeto Solicitud.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(initialCreatureQuantities, creatureNames);
    }

    /**
     * Devuelve una cadena de texto descriptiva de las listas contenidas en la solicitud.
     *
     * @return una representación en formato string del objeto.
     */
    @Override
    public String toString() {
        return "class Solicitud {\n" +
                "\tcantidadesIniciales: " + toIndentedString(initialCreatureQuantities) + "\n" +
                "\tnombreEntidades: " + toIndentedString(creatureNames) + "\n" +
                "}";
    }
}