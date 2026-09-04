package com.gagan.minisolver.solver;

import java.util.List;

import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.model.ModelContext;
import com.gagan.minisolver.pipeline.ExecutionContext;

public class SolverContext {

    private final ExecutionContext executionContext;

    public SolverContext(
            ExecutionContext executionContext) {

        this.executionContext = executionContext;
    }

    public Event getEvent() {
        return executionContext.getEvent();
    }

    public ModelContext getModelContext() {
        return executionContext.getModelContext();
    }

    public SolverResult<?> getPreviousResult() {
        return executionContext.getPreviousResult();
    }

    public List<SolverResult<?>> getResults() {
        return executionContext.getResults();
    }
}