package com.tt1.simserver.model;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * Representa una solicitud de simulación (inmutable).
 */
public class SimulationRequest {
    private final User user;
    private final Map<String, Integer> creaturesNamesQuantities;
    private final int totalNumberOfCreatures;
    private final int ticksToRun;
    private final double initialOccupancy;
    private final double foodProbability;
    private final Random random;

    /**
     * Construye una solicitud de simulación con el usuario, mapa de nombres de criaturas a cantidades de criaturas,
     * número total de criaturas, número de ticks a simular, porcentaje inicial de ocupación, la probabilidad que cada
     * celda tiene de tener comida y generador de números pseudo-aleatorios pasados como parámetros. Asume que el
     * usuario es no nulo; los nombres de criaturas son no nulos y válidos según
     * {@link CreatureFactory#createCreature(String, String)}; las cantidades de criaturas son mayores o iguales a cero;
     * el número total de criaturas es igual a la suma de todas las cantidades de criaturas; el número de ticks a
     * simular es mayor o igual a cero; el porcentaje de ocupación inicial está entre 0.0 y 1.0 (1.0 incluido); la
     * probabilidad que cada celda tiene de tener comida está entre 0.0 y 1.0 (ambos incluidos); y el generador de
     * números pseudo-aleatorios es no nulo.
     *
     * @param user                     el usuario que solicita la simulación.
     * @param creaturesNamesQuantities el mapa de nombres a cantidades de criaturas.
     * @param totalNumberOfCreatures   el número total de criaturas.
     * @param ticksToRun               el número de ticks a simular.
     * @param initialOccupancy         el porcentaje inicial de ocupación.
     * @param foodProbability          la probabilidad que cada celda tiene de tener comida.
     * @param random                   el generador de números pseudo-aleatorios.
     */
    public SimulationRequest(User user, Map<String, Integer> creaturesNamesQuantities, int totalNumberOfCreatures,
                             int ticksToRun, double initialOccupancy, double foodProbability, Random random) {
        this.user = user;
        this.creaturesNamesQuantities = creaturesNamesQuantities;
        this.totalNumberOfCreatures = totalNumberOfCreatures;
        this.ticksToRun = ticksToRun;
        this.initialOccupancy = initialOccupancy;
        this.foodProbability = foodProbability;
        this.random = random;
    }

    /**
     * Construye una solicitud de simulación con el usuario, mapa de nombres de criaturas a cantidades de criaturas,
     * número total de criaturas implícito en el mapa, número de ticks a simular, porcentaje inicial de ocupación, la
     * probabilidad que cada celda tiene de tener comida y generador de números pseudo-aleatorios pasados como
     * parámetros. Asume que el usuario es no nulo; los nombres de criaturas son no nulos y válidos según
     * {@link CreatureFactory#createCreature(String, String)}; las cantidades de criaturas son mayores o iguales a cero;
     * el número de ticks a simular es mayor o igual a cero; el porcentaje de ocupación inicial está entre 0.0 y 1.0
     * (1.0 incluido); la probabilidad que cada celda tiene de tener comida está entre 0.0 y 1.0 (ambos incluidos); y el
     * generador de números pseudo-aleatorios es no nulo.
     *
     * @param user                     el usuario que solicita la simulación.
     * @param creaturesNamesQuantities el mapa de nombres a cantidades de criaturas.
     * @param ticksToRun               el número de ticks a simular.
     * @param initialOccupancy         el porcentaje inicial de ocupación.
     * @param foodProbability          la probabilidad que cada celda tiene de tener comida.
     * @param random                   el generador de números pseudo-aleatorios.
     */
    public SimulationRequest(User user, Map<String, Integer> creaturesNamesQuantities, int ticksToRun,
                             double initialOccupancy, double foodProbability, Random random) {
        this.user = user;
        this.creaturesNamesQuantities = creaturesNamesQuantities;
        this.ticksToRun = ticksToRun;
        this.initialOccupancy = initialOccupancy;
        this.foodProbability = foodProbability;
        this.random = random;

        int numberOfCreaturesAccumulator = 0;
        for (Map.Entry<String, Integer> entry : creaturesNamesQuantities.entrySet()) {
            numberOfCreaturesAccumulator += entry.getValue();
        }
        totalNumberOfCreatures = numberOfCreaturesAccumulator;
    }

    /**
     * Construye una solicitud de simulación con el usuario dado, y el resto de parámetros iguales a los de la solicitud
     * {@code simulationRequest}.
     *
     * @param user              el usuario que solicita la simulación.
     * @param simulationRequest la solicitud de simulación a clonar cambiando el usuario.
     */
    public SimulationRequest(User user, SimulationRequest simulationRequest) {
        this.user = user;
        this.creaturesNamesQuantities = simulationRequest.creaturesNamesQuantities;
        this.totalNumberOfCreatures = simulationRequest.totalNumberOfCreatures;
        this.ticksToRun = simulationRequest.ticksToRun;
        this.initialOccupancy = simulationRequest.initialOccupancy;
        this.foodProbability = simulationRequest.foodProbability;
        this.random = simulationRequest.random;
    }

    /**
     * Obtiene el usuario de esta solicitud.
     *
     * @return el usuario de esta solicitud.
     */
    public User getUser() {
        return user;
    }

    /**
     * Obtiene los nombres de las criaturas de esta solicitud.
     *
     * @return los nombres de las criaturas de esta solicitud.
     */
    public Collection<String> getCreaturesNames() {
        return creaturesNamesQuantities.keySet();
    }

    /**
     * Obtiene la cantidad de criaturas con el nombre {@code creatureName} solicitadas en esta solicitud.
     *
     * @param creatureName el nombre de las criaturas.
     * @return la cantidad de criaturas con dicho nombre solicitadas en esta solicitud.
     */
    public int getCreatureQuantity(String creatureName) {
        return creaturesNamesQuantities.get(creatureName);
    }

    /**
     * Obtiene el número total de criaturas solicitadas en esta solicitud.
     *
     * @return el número total de criaturas solicitadas en esta solicitud.
     */
    public int getTotalNumberOfCreatures() {
        return totalNumberOfCreatures;
    }

    /**
     * Obtiene el número de ticks a simular solicitados en esta solicitud.
     *
     * @return el número de ticks a simular solicitados en esta solicitud.
     */
    public int getTicksToRun() {
        return ticksToRun;
    }

    /**
     * Obtiene el porcentaje de ocupación inicial solicitado en esta solicitud.
     *
     * @return el porcentaje de ocupación inicial solicitado en esta solicitud.
     */
    public double getInitialOccupancy() {
        return initialOccupancy;
    }

    /**
     * Obtiene la probabilidad que cada celda tiene de tener comida solicitada en esta solicitud.
     *
     * @return la probabilidad que cada celda tiene de tener comida solicitada en esta solicitud.
     */
    public double getFoodProbability() {
        return foodProbability;
    }

    /**
     * Obtiene el generador de números pseudo-aleatorios asociado a esta solicitud.
     *
     * @return el generador de números pseudo-aleatorios asociado a esta solicitud.
     */
    public Random getRandom() {
        return random;
    }
}
