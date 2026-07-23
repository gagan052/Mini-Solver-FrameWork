package com.gagan.minisolver.processor;

import com.gagan.minisolver.annotation.Component;
import com.gagan.minisolver.registry.BeanDefinitionRegistry;

@Component
public class LoggingBeanFactoryPostProcessor
        implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(
            BeanDefinitionRegistry registry) {

        System.out.println(
                "Bean Definitions Found : "
                        + registry.getDefinitions().size());
    }
}