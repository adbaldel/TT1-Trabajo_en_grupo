package com.tt1.simserver.database;

import com.tt1.simserver.config.ConfigManager;
import com.tt1.simserver.database.entities.SimulationEntity;
import com.tt1.simserver.database.entities.SimulationResultEntity;
import com.tt1.simserver.database.entities.UserEntity;
import com.tt1.simserver.database.transformers.SimulationResultTransformer;
import com.tt1.simserver.database.transformers.SimulationTransformer;
import com.tt1.simserver.database.transformers.UserTransformer;
import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationData;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de la capa de persistencia (Fachada/DAO). Centraliza y gestiona todas las transacciones de JPA hacia
 * la base de datos MySQL.
 */
public class DBManager implements DBManagerInterface {
    private static DBManager instance;
    private final EntityManagerFactory emf;

    /**
     * Construye el gestor de base de datos inicializando la factoría de entidades del proveedor JPA. Asume que la
     * unidad de persistencia con el nombre especificado en el archivo de propiedades (o "SimServerPU" en su defecto)
     * está correctamente configurada en persistence.xml y que la base de datos es accesible.
     */
    private DBManager() {
        // DOCKER
        Map<String, Object> props = new HashMap<>();

        props.put("jakarta.persistence.jdbc.url", System.getenv("DB_URL"));
        props.put("jakarta.persistence.jdbc.user", System.getenv("DB_USER"));
        props.put("jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));

        // FIN DOCKER
        String persistenceUnitName = ConfigManager.getInstance().getString("database.persistence_unit.name",
                "SimServerPU");
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName, props);
    }

    /**
     * Obtiene la instancia Singleton del gestor de base de datos.
     *
     * @return la instancia única de {@code DBManager}.
     */
    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    @Override
    public User getUser(User user) {
        User realUser = null;
        UserEntity userEntity;

        EntityManager em = emf.createEntityManager();
        userEntity = em.find(UserEntity.class, user.username());
        em.close();

        if (userEntity != null) {
            realUser = UserTransformer.transform(userEntity);
        }

        return realUser;
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = UserTransformer.transform(user);

        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        // Transacción
        t.begin();
        em.persist(userEntity);
        t.commit();
        // Fin transacción
        em.close();
    }

    @Override
    public Simulation getSimulation(Simulation simulation) {
        Simulation realSimulation = null;
        SimulationEntity simulationEntity;

        EntityManager em = emf.createEntityManager();
        simulationEntity = em.find(SimulationEntity.class, simulation.getToken());
        em.close();

        if (simulationEntity != null) {
            if (simulationEntity.getStatus() == SimulationStatus.COMPLETED) {
                realSimulation = SimulationTransformer.transform(simulationEntity, getSimulationResult(simulation));
            } else {
                realSimulation = SimulationTransformer.transform(simulationEntity);
            }
        }

        return realSimulation;
    }

    @Override
    public List<Integer> getUserTokens(User user) {
        List<Integer> userTokens;

        String jpql = "SELECT s.token FROM SimulationEntity s WHERE s.user.username = :uname";
        EntityManager em = emf.createEntityManager();
        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
        query.setParameter("uname", user.username());
        userTokens = query.getResultList();
        em.close();

        return userTokens;
    }

    @Override
    public void saveSimulation(Simulation simulation) {
        UserEntity userEntity;
        SimulationEntity simulationEntity;

        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        // Transacción
        t.begin();
        // Cargar el usuario asociado a la simulación de la BD
        userEntity = em.find(UserEntity.class, simulation.getUser().username());
        // Crear la simulación a guardar con el usuario de la BD
        simulationEntity = SimulationTransformer.transform(simulation, userEntity);
        // Guardar la simulación en la BD
        em.persist(simulationEntity);
        t.commit();
        // Fin transacción
        em.close();
    }

    @Override
    public void updateSimulationStatus(Simulation simulation) {
        SimulationEntity simulationEntity;

        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        // Transacción
        t.begin();
        // Cargar simulación de la BD
        simulationEntity = em.find(SimulationEntity.class, simulation.getToken());
        // Actualizar estado (de momento solo para cambiar a running)
        simulationEntity.setStatus(simulation.getStatus());
        t.commit();
        // Fin transacción
        em.close();
    }

    @Override
    public SimulationData getSimulationResult(Simulation simulation) {
        SimulationData simulationResult = null;
        SimulationResultEntity simulationResultEntity;

        EntityManager em = emf.createEntityManager();
        simulationResultEntity = em.find(SimulationResultEntity.class, simulation.getToken());
        em.close();

        if (simulationResultEntity != null) {
            simulationResult = SimulationResultTransformer.transform(simulationResultEntity);
        }

        return simulationResult;
    }

    @Override
    public void saveSimulationResult(Simulation simulation) {
        SimulationEntity simulationEntity;
        SimulationResultEntity simulationResultEntity;

        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        // Transacción
        t.begin();
        // Cargar simulación de la BD
        simulationEntity = em.find(SimulationEntity.class, simulation.getToken());
        // Actualizar estado a acabada
        simulationEntity.setStatus(simulation.getStatus());
        // Guardar resultado
        simulationResultEntity = SimulationResultTransformer.transform(simulation.getSimulationData(),
                simulationEntity);
        em.persist(simulationResultEntity);
        t.commit();
        // Fin transacción
        em.close();
    }

    /**
     * {@inheritDoc}
     *
     * <p>Notas sobre implementación: Cierra de forma limpia el pool de conexiones al apagar el servidor. La factoría
     * de entidades queda cerrada, liberando recursos y conexiones físicas hacia MySQL.</p>
     */
    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}