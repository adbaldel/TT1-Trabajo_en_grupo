package com.tt1.simserver.database;

import com.tt1.simserver.database.entities.SimulationEntity;
import com.tt1.simserver.database.entities.SimulationResultEntity;
import com.tt1.simserver.database.entities.UserEntity;
import com.tt1.simserver.logic.LogicSimulation;
import com.tt1.simserver.logic.SimulationGrid;
import com.tt1.simserver.logic.SimulationGridInterface;
import com.tt1.simserver.logic.creatures.StaticCreature;
import com.tt1.simserver.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DBManagerTest {

    @Container
    private static final MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.0")
            .withDatabaseName("simserver_test_db")
            .withUsername("testuser")
            .withPassword("testpass");

    private DBManager dbManager;
    private EntityManagerFactory testEmf;

    // --- Arrange Before/After all tests ------------------------------------------------------------------------------

    @BeforeAll
    public static void setUpDatabase() throws Exception {
        mysqlContainer.start();
    }

    @AfterAll
    public static void tearDownDatabase() throws Exception {
        mysqlContainer.stop();
    }

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() throws Exception {
        String jdbcUrl = mysqlContainer.getJdbcUrl().replace("localhost", "127.0.0.1");

        // Configuramos propiedades JPA para conectar exclusivamente al TestContainer
        Map<String, Object> props = new HashMap<>();

        props.put("jakarta.persistence.jdbc.url", jdbcUrl);
        props.put("jakarta.persistence.jdbc.user", mysqlContainer.getUsername());
        props.put("jakarta.persistence.jdbc.password", mysqlContainer.getPassword());

        // Sobreescribir las propiedades del sistema temporalmente.
        // Así, cuando DBManager.getInstance() invoque su constructor privado y lea el persistence.xml original,
        // Hibernate interceptará estas propiedades del sistema y no fallará intentando conectarse a la BD por defecto.
        props.forEach((k, v) -> System.setProperty(k, v.toString()));

        // Creamos nuestro propio EMF de test y lo inyectamos al Singleton DBManager mediante reflexión
        testEmf = Persistence.createEntityManagerFactory("SimServerPUTest");

        dbManager = DBManager.getInstance();
        Field emfField = DBManager.class.getDeclaredField("emf");
        emfField.setAccessible(true);
        // Cerramos el EMF que DBManager haya podido crear al invocarse su constructor si no estaba null
        EntityManagerFactory oldEmf = (EntityManagerFactory) emfField.get(dbManager);
        if (oldEmf != null && oldEmf.isOpen()) {
            oldEmf.close();
        }
        emfField.set(dbManager, testEmf);

        // Limpiamos las tablas garantizando aislamiento total en cada test
        EntityManager em = testEmf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM SimulationResultEntity").executeUpdate();
        em.createQuery("DELETE FROM SimulationEntity").executeUpdate();
        em.createQuery("DELETE FROM UserEntity").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @AfterEach
    public void tearDown() {
        // Limpiar system properties para no ensuciar el contexto de otros posibles tests
        System.clearProperty("jakarta.persistence.jdbc.url");
        System.clearProperty("jakarta.persistence.jdbc.user");
        System.clearProperty("jakarta.persistence.jdbc.password");

        if (testEmf != null && testEmf.isOpen()) {
            testEmf.close();
        }
        dbManager = null;
        testEmf = null;
    }

    // --- Utilidades para Tests ---------------------------------------------------------------------------------------

    private EntityManager getEntityManager() {
        return testEmf.createEntityManager();
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test getUser(User user) -------------------------------------------------------------------------------------

    @Test
    @DisplayName("getUser(User user): Devuelve el usuario si existe.")
    public void given_existingUser_when_getUser_then_returnsUser() {
        // Arrange (Given)
        User queryUser = new User("test_user_exists");
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(new UserEntity("test_user_exists"));
        em.getTransaction().commit();
        em.close();

        // Act (When)
        User result = dbManager.getUser(queryUser);

        // Assert (Then)
        assertNotNull(result, String.format("Se esperaba que el usuario %s fuera devuelto pero fue null.",
                queryUser.username()));
        assertEquals(queryUser.username(), result.username(), String.format("Se esperaba que el usuario devuelto " +
                "tuviese el username %s.", queryUser.username()));
    }

    @Test
    @DisplayName("getUser(User user): Devuelve null si no existe.")
    public void given_nonExistingUser_when_getUser_then_returnsNull() {
        // Arrange (Given)
        User queryUser = new User("test_user_not_exists");

        // Act (When)
        User result = dbManager.getUser(queryUser);

        // Assert (Then)
        assertNull(result, String.format("Se esperaba que no se devolviera ningún usuario (null) para %s.",
                queryUser.username()));
    }

    // --- Test saveUser(User user) ------------------------------------------------------------------------------------

    @Test
    @DisplayName("saveUser(User user): Guarda el usuario.")
    public void given_newUser_when_saveUser_then_userIsSaved() {
        // Arrange (Given)
        User userToSave = new User("new_saved_user");

        // Act (When)
        dbManager.saveUser(userToSave);

        // Assert (Then)
        EntityManager em = getEntityManager();
        UserEntity savedEntity = em.find(UserEntity.class, "new_saved_user");
        em.close();

        assertNotNull(savedEntity, String.format("Se esperaba que el usuario %s existiera en la BD tras guardarlo.",
                userToSave.username()));
        assertEquals(userToSave.username(), savedEntity.getUsername(), String.format("Se esperaba que el nombre del " +
                "usuario guardado coincidiera con %s.", userToSave.username()));
    }

    // --- Test getSimulation(Simulation simulation) -------------------------------------------------------------------

    @Test
    @DisplayName("getSimulation(Simulation simulation): Devuelve la simulación si existe, con el estado apropiado y " +
            "si está completa con los datos de simulación.")
    public void given_existingSimulation_when_getSimulation_then_returnsSimulationWithAppropriateStatusAndData() {
        // Arrange (Given)
        int token = 101;
        User user = new User("sim_owner");
        Simulation querySimulation = new Simulation(token, user, 10);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        UserEntity userEntity = new UserEntity("sim_owner");
        em.persist(userEntity);
        SimulationEntity simEntity = new SimulationEntity(token, userEntity, 10, SimulationStatus.RUNNING);
        em.persist(simEntity);
        em.getTransaction().commit();
        em.close();

        // Act (When)
        Simulation result = dbManager.getSimulation(querySimulation);

        // Assert (Then)
        assertNotNull(result, String.format("Se esperaba que la simulación con token %d no fuera nula.", token));
        assertEquals(token, result.getToken(), String.format("Se esperaba el token %d.", token));
        assertEquals(user.username(), result.getUser().username(), String.format("Se esperaba el usuario %s.",
                user.username()));
        assertEquals(SimulationStatus.RUNNING, result.getStatus(), "Se esperaba que la simulación tuviera estado " +
                "RUNNING.");
        assertNotNull(result.getSimulationData(), "Se esperaba que la simulación devuelta contuviese SimulationData " +
                "inicializado.");
    }

    @Test
    @DisplayName("getSimulation(Simulation simulation): Devuelve null si no existe.")
    public void given_nonExistingSimulation_when_getSimulation_then_returnsNull() {
        // Arrange (Given)
        int nonExistingToken = 999;
        User user = new User("ghost_user");
        Simulation querySimulation = new Simulation(nonExistingToken, user, 10);

        // Act (When)
        Simulation result = dbManager.getSimulation(querySimulation);

        // Assert (Then)
        assertNull(result, String.format("Se esperaba que la consulta de la simulación con token %d devolviera null."
                , nonExistingToken));
    }

    // --- Test getUserTokens(User user) -------------------------------------------------------------------------------

    @Test
    @DisplayName("getUserTokens(User user): Devuelve una lista vacía si no existe el usuario.")
    public void given_nonExistingUser_when_getUserTokens_then_returnsEmptyList() {
        // Arrange (Given)
        User nonExistingUser = new User("non_existing");

        // Act (When)
        List<Integer> tokens = dbManager.getUserTokens(nonExistingUser);

        // Assert (Then)
        assertNotNull(tokens, "La lista devuelta no debería ser nula, sino vacía.");
        assertTrue(tokens.isEmpty(), String.format("Se esperaba una lista vacía para un usuario que no existe en BD, " +
                "pero contenía %d elementos.", tokens.size()));
    }

    @Test
    @DisplayName("getUserTokens(User user): Devuelve una lista vacía si el usuario no tiene simulaciones.")
    public void given_userWithoutSimulations_when_getUserTokens_then_returnsEmptyList() {
        // Arrange (Given)
        User userWithoutSims = new User("idle_user");
        dbManager.saveUser(userWithoutSims);

        // Act (When)
        List<Integer> tokens = dbManager.getUserTokens(userWithoutSims);

        // Assert (Then)
        assertNotNull(tokens, "La lista devuelta no debería ser nula, sino vacía.");
        assertTrue(tokens.isEmpty(), String.format("Se esperaba que la lista estuviese vacía para %s porque no tiene " +
                "simulaciones asignadas.", userWithoutSims.username()));
    }

    @Test
    @DisplayName("getUserTokens(User user): Devuelve una lista con todas las simulaciones del usuario si el usuario " +
            "tiene simulaciones.")
    public void given_userWithSimulations_when_getUserTokens_then_returnsListWithAllUserSimulations() {
        // Arrange (Given)
        User activeUser = new User("active_user");
        dbManager.saveUser(activeUser);

        dbManager.saveSimulation(new Simulation(201, activeUser, 10));
        dbManager.saveSimulation(new Simulation(202, activeUser, 10));

        // Act (When)
        List<Integer> tokens = dbManager.getUserTokens(activeUser);

        // Assert (Then)
        assertNotNull(tokens, "La lista de tokens devuelta no debe ser nula.");
        assertEquals(2, tokens.size(), String.format("Se esperaban 2 tokens, pero se obtuvieron %d.", tokens.size()));
        assertTrue(tokens.contains(201) && tokens.contains(202), "La lista no contiene todos los tokens asociados a " +
                "las simulaciones guardadas (201 y 202).");
    }

    // --- Test saveSimulation(Simulation simulation) ------------------------------------------------------------------

    @Test
    @DisplayName("saveSimulation(Simulation simulation): Guarda la simulación.")
    public void given_validSimulation_when_saveSimulation_then_simulationIsSaved() {
        // Arrange (Given)
        int token = 301;
        User user = new User("sim_creator");
        dbManager.saveUser(user);

        Simulation simulationToSave = new Simulation(token, user, 15); // Tamaño de grid 15,
        // estado PENDING por defecto

        // Act (When)
        dbManager.saveSimulation(simulationToSave);

        // Assert (Then)
        EntityManager em = getEntityManager();
        SimulationEntity savedSimEntity = em.find(SimulationEntity.class, token);
        em.close();

        assertNotNull(savedSimEntity, String.format("Se esperaba que la simulación con token %d fuese persistida.",
                token));
        assertEquals(user.username(), savedSimEntity.getUser().getUsername(), String.format("Se esperaba el dueño %s " +
                "de la simulación persistida.", user.username()));
        assertEquals(15, savedSimEntity.getGridSize(), "Se esperaba que el tamaño del tablero guardado fuera 15.");
        assertEquals(SimulationStatus.PENDING, savedSimEntity.getStatus(), "Se esperaba el estado PENDING de la " +
                "simulación al guardar.");
    }

    // --- Test updateSimulationStatus(Simulation simulation) ----------------------------------------------------------

    @Test
    @DisplayName("updateSimulationStatus(Simulation simulation): Actualiza el estado de la simulación.")
    public void given_existingSimulation_when_updateSimulationStatus_then_statusIsUpdated() {
        // Arrange (Given)
        int token = 401;
        User user = new User("updater_user");
        dbManager.saveUser(user);

        Simulation simulation = new Simulation(token, user, 10);
        dbManager.saveSimulation(simulation); // Guardada como PENDING

        // Modificamos el estado en el dominio para que actualice la base de datos
        simulation.startSimulation(); // Cambia el estado a RUNNING

        // Act (When)
        dbManager.updateSimulationStatus(simulation);

        // Assert (Then)
        EntityManager em = getEntityManager();
        SimulationEntity updatedSimEntity = em.find(SimulationEntity.class, token);
        em.close();

        assertNotNull(updatedSimEntity, "La simulación debería existir tras el update.");
        assertEquals(SimulationStatus.RUNNING, updatedSimEntity.getStatus(), "Se esperaba que el estado actualizado " +
                "en la DB fuera RUNNING.");
    }

    // --- Test getSimulationResult(Simulation simulation) -------------------------------------------------------------

    @Test
    @DisplayName("getSimulationResult(Simulation simulation): Devuelve el resultado de la simulación si existe.")
    public void given_existingSimulationResult_when_getSimulationResult_then_returnsResult() {
        // Arrange (Given)
        int token = 501;
        User user = new User("result_user");
        dbManager.saveUser(user);

        Simulation baseSimulation = new Simulation(token, user, 12);
        dbManager.saveSimulation(baseSimulation);

        SimulationData expectedData = new SimulationData(12);
        Position position = new Position(0, 0);
        Creature logicCreature =
                new StaticCreature(new Creature("sc", "static-test"), 5, position);
        Map<Position, Creature> stepMap = new HashMap<>();
        stepMap.put(position, logicCreature);
        expectedData.addStep(new SimulationStep(12, stepMap)); // Insertamos
        // data genérica vacía
        // para evitar
        // fallos del Csv
        // Converter

        Simulation completedSim = new Simulation(token, user, expectedData);
        completedSim.completeSimulation();
        dbManager.saveSimulationResult(completedSim);

        // Act (When)
        SimulationData fetchedResult = dbManager.getSimulationResult(baseSimulation);

        // Assert (Then)
        assertNotNull(fetchedResult, String.format("Se esperaba encontrar los resultados para la simulación %d.",
                token));
        assertEquals(expectedData.getTicks(), fetchedResult.getTicks(), "La cantidad de ticks del resultado no " +
                "coincide.");
        assertEquals(expectedData.getGridSize(), fetchedResult.getGridSize(), "El tamaño del grid del resultado no " +
                "coincide.");
    }

    @Test
    @DisplayName("getSimulationResult(Simulation simulation): Devuelve null si no existe.")
    public void given_nonExistingSimulationResult_when_getSimulationResult_then_returnsNull() {
        // Arrange (Given)
        int token = 601;
        User user = new User("empty_result_user");
        dbManager.saveUser(user);

        Simulation runningSimulation = new Simulation(token, user, 10);
        dbManager.saveSimulation(runningSimulation);

        // Act (When)
        SimulationData fetchedResult = dbManager.getSimulationResult(runningSimulation);

        // Assert (Then)
        assertNull(fetchedResult, "Se esperaba que devolviera null si la simulación no tiene resultados guardados en " +
                "BD.");
    }

    // --- Test saveSimulationResult(Simulation simulation) ------------------------------------------------------------

    @Test
    @DisplayName("saveSimulationResult(Simulation simulation): Guarda el resultado de la simulación y actualiza el " +
            "estado de la simulación.")
    public void given_completedSimulation_when_saveSimulationResult_then_resultIsSavedAndStatusUpdated() {
        // Arrange (Given)
        int token = 701;
        User user = new User("completer_user");
        dbManager.saveUser(user);

        SimulationStep initialStep = new SimulationStep(20);
        SimulationGridInterface simulationGrid = new SimulationGrid(initialStep, 20, new Random());
        LogicSimulation simulation = new LogicSimulation(token, user, 10, simulationGrid);
        dbManager.saveSimulation(simulation);

        SimulationData simulationData = new SimulationData(20);
        simulationData.addStep(initialStep);
        Simulation completedSimulation = new Simulation(token, user, simulationData);
        completedSimulation.completeSimulation(); // Estado local COMPLETED

        // Act (When)
        dbManager.saveSimulationResult(completedSimulation);

        // Assert (Then)
        EntityManager em = getEntityManager();
        SimulationEntity simEntity = em.find(SimulationEntity.class, token);
        SimulationResultEntity resultEntity = em.find(SimulationResultEntity.class, token);
        em.close();

        assertNotNull(simEntity, "La entidad simulación original debe persistir.");
        assertEquals(SimulationStatus.COMPLETED, simEntity.getStatus(), "El estado de la simulación debería haberse " +
                "actualizado a COMPLETED.");

        assertNotNull(resultEntity, "La entidad del resultado de la simulación debería haber sido persistida.");
        assertEquals(1, resultEntity.getTicks(), "Debe reflejar 1 tick según el SimulationData guardado.");
        assertNotNull(resultEntity.getResultData(), "El Clob convertido de SimulationData no debería ser null en el " +
                "resultado.");
    }
}