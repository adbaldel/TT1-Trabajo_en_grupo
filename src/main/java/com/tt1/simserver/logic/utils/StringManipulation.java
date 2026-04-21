package com.tt1.simserver.logic.utils;

/**
 * Clase que agrupa las utilidades usadas en el proyecto relacionadas con los Strings
 */
public class StringManipulation {
    /**
     * Convierte el objeto dado a una cadena, indentando cada línea con un tabulador (excepto la primera línea).
     *
     * @param o el objeto a representar en una cadena indentada.
     * @return la representación en cadena indentada del objecto.
     */
    public static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n\t");
    }
}
