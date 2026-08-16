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

        // Step 1: Create bean definition registry
        registry = new BeanDefinitionRegistry();

        // Step 2: Scan engine classes
        ComponentScanner scanner =
                new ComponentScanner(registry);

        scanner.scan(basePackage);

        // Step 3: Create BeanFactory
        beanFactory =
                new BeanFactory(registry);

        // Step 4: Execute BeanFactoryPostProcessors
        invokeBeanFactoryPostProcessors(
                registry,
                beanFactory
        );

        // Step 5: Register BeanPostProcessors
        registerBeanPostProcessors(
                registry,
                beanFactory
        );

        // Step 6: Create Solver Manifest
        SolverManifest manifest =
                new SolverManifest();

        // Step 7: Discover external solver JARs
        SolverRegistrar registrar =
                new SolverRegistrar();

        registrar.registerSolvers(manifest);

        // Step 8: Create execution pipeline
        ExecutionPipeline pipeline =
                new ExecutionPipeline(manifest);

        // Step 9: Create SolverEngine
        solverEngine =
                new SolverEngine(pipeline);
    }

    private void registerBeanPostProcessors(
            BeanDefinitionRegistry registry,
            BeanFactory beanFactory) {

        for (BeanDefinition definition :
                registry.getDefinitions()) {

            Class<?> beanClass =
                    definition.getBeanClass();

            if (BeanPostProcessor.class
                    .isAssignableFrom(beanClass)) {

                BeanPostProcessor processor =
                        (BeanPostProcessor)
                                beanFactory.getBean(beanClass);

                beanFactory.addBeanPostProcessor(processor);
            }
        }
    }

    private void invokeBeanFactoryPostProcessors(
            BeanDefinitionRegistry registry,
            BeanFactory beanFactory) {

        for (BeanDefinition definition :
                registry.getDefinitions()) {

            Class<?> beanClass =
                    definition.getBeanClass();

            if (BeanFactoryPostProcessor.class
                    .isAssignableFrom(beanClass)) {

                BeanFactoryPostProcessor processor =
                        (BeanFactoryPostProcessor)
                                beanFactory.getBean(beanClass);

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