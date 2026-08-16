package com.gagan.minisolver.building;

public class BuildingModel {

    private final String id;

    private final double width;
    private final double depth;
    private final double height;

    public BuildingModel(
            String id,
            double width,
            double depth,
            double height) {

        this.id = id;
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    public String getId() {
        return id;
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
                "id='" + id + '\'' +
                ", width=" + width +
                ", depth=" + depth +
                ", height=" + height +
                '}';
    }
}