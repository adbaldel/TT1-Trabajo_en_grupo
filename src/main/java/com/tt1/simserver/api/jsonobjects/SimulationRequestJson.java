package com.tt1.simserver.api.jsonobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tt1.simserver.utils.StringManipulation.toIndentedString;

/**
 * Representa un objeto JSON para los parámetros enviados al crear una nueva simulación.
 */
@JsonTypeName("Solicitud")
public class SimulationRequestJson {
    private List<Integer> creatureQuantities;
    private List<String> creatureNames;

    /**
     * Construye una solicitud vacía.
     */
    public SimulationRequestJson() {
    }

    /**
     * Obtiene las cantidades iniciales de las entidades.
     *
     * @return la lista de cantidades iniciales.
     */
    @JsonProperty("cantidadesIniciales")
    public List<Integer> getCreatureQuantities() {
        return creatureQuantities;
    }

    /**
     * Cambia las cantidades iniciales por {@code creatureQuantities}.
     *
     * @param creatureQuantities las nuevas cantidades iniciales.
     */
    @JsonProperty("cantidadesIniciales")
    public void setCreatureQuantities(List<Integer> creatureQuantities) {
        this.creatureQuantities = creatureQuantities;
    }

    /**
     * Añade la cantidad {@code cantidadesInicialesItem} a la lista de cantidades iniciales.
     *
     * @param cantidadesInicialesItem la cantidad a agregar.
     * @return este mismo objeto para permitir llamadas encadenadas.
     */
    public SimulationRequestJson addCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (this.creatureQuantities == null) {
            this.creatureQuantities = new ArrayList<>();
        }

        this.creatureQuantities.add(cantidadesInicialesItem);
        return this;
    }

    /**
     * Remueve la cantidad {@code cantidadesInicialesItem} de la lista de cantidades iniciales.
     *
     * @param cantidadesInicialesItem la cantidad a quitar.
     * @return este mismo objeto para permitir llamadas encadenadas.
     */
    public SimulationRequestJson removeCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (cantidadesInicialesItem != null && this.creatureQuantities != null) {
            this.creatureQuantities.remove(cantidadesInicialesItem);
        }

        return this;
    }

    /**
     * Obtiene los nombres de las entidades.
     *
     * @return la lista de nombres.
     */
    @JsonProperty("nombreEntidades")
    public List<String> getCreatureNames() {
        return creatureNames;
    }

    /**
     * Cambia los nombres de las entidades por {@code creatureNames}.
     *
     * @param creatureNames la nueva lista de nombres.
     */
    @JsonProperty("nombreEntidades")
    public void setCreatureNames(List<String> creatureNames) {
        this.creatureNames = creatureNames;
    }

    /**
     * Añade el nombre {@code nombreEntidadesItem} a la lista de nombres de entidades.
     *
     * @param nombreEntidadesItem el nombre a añadir.
     * @return este mismo objeto para permitir llamadas encadenadas.
     */
    public SimulationRequestJson addNombreEntidadesItem(String nombreEntidadesItem) {
        if (this.creatureNames == null) {
            this.creatureNames = new ArrayList<>();
        }

        this.creatureNames.add(nombreEntidadesItem);
        return this;
    }

    /**
     * Remueve el nombre {@code nombreEntidadesItem} de la lista de nombres de entidades.
     *
     * @param nombreEntidadesItem el nombre a quitar.
     * @return este mismo objeto para permitir llamadas encadenadas.
     */
    public SimulationRequestJson removeNombreEntidadesItem(String nombreEntidadesItem) {
        if (nombreEntidadesItem != null && this.creatureNames != null) {
            this.creatureNames.remove(nombreEntidadesItem);
        }

        return this;
    }

    /**
     * Comprueba si esta solicitud es igual al objeto {@code o}. Devuelve cierto si el objeto es una solicitud con las
     * cantidades iniciales y los nombres de las entidades iguales.
     *
     * @param o el objeto a comparar.
     * @return cierto si esta solicitud es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimulationRequestJson request = (SimulationRequestJson) o;
        return Objects.equals(this.creatureQuantities, request.creatureQuantities) &&
                Objects.equals(this.creatureNames, request.creatureNames);
    }

    /**
     * Obtiene el código hash de la solicitud.
     *
     * @return el código hash de la solicitud.
     */
    @Override
    public int hashCode() {
        return Objects.hash(creatureQuantities, creatureNames);
    }

    /**
     * Obtiene una representación en cadena de texto del objeto.
     *
     * @return la cadena de texto con los atributos.
     */
    @Override
    public String toString() {
        return "class Solicitud {\n" +
                "\tcantidadesIniciales: " + toIndentedString(creatureQuantities) + "\n" +
                "\tnombreEntidades: " + toIndentedString(creatureNames) + "\n" +
                "}";
    }
}