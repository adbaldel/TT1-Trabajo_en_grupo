package com.tt1.simserver.presentation.jsonobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.tt1.simserver.presentation.jsonobjects.utils.StringManipulation.toIndentedString;

/**
 * Representa los datos iniciales necesarios que envía el cliente para crear una nueva simulación.
 */
@JsonTypeName("Solicitud")
public class Request {
    private List<Integer> initialCreatureQuantities;
    private List<String> creatureNames;

    /**
     * Obtiene las cantidades iniciales de cada criatura para poblar el tablero.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la lista de números enteros con la cantidad especificada de cada criatura.
     *
     * @return la lista de enteros con dichas cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public List<Integer> getInitialCreatureQuantities() {
        return initialCreatureQuantities;
    }

    /**
     * Sustituye la lista de cantidades iniciales de criaturas.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: La lista interna se sobreescribe con la nueva colección de cantidades proporcionada.
     *
     * @param initialCreatureQuantities la nueva lista de cantidades.
     */
    @JsonProperty("cantidadesIniciales")
    public void setInitialCreatureQuantities(List<Integer> initialCreatureQuantities) {
        this.initialCreatureQuantities = initialCreatureQuantities;
    }

    /**
     * Añade una nueva cantidad a la lista de criaturas iniciales.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Si la lista interna no existía, se crea. La nueva cantidad se añade al final de la colección. Devuelve esta misma solicitud actualizada.
     *
     * @param cantidadesInicialesItem la cantidad de la criatura a añadir.
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
     * Elimina una cantidad específica de la lista de criaturas iniciales.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Si la lista existe y contiene la cantidad exacta indicada, esta se elimina. Devuelve esta misma solicitud actualizada.
     *
     * @param cantidadesInicialesItem la cantidad a retirar de la lista.
     * @return la instancia actual de la solicitud.
     */
    public Request removeCantidadesInicialesItem(Integer cantidadesInicialesItem) {
        if (cantidadesInicialesItem != null && this.initialCreatureQuantities != null) {
            this.initialCreatureQuantities.remove(cantidadesInicialesItem);
        }

        return this;
    }

    /**
     * Obtiene los nombres de las especies de criaturas que participarán en la simulación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la lista con los nombres identificadores de las criaturas que se añadirán al tablero.
     *
     * @return la lista de nombres de las criaturas.
     */
    @JsonProperty("nombreEntidades")
    public List<String> getCreatureNames() {
        return creatureNames;
    }

    /**
     * Sustituye la lista de nombres de las criaturas de la simulación.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: La lista interna se sobreescribe con los nuevos nombres proporcionados.
     *
     * @param creatureNames la nueva lista de nombres.
     */
    @JsonProperty("nombreEntidades")
    public void setCreatureNames(List<String> creatureNames) {
        this.creatureNames = creatureNames;
    }

    /**
     * Añade el nombre de una criatura a la lista de especies.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Si la lista interna no existía, se crea. El nombre se añade al final de la colección. Devuelve esta misma solicitud actualizada.
     *
     * @param nombreEntidadesItem el nombre de la especie a añadir.
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
     * Elimina el nombre de una criatura de la lista de especies.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Si la lista existe y contiene el nombre exacto indicado, este se elimina. Devuelve esta misma solicitud actualizada.
     *
     * @param nombreEntidadesItem el nombre a retirar de la lista.
     * @return la instancia actual de la solicitud.
     */
    public Request removeNombreEntidadesItem(String nombreEntidadesItem) {
        if (nombreEntidadesItem != null && this.creatureNames != null) {
            this.creatureNames.remove(nombreEntidadesItem);
        }

        return this;
    }

    /**
     * Compara esta solicitud con otro objeto para comprobar si solicitan los mismos datos iniciales.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si ambas tienen exactamente las mismas listas de cantidades y especies. Devuelve falso en caso contrario.
     *
     * @param o el objeto a comparar.
     * @return verdadero si son solicitudes idénticas, falso si no.
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
     * Calcula el código numérico para usar esta solicitud en colecciones basadas en hash.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve un número entero generado a partir de las listas de criaturas y cantidades.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(initialCreatureQuantities, creatureNames);
    }

    /**
     * Genera una representación en texto con los datos de esta solicitud.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una cadena de texto multilínea listando las cantidades y los nombres de las criaturas.
     *
     * @return una representación en formato de texto.
     */
    @Override
    public String toString() {
        return "class Solicitud {\n" +
                "\tcantidadesIniciales: " + toIndentedString(initialCreatureQuantities) + "\n" +
                "\tnombreEntidades: " + toIndentedString(creatureNames) + "\n" +
                "}";
    }
}