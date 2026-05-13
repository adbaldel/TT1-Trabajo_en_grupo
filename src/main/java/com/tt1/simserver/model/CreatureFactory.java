package com.tt1.simserver.model;

import com.tt1.simserver.config.ConfigManager;

import java.util.*;

/**
 * Factoría de criaturas.
 */
public class CreatureFactory {
    private static final Set<String> CREATURE_NAMES;
    private static final Map<String, String> CREATURE_COLORS_MAP;

    static {
        ConfigManager config = ConfigManager.getInstance();

        // CARGAR CRIATURAS ESTÁTICAS
        List<String> staticCreatureNames = config.getStringList(
                "creature.static.names",
                List.of("perezoso")
        );
        List<String> staticCreatureColors = config.getStringList(
                "creature.static.colors",
                List.of("green")
        );
        if (staticCreatureNames.size() != staticCreatureColors.size()) {
            staticCreatureNames = List.of("perezoso");
            staticCreatureColors = List.of("green");
        }

        // CARGAR CRIATURAS MÓVILES
        List<String> mobileCreatureNames = config.getStringList(
                "creature.mobile.names",
                List.of("gato")
        );
        List<String> mobileCreatureColors = config.getStringList(
                "creature.mobile.colors",
                List.of("red")
        );
        if (mobileCreatureNames.size() != mobileCreatureColors.size()) {
            mobileCreatureNames = List.of("gato");
            mobileCreatureColors = List.of("red");
        }

        // CARGAR CONEJOS ESTÁTICOS
        List<String> staticRabbitNames = config.getStringList(
                "creature.static_rabbit.names",
                List.of("conejo")
        );
        List<String> staticRabbitColors = config.getStringList(
                "creature.static_rabbit.colors",
                List.of("blue")
        );
        if (staticRabbitNames.size() != staticRabbitColors.size()) {
            staticRabbitNames = List.of("conejo");
            staticRabbitColors = List.of("blue");
        }

        // POBLAR LAS ESTRUCTURAS DE DATOS EFICIENTES
        CREATURE_NAMES = new HashSet<>();
        CREATURE_NAMES.addAll(staticCreatureNames);
        CREATURE_NAMES.addAll(mobileCreatureNames);
        CREATURE_NAMES.addAll(staticRabbitNames);

        CREATURE_COLORS_MAP = new HashMap<>();
        for (int i = 0; i < staticCreatureNames.size(); i++) {
            CREATURE_COLORS_MAP.put(staticCreatureNames.get(i), staticCreatureColors.get(i));
        }
        for (int i = 0; i < mobileCreatureNames.size(); i++) {
            CREATURE_COLORS_MAP.put(mobileCreatureNames.get(i), mobileCreatureColors.get(i));
        }
        for (int i = 0; i < staticRabbitNames.size(); i++) {
            CREATURE_COLORS_MAP.put(staticRabbitNames.get(i), staticRabbitColors.get(i));
        }
    }

    /**
     * Crea una criatura con el id dado a partir del nombre dado y con el color asociado a dicho nombre. Asume que el
     * nombre existe, y que el id es no nulo y único para esta criatura.
     *
     * @param name el nombre de la criatura.
     * @param id   el id de la criatura.
     * @return la criatura creada.
     */
    public static Creature createCreatureFromName(String name, String id) {
        String color = CREATURE_COLORS_MAP.get(name);
        return new Creature(id, name, color);
    }

    /**
     * Comprueba si el nombre {@code name} es uno de los que reconoce la aplicación, es decir, es uno de los definidos
     * en el archivo de propiedades (o en su defecto, uno de los definidos por defecto). Asume que el nombre es no
     * nulo.
     *
     * @param name el nombre a comprobar.
     * @return cierto si es reconocido por la aplicación, falso en caso contrario.
     */
    public static boolean existsCreatureName(String name) {
        return CREATURE_NAMES.contains(name);
    }
}
