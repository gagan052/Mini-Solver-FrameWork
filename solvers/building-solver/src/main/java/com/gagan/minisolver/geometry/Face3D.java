package com.gagan.minisolver.geometry;

import java.util.List;

public class Face3D {

    private final List<Point3D> vertices;

    public Face3D(List<Point3D> vertices) {
        this.vertices = List.copyOf(vertices);
    }

    public List<Point3D> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Face3D{" +
                "vertices=" + vertices +
                '}';
    }
}