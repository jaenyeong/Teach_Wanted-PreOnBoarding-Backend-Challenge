package com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

abstract class AbstractUsage {
    private static final int NUMBER_OF_THREADS = 3;
    private static final int UPPER_BOUND = 1_000;

    static void increaseCountAsUpperBoundBy(Counter incrementer) {
        final ExecutorService threadPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        IntStream.range(0, UPPER_BOUND)
            .forEach(count -> threadPool.submit(incrementer::increase));

        try {
            // 위 작업이 완료될 때까지 대기
            threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);

            final int finalCountValue = incrementer.getValue();
            final String incrementerName = incrementer.getCounterName();

            System.out.println("The final value of `" + incrementerName + "` is " + finalCountValue);
            System.out.println("[ Expected Value (1_000) == " + incrementerName + " ] is " + (UPPER_BOUND == finalCountValue));

            threadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
