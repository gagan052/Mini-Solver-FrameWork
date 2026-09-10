package com.gagan.minisolver.building;

import com.gagan.minisolver.model.SpatialObject;

public class StoreyModel extends SpatialObject {

    private final int level;
    private final double height;

    public StoreyModel(
            String id,
            int level,
            double height) {

        super(id, "Storey", "Storey");

        this.level = level;
        this.height = height;
    }

    public int getLevel() {
        return level;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "StoreyModel{" +
                "id='" + getId() + '\'' +
                ", level=" + level +
                ", height=" + height +
                '}';
    }
}