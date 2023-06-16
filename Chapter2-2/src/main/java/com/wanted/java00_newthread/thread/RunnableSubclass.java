package com.wanted.java00_newthread.thread;

public class RunnableSubclass implements Runnable {
    private final String threadName;

    public RunnableSubclass(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println(this.threadName + " is running...");
    }
}
