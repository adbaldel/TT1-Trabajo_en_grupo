package com.tt1.simserver.logic;

import com.tt1.simserver.model.User;
import com.tt1.simserver.model.jsonrepresentations.Request;
import org.junit.Test;

import java.util.Random;

/**
 * Pruebas de integración del servicio principal.
 */
public class SimulationServiceTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testDefaultConstructor() {
        new SimulationService();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRandomConstructor() {
        new SimulationService(new Random());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetUser() {
        SimulationService service = new SimulationService();
        service.getUser(new User("testUser"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testExistsSimulation() {
        SimulationService service = new SimulationService();
        service.existsSimulation(new User("testUser"), 123);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetSimulationStatus() {
        SimulationService service = new SimulationService();
        service.getSimulationStatus(new User("testUser"), 123);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetSimulationResult() {
        SimulationService service = new SimulationService();
        service.getSimulationResult(new User("testUser"), 123);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetUserTokens() {
        SimulationService service = new SimulationService();
        service.getUserTokens(new User("testUser"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRequestSimulation() {
        SimulationService service = new SimulationService();
        User user = new User("Juan");
        Request request = new Request();
        request.addNombreEntidadesItem("Conejo");
        request.addCantidadesInicialesItem(5);

        service.requestSimulation(user, request);
    }
}