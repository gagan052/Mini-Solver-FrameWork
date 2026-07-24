package com.gagan.minisolver.service;

import com.gagan.minisolver.annotation.Component;
import com.gagan.minisolver.annotation.Scope;
import com.gagan.minisolver.bean.BeanScope;

@Component
@Scope(BeanScope.PROTOTYPE)
public class PrototypeService {

    public PrototypeService() {
        System.out.println("PrototypeService created");
    }
}