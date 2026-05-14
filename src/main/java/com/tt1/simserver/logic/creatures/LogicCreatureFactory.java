package com.tt1.simserver.logic.creatures;

import com.tt1.simserver.config.ConfigManager;
import com.tt1.simserver.model.Creature;
import com.tt1.simserver.model.CreatureFactory;
import com.tt1.simserver.model.Position;

import java.util.*;

/**
 * Factoría de criaturas con funcionalidad.
 */
public class LogicCreatureFactory {
    private static final Set<String> CREATURES_NAMES;
    private static final Map<String, String> CREATURES_TYPES_MAP;
    private static final Map<String, Integer> CREATURES_STARVATION_THRESHOLD_MAP;
    private static final Map<String, Double> CREATURES_MOVE_PROBABILITY_MAP;
    private static final Map<String, Double> CREATURES_MULTIPLY_PROBABILITY_MAP;

    static {
        ConfigManager config = ConfigManager.getInstance();

        // CARGAR CRIATURAS ESTÁTICAS
        List<String> staticCreatureNames = config.getStringList(
                "creature.static.names",
                List.of("perezoso")
        );
        List<Integer> staticCreatureStarvationThresholds = config.getIntList(
                "creature.static.starvation_thresholds",
                List.of(5)
        );
        if (staticCreatureNames.size() != staticCreatureStarvationThresholds.size()) {
            staticCreatureNames = List.of("perezoso");
            staticCreatureStarvationThresholds = List.of(5);
        }

        // CARGAR CRIATURAS MÓVILES
        List<String> mobileCreatureNames = config.getStringList(
                "creature.mobile.names",
                List.of("gato")
        );
        List<Integer> mobileCreatureStarvationThresholds = config.getIntList(
                "creature.mobile.starvation_thresholds",
                List.of(5)
        );
        List<Double> mobileCreatureMoveProbabilities = config.getDoubleList(
                "creature.mobile.move_probabilities",
                List.of(0.5)
        );
        if (mobileCreatureNames.size() != mobileCreatureStarvationThresholds.size() || mobileCreatureNames.size() != mobileCreatureMoveProbabilities.size()) {
            mobileCreatureNames = List.of("gato");
            mobileCreatureStarvationThresholds = List.of(5);
            mobileCreatureMoveProbabilities = List.of(0.5);
        }

        // CARGAR CONEJOS ESTÁTICOS
        List<String> staticRabbitNames = config.getStringList(
                "creature.static_rabbit.names",
                List.of("conejo")
        );
        List<Integer> staticRabbitStarvationThresholds = config.getIntList(
                "creature.static_rabbit.starvation_thresholds",
                List.of(5)
        );
        List<Double> staticRabbitMultiplyProbabilities = config.getDoubleList(
                "creature.static_rabbit.multiply_probabilities",
                List.of(0.2)
        );
        if (staticRabbitNames.size() != staticRabbitStarvationThresholds.size() || staticRabbitNames.size() != staticRabbitMultiplyProbabilities.size()) {
            staticRabbitNames = List.of("conejo");
            staticRabbitStarvationThresholds = List.of(5);
            staticRabbitMultiplyProbabilities = List.of(0.2);
        }

        // POBLAR LAS ESTRUCTURAS DE DATOS EFICIENTES
        CREATURES_NAMES = new HashSet<>();
        CREATURES_NAMES.addAll(staticCreatureNames);
        CREATURES_NAMES.addAll(mobileCreatureNames);
        CREATURES_NAMES.addAll(staticRabbitNames);

        CREATURES_TYPES_MAP = new HashMap<>();
        for (String staticCreatureName : staticCreatureNames) {
            CREATURES_TYPES_MAP.put(staticCreatureName, "static");
        }
        for (String mobileCreatureName : mobileCreatureNames) {
            CREATURES_TYPES_MAP.put(mobileCreatureName, "mobile");
        }
        for (String staticRabbitCreatureName : staticRabbitNames) {
            CREATURES_TYPES_MAP.put(staticRabbitCreatureName, "static_rabbit");
        }

        CREATURES_STARVATION_THRESHOLD_MAP = new HashMap<>();
        for (int i = 0; i < staticCreatureNames.size(); i++) {
            CREATURES_STARVATION_THRESHOLD_MAP.put(staticCreatureNames.get(i),
                    staticCreatureStarvationThresholds.get(i));
        }
        for (int i = 0; i < mobileCreatureNames.size(); i++) {
            CREATURES_STARVATION_THRESHOLD_MAP.put(mobileCreatureNames.get(i),
                    mobileCreatureStarvationThresholds.get(i));
        }
        for (int i = 0; i < staticRabbitNames.size(); i++) {
            CREATURES_STARVATION_THRESHOLD_MAP.put(staticRabbitNames.get(i),
                    staticRabbitStarvationThresholds.get(i));
        }

        CREATURES_MOVE_PROBABILITY_MAP = new HashMap<>();
        for (int i = 0; i < mobileCreatureNames.size(); i++) {
            CREATURES_MOVE_PROBABILITY_MAP.put(mobileCreatureNames.get(i), mobileCreatureMoveProbabilities.get(i));
        }

        CREATURES_MULTIPLY_PROBABILITY_MAP = new HashMap<>();
        for (int i = 0; i < staticRabbitNames.size(); i++) {
            CREATURES_MULTIPLY_PROBABILITY_MAP.put(staticRabbitNames.get(i),
                    staticRabbitMultiplyProbabilities.get(i));
        }
    }

    /**
     * Constructor privado para evitar que se construyan objetos instancia de esta factoría.
     */
    private LogicCreatureFactory() {
        // No hace nada
    }

    /**
     * Crea una criatura con funcionalidad a partir de una criatura dada, con los parámetros definidos en el archivo de
     * propiedades de la aplicación, la posición dada y le asocia el generador de pseudo-aleatoriedad dado. Asume que la
     * criatura, posición y generador de números pseudo-aleatorios son no nulos; que la criatura tiene como nombre uno
     * existente según {@link CreatureFactory#existsCreature(String)}.
     *
     * @param creature la criatura sin funcionalidad base.
     * @param position la posición de la criatura.
     * @param random   el generador de números pseudo-aleatorios.
     * @return la criatura con funcionalidad creada.
     */
    public static LogicCreature createCreature(Creature creature, Position position, Random random) {
        LogicCreature logicCreature = null;
        String type = CREATURES_TYPES_MAP.get(creature.getName());
        int starvationThreshold = CREATURES_STARVATION_THRESHOLD_MAP.get(creature.getName());

        switch (type) {
            case "static":
                logicCreature = new StaticCreature(creature.getId(), creature.getName(), creature.getColor(),
                        starvationThreshold, position);
                break;
            case "mobile":
                double moveProbability = CREATURES_MOVE_PROBABILITY_MAP.get(creature.getName());
                logicCreature = new MobileCreature(creature.getId(), creature.getName(), creature.getColor(),
                        starvationThreshold, moveProbability, position, random);
                break;
            case "static_rabbit":
                double multiplyProbability = CREATURES_MULTIPLY_PROBABILITY_MAP.get(creature.getName());
                logicCreature = new StaticRabbit(creature.getId(), creature.getName(), creature.getColor(),
                        starvationThreshold, multiplyProbability, position, random);
                break;
            default:
                throw new IllegalArgumentException("Tipo de criatura desconocido: " + creature.getName());
        }

        return logicCreature;
    }
}