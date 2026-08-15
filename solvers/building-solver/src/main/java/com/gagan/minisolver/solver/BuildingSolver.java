package com.gagan.minisolver.solver;

import com.gagan.minisolver.event.Event;

public class BuildingSolver implements Solver {

    @Override
    public void solve(Event event) {
        System.out.println("BuildingSolver is processing:");
        System.out.println(event);
    }
}