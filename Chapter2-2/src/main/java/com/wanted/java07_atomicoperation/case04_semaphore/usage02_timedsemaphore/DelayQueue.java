package com.wanted.java07_atomicoperation.case04_semaphore.usage02_timedsemaphore;

import org.apache.commons.lang3.concurrent.TimedSemaphore;

import java.util.concurrent.TimeUnit;

public class DelayQueue {
    private final TimedSemaphore semaphore;

    public DelayQueue(long period, int slotLimit) {
        // period 동안에 slotLimit 요청만큼만 허용
        semaphore = new TimedSemaphore(period, TimeUnit.SECONDS, slotLimit);
    }

    public boolean tryAdd() {
        return semaphore.tryAcquire();
    }

    public int availableSlots() {
        return semaphore.getAvailablePermits();
    }
}
