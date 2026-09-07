package com.gagan.minisolver.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpatialObject {

    private final String id;
    private final String type;
    private final String name;

    private final Map<String, Object> dynamicFacets
            = new LinkedHashMap<>();

    private final Map<String, Object> facets
            = new LinkedHashMap<>();

    private final Map<String, Object> metrics
            = new LinkedHashMap<>();

    private final Map<String, Object> states
            = new LinkedHashMap<>();

    public SpatialObject(
            String id,
            String type,
            String name) {

        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getDynamicFacets() {
        return dynamicFacets;
    }

    public Map<String, Object> getFacets() {
        return facets;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public Map<String, Object> getStates() {
        return states;
    }

    public void addDynamicFacet(String key, Object value) {
        dynamicFacets.put(key, value);
    }

    public void addFacet(String key, Object value) {
        facets.put(key, value);
    }

    public void addMetric(String key, Object value) {
        metrics.put(key, value);
    }

    public void setState(String key, Object value) {
        states.put(key, value);
    }
}