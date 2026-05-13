package com.tt1.simserver.database.entities.converters;

import com.tt1.simserver.model.SimulationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor entre el estado de simulación {@code SimulationStatus} y la cadena que lo representa en la base de
 * datos.
 */
@Converter
public class SimulationStatusConverter implements AttributeConverter<SimulationStatus, String> {

    /**
     * Convierte en una cadena el estado de simulación {@code status}. Asume que el estado de simulación es no nulo.
     *
     * @param status el estado de simulación a convertir.
     * @return el estado de simulación convertido.
     */
    @Override
    public String convertToDatabaseColumn(SimulationStatus status) {
        return status == null ? null : status.name();
    }

    /**
     * Convierte a estado de simulación la cadena {@code dbData}. Asume que la cadena es no nula y se corresponde con
     * uno de los nombres de los estados definidos en {@link SimulationStatus}.
     *
     * @param dbData la cadena a convertir.
     * @return la cadena convertida.
     */
    @Override
    public SimulationStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : SimulationStatus.valueOf(dbData);
    }
}
