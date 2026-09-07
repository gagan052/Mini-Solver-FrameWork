package com.gagan.minisolver.solver;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.geometry.Solid3D;

@SolverDefinition(
        command = Command.ADD,
        objectType = "Building",
        priority = 2
)
public class BuildingValidationSolver
        implements Solver<Solid3D, Solid3D> {

    @Override
    public SolverResult<Solid3D> solve(SolverContext context) {

        SolverResult<?> previousResult
                = context.getPreviousResult();

        System.out.println(
                "Previous solver result: "
                + previousResult
        );

        Event event = context.getEvent();

        System.out.println(
                "BuildingValidationSolver is processing:"
        );

        System.out.println(event);

        if (previousResult == null) {

            return SolverResult.failure(
                    "No previous solver result available"
            );
        }

        if (!previousResult.isSuccess()) {

            return SolverResult.failure(
                    "Previous solver failed"
            );
        }

        Object data = previousResult.getData();
        if (!(data instanceof Solid3D)) {
            return SolverResult.failure(
                    "Previous solver did not produce Solid3D"
            );
        }
        Solid3D geometry = (Solid3D) data;

        System.out.println(
                "Validation received geometry: "
                + geometry
        );

        if (geometry.getVertices().isEmpty()
                || geometry.getFaces().isEmpty()) {


            System.out.println(
                    "Building geometry validation failed"
            );

            return SolverResult.failure(
                    "Building geometry is invalid"
            );
        }

        System.out.println(
                "Building geometry validation passed"
        );

        return SolverResult.success(
                "Building validation completed",
                geometry
        );
    }
}
