package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.SimulationManagerFake;
import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.api.jsonobjects.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationServiceTest {

    private SimulationService service;
    private User defaultUser;
    private SimulationManagerFake managerFake;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        service = SimulationService.getInstance();
        defaultUser = new User("testUser");
        managerFake = new SimulationManagerFake();
    }

    @AfterEach
    public void tearDown() {
        service.reset();
        service = null;
        defaultUser = null;
        managerFake = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test getUser ------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Obtener usuario: Crea en memoria y devuelve el usuario si no existía antes")
    void given_newUser_when_getUser_then_userIsCreatedAndReturned() {
        // Arrange (Given)
        User newUser = new User("alice");

        // Act (When)
        User storedUser = service.getUser(newUser);

        // Assert (Then)
        assertNotNull(storedUser, "El servicio debe responsabilizarse de construir un usuario persistente válido");
        assertEquals("alice", storedUser.getUsername(), "El nombre del usuario recién creado no debe modificarse");
    }

    @Test
    @DisplayName("Obtener usuario: Funciona como caché y retorna la misma instancia en memoria para repetidas llamadas")
    void given_existingUser_when_getUser_then_returnsSameStoredInstance() {
        // Arrange (Given)
        User firstCallUser = service.getUser(new User("bob"));

        // Act (When)
        // Intentamos obtener el mismo usuario pasando una nueva instancia con el mismo nombre
        User secondCallUser = service.getUser(new User("bob"));

        // Assert (Then)
        assertSame(firstCallUser, secondCallUser, "Para optimizar la persistencia, el servicio debe recuperar la misma referencia en memoria del usuario ya guardado");
    }

    // --- Test existsSimulation ----------------------------------------------------------------------------------------

    @Test
    @DisplayName("Comprobar simulación: Acredita como verdadera si el token pertenece a las peticiones del usuario")
    void given_userWithSimulation_when_existsSimulation_then_returnsTrue() {
        // Arrange (Given)
        User storedUser = service.getUser(defaultUser);
        managerFake.setToken(100);
        storedUser.addRequest(managerFake); // Inyectamos el fake en el usuario real

        // Act (When)
        boolean exists = service.existsSimulation(defaultUser, 100);

        // Assert (Then)
        assertTrue(exists, "El servicio tiene que certificar positivamente el acceso de un usuario a un token de su propiedad");
    }

    @Test
    @DisplayName("Comprobar simulación: Rechaza el acceso devolviendo falso si el token no es suyo")
    void given_userWithoutSimulation_when_existsSimulation_then_returnsFalse() {
        // Arrange (Given)
        service.getUser(defaultUser); // Nos aseguramos de que el usuario existe en el sistema

        // Act (When)
        boolean exists = service.existsSimulation(defaultUser, 999);

        // Assert (Then)
        assertFalse(exists, "El servicio debe denegar como inexistente cualquier simulación cuyo token no obre en poder del usuario solicitante");
    }

    // --- Test getSimulationStatus ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Estado de simulación: Delega la pregunta y retorna el estado extraído desde el gestor")
    void given_userWithRunningSimulation_when_getSimulationStatus_then_returnsRunning() {
        // Arrange (Given)
        User storedUser = service.getUser(defaultUser);
        managerFake.setToken(10);
        managerFake.setSimulationStatus(SimulationStatus.RUNNING);
        storedUser.addRequest(managerFake);

        // Act (When)
        SimulationStatus status = service.getSimulationStatus(defaultUser, 10);

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, status, "El servicio actúa como intermediario y debe retransmitir fielmente el estado que marque el gestor subyacente");
    }

    // --- Test getSimulationResult ------------------------------------------------------------------------------------

    @Test
    @DisplayName("Resultado de simulación: Recupera el historial guardado por el gestor en nombre del usuario")
    void given_userWithCompletedSimulation_when_getSimulationResult_then_returnsResult() {
        // Arrange (Given)
        User storedUser = service.getUser(defaultUser);
        managerFake.setToken(20);
        SimulationResult expectedResult = new SimulationResult(1);
        managerFake.setSimulationResult(expectedResult);
        storedUser.addRequest(managerFake);

        // Act (When)
        SimulationResult actualResult = service.getSimulationResult(defaultUser, 20);

        // Assert (Then)
        assertEquals(expectedResult, actualResult, "El servicio debe extraer y devolver a la vista de red el resultado intacto proporcionado por el gestor de la simulación");
    }

    // --- Test getUserTokens ------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Listar tokens: Recupera la colección completa de identificadores asociados a un usuario")
    void given_userWithMultipleSimulations_when_getUserTokens_then_returnsAllTokens() {
        // Arrange (Given)
        User storedUser = service.getUser(defaultUser);

        managerFake.setToken(1);
        SimulationManagerFake managerFake2 = new SimulationManagerFake();
        managerFake2.setToken(2);

        storedUser.addRequest(managerFake);
        storedUser.addRequest(managerFake2);

        // Act (When)
        Collection<Integer> tokens = service.getUserTokens(defaultUser);

        // Assert (Then)
        assertNotNull(tokens, "La colección consultada nunca puede venir nula, incluso si estuviera vacía");
        assertEquals(2, tokens.size(), "El listado debe agrupar todas las peticiones registradas, sin omitir ninguna");
        assertTrue(tokens.contains(1), "El conjunto resultante debe arrastrar el primer token que solicitó el usuario");
        assertTrue(tokens.contains(2), "El conjunto resultante debe arrastrar el segundo token que solicitó el usuario");
    }


    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test requestSimulation ---------------------------------------------------------------------------------------

    @Test
    @DisplayName("Integración Solicitar: Orquesta la creación de tablero, vincula al usuario y arranca cálculos")
    void integration_given_validRequest_when_requestSimulation_then_simulationStartsAndIsAddedToUser() {
        // Arrange (Given)
        Request request = new Request();
        request.setCreatureNames(List.of("gato", "perezoso", "conejo"));
        request.setInitialCreatureQuantities(List.of(2, 3, 1));

        // Act (When)
        int assignedToken = service.requestSimulation(defaultUser, request);

        // Assert (Then)
        assertTrue(assignedToken >= 0, "Al cursar una solicitud, el servidor emite siempre un token válido por encima del cero");

        assertTrue(service.existsSimulation(defaultUser, assignedToken),
                "El flujo de servicio amarra lógicamente la simulación recién creada al portafolio del usuario");

        SimulationStatus status = service.getSimulationStatus(defaultUser, assignedToken);
        assertTrue(status == SimulationStatus.RUNNING || status == SimulationStatus.COMPLETED,
                "El servicio arranca asíncronamente el proceso, por lo que este ya estará calculando turnos o ya habrá terminado de forma veloz");
    }
}