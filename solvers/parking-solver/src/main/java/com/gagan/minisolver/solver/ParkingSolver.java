package com.gagan.minisolver.solver;

import com.gagan.minisolver.event.Event;

public class ParkingSolver implements Solver {

    @Override
    public void solve(Event event) {

        System.out.println("ParkingSolver is processing:");
        System.out.println(event);

    }
}