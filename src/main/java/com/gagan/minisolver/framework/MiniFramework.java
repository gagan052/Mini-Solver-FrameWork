package com.gagan.minisolver.framework;

import com.gagan.minisolver.engine.SolverEngine;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.pipeline.ExecutionPipeline;
import com.gagan.minisolver.registrar.SolverRegistrar;

public class MiniFramework {

    public SolverEngine start() {

        SolverManifest manifest = new SolverManifest();

        SolverRegistrar registrar = new SolverRegistrar();

        registrar.registerSolvers(manifest);

        ExecutionPipeline pipeline
                = new ExecutionPipeline(manifest);

        return new SolverEngine(pipeline);
    }

}
