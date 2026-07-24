package com.gagan.minisolver.service;

import com.gagan.minisolver.annotation.Component;
import com.gagan.minisolver.annotation.PostConstruct;

@Component
public class DefaultGeometryService implements GeometryService {

    @PostConstruct
    public void init() {
        System.out.println("DefaultGeometryService initialized");
    }

    @Override
    public void generateGeometry() {
        System.out.println("Generating geometry...");
    }
}