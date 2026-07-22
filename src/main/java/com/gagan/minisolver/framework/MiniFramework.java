package com.gagan.minisolver.framework;

import com.gagan.minisolver.engine.SolverEngine;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.manifest.SolverRegistration;
import com.gagan.minisolver.pipeline.ExecutionPipeline;
import com.gagan.minisolver.solver.BuildingSolver;

public class MiniFramework {

    public SolverEngine start() {

        SolverManifest manifest = new SolverManifest();

        manifest.register(
                new SolverRegistration(
                        new BuildingSolver(),
                        Command.ADD,
                        "Building",
                        1
                )
        );

        ExecutionPipeline pipeline =
                new ExecutionPipeline(manifest);

        return new SolverEngine(pipeline);
    }

}