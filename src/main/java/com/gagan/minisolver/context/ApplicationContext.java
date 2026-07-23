package com.gagan.minisolver.context;

import com.gagan.minisolver.bean.BeanDefinition;
import com.gagan.minisolver.container.BeanFactory;
import com.gagan.minisolver.engine.SolverEngine;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.pipeline.ExecutionPipeline;
import com.gagan.minisolver.processor.BeanFactoryPostProcessor;
import com.gagan.minisolver.processor.BeanPostProcessor;
import com.gagan.minisolver.registrar.SolverRegistrar;
import com.gagan.minisolver.registry.BeanDefinitionRegistry;
import com.gagan.minisolver.scanner.ComponentScanner;

public class ApplicationContext {

    private final BeanDefinitionRegistry registry;
    private final BeanFactory beanFactory;
    private final SolverEngine solverEngine;

    public ApplicationContext(String basePackage) {

        // Step 1
        registry = new BeanDefinitionRegistry();

        // Step 2
        ComponentScanner scanner = new ComponentScanner(registry);
        scanner.scan(basePackage);

        // Step 3
        beanFactory = new BeanFactory(registry);

        // Step 4
        invokeBeanFactoryPostProcessors(registry, beanFactory);

        // Step 5
        registerBeanPostProcessors(registry, beanFactory);

        // Step 6 - NOW create application beans
        SolverManifest manifest = new SolverManifest();

        SolverRegistrar registrar
                = new SolverRegistrar(registry, beanFactory);

        registrar.registerSolvers(manifest);

        // Step 7
        ExecutionPipeline pipeline
                = new ExecutionPipeline(manifest);

        solverEngine = new SolverEngine(pipeline);
    }

    private void registerBeanPostProcessors(
            BeanDefinitionRegistry registry,
            BeanFactory beanFactory) {

        for (BeanDefinition definition : registry.getDefinitions()) {

            Class<?> beanClass = definition.getBeanClass();

            if (BeanPostProcessor.class.isAssignableFrom(beanClass)) {

                BeanPostProcessor processor
                        = (BeanPostProcessor) beanFactory.getBean(beanClass);

                beanFactory.addBeanPostProcessor(processor);
            }
        }
    }

    private void invokeBeanFactoryPostProcessors(
            BeanDefinitionRegistry registry,
            BeanFactory beanFactory) {

        for (BeanDefinition definition : registry.getDefinitions()) {

            Class<?> beanClass = definition.getBeanClass();

            if (BeanFactoryPostProcessor.class.isAssignableFrom(beanClass)) {

                BeanFactoryPostProcessor processor
                        = (BeanFactoryPostProcessor) beanFactory.getBean(beanClass);

                processor.postProcessBeanFactory(registry);
            }
        }
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public SolverEngine getSolverEngine() {
        return solverEngine;
    }
}
