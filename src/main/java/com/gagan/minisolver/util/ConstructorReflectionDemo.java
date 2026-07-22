package com.gagan.minisolver.util;

import java.lang.reflect.Constructor;

import com.gagan.minisolver.solver.BuildingSolver;

public class ConstructorReflectionDemo {

    public static void inspect() {

        Class<?> clazz = BuildingSolver.class;

        Constructor<?>[] constructors
                = clazz.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {

            System.out.println(constructor);

            Class<?>[] parameterTypes
                    = constructor.getParameterTypes();

            for (Class<?> parameter : parameterTypes) {

                System.out.println("Dependency: "
                        + parameter.getName());

            }

        }

    }

}
