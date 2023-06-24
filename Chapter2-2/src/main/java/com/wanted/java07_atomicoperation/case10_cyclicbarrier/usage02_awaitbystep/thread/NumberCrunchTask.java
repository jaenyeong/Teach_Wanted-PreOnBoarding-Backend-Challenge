package com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage02_awaitbystep.thread;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberCrunchTask implements Runnable {
    private static final Random RANDOM = new Random();

    private final List<List<Integer>> partialResults;
    private final CyclicBarrier cyclicBarrier;
    private final int numberPartialResults;

    public NumberCrunchTask(List<List<Integer>> partialResults, CyclicBarrier cyclicBarrier, int numberPartialResults) {
        this.partialResults = partialResults;
        this.cyclicBarrier = cyclicBarrier;
        this.numberPartialResults = numberPartialResults;
    }

    @Override
    public void run() {
        final String currentThreadName = Thread.currentThread().getName();

        final List<Integer> partialResult = IntStream.range(0, numberPartialResults)
            .map(i -> RANDOM.nextInt(10))
            .peek(randomValue -> System.out.println(currentThreadName + ": Crunching some numbers! Final result - " + randomValue))
            .boxed()
            .collect(Collectors.toList());

        partialResults.add(partialResult);

        try {
            System.out.println(currentThreadName + " waiting for others to reach barrier.");
            // 작업을 마친 뒤 배리어 도달하여 다른 파티 스레드가 도달할 때까지 대기
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
