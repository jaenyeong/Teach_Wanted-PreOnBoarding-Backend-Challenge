package com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage03_reentrancy;

import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.Counter;

public class ReentrantSyncIncrementer implements Counter {
    private static final String COUNTER_NAME = "Reentrancy Sync Counter";
    private final Object lockObject = new Object();
    private int value = 0;

    @Override
    public void increase() {
        synchronized (lockObject) {
            System.out.println("First Sync Block");

            synchronized (lockObject) {
                System.out.println("Second Sync Block");

                synchronized (lockObject) {
                    System.out.println("Third Sync Block");
                    value++;
                }
            }
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
