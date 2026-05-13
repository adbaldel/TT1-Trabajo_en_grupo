package com.tt1.simserver.database.transformers;

import com.tt1.simserver.database.entities.SimulationEntity;
import com.tt1.simserver.database.entities.SimulationResultEntity;
import com.tt1.simserver.model.SimulationData;

/**
 * Transformador entre datos de simulación del modelo de dominio ({@code SimulationData}) y resultados de simulación de
 * la persistencia ({@code SimulationResultEntity}).
 */
public class SimulationResultTransformer {

    /**
     * Transforma a datos de simulación del modelo de dominio el resultado de simulación de la persistencia
     * ({@code simulationResultEntity}). Asume que el resultado de simulación de la persistencia es no nulo.
     *
     * <p>Cuidado con las modificaciones de los datos de simulación devueltos. Si el resultado de simulación está en el
     * contexto de persistencia de JPA y se modifican los datos devueltos, cómo están asociados al resultado de
     * simulación estás modificaciones podrían acabar persistiéndose en la base de datos.</p>
     *
     * @param simulationResultEntity el resultado de simulación de la persistencia a transformar.
     * @return el resultado de simulación de la persistencia transformado.
     */
    public static SimulationData transform(SimulationResultEntity simulationResultEntity) {
        return simulationResultEntity.getResultData();
    }

    /**
     * Transforma a resultado de simulación de la persistencia los datos de simulación del modelo de dominio
     * ({@code simulationResult}) y los asocia a la simulación de la persistencia {@code simulationEntity}. Asume que
     * los datos de simulación del modelo de dominio no son nulos y tiene un número de ticks mayor que cero; y que la
     * simulación de la persistencia es no nula y tiene el mismo tamaño de tablero que los datos de simulación.
     *
     * @param simulationResult los datos de simulación del modelo de dominio a transformar.
     * @param simulationEntity la simulación de la persistencia a asociar con los datos transformados.
     * @return los datos de simulación del modelo de dominio transformados.
     */
    public static SimulationResultEntity transform(SimulationData simulationResult, SimulationEntity simulationEntity) {
        return new SimulationResultEntity(simulationEntity, simulationResult.getTicks(), simulationResult);
    }
}