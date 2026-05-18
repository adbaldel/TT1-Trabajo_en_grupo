package com.tt1.simserver.utils;

import java.util.Collection;
import java.util.Random;

/**
 * Proveedor de métodos utilitarios para generación de pseudo-aleatoriedad.
 */
public class RandomUtil {

    /**
     * Constructor privado para evitar que se construyan objetos instancia de esta utilidad.
     */
    private RandomUtil() {
        // No hace nada
    }

    /**
     * Obtiene un elemento de manera pseudo-aleatoria de la colección {@code collection} usando el generador de
     * pseudo-aleatoriedad {@code random}. Asume que la colección es no nula y tiene al menos un elemento; y que el
     * generador de pseudo-aleatoriedad es no nulo.
     *
     * @param collection la colección de la que obtener un elemento.
     * @param random     el generador de pseudo-aleatoriedad.
     * @param <T>        la clase de objetos que hay en la colección
     * @return el elemento de la colección seleccionado de manera pseudo-aleatoria.
     */
    public static <T> T getRandomElement(Collection<T> collection, Random random) {
        T randomElement = null;
        int randomElementIndex;
        int iteratorIndex;

        randomElementIndex = random.nextInt(collection.size());

        iteratorIndex = 0;
        for (T element : collection) {
            if (iteratorIndex == randomElementIndex) {
                randomElement = element;
                break;
            }
            iteratorIndex++;
        }

        return randomElement;
    }
}
