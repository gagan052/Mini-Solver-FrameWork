package com.gagan.minisolver.engine;

import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.pipeline.ExecutionPipeline;

public class SolverEngine {

    private final ExecutionPipeline pipeline;

    public SolverEngine(ExecutionPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public SolverResult dispatch(Event event) {

        System.out.println("Engine received event...");

        return pipeline.execute(event);
    }
}