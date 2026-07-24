package com.gagan.minisolver.bean;

public class BeanDefinition {

    private final Class<?> beanClass;
    private BeanScope scope = BeanScope.SINGLETON;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public BeanScope getScope() {
        return scope;
    }

    public void setScope(BeanScope scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass.getSimpleName() +
                '}';
    }
}