package com.wanted.java07_atomicoperation.case09_countdownlatch.domain;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {
    private final List<String> outputScraper;
    private final CountDownLatch countDownLatch;

    public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
        this.outputScraper = outputScraper;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Doing something");

        outputScraper.add("Counted down");
        countDownLatch.countDown();
    }
}
