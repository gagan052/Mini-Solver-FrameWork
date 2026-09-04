package com.gagan.minisolver.solver;

import java.util.Map;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.building.BuildingGeometryGenerator;
import com.gagan.minisolver.building.BuildingModel;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.geometry.Solid3D;

@SolverDefinition(
        command = Command.ADD,
        objectType = "Building",
        priority = 1
)
public class BuildingSolver
        implements Solver<BuildingModel, Solid3D> {

    private final BuildingGeometryGenerator geometryGenerator;

    public BuildingSolver() {
        this.geometryGenerator = new BuildingGeometryGenerator();
    }

    @Override
    public SolverResult<Solid3D> solve(SolverContext context) {

        System.out.println("BuildingSolver is processing:");

        Event event = context.getEvent();

        System.out.println(event);

        Map<String, Object> data = event.getData();

        double width = ((Number) data.get("width")).doubleValue();
        double depth = ((Number) data.get("depth")).doubleValue();
        double height = ((Number) data.get("height")).doubleValue();

        BuildingModel building
                = new BuildingModel(
                        event.getObjectId(),
                        width,
                        depth,
                        height
                );
        
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
