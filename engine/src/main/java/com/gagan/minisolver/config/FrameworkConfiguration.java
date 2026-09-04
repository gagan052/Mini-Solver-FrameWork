package com.gagan.minisolver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gagan.minisolver.engine.SolverEngine;
import com.gagan.minisolver.framework.MiniFramework;

@Configuration
public class FrameworkConfiguration {

    @Bean
    public MiniFramework miniFramework() {

        MiniFramework framework =
                new MiniFramework();

        framework.start();

        return framework;
    }

    @Bean
    public SolverEngine solverEngine(
            MiniFramework framework) {

        return framework.getApplicationContext()
                .getSolverEngine();
    }
}