package com.gagan.minisolver.building;

import com.gagan.minisolver.model.SpatialObject;

public class BuildingModel extends SpatialObject {

    private final double width;
    private final double depth;
    private final double height;

    public BuildingModel(
            String id,
            double width,
            double depth,
            double height) {

        super(id, "Building", "Building");

        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getDepth() {
        return depth;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "BuildingModel{" +
                "id='" + getId() + '\'' +
                ", width=" + width +
                ", depth=" + depth +
                ", height=" + height +
                '}';
    }
}