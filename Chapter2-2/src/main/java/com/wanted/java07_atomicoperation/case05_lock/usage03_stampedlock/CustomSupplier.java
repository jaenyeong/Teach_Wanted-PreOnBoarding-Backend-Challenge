package com.wanted.java07_atomicoperation.case05_lock.usage03_stampedlock;

@FunctionalInterface
public interface CustomSupplier<T> {
    T run(String key) throws InterruptedException;
}
