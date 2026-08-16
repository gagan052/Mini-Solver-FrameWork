package com.gagan.minisolver.registrar;

import java.io.File;
import java.util.List;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.manifest.SolverRegistration;
import com.gagan.minisolver.plugin.SolverPluginLoader;
import com.gagan.minisolver.solver.Solver;

public class SolverRegistrar {

    public void registerSolvers(SolverManifest manifest) {

        File solverDirectory
                = new File("solvers");

        System.out.println(
                "Scanning solver directory: "
                + solverDirectory.getAbsolutePath()
        );

        SolverPluginLoader loader
                = new SolverPluginLoader(solverDirectory);

        List<Solver> solvers
                = loader.loadSolvers();

        for (Solver solver : solvers) {

            Class<? extends Solver> solverClass
                    = solver.getClass();

            System.out.println(
                    "Discovered solver: "
                    + solverClass.getName()
            );

            SolverDefinition definition
                    = solverClass.getAnnotation(
                            SolverDefinition.class
                    );

            if (definition == null) {

                System.out.println(
                        "Skipping solver without @SolverDefinition: "
                        + solverClass.getName()
                );

                continue;
            }

            manifest.register(
                    new SolverRegistration(
                            solver,
                            definition.command(),
                            definition.objectType(),
                            definition.priority()
                    )
            );

            System.out.println(
                    "Registered solver: "
                    + definition.objectType()
            );
        }
    }
}
