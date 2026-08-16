package com.gagan.minisolver.solver;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.building.BuildingGeometryGenerator;
import com.gagan.minisolver.building.BuildingModel;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;

@SolverDefinition(
        command = Command.ADD,
        objectType = "Building",
        priority = 1
)
public class BuildingSolver implements Solver {

    private final BuildingGeometryGenerator geometryGenerator;

    public BuildingSolver() {
        this.geometryGenerator = new BuildingGeometryGenerator();
    }

    @Override
    public SolverResult solve(SolverContext context) {

        System.out.println("BuildingSolver is processing:");

        Event event = context.getEvent();

        System.out.println(event);

        BuildingModel building
                = new BuildingModel(
                        event.getObjectId(),
                        20.0,
                        30.0,
                        10.0
                );
        // BuildingModel building
        //         = context.getModelContext()
        //                 .require(
        //                         building.getId(),
        //                         BuildingModel.class
        //                 );

        // System.out.println(
        //         "Retrieved from ModelContext: "
        //         + storedBuilding
        // );

        var geometry
                = geometryGenerator.generate(building);

        System.out.println("Generated geometry:");
        System.out.println(geometry);

        System.out.println("Vertices:");

        geometry.getVertices()
                .forEach(System.out::println);

        System.out.println(
                "Faces: " + geometry.getFaces().size()
        );

        context.getModelContext()
                .add(
                        building.getId(),
                        building
                );

        BuildingModel storedBuilding
                = context.getModelContext()
                        .get(
                                building.getId(),
                                BuildingModel.class
                        );

        System.out.println("Stored model:");
        System.out.println(storedBuilding);

        return SolverResult.success(
                "Building geometry generated",
                geometry
        );
    }
}
