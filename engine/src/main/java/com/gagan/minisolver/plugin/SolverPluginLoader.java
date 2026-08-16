package com.gagan.minisolver.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.gagan.minisolver.solver.Solver;

public class SolverPluginLoader {

    private final File solverDirectory;

    public SolverPluginLoader(File solverDirectory) {
        this.solverDirectory = solverDirectory;
    }

    public List<Solver> loadSolvers() {

        List<Solver> solvers = new ArrayList<>();

        File[] jars = solverDirectory.listFiles(
                file -> file.isFile()
                        && file.getName().endsWith(".jar")
        );

        if (jars == null) {
            System.out.println("No solver JARs found.");
            return solvers;
        }

        for (File jar : jars) {

            try {

                System.out.println(
                        "Loading solver plugin: " + jar.getName()
                );

                URLClassLoader classLoader =
                        new URLClassLoader(
                                new URL[]{jar.toURI().toURL()},
                                getClass().getClassLoader()
                        );

                ServiceLoader<Solver> serviceLoader =
                        ServiceLoader.load(
                                Solver.class,
                                classLoader
                        );

                for (Solver solver : serviceLoader) {

                    System.out.println(
                            "Loaded solver: "
                                    + solver.getClass().getName()
                    );

                    solvers.add(solver);
                }

            } catch (Exception e) {

                throw new RuntimeException(
                        "Failed to load solver plugin: "
                                + jar.getName(),
                        e
                );
            }
        }

        return solvers;
    }
}