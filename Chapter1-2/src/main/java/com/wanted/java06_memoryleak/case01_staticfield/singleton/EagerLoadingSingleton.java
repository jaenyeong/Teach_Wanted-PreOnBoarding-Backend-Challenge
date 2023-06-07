package com.wanted.java06_memoryleak.case01_staticfield.singleton;

public class EagerLoadingSingleton {
    private static final EagerLoadingSingleton singletonInstance = new EagerLoadingSingleton();

    private EagerLoadingSingleton() {
    }

    public static EagerLoadingSingleton getInstance() {
        return singletonInstance;
    }
}
