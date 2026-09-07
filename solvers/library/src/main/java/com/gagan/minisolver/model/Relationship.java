package com.gagan.minisolver.model;

public class Relationship {

    private final String id;
    private final String type;
    private final String sourceId;
    private final String targetId;

    public Relationship(
            String id,
            String type,
            String sourceId,
            String targetId) {

        this.id = id;
        this.type = type;
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", targetId='" + targetId + '\'' +
                '}';
    }
}