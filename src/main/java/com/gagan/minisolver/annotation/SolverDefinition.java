package com.gagan.minisolver.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.gagan.minisolver.event.Command;

@Retention(RetentionPolicy.RUNTIME)
public @interface SolverDefinition {

    Command command();

    String objectType();

    int priority() default 0;
}