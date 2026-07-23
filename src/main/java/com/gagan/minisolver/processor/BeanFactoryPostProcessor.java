package com.gagan.minisolver.processor;

import com.gagan.minisolver.registry.BeanDefinitionRegistry;

public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(
            BeanDefinitionRegistry registry
    );
}