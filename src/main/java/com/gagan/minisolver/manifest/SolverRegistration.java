package com.gagan.minisolver.manifest;

import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.solver.Solver;

public class SolverRegistration {

    private final Solver solver;
    private final Command command;
    private final String objectType;
    private final int priority;

    public SolverRegistration(
            Solver solver,
            Command command,
            String objectType,
            int priority) {

        this.solver = solver;
        this.command = command;
        this.objectType = objectType;
        this.priority = priority;
    }

    public Solver getSolver() {
        return solver;
    }

    public Command getCommand() {
        return command;
    }

    public String getObjectType() {
        return objectType;
    }

    public int getPriority() {
        return priority;
    }
}