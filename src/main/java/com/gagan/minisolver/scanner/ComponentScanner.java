package com.gagan.minisolver.scanner;

import com.gagan.minisolver.annotation.Component;
import com.gagan.minisolver.bean.BeanDefinition;
import com.gagan.minisolver.registry.BeanDefinitionRegistry;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class ComponentScanner {

    private final BeanDefinitionRegistry registry;

    public ComponentScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void scan(String basePackage) {

        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages(basePackage)
                .enableClassInfo()
                .enableAnnotationInfo()
                .scan()) {

            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(Component.class.getName())) {

                Class<?> clazz = classInfo.loadClass();

                BeanDefinition beanDefinition = new BeanDefinition(clazz);

                registry.register(beanDefinition);
            }
        }
    }
}