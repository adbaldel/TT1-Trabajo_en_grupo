package com.tt1.simserver.database.entities;

import com.tt1.simserver.database.entities.converters.SimulationDataConverter;
import com.tt1.simserver.model.SimulationData;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Entidad de JPA que representa un resultado de simulación en la persistencia.
 */
@Entity
@Table(name = "simulation_result")
public class SimulationResultEntity {

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "token", nullable = false)
    private SimulationEntity simulation;

    @Column(name = "ticks", nullable = false)
    private int ticks;

    @Lob
    @Column(name = "result_csv", nullable = false, columnDefinition = "LONGTEXT")
    @Convert(converter = SimulationDataConverter.class)
    private SimulationData resultData;

    /**
     * Construye una simulación vacía. Todos los campos por defecto (null, 0 o false).
     */
    public SimulationResultEntity() {
    }

    /**
     * Construye un resultado de simulación con los ticks y los datos pasados como parámetros y asociado a la simulación
     * {@code simulation}. Asume que la simulación es no nula, que el número de ticks es mayor que cero y que los datos
     * de simulación son no nulos.
     *
     * @param simulation la simulación a la que asociar el resultado.
     * @param ticks      el número ticks del resultado.
     * @param resultData los datos del resultado.
     */
    public SimulationResultEntity(SimulationEntity simulation, int ticks, SimulationData resultData) {
        this.simulation = simulation;
        this.ticks = ticks;
        this.resultData = resultData;
    }

    /**
     * Obtiene la simulación asociada a este resultado de simulación.
     *
     * @return la simulación asociada a este resultado de simulación.
     */
    public SimulationEntity getSimulation() {
        return simulation;
    }

    /**
     * Cambia la simulación asociada a este resultado de simulación por {@code simulation}. Asume que la simulación es
     * no nula.
     *
     * @param simulacion la nueva simulación asociada.
     */
    public void setSimulation(SimulationEntity simulacion) {
        this.simulation = simulacion;
    }

    /**
     * Obtiene el número de ticks en este resultado de simulación.
     *
     * @return el número de ticks en este resultado de simulación.
     */
    public int getTicks() {
        return ticks;
    }

    /**
     * Cambia el número de ticks de este resultado de simulación por {@code ticks}. Asume que el número de ticks es
     * mayor que cero.
     *
     * @param ticks el nuevo número de ticks.
     */
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    /**
     * Obtiene los datos de este resultado de simulación.
     *
     * @return los datos de este resultado de simulación.
     */
    public SimulationData getResultData() {
        return resultData;
    }

    /**
     * Cambia los datos de este resultado de simulación por {@code resultData}. Asume que los datos son no nulos.
     *
     * @param resultData los nuevos datos de resultado de simulación.
     */
    public void setResultData(SimulationData resultData) {
        this.resultData = resultData;
    }

    /**
     * Comprueba si esta entidad de resultado de simulación es igual al objeto {@code o}. Devuelve cierto si el objeto
     * es una entidad de resultado de simulación de la misma entidad de simulación.
     *
     * @param o el objeto a comparar.
     * @return cierto si esta entidad de resultado de simulación es igual al objeto, falso en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimulationResultEntity that = (SimulationResultEntity) o;
        return simulation.equals(that.simulation);
    }

    /**
     * Obtiene el código hash de esta entidad de resultado de simulación.
     *
     * @return el código hash de esta entidad de resultado de simulación.
     */
    @Override
    public int hashCode() {
        return Objects.hash(simulation);
    }
}