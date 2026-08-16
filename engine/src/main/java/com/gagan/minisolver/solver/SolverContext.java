package com.gagan.minisolver.solver;

import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.model.ModelContext;

public class SolverContext {

    private final Event event;
    private final ModelContext modelContext;
    private final SolverResult previousResult;

    public SolverContext(
            Event event,
            ModelContext modelContext,
            SolverResult<?> previousResult) {

        this.event = event;
        this.modelContext = modelContext;
        this.previousResult = previousResult;
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
}