package com.gagan.minisolver.service;

import com.gagan.minisolver.annotation.Component;

@Component
public class AdvancedGeometryService implements GeometryService {

    @Override
    public void generateGeometry() {
        System.out.println("Generating advanced geometry...");
    }
}