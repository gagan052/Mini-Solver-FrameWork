package com.gagan.minisolver.solver;

import java.util.Map;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.building.BuildingGeometryGenerator;
import com.gagan.minisolver.building.BuildingModel;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.geometry.Solid3D;
import com.gagan.minisolver.model.Criteria;

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

        Criteria criteria = event.getCriteria();

        double width = criteria.get("width", Number.class).doubleValue();
        double depth = criteria.get("depth", Number.class).doubleValue();
        double height = criteria.get("height", Number.class).doubleValue();

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
            
        
        var buildings
                = context.getModelContext()
                        .getAllByType("Building");

        System.out.println("Buildings in ModelContext:");
        System.out.println(buildings);                

        System.out.println("Stored model:");
        System.out.println(storedBuilding);

        return SolverResult.success(
                "Building geometry generated",
                geometry
        );
    }
}
