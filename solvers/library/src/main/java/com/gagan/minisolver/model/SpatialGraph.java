package com.gagan.minisolver.model;

import java.util.ArrayList;
import java.util.List;

public class SpatialGraph {

    private final List<Relationship> relationships =
            new ArrayList<>();

    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
    }

    public List<Relationship> getRelationships() {
        return List.copyOf(relationships);
    }

    public List<Relationship> getRelationshipsFrom(
            String sourceId) {

        return relationships.stream()
                .filter(r ->
                        r.getSourceId().equals(sourceId))
                .toList();
    }

    public List<Relationship> getRelationshipsTo(
            String targetId) {

        return relationships.stream()
                .filter(r ->
                        r.getTargetId().equals(targetId))
                .toList();
    }

    public List<Relationship> getRelationshipsBetween(
            String sourceId,
            String targetId) {

        return relationships.stream()
                .filter(r ->
                        r.getSourceId().equals(sourceId)
                                && r.getTargetId().equals(targetId))
                .toList();
    }
}