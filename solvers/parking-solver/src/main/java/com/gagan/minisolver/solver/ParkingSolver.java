package com.gagan.minisolver.solver;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Event;

@SolverDefinition(
        command = Command.ADD,
        objectType = "Parking",
        priority = 1
)
public class ParkingSolver implements Solver {

    @Override
public SolverResult solve(SolverContext context) {
    Event event = context.getEvent();

    System.out.println("ParkingSolver is processing: " + event);

    return SolverResult.success(
            "Parking solver executed",
            null
    );
}
}