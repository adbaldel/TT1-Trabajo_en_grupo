package com.tt1.simserver.mocks;

import com.tt1.simserver.logic.SimulationEngineInterface;
import com.tt1.simserver.model.SimulationResult;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimulationEngineFake implements SimulationEngineInterface {
    private boolean done;
    private SimulationResult result;
    private boolean runCalled;
    // Herramienta de concurrencia para tests: permite al test esperar a que el pool de hilos
    // termine de llamar al método run() asíncrono.
    private final CountDownLatch runLatch;


    public SimulationEngineFake() {
        this.done = false;
        this.result = null;
        this.runCalled = false;
        this.runLatch = new CountDownLatch(1);
    }


    // --- Setters de control para los tests ---------------------------------------------------------------------------

    public boolean isRunCalled() {
        return runCalled;
    }

    public boolean awaitRun(long timeout, TimeUnit unit) throws InterruptedException {
        return runLatch.await(timeout, unit);
    }

    @Override
    public void run() {
        runCalled = true;
        // Liberamos el candado para avisar al test de que ya se ejecutó el hilo
        runLatch.countDown();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    // --- Implementación de la interfaz -------------------------------------------------------------------------------

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public SimulationResult getResult() {
        return result;
    }

    public void setResult(SimulationResult result) {
        this.result = result;
    }
}