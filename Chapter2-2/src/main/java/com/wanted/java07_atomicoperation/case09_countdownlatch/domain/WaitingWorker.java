package com.wanted.java07_atomicoperation.case09_countdownlatch.domain;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WaitingWorker implements Runnable {
    private final List<String> outputScraper;
    private final CountDownLatch waitUntilThreadsReadyState;
    private final CountDownLatch wakeUpBlockedThreads;
    private final CountDownLatch waitUntilThreadsCompletedState;

    public WaitingWorker(
        List<String> outputScraper,
        CountDownLatch waitUntilThreadsReadyState,
        CountDownLatch wakeUpBlockedThreads,
        CountDownLatch waitUntilThreadsCompletedState
    ) {
        this.outputScraper = outputScraper;
        this.waitUntilThreadsReadyState = waitUntilThreadsReadyState;
        this.wakeUpBlockedThreads = wakeUpBlockedThreads;
        this.waitUntilThreadsCompletedState = waitUntilThreadsCompletedState;
    }

    @Override
    public void run() {
        waitUntilThreadsReadyState.countDown();

        try {
            wakeUpBlockedThreads.await();
            outputScraper.add("Counted down");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            waitUntilThreadsCompletedState.countDown();
        }
    }
}
