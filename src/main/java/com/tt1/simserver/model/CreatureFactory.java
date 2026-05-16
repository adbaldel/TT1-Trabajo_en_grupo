package com.tt1.simserver.model;

import com.tt1.simserver.config.ConfigManager;

import java.util.*;

/**
 * Factoría de criaturas.
 */
public class CreatureFactory {
    private static final Set<String> CREATURES_NAMES;

    static {
        ConfigManager config = ConfigManager.getInstance();

        // CARGAR CRIATURAS ESTÁTICAS
        List<String> staticCreatureNames = config.getStringList(
                "creature.static.names",
                List.of("perezoso")
        );

        // CARGAR CRIATURAS MÓVILES
        List<String> mobileCreatureNames = config.getStringList(
                "creature.mobile.names",
                List.of("gato")
        );

        // CARGAR CONEJOS ESTÁTICOS
        List<String> staticRabbitNames = config.getStringList(
                "creature.static_rabbit.names",
                List.of("conejo")
        );

        // POBLAR LAS ESTRUCTURAS DE DATOS EFICIENTES
        CREATURES_NAMES = new HashSet<>();
        CREATURES_NAMES.addAll(staticCreatureNames);
        CREATURES_NAMES.addAll(mobileCreatureNames);
        CREATURES_NAMES.addAll(staticRabbitNames);
    }

    /**
     * Constructor privado para evitar que se construyan objetos instancia de esta factoría.
     */
    private CreatureFactory() {
        // No hace nada
    }

    /**
     * Obtiene los nombres de todas las criaturas que el servicio sabe simular.
     *
     * @return la colección los nombres de todas las criaturas que el servicio sabe simular.
     */
    public static Collection<String> getCreaturesNames() {
        return CREATURES_NAMES;
    }

    /**
     * Comprueba si el nombre {@code name} es uno de los que reconoce la aplicación, es decir, es uno de los definidos
     * en el archivo de propiedades (o en su defecto, uno de los definidos por defecto). Asume que el nombre es no
     * nulo.
     *
     * @param name el nombre a comprobar.
     * @return cierto si es reconocido por la aplicación, falso en caso contrario.
     */
    public static boolean existsCreature(String name) {
        return CREATURES_NAMES.contains(name);
    }

    /**
     * Crea una criatura con el id dado a partir del nombre dado. Asume que el
     * nombre existe, y que el id es no nulo y único para esta criatura.
     *
     * @param name el nombre de la criatura.
     * @param id   el id de la criatura.
     * @return la criatura creada.
     */
    public static Creature createCreature(String name, String id) {
        return new Creature(id, name);
    }
}
