package com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage02_awaitbystep.thread;

import java.util.List;

public class ResultAggregateTask implements Runnable {
    private final List<List<Integer>> partialResults;
    private final int numberPartialResults;
    private final int numberOfWorkers;

    public ResultAggregateTask(List<List<Integer>> partialResults, int numberPartialResults, int numberOfWorkers) {
        this.partialResults = partialResults;
        this.numberPartialResults = numberPartialResults;
        this.numberOfWorkers = numberOfWorkers;
    }

    @Override
    public void run() {
        final String thisThreadName = Thread.currentThread().getName();
        System.out.println(thisThreadName + ": Computing final sum of " + numberOfWorkers + " workers, having " + numberPartialResults + " results each.");

        int sum = 0;
        for (List<Integer> threadResult : partialResults) {
            System.out.print("Adding ");
            final int partialSum = threadResult.stream()
                .peek(partialResult -> System.out.print(partialResult + " "))
                .mapToInt(Integer::intValue)
                .sum();
            System.out.println();

            sum += partialSum;
        }

        System.out.println(Thread.currentThread().getName() + ": Final result = " + sum);
    }
}
