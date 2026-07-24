package com.gagan.minisolver.framework;

import com.gagan.minisolver.context.ApplicationContext;
import com.gagan.minisolver.engine.SolverEngine;

public class MiniFramework {

    private ApplicationContext applicationContext;

    public SolverEngine start() {

        applicationContext =
                new ApplicationContext("com.gagan.minisolver");

        System.out.println("Framework Bootstrapped");

        return applicationContext.getSolverEngine();
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}