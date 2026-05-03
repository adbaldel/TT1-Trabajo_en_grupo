package com.tt1.simserver.presentation.jsonobjects.utils;

/**
 * Clase utilitaria que agrupa funciones relacionadas con el formato y procesado de cadenas de texto.
 */
public class StringManipulation {
    /**
     * Convierte un objeto a una cadena de texto aplicando indentación adicional a todas sus líneas.
     *
     * <p>Precondición: Ninguna.
     *
     * <p>Postcondición: Devuelve la palabra "null" si el objeto suministrado es nulo. Si no lo es, devuelve su representación en texto habiendo sustituido cada salto de línea original por un salto de línea seguido de un tabulador.
     *
     * @param o el objeto a representar y formatear como texto.
     * @return la representación en cadena indentada del objeto.
     */
    public static String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n\t");
    }
}