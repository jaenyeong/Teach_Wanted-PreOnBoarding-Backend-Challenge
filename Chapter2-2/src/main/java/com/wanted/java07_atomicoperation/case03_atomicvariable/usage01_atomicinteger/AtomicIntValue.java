package com.wanted.java07_atomicoperation.case03_atomicvariable.usage01_atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntValue {
    private final AtomicInteger atomicValue = new AtomicInteger(0);

    public int getValue() {
        return atomicValue.get();
    }

    public void increment() {
        atomicValue.incrementAndGet();
    }
}
