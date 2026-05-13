package com.tt1.simserver.database.transformers;

import com.tt1.simserver.database.entities.SimulationEntity;
import com.tt1.simserver.database.entities.UserEntity;
import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationData;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.SimulationStep;

/**
 * Transformador entre simulaciones del modelo de dominio ({@code Simulation}) y simulaciones entidad de la persistencia
 * ({@code SimulationEntity}).
 */
public class SimulationTransformer {

    /**
     * Constructor privado para evitar que se construyan objetos instancia de este transformador.
     */
    private SimulationTransformer() {
        // No hace nada
    }

    /**
     * Transforma a simulación del modelo de dominio la simulación de la persistencia ({@code simulationEntity}). Asume
     * que la simulación de la persistencia es no nula, tiene un token, usuario, estado y tamaño de tablero; y que el
     * estado de la simulación no es acabada. La simulación transformada tiene un estado inicial de simulación vacío en
     * un tablero del tamaño definido en la simulación de la persistencia.
     *
     * @param simulationEntity la simulación de la persistencia a transformar.
     * @return la simulación del modelo domino transformada.
     */
    public static Simulation transform(SimulationEntity simulationEntity) {
        Simulation simulation = new Simulation(
                simulationEntity.getToken(),
                UserTransformer.transform(simulationEntity.getUser()),
                simulationEntity.getGridSize() // Las simulaciones de la base de datos
                // transformadas a simulaciones del dominio no tienen estado inicial consistente (es vacío porque
                // este no se guarda en la base de datos)
        );

        if (simulationEntity.getStatus() ==  SimulationStatus.RUNNING) {
            simulation.startSimulation();
        }

        return simulation;
    }

    /**
     * Transforma a simulación del modelo de dominio la simulación de la persistencia ({@code simulationEntity}) y le
     * asigna como datos de simulación los datos {@code simulationResult}. Asume que la simulación de la persistencia es
     * no nula, tiene un token, usuario, estado y tamaño del tablero; que el estado de la simulación es acabada; y que
     * los datos de simulación son no nulos y tienen el mismo tamaño de tablero que el de la simulación de la
     * persistencia.
     *
     * @param simulationEntity la simulación de la persistencia a transformar.
     * @param simulationResult los datos de simulación acabada a asignar a la simulación transformada.
     * @return la simulación de la persistencia transformada.
     */
    public static Simulation transform(SimulationEntity simulationEntity, SimulationData simulationResult) {
        return new Simulation(
                simulationEntity.getToken(),
                UserTransformer.transform(simulationEntity.getUser()),
                simulationResult
        );
    }

    /**
     * Transforma a simulación de la persistencia la simulación del modelo de dominio ({@code simulation}). Asume que la
     * simulación del modelo de dominio es no nula, tiene token, usuario, estado y tamaño de tablero. El usuario de la
     * simulación devuelta no tiene por qué estar en el contexto de persistencia de JPA.
     *
     * @param simulation la simulación del modelo de dominio a transformar.
     * @return la simulación del modelo de dominio transformada.
     */
    public static SimulationEntity transform(Simulation simulation) {
        return new SimulationEntity(
                simulation.getToken(),
                UserTransformer.transform(simulation.getUser()),
                simulation.getGridSize(),
                simulation.getStatus()
        );
    }

    /**
     * Transforma a simulación de la persistencia la simulación del modelo de dominio ({@code simulation}) y asignándole
     * el usuario de la persistencia {@code userEntity}. Asume que la simulación del modelo de dominio es no nula, tiene
     * token, usuario, estado y tamaño de tablero; y que el usuario de la persistencia representa el mismo usuario que
     * el usuario almacenado en la simulación del modelo de dominio.
     *
     * @param simulation la simulación del modelo de dominio a transformar.
     * @param userEntity el usuario de la persistencia a asignar.
     * @return la simulación del modelo de dominio transformada.
     */
    public static SimulationEntity transform(Simulation simulation, UserEntity userEntity) {
        return new SimulationEntity(
                simulation.getToken(),
                userEntity,
                simulation.getGridSize(),
                simulation.getStatus()
        );
    }
}
