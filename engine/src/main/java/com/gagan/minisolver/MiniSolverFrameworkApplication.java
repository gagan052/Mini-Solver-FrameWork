package com.gagan.minisolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gagan.minisolver.engine.SolverEngine;
import com.gagan.minisolver.engine.SolverResult;
import com.gagan.minisolver.event.Command;
import com.gagan.minisolver.event.Event;
import com.gagan.minisolver.framework.MiniFramework;

@SpringBootApplication
public class MiniSolverFrameworkApplication {

    public static void main(String[] args) {

        SpringApplication.run(MiniSolverFrameworkApplication.class, args);

        MiniFramework framework = new MiniFramework();

        SolverEngine engine = framework.start();

        SolverResult result = engine.dispatch(
                new Event(
                        Command.ADD,
                        "Building",
                        "B-101"
                )
        );

        System.out.println("Solver Result: " + result.getMessage());

        // BeanDefinitionRegistry registry = new BeanDefinitionRegistry();
        // ComponentScanner scanner = new ComponentScanner(registry);
        // scanner.scan("com.gagan.minisolver");
        // BeanFactory beanFactory = new BeanFactory(registry);
        // BuildingSolver buildingSolver = beanFactory.getBean(BuildingSolver.class);
        // MiniFramework framework
        //         = new MiniFramework();
        // SolverEngine engine
        //         = framework.start();
        // engine.dispatch(
        //         new Event(
        //                 Command.ADD,
        //                 "Parking",
        //                 "P-101"
        //         )
        // );
        // ConstructorReflectionDemo.inspect();
        // BeanFactory beanFactory = new BeanFactory();
        // BuildingSolver s1
        //         = beanFactory.getBean(BuildingSolver.class);
        // BuildingSolver s2
        //         = beanFactory.getBean(BuildingSolver.class);
        // System.out.println(s1 == s2);
        // System.out.println(s1);
        // System.out.println(s2);
        // ReflectionDemo.inspect();
        // SolverManifest manifest = new SolverManifest();
        // manifest.register(
        //         new SolverRegistration(
        //                 new BuildingSolver(),
        //                 Command.ADD,
        //                 "Building",
        //                 1
        //         )
        // );
        // ExecutionPipeline pipeline = new ExecutionPipeline(manifest);
        // SolverEngine engine = new SolverEngine(pipeline);
        // Event event = new Event(
        //         Command.ADD,
        //         "Building",
        //         "B-101"
        // );
        // engine.dispatch(event);
    }
}
