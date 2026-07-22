package com.gagan.minisolver.util;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.solver.BuildingSolver;

public class ReflectionDemo {

    public static void inspect() {

        Class<?> clazz = BuildingSolver.class;

        System.out.println("Class Name : " + clazz.getName());

        System.out.println(
                "Has SolverDefinition : "
                        + clazz.isAnnotationPresent(SolverDefinition.class)
        );

        SolverDefinition annotation =
                clazz.getAnnotation(SolverDefinition.class);

        System.out.println(
                "Object Type : "
                        + annotation.objectType()
        );
    }
}