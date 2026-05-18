package com.tt1.simserver.utils;

/**
 * Proveedor de métodos utilitarios para la manipulación y formateo de cadenas de texto.
 */
public class StringManipulation {

    /**
     * Constructor privado para evitar que se construyan objetos instancia de esta utilidad.
     */
    private StringManipulation() {
        // No hace nada
    }

    /**
     * Convierte un objeto a una representación textual indentada de forma legible.
     *
     * @param o el objeto a convertir.
     * @return la representación en formato de texto indentado.
     */
    public static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n\t");
    }
}