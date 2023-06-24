package com.wanted.java07_atomicoperation.case03_atomicvariable.usage02_atomiclong;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongValue {
    private final AtomicLong atomicValue = new AtomicLong(0L);

    public long getValue() {
        return atomicValue.get();
    }

    public void increment() {
        atomicValue.incrementAndGet();
    }
}
