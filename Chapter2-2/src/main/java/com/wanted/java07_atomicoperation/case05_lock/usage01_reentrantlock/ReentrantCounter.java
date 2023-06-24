package com.wanted.java07_atomicoperation.case05_lock.usage01_reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCounter {
    private final ReentrantLock lock = new ReentrantLock(true);
    private int value = 0;

    public void increase() {
        lock.lock();
        final String currentThreadName = Thread.currentThread().getName();

        System.out.println("Thread - " + currentThreadName + " acquired the lock");

        try {
            System.out.println("Thread - " + currentThreadName + " processing");
            value++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("Thread - " + currentThreadName + " released the lock");
        }
    }

    private void performTryLock() {
        final String currentThreadName = Thread.currentThread().getName();
        System.out.println("Thread - " + currentThreadName + " attempting to acquire the lock");

        try {
            final boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);

            if (isLockAcquired) {
                try {
                    System.out.println("Thread - " + currentThreadName + " acquired the lock");
                    System.out.println("Thread - " + currentThreadName + " processing");
                    Thread.sleep(1_000);
                } finally {
                    lock.unlock();
                    System.out.println("Thread - " + currentThreadName + " released the lock");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread - " + currentThreadName + " could not acquire the lock");
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public boolean isLocked() {
        return lock.isLocked();
    }

    public boolean hasQueuedThreads() {
        return lock.hasQueuedThreads();
    }

    public int getValue() {
        return value;
    }
}
