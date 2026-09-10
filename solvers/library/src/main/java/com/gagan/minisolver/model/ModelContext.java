package com.gagan.minisolver.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModelContext {

    private final Map<String, Object> objects =
            new ConcurrentHashMap<>();
    private final SpatialGraph spatialGraph =
            new SpatialGraph();


    public void add(String id, Object object) {
        objects.put(id, object);
    }

    public Object get(String id) {
        return objects.get(id);
    }

    public <T> T get(String id, Class<T> type) {

        Object object = objects.get(id);

        if (object == null) {
            return null;
        }

        return type.cast(object);
    }

    public boolean contains(String id) {
        return objects.containsKey(id);
    }

    public void remove(String id) {
        objects.remove(id);
    }

    public Map<String, Object> getObjects() {
        return Map.copyOf(objects);
    }

    public <T> T require(String id, Class<T> type) {

        Object object = objects.get(id);

        if (object == null) {
            throw new IllegalStateException(
                    "Object not found in ModelContext: " + id
            );
        }

        return type.cast(object);
    }

    public <T> List<T> getAll(Class<T> type) {

        return objects.values()
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    public List<Object> getAllByType(String type) {

        return objects.values()
                .stream()
                .filter(object ->
                        object instanceof SpatialObject spatialObject
                                && spatialObject.getType().equals(type))
                .toList();
    }

    public SpatialGraph getSpatialGraph() {
        return spatialGraph;
    }

    public void addRelationship(Relationship relationship) {
        spatialGraph.addRelationship(relationship);
    }

    public List<Relationship> getRelationshipsFrom(
            String sourceId) {

        return spatialGraph.getRelationshipsFrom(sourceId);
    }

    public List<Relationship> getRelationshipsTo(
            String targetId) {

        return spatialGraph.getRelationshipsTo(targetId);
    }

}