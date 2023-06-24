package com.wanted.java07_atomicoperation.case05_lock.usage02_reentrantreadwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentReadStore {
    private final Map<String, String> syncStore = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public void put(String key, String value) throws InterruptedException {
        try {
            writeLock.lock();
            final String currentThreadName = Thread.currentThread().getName();
            System.out.println(currentThreadName + " writing");

            syncStore.put(key, value);
            Thread.sleep(1_000);
        } finally {
            writeLock.unlock();
        }
    }

    public String get(String key) {
        try {
            readLock.lock();
            final String currentThreadName = Thread.currentThread().getName();
            System.out.println(currentThreadName + " reading");

            return syncStore.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public String remove(String key) {
        try {
            writeLock.lock();
            return syncStore.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean containsKey(String key) {
        try {
            readLock.lock();
            return syncStore.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    public boolean isReadLockAvailable() {
        return readLock.tryLock();
    }
}
