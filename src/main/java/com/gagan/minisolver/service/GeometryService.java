package com.gagan.minisolver.service;

import com.gagan.minisolver.annotation.Component;
import com.gagan.minisolver.annotation.PostConstruct;

@Component
public class GeometryService {

    @PostConstruct
    public void initialize() {
        System.out.println("GeometryService initialized");
    }

    public void generateGeometry() {
        System.out.println("Generating geometry...");
    }
}