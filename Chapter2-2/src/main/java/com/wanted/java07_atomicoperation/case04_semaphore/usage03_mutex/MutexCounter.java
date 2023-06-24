package com.wanted.java07_atomicoperation.case04_semaphore.usage03_mutex;

import java.util.concurrent.Semaphore;

public class MutexCounter {
    private static final int BINARY_SEMAPHORE = 1;
    private final Semaphore mutex = new Semaphore(BINARY_SEMAPHORE);
    private int value;

    public MutexCounter() {
        this.value = 0;
    }

    public void increase() {
        try {
            mutex.acquire();
            this.value++;
            Thread.sleep(1_000);
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getValue() {
        return this.value;
    }

    public boolean hasQueuedThreads() {
        return mutex.hasQueuedThreads();
    }
}
