package com.gagan.minisolver.processor;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean);

    Object postProcessAfterInitialization(Object bean);
}