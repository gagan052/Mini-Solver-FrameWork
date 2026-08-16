package com.gagan.minisolver.pipeline;

import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.manifest.SolverManifest;
import com.gagan.minisolver.model.ModelContext;

public class ExecutionPipeline {

    private final SolverManifest manifest;
    private final ModelContext modelContext;

    public ExecutionPipeline(SolverManifest manifest) {
        this.manifest = manifest;
        this.modelContext = new ModelContext();
    }

    public SolverResult<?> execute(Event event) {

        var registrations = manifest.findMatching(event);

        if (registrations.isEmpty()) {
            return SolverResult.failure(
                    "No solver found for event: " + event
            );
        }

        ExecutionContext context
                = new ExecutionContext(
                        event,
                        modelContext
                );

        // var registrations
        //         = manifest.findMatching(event);

        // if (registrations.isEmpty()) {

        //     context.setStatus(ExecutionStatus.FAILED);

        //     return SolverResult.failure(
        //             "No solver found for event: " + event
        //     );
        // }

        for (var registration : registrations) {

            String solverName
                    = registration.getSolver()
                            .getClass()
                            .getSimpleName();

            context.setCurrentSolver(solverName);

            System.out.println(
                    "Executing solver: "
                    + solverName
                    + " | priority="
                    + registration.getPriority()
            );

            SolverResult<?> result
                    = registration
                            .getSolver()
                            .solve(
                                    new com.gagan.minisolver.solver.SolverContext(
                                            context.getEvent(),
                                            context.getModelContext(),
                                            context.getPreviousResult()
                                    )
                            );

            context.setPreviousResult(result);
            context.addResult(result);

            if (!result.isSuccess()) {

                context.setStatus(
                        ExecutionStatus.FAILED
                );

                System.out.println(
                        "Solver failed. Stopping pipeline: "
                        + solverName
                );

                return result;
            }
        }

        context.setStatus(
                ExecutionStatus.SUCCESS
        );

        return context.getPreviousResult();
    }

    // public ExecutionContext getContext() {
    //     // return context;
    // }

    // public com.gagan.minisolver.model.ModelContext getModelContext() {
    //     return context.getModelContext();
    // }
}
