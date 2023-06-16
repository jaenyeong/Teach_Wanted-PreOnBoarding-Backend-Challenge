package com.wanted.java03_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskRunner {
    private static final int NUMBER_OF_TASK = 200_000;
    private final ExecutorService executors;

    public TaskRunner(final ExecutorService executors) {
        this.executors = executors;
    }

    public void runTasks() {
        final long startTime = System.currentTimeMillis();

        submitTasksToExecutors();

        final long endTime = System.currentTimeMillis();
        final long runningTime = endTime - startTime;
        System.out.println("Running time is " + runningTime + "ms");
    }

    private void submitTasksToExecutors() {
        for (int i = 1; i <= NUMBER_OF_TASK; i++) {
            final int currentJobNumber = i;
            executors.submit(() -> {
                final String threadName = Thread.currentThread().getName();
                System.out.println("[" + currentJobNumber + "] : " + threadName);
            });
        }

        executors.shutdown();

        try {
            if (!executors.awaitTermination(1, TimeUnit.SECONDS)) {
                executors.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executors.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
