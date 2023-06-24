package com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage02_blocklevel;

import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.Counter;

public class SyncBlockIncrementer implements Counter {
    private static final String COUNTER_NAME = "Block Counter";
    private final Object lockObject = new Object();
    private int value = 0;

    @Override
    public void increase() {
        synchronized (lockObject) {
            value++;
        }
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getCounterName() {
        return COUNTER_NAME;
    }
}
