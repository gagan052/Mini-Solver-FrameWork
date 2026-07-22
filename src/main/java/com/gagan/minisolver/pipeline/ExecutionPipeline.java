package com.gagan.minisolver.pipeline;

import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.manifest.SolverManifest;

public class ExecutionPipeline {

    private final SolverManifest manifest;

    public ExecutionPipeline(SolverManifest manifest) {
        this.manifest = manifest;
    }

    public void process(Event event) {

        var registrations = manifest.findMatching(event);

        for (var registration : registrations) {
            registration.getSolver().solve(event);
        }
    }
}