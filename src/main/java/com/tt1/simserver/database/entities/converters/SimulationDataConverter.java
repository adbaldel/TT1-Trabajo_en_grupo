package com.tt1.simserver.database.entities.converters;

import com.tt1.simserver.model.*;
import jakarta.persistence.AttributeConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Convertidor entre los datos de simulación ({@code SimulationData}) y la cadena que los representa en la base de
 * datos.
 */
public class SimulationDataConverter implements AttributeConverter<SimulationData, String> {

    /**
     * Convierte en una cadena los datos de simulación {@code resultData}. Asume que los datos de simulación no son
     * nulos.
     *
     * @param resultData los datos de simulación a convertir.
     * @return los datos de simulación convertidos.
     */
    @Override
    public String convertToDatabaseColumn(SimulationData resultData) {
        if (resultData == null) {
            return null;
        }

        return resultData.toCsvStringUsingCreatureName();
    }

    /**
     * Convierte a datos de simulación la cadena {@code dbData}. Asume que la cadena no es nula y que sigue el formato
     * definido en {@link SimulationData#toCsvStringUsingCreatureName()}.
     *
     * @param dbData la cadena a convertir.
     * @return la cadena convertida.
     */
    @Override
    public SimulationData convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }

        // "\\r?\\n" to safely split regardless of OS line endings
        String[] dataLines = dbData.split("\\r?\\n");
        String[] stepData;
        int gridSize = Integer.parseInt(dataLines[0]);
        SimulationData result = new SimulationData(gridSize);
        Map<Integer, Map<Position, Creature>> simulationSteps = new HashMap<>();
        int tick;
        int x;
        int y;
        String color;

        for (int i = 1; i < dataLines.length; i++) {
            stepData = dataLines[i].split(",");
            tick = Integer.parseInt(stepData[0]);
            x = Integer.parseInt(stepData[1]);
            y = Integer.parseInt(stepData[2]);
            color = stepData[3];

            if (!simulationSteps.containsKey(tick)) {
                simulationSteps.put(tick, new HashMap<>());
            }

            simulationSteps.get(tick).put(
                    new Position(x, y),
                    CreatureFactory.createCreatureFromName(color, UUID.randomUUID().toString())
            );
        }

        for (Map.Entry<Integer, Map<Position, Creature>> entry : simulationSteps.entrySet()) {
            result.addStep(new SimulationStep(gridSize, entry.getValue()));
        }

        return result;
    }
}
