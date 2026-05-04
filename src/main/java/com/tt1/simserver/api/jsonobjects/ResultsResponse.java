package com.tt1.simserver.api.jsonobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.SimulationStep;
import com.tt1.simserver.model.SimulationResult;

import java.util.Objects;

import static com.tt1.simserver.api.jsonobjects.utils.StringManipulation.toIndentedString;

/**
 * Representa la respuesta devuelta por el servidor al consultar el historial de resultados de un tablero.
 */
@JsonTypeName("ResultsResponse")
public class ResultsResponse {
    private Boolean done;
    private Integer requestToken;
    private String errorMessage;
    private String data;

    /**
     * Indica si los datos de la simulación se obtuvieron correctamente.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si los resultados están incluidos en esta respuesta, o falso si hubo un fallo al recuperarlos.
     *
     * @return el estado de éxito de la operación.
     */
    @JsonProperty("done")
    public Boolean getDone() {
        return done;
    }

    /**
     * Actualiza el estado de éxito al recuperar los resultados.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El estado de éxito interno se sobreescribe con el valor proporcionado.
     *
     * @param done verdadero si la petición fue exitosa, falso en caso contrario.
     */
    @JsonProperty("done")
    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     * Obtiene el identificador numérico de la simulación a la que pertenecen los resultados.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el token exacto de la simulación consultada.
     *
     * @return el token numérico de la simulación.
     */
    @JsonProperty("tokenSolicitud")
    public Integer getRequestToken() {
        return requestToken;
    }

    /**
     * Establece el identificador numérico de la simulación a la que pertenecen los resultados.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El token interno de la petición se sobreescribe con el valor proporcionado.
     *
     * @param requestToken el token numérico.
     */
    @JsonProperty("tokenSolicitud")
    public void setRequestToken(Integer requestToken) {
        this.requestToken = requestToken;
    }

    /**
     * Obtiene el mensaje descriptivo en caso de error durante la búsqueda de los resultados.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve el texto del error, o nulo si los resultados se recuperaron con éxito.
     *
     * @return el texto del mensaje de error, o nulo si no hubo error.
     */
    @JsonProperty("errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Establece el mensaje de error de la operación de consulta.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: El mensaje de error interno se actualiza con el texto indicado.
     *
     * @param errorMessage el texto del mensaje de error.
     */
    @JsonProperty("errorMessage")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Obtiene los datos resultantes con el historial del tablero simulado.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una cadena de texto que contiene todos los pasos capturados de la simulación finalizada.
     *
     * @return el historial generado por la simulación.
     */
    @JsonProperty("data")
    public String getData() {
        return data;
    }

    /**
     * Establece los datos resultantes con el historial del tablero simulado.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Los datos internos del historial se sobreescriben con el contenido proporcionado.
     *
     * @param data la información en texto de la simulación.
     */
    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Compara esta respuesta con otro objeto para verificar si exponen los mismos resultados.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve verdadero si el otro objeto es una respuesta idéntica con el mismo token e historial de datos. Devuelve falso en caso contrario.
     *
     * @param o el objeto a comparar.
     * @return verdadero si tienen la misma información, falso si difieren.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultsResponse resultsResponse = (ResultsResponse) o;
        return Objects.equals(this.done, resultsResponse.done) &&
                Objects.equals(this.requestToken, resultsResponse.requestToken) &&
                Objects.equals(this.errorMessage, resultsResponse.errorMessage) &&
                Objects.equals(this.data, resultsResponse.data);
    }

    /**
     * Calcula el código numérico para usar esta respuesta en colecciones basadas en hash.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve un número entero generado a partir del estado, el token y los datos resultantes.
     *
     * @return el valor hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(done, requestToken, errorMessage, data);
    }

    /**
     * Genera una representación en texto con los datos del historial devuelto.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve una cadena de texto multilínea mostrando si fue un éxito y la información contenida en el resultado.
     *
     * @return una representación en formato de texto.
     */
    @Override
    public String toString() {
        return "class ResultsResponse {\n" +
                "\tdone: " + toIndentedString(done) + "\n" +
                "\ttokenSolicitud: " + toIndentedString(requestToken) + "\n" +
                "\terrorMessage: " + toIndentedString(errorMessage) + "\n" +
                "\tdata: " + toIndentedString(data) + "\n" +
                "}";
    }

//    ESPECIFICACIÓN DE CONVERT TO SIMULATION STEP
//    /**
//     * Construye una captura extrayendo los datos del tablero en este instante.
//     *
//     * <p>Precondición: {@code grid} no es nulo.
//     *
//     * <p>Postcondición: El estado actual del tablero es copiado en memoria, asociando de forma inmutable el color de cada criatura con la casilla exacta que ocupa.
//     *
//     * @param grid el tablero que se va a fotografiar.
//     * @return una copia del estado actual del tablero en forma de paso de simulación.
//     */
    /**
     * Convierte un resultado de simulación en la cadena que se envía en el campo data de una respuesta de resultado.
     *
     * <p>Precondición: {@code result} no es nulo.
     *
     * <p>Postcondición: Devuelve una cadena que representa el resultado de la simulación con el formato en el que se envía
     * como respuesta de resultado en la presentación. Este formato es el siguiente:
     *    <ancho_tablero>
     *    <tiempo><x><y><color>
     *    <tiempo><x><y><color>
     *    <tiempo><x><y><color>
     *    ...
     *    <tiempo><x><y><color>
     * Donde para cada instante de tiempo se incluyen todas las posiciones donde hay criaturas con su color correspondiente.
     *
     * @param result el resultado de simulación a representar en formato cadena.
     * @return la cadena que representa el resultado de la simulación.
     */
    public static String convertToResultResponseData(SimulationResult result) {
        StringBuilder dataBuilder = new StringBuilder();
        int size = result.getSize();
        SimulationStep step;
        String creatureColor;

        // Guardamos la primera línea con el tamaño base inferido del tablero
        dataBuilder.append(size).append("\n");

        // Construimos las líneas formato "tiempo,x,y,color"
        for (int t = 0; t < result.getSeconds(); t++) {
            step = result.getSimulationStep(t);

            for (Position p : step.getNonEmptyPositions()) {
                creatureColor = step.getColor(p);
                dataBuilder.append(t).append(",")
                        .append(p.getY()).append(",")
                        .append(p.getX()).append(",")
                        .append(creatureColor).append("\n");
            }
        }

        return dataBuilder.toString().trim();
    }
}