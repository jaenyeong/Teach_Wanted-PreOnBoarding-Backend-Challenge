package com.wanted.java07_threaddump;

import java.util.ArrayList;
import java.util.List;

public class Example07 {
    private static final int ATTEMPT_TIMES = 100;
    private static final int CREATED_QTY_AT_A_TIME = 100;

    public static void main(String[] args) throws InterruptedException {
        final List<Thread> totalThreads = new ArrayList<>();

        for (int i = 0; i < ATTEMPT_TIMES; i++) {
            totalThreads.addAll(createThreads());

            System.out.println("Created " + CREATED_QTY_AT_A_TIME + " threads. Total threads: " + totalThreads.size());

            // 스레드를 더 생성하기 전에 10초 일시 중지하여 10초마다 스레드를 100개씩 생성
            Thread.sleep(10_000);
        }

        System.out.println(totalThreads.size());
    }

    private static List<Thread> createThreads() {
        final List<Thread> newThreads = new ArrayList<>();

        for (int i = 0; i < CREATED_QTY_AT_A_TIME; i++) {
            final Thread newThread = new Thread(() -> {
                try {
                    // 생성된 스레드 10초 일시 중지
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            newThread.start();
            newThreads.add(newThread);
        }

        return newThreads;
    }
}
