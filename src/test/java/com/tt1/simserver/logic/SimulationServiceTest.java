package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.SimulationManagerFake;
import com.tt1.simserver.model.SimulationResult;
import com.tt1.simserver.model.SimulationStatus;
import com.tt1.simserver.model.User;
import com.tt1.simserver.model.jsonrepresentations.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
        service = new SimulationService();
        defaultUser = new User("testUser");
        managerFake = new SimulationManagerFake();
    }

    @AfterEach
    public void tearDown() {
        service = null;
        defaultUser = null;
        managerFake = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test getUser ------------------------------------------------------------------------------------------------

    @Test
    void given_newUser_when_getUser_then_userIsCreatedAndReturned() {
        // Arrange (Given)
        User newUser = new User("alice");

        // Act (When)
        User storedUser = service.getUser(newUser);

        // Assert (Then)
        assertNotNull(storedUser, "El usuario devuelto no debe ser nulo.");
        assertEquals("alice", storedUser.getUsername(), "El nombre de usuario debe coincidir.");
    }

    @Test
    void given_existingUser_when_getUser_then_returnsSameStoredInstance() {
        // Arrange (Given)
        User firstCallUser = service.getUser(new User("bob"));

        // Act (When)
        // Intentamos obtener el mismo usuario pasando una nueva instancia con el mismo nombre
        User secondCallUser = service.getUser(new User("bob"));

        // Assert (Then)
        assertSame(firstCallUser, secondCallUser, "Debe devolver exactamente la misma instancia en memoria para el mismo username.");
    }

    // --- Test existsSimulation ----------------------------------------------------------------------------------------

    @Test
    void given_userWithSimulation_when_existsSimulation_then_returnsTrue() {
        // Arrange (Given)
        User storedUser = service.getUser(defaultUser);
        managerFake.setToken(100);
        storedUser.addRequest(managerFake); // Inyectamos el fake en el usuario real

        // Act (When)
        boolean exists = service.existsSimulation(defaultUser, 100);

        // Assert (Then)
        assertTrue(exists, "Debe retornar true si el usuario tiene una simulación con ese token.");
    }

    @Test
    void given_userWithoutSimulation_when_existsSimulation_then_returnsFalse() {
        // Arrange (Given)
        service.getUser(defaultUser); // Nos aseguramos de que el usuario existe en el sistema

        // Act (When)
        boolean exists = service.existsSimulation(defaultUser, 999);

        // Assert (Then)
        assertFalse(exists, "Debe retornar false si el token no pertenece al usuario.");
    }

    // --- Test getSimulationStatus ------------------------------------------------------------------------------------

    @Test
    void given_userWithRunningSimulation_when_getSimulationStatus_then_returnsRunning() {
        // Arrange (Given)
        User storedUser = service.getUser(defaultUser);
        managerFake.setToken(10);
        managerFake.setSimulationStatus(SimulationStatus.RUNNING);
        storedUser.addRequest(managerFake);

        // Act (When)
        SimulationStatus status = service.getSimulationStatus(defaultUser, 10);

        // Assert (Then)
        assertEquals(SimulationStatus.RUNNING, status, "Debe delegar y devolver el estado asignado en el manager.");
    }

    // --- Test getSimulationResult ------------------------------------------------------------------------------------

    @Test
    void given_userWithCompletedSimulation_when_getSimulationResult_then_returnsResult() {
        // Arrange (Given)
        User storedUser = service.getUser(defaultUser);
        managerFake.setToken(20);
        SimulationResult expectedResult = new SimulationResult();
        managerFake.setSimulationResult(expectedResult);
        storedUser.addRequest(managerFake);

        // Act (When)
        SimulationResult actualResult = service.getSimulationResult(defaultUser, 20);

        // Assert (Then)
        assertEquals(expectedResult, actualResult, "Debe devolver el resultado almacenado en el manager del usuario.");
    }

    // --- Test getUserTokens ------------------------------------------------------------------------------------------

    @Test
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
        assertNotNull(tokens);
        assertEquals(2, tokens.size(), "Debe devolver exactamente los 2 tokens añadidos.");
        assertTrue(tokens.contains(1));
        assertTrue(tokens.contains(2));
    }


    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test requestSimulation ---------------------------------------------------------------------------------------

    @Test
    void integration_given_validRequest_when_requestSimulation_then_simulationStartsAndIsAddedToUser() {
        // Arrange (Given)
        Request request = new Request();
        request.setCreatureNames(List.of("gato", "perezoso", "conejo"));
        request.setInitialCreatureQuantities(List.of(2, 3, 1));

        // Act (When)
        int assignedToken = service.requestSimulation(defaultUser, request);

        // Assert (Then)
        assertTrue(assignedToken >= 0, "El token devuelto debe ser un identificador válido (>= 0).");

        assertTrue(service.existsSimulation(defaultUser, assignedToken),
                "La simulación debe haberse registrado en la cuenta del usuario.");

        SimulationStatus status = service.getSimulationStatus(defaultUser, assignedToken);
        assertTrue(status == SimulationStatus.RUNNING || status == SimulationStatus.COMPLETED,
                "El estado de la simulación debe haber pasado a RUNNING (o COMPLETED si el hilo fue muy rápido).");
    }
}