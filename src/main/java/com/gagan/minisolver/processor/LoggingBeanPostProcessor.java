package com.gagan.minisolver.processor;

import com.gagan.minisolver.annotation.Component;

@Component
public class LoggingBeanPostProcessor
        implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean) {

        System.out.println(
                "Before Initialization : "
                        + bean.getClass().getSimpleName());

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean) {

        System.out.println(
                "After Initialization : "
                        + bean.getClass().getSimpleName());

        return bean;
    }
}