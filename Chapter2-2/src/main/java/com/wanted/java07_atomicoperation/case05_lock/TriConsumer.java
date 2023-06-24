package com.wanted.java07_atomicoperation.case05_lock;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);
}
