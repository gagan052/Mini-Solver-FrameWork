package com.gagan.minisolver.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {

    private final Map<Class<?>, Object> singletonBeans = new HashMap<>();

    public <T> T getBean(Class<T> clazz) {

        // Return cached singleton if it already exists
        if (singletonBeans.containsKey(clazz)) {
            return clazz.cast(singletonBeans.get(clazz));
        }

        try {

            // For now, assume every class has only one constructor
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];

            // Discover constructor dependencies
            Class<?>[] dependencyTypes = constructor.getParameterTypes();

            // Resolve all dependencies recursively
            Object[] constructorArguments = new Object[dependencyTypes.length];

            for (int i = 0; i < dependencyTypes.length; i++) {
                constructorArguments[i] = getBean(dependencyTypes[i]);
            }

            // Create the bean using the resolved dependencies
            T bean = clazz.cast(constructor.newInstance(constructorArguments));

            // Store it as a singleton
            singletonBeans.put(clazz, bean);

            return bean;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to create bean: " + clazz.getName(),
                    e
            );
        }
    }
}