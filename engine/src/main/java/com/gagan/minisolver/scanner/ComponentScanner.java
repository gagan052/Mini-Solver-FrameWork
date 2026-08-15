package com.gagan.minisolver.scanner;

import com.gagan.minisolver.annotation.Component;
import com.gagan.minisolver.annotation.Primary;
import com.gagan.minisolver.annotation.Scope;
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
                String beanName = generateBeanName(clazz);

                BeanDefinition beanDefinition = new BeanDefinition(clazz, beanName);

                if (clazz.isAnnotationPresent(Primary.class)) {
                    beanDefinition.setPrimary(true);
                }
                if (clazz.isAnnotationPresent(Scope.class)) {
                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                    beanDefinition.setScope(scopeAnnotation.value());
                }

                registry.register(beanDefinition);
            }
        }
    }

    private String generateBeanName(Class<?> clazz) {

        String simpleName = clazz.getSimpleName();

        return Character.toLowerCase(simpleName.charAt(0))
                + simpleName.substring(1);
    }
}
