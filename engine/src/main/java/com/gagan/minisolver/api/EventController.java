package com.gagan.minisolver.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gagan.minisolver.engine.SolverEngine;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Event;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final SolverEngine solverEngine;

    public EventController(SolverEngine solverEngine) {
        this.solverEngine = solverEngine;
    }

    @PostMapping
    public SolverResult<?> dispatch(
            @RequestBody Event event) {

        return solverEngine.dispatch(event);
    }
}