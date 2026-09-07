package com.gagan.minisolver.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Criteria {

    private final Map<String, Object> values = new LinkedHashMap<>();

    @JsonAnySetter
    public void set(String key, Object value) {
        values.put(key, value);
    }

    public Object get(String key) {
        return values.get(key);
    }

    public <T> T get(String key, Class<T> type) {

        Object value = values.get(key);

        if (value == null) {
            return null;
        }

        return type.cast(value);
    }

    public boolean contains(String key) {
        return values.containsKey(key);
    }

    public Map<String, Object> getValues() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}