package com.wanted.java06_synchronizedproblem.case01_racecondition.domain;

public class Counter {
    private int value = 0;

    public void increment() {
        value++;
    }

    public int getValue() {
        return value;
    }
}
