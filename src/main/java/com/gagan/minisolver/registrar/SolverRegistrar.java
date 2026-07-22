package com.gagan.minisolver.registrar;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.container.BeanFactory;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.manifest.SolverRegistration;
import com.gagan.minisolver.solver.Solver;
import com.gagan.minisolver.solver.SolverRegistry;

public class SolverRegistrar {
    private final SolverRegistry registry = new SolverRegistry();

    public void registerSolvers(SolverManifest manifest) {

        var solverClasses = registry.getSolverClasses();
        BeanFactory beanFactory = new BeanFactory();

        for (Class<? extends Solver> clazz : solverClasses) {

            Solver solver = beanFactory.getBean(clazz);
            SolverDefinition definition
                    = clazz.getAnnotation(SolverDefinition.class);

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
