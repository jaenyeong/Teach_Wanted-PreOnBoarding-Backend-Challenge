package com.wanted.java07_atomicoperation.case05_lock.usage03_stampedlock;

import com.wanted.java07_atomicoperation.case05_lock.TriConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Usage03 {
    /*
    # Optimistic Read 사용 예시
     */

    private static final String SAVE_KEY = "KEY ";
    private static final String TEST_KEY = "TEST";

    public static void main(String[] args) {
        validateTryToWrite("When Read Lock mode, ", 6, Usage03::get);
        System.out.println("--------------------------------");

        validateTryToWrite("When Optimistic Read mode, ", 5, Usage03::optimisticGet);
    }

    private static void validateTryToWrite(String message, int threadCount, TriConsumer<OptimisticGetStore, Integer, ExecutorService> consumer) {
        final OptimisticGetStore store = new OptimisticGetStore();
        final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            threadPool.execute(() -> store.put(SAVE_KEY + idx, "value" + idx));
        }

        consumer.accept(store, threadCount, threadPool);

        try {
            Thread.sleep(3_000);
            System.out.println(message + TEST_KEY + " is : " + store.get(TEST_KEY));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

    private static void get(OptimisticGetStore store, int threadCount, ExecutorService threadPool) {
        getAndWriteByReadMode((key) -> {
            final String data = store.get(key);
            store.put(TEST_KEY, "Value");
            return data;
        }, threadCount, threadPool);
    }

    private static void optimisticGet(OptimisticGetStore store, int threadCount, ExecutorService threadPool) {
        getAndWriteByReadMode((key) -> {
            final String data = store.optimisticGet(key);
            store.put(TEST_KEY, "Optimistic Value");
            return data;
        }, threadCount, threadPool);
    }

    private static void getAndWriteByReadMode(CustomSupplier<String> task, int threadCount, ExecutorService threadPool) {
        for (int i = 0; i < threadCount; i++) {
            final int idx = i;

            threadPool.execute(() -> {
                try {
                    final String data = task.run(SAVE_KEY + idx);
                    System.out.println("Reading Data : " + data);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

