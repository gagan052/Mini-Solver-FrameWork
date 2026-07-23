package com.gagan.minisolver.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gagan.minisolver.annotation.PostConstruct;
import com.gagan.minisolver.bean.BeanDefinition;
import com.gagan.minisolver.processor.BeanPostProcessor;
import com.gagan.minisolver.registry.BeanDefinitionRegistry;

public class BeanFactory {

    private final BeanDefinitionRegistry registry;
    private final Map<Class<?>, Object> singletonObjects = new HashMap<>();

    public BeanFactory(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    private final List<BeanPostProcessor> beanPostProcessors
            = new ArrayList<>();

    public void addBeanPostProcessor(BeanPostProcessor processor) {
        beanPostProcessors.add(processor);
    }

    public <T> T getBean(Class<T> beanClass) {

        if (singletonObjects.containsKey(beanClass)) {
            return beanClass.cast(singletonObjects.get(beanClass));
        }

        BeanDefinition beanDefinition = registry.getDefinition(beanClass);

        if (beanDefinition == null) {
            throw new RuntimeException("No BeanDefinition found for " + beanClass.getName());
        }

        Object bean = createBean(beanDefinition);

        singletonObjects.put(beanClass, bean);

        return beanClass.cast(bean);
    }

    private Object createBean(BeanDefinition beanDefinition) {

        Class<?> beanClass = beanDefinition.getBeanClass();

        try {

            Constructor<?> constructor = beanClass.getDeclaredConstructors()[0];

            Class<?>[] parameterTypes = constructor.getParameterTypes();

            Object[] dependencies = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getBean(parameterTypes[i]);
            }

            Object bean = constructor.newInstance(dependencies);

            // Initialize the bean
            bean = initializeBean(bean);

            return bean;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create bean: " + beanClass.getName(), e);
        }
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
