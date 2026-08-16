package com.gagan.minisolver.building;

import java.util.List;

import com.gagan.minisolver.geometry.Face3D;
import com.gagan.minisolver.geometry.Point3D;
import com.gagan.minisolver.geometry.Solid3D;

public class BuildingGeometryGenerator {

    public Solid3D generate(BuildingModel building) {

        double width = building.getWidth();
        double depth = building.getDepth();
        double height = building.getHeight();

        Point3D p0 = new Point3D(0, 0, 0);
        Point3D p1 = new Point3D(width, 0, 0);
        Point3D p2 = new Point3D(width, depth, 0);
        Point3D p3 = new Point3D(0, depth, 0);

        Point3D p4 = new Point3D(0, 0, height);
        Point3D p5 = new Point3D(width, 0, height);
        Point3D p6 = new Point3D(width, depth, height);
        Point3D p7 = new Point3D(0, depth, height);

        List<Point3D> vertices = List.of(
                p0, p1, p2, p3,
                p4, p5, p6, p7
        );

        Face3D bottom = new Face3D(
                List.of(p0, p1, p2, p3)
        );

        Face3D top = new Face3D(
                List.of(p4, p7, p6, p5)
        );

        Face3D front = new Face3D(
                List.of(p0, p4, p5, p1)
        );

        Face3D right = new Face3D(
                List.of(p1, p5, p6, p2)
        );

        Face3D back = new Face3D(
                List.of(p2, p6, p7, p3)
        );

        Face3D left = new Face3D(
                List.of(p3, p7, p4, p0)
        );

        List<Face3D> faces = List.of(
                bottom,
                top,
                front,
                right,
                back,
                left
        );

        Solid3D solid = new Solid3D(vertices, faces);

        System.out.println("Generated geometry:");
        System.out.println(solid);

        return solid;
    }
}