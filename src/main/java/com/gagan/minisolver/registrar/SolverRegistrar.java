package com.gagan.minisolver.registrar;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.bean.BeanDefinition;
import com.gagan.minisolver.container.BeanFactory;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.manifest.SolverRegistration;
import com.gagan.minisolver.registry.BeanDefinitionRegistry;
import com.gagan.minisolver.solver.Solver;

public class SolverRegistrar {

    private final BeanDefinitionRegistry registry;
    private final BeanFactory beanFactory;

    public SolverRegistrar(
            BeanDefinitionRegistry registry,
            BeanFactory beanFactory
    ) {
        this.registry = registry;
        this.beanFactory = beanFactory;
    }

    public void registerSolvers(SolverManifest manifest) {

        for (BeanDefinition beanDefinition : registry.getDefinitions()) {

            Class<?> beanClass = beanDefinition.getBeanClass();

            if (!Solver.class.isAssignableFrom(beanClass)) {
                continue;
            }

            @SuppressWarnings("unchecked")
            Class<? extends Solver> solverClass =
                    (Class<? extends Solver>) beanClass;

            Solver solver = beanFactory.getBean(solverClass);

            SolverDefinition definition =
                    solverClass.getAnnotation(SolverDefinition.class);

            manifest.register(
                    new SolverRegistration(
                            solver,
                            definition.command(),
                            definition.objectType(),
                            definition.priority()
                    )
            );
        }
    }
}