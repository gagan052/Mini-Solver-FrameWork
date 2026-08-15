package com.gagan.minisolver.bean;

public class BeanDefinition {

    private final Class<?> beanClass;
    private final String beanName;

    private BeanScope scope = BeanScope.SINGLETON;
    private boolean primary;

    public BeanDefinition(
            Class<?> beanClass,
            String beanName
    ) {
        this.beanClass = beanClass;
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public BeanScope getScope() {
        return scope;
    }

    public void setScope(BeanScope scope) {
        this.scope = scope;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}