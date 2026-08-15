package com.gagan.minisolver.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gagan.minisolver.annotation.Autowired;
import com.gagan.minisolver.annotation.PostConstruct;
import com.gagan.minisolver.annotation.Qualifier;
import com.gagan.minisolver.bean.BeanDefinition;
import com.gagan.minisolver.bean.BeanScope;
import com.gagan.minisolver.processor.BeanPostProcessor;
import com.gagan.minisolver.registry.BeanDefinitionRegistry;

public class BeanFactory {

    private final BeanDefinitionRegistry registry;
    private final Map<Class<?>, Object> singletonObjects = new HashMap<>();
    private final Set<Class<?>> beansCurrentlyInCreation = new HashSet<>();
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public BeanFactory(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void addBeanPostProcessor(BeanPostProcessor processor) {
        beanPostProcessors.add(processor);
    }

    public <T> T getBean(Class<T> beanClass) {
        BeanDefinition beanDefinition = registry.getDefinition(beanClass);
        if (beanDefinition == null) {
            throw new RuntimeException("No BeanDefinition found for " + beanClass.getName());
        }
        // SINGLETON
        if (beanDefinition.getScope() == BeanScope.SINGLETON) {

            if (singletonObjects.containsKey(beanClass)) {
                return beanClass.cast(singletonObjects.get(beanClass));
            }
            return createAndTrackBean(beanClass, beanDefinition, true);
        }
        // PROTOTYPE
        return createAndTrackBean(beanClass, beanDefinition, false);
    }

    private <T> T createAndTrackBean(Class<T> beanClass, BeanDefinition beanDefinition, boolean cacheSingleton) {
        if (beansCurrentlyInCreation.contains(beanClass)) {
            throw new RuntimeException("Circular dependency detected while creating: " + beanClass.getName());
        }

        try {
            beansCurrentlyInCreation.add(beanClass);
            Object bean = createBean(beanDefinition);
            if (cacheSingleton) {
                singletonObjects.put(beanClass, bean);
            }
            return beanClass.cast(bean);
        } finally {
            beansCurrentlyInCreation.remove(beanClass);
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Object bean = instantiateBean(beanDefinition);
        bean = populateBean(bean);
        bean = initializeBean(bean);
        return bean;
    }

    private Object instantiateBean(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            Constructor<?> constructor = resolveConstructor(beanClass);
            Parameter[] parameters = constructor.getParameters();
            Object[] dependencies = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                String qualifier = null;

                if (parameter.isAnnotationPresent(Qualifier.class)) {
                    Qualifier qualifierAnnotation = parameter.getAnnotation(Qualifier.class);
                    qualifier = qualifierAnnotation.value();
                }
                dependencies[i] = resolveDependency(parameter.getType(), qualifier);
            }
            return constructor.newInstance(dependencies);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to instantiate bean: "
                    + beanClass.getName(),
                    e);
        }
    }

    private Constructor<?> resolveConstructor(Class<?> beanClass) {
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
        if (constructors.length == 1) {
            return constructors[0];
        }
        Constructor<?> autowiredConstructor = null;

        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                if (autowiredConstructor != null) {
                    throw new RuntimeException("Multiple @Autowired constructors found in " + beanClass.getName()
                    );
                }
                autowiredConstructor = constructor;
            }
        }
        if (autowiredConstructor != null) {
            return autowiredConstructor;
        }
        throw new RuntimeException("Multiple constructors found but none marked @Autowired in " + beanClass.getName());
    }

    private Object resolveDependency(
            Class<?> requiredType,
            String qualifier) {

        List<BeanDefinition> candidates = registry.findByType(requiredType);

        if (candidates.isEmpty()) {
            throw new RuntimeException("No bean found for dependency type: " + requiredType.getName());
        }

        // Qualifier has explicit priority
        if (qualifier != null) {

            List<BeanDefinition> qualifiedCandidates = candidates.stream()
                            .filter(definition  -> definition.getBeanName()
                            .equals(qualifier))
                            .toList();

            if (qualifiedCandidates.isEmpty()) {
                throw new RuntimeException("No bean found with qualifier '" + qualifier + "' for type " + requiredType.getName());
            }

            if (qualifiedCandidates.size() > 1) {
                throw new RuntimeException("Multiple beans found with qualifier: "+ qualifier);
            }

            return getBean(qualifiedCandidates.get(0).getBeanClass());
        }

        // Normal type resolution
        if (candidates.size() == 1) {
            return getBean(candidates.get(0).getBeanClass());
        }

        // Primary resolution
        List<BeanDefinition> primaryCandidates = candidates.stream()
                        .filter(BeanDefinition::isPrimary)
                        .toList();

        if (primaryCandidates.size() == 1) {
            return getBean(primaryCandidates.get(0).getBeanClass());
        }

        if (primaryCandidates.size() > 1) {
            throw new RuntimeException("Multiple @Primary beans found for dependency type: " + requiredType.getName());
        }

        throw new RuntimeException("Multiple beans found for dependency type: " + requiredType.getName() + " and no @Primary bean is defined");
    }

    private Object populateBean(Object bean) {

        Class<?> beanClass = bean.getClass();

        for (Field field : beanClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            Class<?> dependencyType = field.getType();
            Object dependency = resolveDependency(dependencyType,null);
            try {
                field.setAccessible(true);
                field.set(bean, dependency);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                        "Failed to inject dependency "
                        + dependencyType.getName()
                        + " into "
                        + beanClass.getName(),
                        e
                );
            }
        }
        return bean;
    }

    private void invokePostConstruct(Object bean) {
        Class<?> clazz = bean.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(bean);
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Failed to invoke @PostConstruct on "
                            + clazz.getName(),
                            e
                    );
                }
            }
        }
    }

    private Object initializeBean(Object bean) {
        for (BeanPostProcessor processor : beanPostProcessors) {
            bean = processor.postProcessBeforeInitialization(bean);
        }

        invokePostConstruct(bean);

        for (BeanPostProcessor processor : beanPostProcessors) {
            bean = processor.postProcessAfterInitialization(bean);
        }
        return bean;
    }

}
