package com.gagan.minisolver.solver;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;

@SolverDefinition(
        command = Command.ADD,
        objectType = "Parking",
        priority = 2
)
public class ParkingSolver implements Solver {

    @Override
    public void solve(Event event) {

        System.out.println("ParkingSolver is processing:");
        System.out.println(event);
    }
}