package com.wanted.java07_atomicoperation.case05_lock.usage03_stampedlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class OptimisticGetStore {
    private final Map<String, String> syncStore = new HashMap<>();
    private final StampedLock lock = new StampedLock();

    public void put(String key, String value) {
        final long stamp = lock.writeLock();
        final String currentThreadName = Thread.currentThread().getName();

        try {
            System.out.println(currentThreadName + " acquired the write lock with stamp " + stamp);
            syncStore.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
            System.out.println(currentThreadName + " unlocked the write lock with stamp " + stamp);
        }
    }

    public String get(String key) throws InterruptedException {
        final long stamp = lock.readLock();
        final String currentThreadName = Thread.currentThread().getName();

        System.out.println(currentThreadName + " acquired the read lock with stamp " + stamp);

        try {
            Thread.sleep(5_000);
            return syncStore.get(key);
        } finally {
            lock.unlockRead(stamp);
            System.out.println(currentThreadName + " unlocked the read lock with stamp " + stamp);
        }
    }

    public String optimisticGet(String key) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        final String value = syncStore.get(key);

        if (!lock.validate(stamp)) {
            stamp = lock.readLock();

            try {
                Thread.sleep(5_000);
                return syncStore.get(key);
            } finally {
                lock.unlock(stamp);
                final String currentThreadName = Thread.currentThread().getName();
                System.out.println(currentThreadName + " unlocked the read lock with stamp " + stamp);
            }
        }

        return value;
    }
}
