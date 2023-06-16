package com.wanted.java02_lifecyclestate.case04_waiting.thread;

public class WaitingThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running...");
        final Thread thisThread = Thread.currentThread();

        final Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            System.out.println(thisThread.getState());
        });

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
