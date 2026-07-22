package com.gagan.minisolver.solver;

import java.util.ArrayList;
import java.util.List;

import com.gagan.minisolver.annotation.SolverDefinition;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class SolverRegistry {

    public List<Class<? extends Solver>> getSolverClasses() {

        List<Class<? extends Solver>> solverClasses = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages("com.gagan.minisolver")
                .enableClassInfo()
                .scan()) {

            for (ClassInfo classInfo : scanResult.getAllClasses()) {

                Class<?> clazz = classInfo.loadClass();

                if (clazz.isAnnotationPresent(SolverDefinition.class)
                        && Solver.class.isAssignableFrom(clazz)) {

                    @SuppressWarnings("unchecked")
                    Class<? extends Solver> solverClass =
                            (Class<? extends Solver>) clazz;

                    solverClasses.add(solverClass);
                }
            }
        }

        return solverClasses;
    }
}