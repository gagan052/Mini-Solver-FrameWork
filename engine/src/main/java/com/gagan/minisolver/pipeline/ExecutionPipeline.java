package com.gagan.minisolver.pipeline;

import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.manifest.SolverManifest;

public class ExecutionPipeline {

    private final SolverManifest manifest;

    public ExecutionPipeline(SolverManifest manifest) {
        this.manifest = manifest;
    }

    public SolverResult execute(Event event) {

        var registrations = manifest.findMatching(event);

        if (registrations.isEmpty()) {
            return new SolverResult(
                    false,
                    "No solver found for event: " + event
            );
        }

        for (var registration : registrations) {
            registration.getSolver().solve(event);
        }

        return new SolverResult(
                true,
                "Event processed successfully"
        );
    }
}