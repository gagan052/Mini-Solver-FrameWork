package com.gagan.minisolver.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gagan.minisolver.event.Command;

@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface SolverDefinition {

    Command command();

    String objectType();

    int priority() default 0;
}