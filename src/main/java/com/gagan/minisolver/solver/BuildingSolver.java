package com.gagan.minisolver.solver;

import com.gagan.minisolver.annotation.SolverDefinition;
import com.gagan.minisolver.event.Event;

@SolverDefinition(objectType = "Building")
public class BuildingSolver implements Solver {

    @Override
    public void solve(Event event) {

        System.out.println("BuildingSolver is processing:");
        System.out.println(event);

    }
}