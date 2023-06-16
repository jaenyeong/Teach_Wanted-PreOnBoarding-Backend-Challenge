package com.wanted.java00_newthread.thread;

public class ThreadSubclass extends Thread {
    public ThreadSubclass() {
        super("ThreadSubclass thread");
    }

    @Override
    public void run() {
        System.out.println(this.getName() + " is running...");
    }
}
