package com.gagan.minisolver.engine;

public class SolverResult {

    private final boolean success;
    private final String message;

    public SolverResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
