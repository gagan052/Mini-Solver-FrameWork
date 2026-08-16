package com.gagan.minisolver.solver;

import com.gagan.minisolver.engine.SolverResult;

public interface Solver {

    SolverResult<?> solve(SolverContext context);
}