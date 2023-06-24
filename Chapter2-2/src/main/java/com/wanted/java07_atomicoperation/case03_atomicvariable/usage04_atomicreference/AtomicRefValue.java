package com.wanted.java07_atomicoperation.case03_atomicvariable.usage04_atomicreference;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefValue {
    private final AtomicReference<ValueRecord> atomicValue = new AtomicReference<>(new ValueRecord(0));

    public ValueRecord getValue() {
        return new ValueRecord(atomicValue.get().atomicValue());
    }

    public void increment() {
        ValueRecord originValue;
        ValueRecord newValue;
        do {
            originValue = atomicValue.get();
            newValue = new ValueRecord(originValue.atomicValue() + 1);
        } while (!atomicValue.compareAndSet(originValue, newValue));
    }
}
