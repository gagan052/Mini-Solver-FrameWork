package com.gagan.minisolver.geometry;

import java.util.List;

public class Solid3D {

    private final List<Point3D> vertices;
    private final List<Face3D> faces;

    public Solid3D(
            List<Point3D> vertices,
            List<Face3D> faces) {

        this.vertices = List.copyOf(vertices);
        this.faces = List.copyOf(faces);
    }

    public List<Point3D> getVertices() {
        return vertices;
    }

    public List<Face3D> getFaces() {
        return faces;
    }

    @Override
    public String toString() {
        return "Solid3D{" +
                "vertices=" + vertices.size() +
                ", faces=" + faces.size() +
                '}';
    }
}