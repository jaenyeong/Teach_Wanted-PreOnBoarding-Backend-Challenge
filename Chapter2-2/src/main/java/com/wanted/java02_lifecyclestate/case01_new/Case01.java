package com.wanted.java02_lifecyclestate.case01_new;

public class Case01 {
    /*
    # 스레드 NEW 상태
     */

    public static void main(String[] args) {
        final Thread newStateThread = new Thread(() -> System.out.println("Thread is running..."));

        System.out.println(newStateThread.getState());
    }
}
