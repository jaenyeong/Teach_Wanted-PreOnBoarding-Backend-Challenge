package com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage01_methodlevel;

import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.Counter;

public class DefaultIncrementer implements Counter {
    private static final String COUNTER_NAME = "Default Counter";
    private int value = 0;

    @Override
    public void increase() {
        value++;
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
