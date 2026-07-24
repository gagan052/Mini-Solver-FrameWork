package com.gagan.minisolver.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gagan.minisolver.bean.BeanDefinition;

public class BeanDefinitionRegistry {

    private final Map<Class<?>, BeanDefinition> definitions
            = new HashMap<>();

    public void register(BeanDefinition definition) {
        definitions.put(definition.getBeanClass(), definition);
    }

    public List<BeanDefinition> findByType(Class<?> requiredType) {
        return definitions.values()
                .stream()
                .filter(definition -> requiredType.isAssignableFrom(definition.getBeanClass()))
                .toList();
    }

    public BeanDefinition getDefinition(Class<?> clazz) {
        return definitions.get(clazz);
    }

    public Collection<BeanDefinition> getDefinitions() {
        return definitions.values();
    }

}
