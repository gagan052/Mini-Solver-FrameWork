package com.gagan.minisolver.util;

public class ObjectFactory {

    public static <T> T create(Class<T> clazz) {

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}