package com.wanted.java06_memoryleak.case01_staticfield.singleton;

public class LazyLoadingSingleton {
    private static LazyLoadingSingleton singletonInstance;

    private LazyLoadingSingleton() {
    }

    public static synchronized LazyLoadingSingleton getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new LazyLoadingSingleton();
        }

        return singletonInstance;
    }
}
