package com.gagan.minisolver.engine;

public class SolverResult<T> {

    private final boolean success;
    private final String message;
    private final T data;

    private SolverResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> SolverResult<T> success(T data) {

        return new SolverResult<>(
                true,
                "Solver executed successfully",
                data
        );
    }

    public static <T> SolverResult<T> success(
            String message,
            T data) {

        return new SolverResult<>(
                true,
                message,
                data
        );
    }

    public static <T> SolverResult<T> failure(
            String message) {

        return new SolverResult<>(
                false,
                message,
                null
        );
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {

        return "SolverResult{"
                + "success=" + success
                + ", message='" + message + '\''
                + ", data=" + data
                + '}';
    }

}
