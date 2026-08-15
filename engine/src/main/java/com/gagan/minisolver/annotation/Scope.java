package com.gagan.minisolver.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.gagan.minisolver.bean.BeanScope;

@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    BeanScope value() default BeanScope.SINGLETON;
}