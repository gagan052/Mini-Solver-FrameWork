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

        // 1. Already completely created?
        if (singletonObjects.containsKey(beanClass)) {
            return beanClass.cast(singletonObjects.get(beanClass));
        }

        // 2. Are we already creating this bean?
        if (beansCurrentlyInCreation.contains(beanClass)) {
            throw new RuntimeException(
                    "Circular dependency detected while creating: "
                    + beanClass.getName()
            );
        }

        BeanDefinition beanDefinition
                = registry.getDefinition(beanClass);

        if (beanDefinition == null) {
            throw new RuntimeException(
                    "No BeanDefinition found for "
                    + beanClass.getName()
            );
        }

        try {

            // 3. Mark bean as currently being created
            beansCurrentlyInCreation.add(beanClass);

            // 4. Create complete bean
            Object bean = createBean(beanDefinition);

            // 5. Bean successfully created
            singletonObjects.put(beanClass, bean);

            return beanClass.cast(bean);

        } finally {

            // 6. Always remove creation marker
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

            Constructor<?> constructor
                    = beanClass.getDeclaredConstructors()[0];

            Class<?>[] parameterTypes
                    = constructor.getParameterTypes();

            Object[] dependencies
                    = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getBean(parameterTypes[i]);
            }

            return constructor.newInstance(dependencies);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to instantiate bean: "
                    + beanClass.getName(),
                    e);
        }
    }

    private Object populateBean(Object bean) {

        Class<?> beanClass = bean.getClass();

        for (Field field : beanClass.getDeclaredFields()) {

            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }

            Class<?> dependencyType = field.getType();

            Object dependency = getBean(dependencyType);

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

    // private final List<BeanPostProcessor> beanPostProcessors = List.of(
    //         new LoggingBeanPostProcessor()
    // );
}
