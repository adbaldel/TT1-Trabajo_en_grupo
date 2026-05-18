package com.tt1.simserver.logic;

import com.tt1.simserver.mocks.database.DBManagerMock;
import com.tt1.simserver.model.Position;
import com.tt1.simserver.model.Simulation;
import com.tt1.simserver.model.SimulationRequest;
import com.tt1.simserver.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationRequestManagerTest {
    Map<String, Integer> creaturesNamesQuantities;
    private User user;
    private int totalNumberOfCreatures;
    private int ticksToRun;
    private double initialOccupancy;
    private double foodProbability;
    private Random random;
    private SimulationRequest simulationRequest;
    private DBManagerMock dbMock;
    private SimulationRequestManager simulationRequestManager;
    private LogicSimulation simulation;

    // --- Arrange Before/After each test ------------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        user = new User("testUser");
        totalNumberOfCreatures = 1;
        creaturesNamesQuantities = new HashMap<>();
        creaturesNamesQuantities.put("mobile-test", 1);
        ticksToRun = 1000;
        initialOccupancy = 0.04;
        foodProbability = 0.2;
        random = new Random();
        simulationRequest = new SimulationRequest(user, creaturesNamesQuantities, totalNumberOfCreatures, ticksToRun,
                initialOccupancy, foodProbability, random);
        dbMock = new DBManagerMock();
    }

    @AfterEach
    public void tearDown() {
        user = null;
        totalNumberOfCreatures = 0;
        creaturesNamesQuantities = null;
        ticksToRun = 0;
        initialOccupancy = 0;
        foodProbability = 0;
        random = null;
        simulationRequest = null;
        dbMock = null;
        simulationRequestManager = null;
        simulation = null;
    }

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS UNITARIOS
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test createSimulation(SimulationRequest simulationRequest) --------------------------------------------------

    // (La lógica descrita en el plan de pruebas para este componente es fundamentalmente de naturaleza de Integración
    // y estadística de generadores, ubicada en la sección de Integración)

    // -----------------------------------------------------------------------------------------------------------------
    //     TESTS DE INTEGRACIÓN
    // -----------------------------------------------------------------------------------------------------------------

    // --- Test createSimulation(SimulationRequest simulationRequest) --------------------------------------------------

    @Test
    @DisplayName("createSimulation: genera siempre un token único para las simulaciones")
    public void given_multipleRequests_when_createSimulation_then_generatesUniqueTokens() {
        // Arrange (Given)
        int iterations = 1000;
        Set<Integer> tokensGenerated = new HashSet<>();
        simulationRequestManager = new SimulationRequestManager(random, dbMock);

        // Act (When)
        for (int i = 0; i < iterations; i++) {
            Simulation sim = simulationRequestManager.createSimulation(simulationRequest);
            tokensGenerated.add(sim.getToken());
        }

        // Assert (Then)
        assertEquals(iterations, tokensGenerated.size(),
                String.format("Se esperaban %d tokens únicos, pero hubo colisiones resultando en %d tokens.",
                        iterations, tokensGenerated.size()));
    }

    @Test
    @DisplayName("createSimulation: asigna posiciones iniciales aleatorias y equiprobables (estadístico)")
    public void given_simulationRequests_when_createSimulation_then_creaturesPositionsAreEquiprobable() {
        // Ocupación 0.04 para 1 criatura -> grid size será 5 (25 celdas). P(celda) = 1/25 = 0.04
        int n = 1000;
        double p = 0.04;
        // Statistical boundaries for 99.99% confidence interval
        // 1. Calculate Mean (μ)
        double mean = n * p;
        // 2. Calculate Standard Deviation (σ) = sqrt(n * p * (1-p))
        double sigma = Math.sqrt(n * p * (1 - p));
        // 3. Define 99.99% confidence interval (3.891 standard deviations)
        double margin = 3.891 * sigma;
        double lowerBound = mean - margin;
        double upperBound = mean + margin;

        int occurrencesAtTopLeft = 0;
        int occurrencesAtCenter = 0;

        // Arrange (Given)
        Position topLeft = new Position(0, 0);
        Position center = new Position(2, 2);
        simulationRequestManager = new SimulationRequestManager(random, dbMock);

        // Act (When)
        for (int i = 0; i < n; i++) {
            simulation = simulationRequestManager.createSimulation(simulationRequest);
            // Tomamos el paso 0
            if (simulation.getSimulationData().getSimulationStepAt(0).getCreatureAt(topLeft) != null) {
                occurrencesAtTopLeft++;
            }
            if (simulation.getSimulationData().getSimulationStepAt(0).getCreatureAt(center) != null) {
                occurrencesAtCenter++;
            }
        }

        // Assert (Then)
        assertTrue((occurrencesAtTopLeft >= lowerBound && occurrencesAtTopLeft <= upperBound)
                        && (occurrencesAtCenter >= lowerBound && occurrencesAtCenter <= upperBound),
                String.format("Las posiciones iniciales no son equiprobables. Esperado valor en [%.2f, %.2f] para la " +
                                "celda (0,0), Actual: %d (topLeft) %d (center).",
                        lowerBound, upperBound, occurrencesAtTopLeft, occurrencesAtCenter));
    }
}