package com.gagan.minisolver.registrar;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.manifest.SolverRegistration;
import com.gagan.minisolver.solver.Solver;
import com.gagan.minisolver.solver.SolverRegistry;
import com.gagan.minisolver.util.ObjectFactory;

public class SolverRegistrar {

    private final SolverRegistry registry = new SolverRegistry();

    public void registerSolvers(SolverManifest manifest) {

        var solverClasses = registry.getSolverClasses();

        for (Class<? extends Solver> clazz : solverClasses) {

            Solver solver = ObjectFactory.create(clazz);

            SolverDefinition definition =
                    clazz.getAnnotation(SolverDefinition.class);

            manifest.register(
                    new SolverRegistration(
                            solver,
                            definition.command(),
                            definition.objectType(),
                            definition.priority()
                    )
            );
        }
    }
}