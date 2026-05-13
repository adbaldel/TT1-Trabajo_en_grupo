package com.tt1.simserver.api.transformers;

import com.tt1.simserver.api.jsonobjects.SimulationRequestJson;
import com.tt1.simserver.config.ConfigManager;
import com.tt1.simserver.model.CreatureFactory;
import com.tt1.simserver.model.SimulationRequest;
import com.tt1.simserver.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Transformador entre solicitudes de la capa de la api {@link SimulationRequestJson} y solicitudes del modelo de
 * dominio {@link SimulationRequest}
 */
public class RequestTransformer {
    private static final int TICKS_TO_RUN;
    private static final double INITIAL_OCCUPANCY;
    private static final double FOOD_PROBABILITY;

    static {
        ConfigManager config = ConfigManager.getInstance();

        TICKS_TO_RUN = config.getInt("simulation.ticks_to_run", 100);
        INITIAL_OCCUPANCY = config.getDouble("simulation.initial_occupancy", 0.35);
        FOOD_PROBABILITY = config.getDouble("simulation.food_probability", 1.0);
    }

    /**
     * Transforma una solicitud del la capa de api {@code request} a un a solicitud del modelo de dominio con el mismo
     * usuario; cantidades de criaturas asociadas a las criaturas representadas por los nombres y cantidades dadas en la
     * solicitud; los ticks a correr, ocupación incial y probabilidad de comida por defecto de la aplicación; y el
     * generador de pseudo-aleatoriedad {@code random}. Asume que {@code request} posee listas no nulas de nombres y
     * cantidades; que hay la misma cantidad de nombres y de cantidades; que todos los nombres son reconocidos por la
     * aplicación según {@link CreatureFactory#existsCreatureName(String)}; que todas las cantidades son mayores o
     * iguales que cero; que {@code user} es no nulo y tiene información completa (no es de consulta); y que el
     * generador de números pseudo-aleatorios es no nulo.
     *
     * @param simulationRequestJSON la solicitud de la capa de api.
     * @param user                  el usuario que hace la solicitud.
     * @param random                el generador de números pseudo-aleatorios.
     * @return la solicitud de simulación de la capa de api transformada.
     */
    public static SimulationRequest transform(SimulationRequestJson simulationRequestJSON, User user, Random random) {
        Map<String, Integer> creaturesNamesQuantities = new HashMap<>();
        int totalNumberOfCreatures = 0;
        List<String> creaturesNames = simulationRequestJSON.getCreatureNames();
        List<Integer> creaturesQuantities = simulationRequestJSON.getCreatureQuantities();
        for (int i = 0; i < creaturesNames.size(); i++) {
            creaturesNamesQuantities.put(creaturesNames.get(i).toLowerCase(), creaturesQuantities.get(i));
            totalNumberOfCreatures += creaturesQuantities.get(i);
        }

        return new SimulationRequest(user, creaturesNamesQuantities, totalNumberOfCreatures, TICKS_TO_RUN,
                INITIAL_OCCUPANCY, FOOD_PROBABILITY, random);
    }
}
