package com.gagan.minisolver.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.gagan.minisolver.solver.Solver;

public class SolverPluginLoader {

    public List<Solver> loadPlugins(String directoryPath) {

        List<Solver> solvers = new ArrayList<>();

        File directory = new File(directoryPath);

        if (!directory.exists()) {
            throw new RuntimeException(
                    "Solver directory does not exist: " + directory.getAbsolutePath()
            );
        }

        File[] jars = directory.listFiles(
                file -> file.getName().endsWith(".jar")
        );

        if (jars == null) {
            return solvers;
        }

        for (File jar : jars) {

            try {

                System.out.println(
                        "Loading solver JAR: " + jar.getName()
                );

                URL[] urls = {
                        jar.toURI().toURL()
                };

                URLClassLoader classLoader =
                        new URLClassLoader(
                                urls,
                                SolverPlugin.class.getClassLoader()
                        );

                ServiceLoader<SolverPlugin> loader =
                        ServiceLoader.load(
                                SolverPlugin.class,
                                classLoader
                        );

                for (SolverPlugin plugin : loader) {

                    System.out.println(
                            "Loaded plugin: "
                                    + plugin.getClass().getName()
                    );

                    solvers.addAll(plugin.getSolvers());
                }

            } catch (Exception e) {

                throw new RuntimeException(
                        "Failed to load solver JAR: " + jar.getName(),
                        e
                );
            }
        }

        return solvers;
    }
}