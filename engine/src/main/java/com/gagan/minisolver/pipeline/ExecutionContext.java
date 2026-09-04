package com.gagan.minisolver.pipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.model.ModelContext;

public class ExecutionContext {

    private final Event event;
    private final ModelContext modelContext;

    private SolverResult<?> previousResult;

    private String currentSolver;

    private ExecutionStatus status;

    private final List<SolverResult<?>> results =
            new ArrayList<>();

    public ExecutionContext(
            Event event,
            ModelContext modelContext) {

        this.event = event;
        this.modelContext = modelContext;
        this.status = ExecutionStatus.RUNNING;
    }

    public Event getEvent() {
        return event;
    }

    public ModelContext getModelContext() {
        return modelContext;
    }

    public SolverResult<?> getPreviousResult() {
        return previousResult;
    }

    public void setPreviousResult(
            SolverResult<?> previousResult) {

        this.previousResult = previousResult;
    }

    public void addResult(
            SolverResult<?> result) {

        results.add(result);
    }

    public List<SolverResult<?>> getResults() {

        return Collections.unmodifiableList(results);
    }

    public String getCurrentSolver() {
        return currentSolver;
    }

    public void setCurrentSolver(
            String currentSolver) {

        this.currentSolver = currentSolver;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(
            ExecutionStatus status) {

        this.status = status;
    }
}