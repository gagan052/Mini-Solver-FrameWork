package com.gagan.minisolver.solver;

import com.gagan.minisolver.annotation.Component;
import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.service.GeometryService;

@Component
@SolverDefinition(
        command = Command.ADD,
        objectType = "Building",
        priority = 1
)
public class BuildingSolver implements Solver {

    private final GeometryService geometryService;

    public BuildingSolver(GeometryService geometryService) {
        this.geometryService = geometryService;
    }

    @Override
    public void solve(Event event) {

        geometryService.generateGeometry();

        System.out.println("BuildingSolver is processing:");
        System.out.println(event);
    }

}