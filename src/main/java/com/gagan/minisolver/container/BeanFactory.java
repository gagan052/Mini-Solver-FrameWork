package com.gagan.minisolver.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gagan.minisolver.annotation.Autowired;
import com.gagan.minisolver.annotation.PostConstruct;
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
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] dependencies = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = resolveDependency(parameterTypes[i]);
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

    private Object resolveDependency(Class<?> requiredType) {

    List<BeanDefinition> candidates = registry.findByType(requiredType);
    if (candidates.isEmpty()) {
        throw new RuntimeException("No bean found for dependency type: " + requiredType.getName());
    }

    if (candidates.size() > 1) {
        throw new RuntimeException("Multiple beans found for dependency type: " + requiredType.getName());
    }
    Class<?> implementationClass = candidates.get(0).getBeanClass();

    return getBean(implementationClass);
}

    private Object populateBean(Object bean) {

        Class<?> beanClass = bean.getClass();

        for (Field field : beanClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            Class<?> dependencyType = field.getType();
            Object dependency = resolveDependency(dependencyType);
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
