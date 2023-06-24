package com.wanted.java07_atomicoperation.case03_atomicvariable.usage03_atomicboolean;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBoolValue {
    private final AtomicBoolean atomicValue = new AtomicBoolean(false);

    public Boolean getValue() {
        return atomicValue.get();
    }

    public void toggleValue() {
        boolean originValue;

        do {
            originValue = atomicValue.get();
        } while (!atomicValue.compareAndSet(originValue, !originValue));
    }
}
