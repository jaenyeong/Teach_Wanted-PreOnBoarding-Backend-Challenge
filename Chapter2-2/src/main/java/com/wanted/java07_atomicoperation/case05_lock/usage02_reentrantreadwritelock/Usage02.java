package com.wanted.java07_atomicoperation.case05_lock.usage02_reentrantreadwritelock;

import com.wanted.java07_atomicoperation.case05_lock.TriConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Usage02 {
    /*
    # ReentrantReadWriteLock 사용 예시
     */

    public static void main(String[] args) {
        validateConcurrentRead("When Writing, ", 3, Usage02::write);
        System.out.println("--------------------------------");

        validateConcurrentRead("When Reading, ", 5, Usage02::read);
    }

    private static void validateConcurrentRead(String message, int threadCount, TriConsumer<ConcurrentReadStore, Integer, ExecutorService> consumer) {
        final ConcurrentReadStore store = new ConcurrentReadStore();
        final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        consumer.accept(store, threadCount, threadPool);

        try {
            Thread.sleep(1_000);
            System.out.println(message + "ReadLock Available state is : " + store.isReadLockAvailable());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

    private static void write(ConcurrentReadStore store, int threadCount, ExecutorService threadPool) {
        for (int i = 0; i < threadCount; i++) {
            final int idx = i;

            threadPool.execute(() -> {
                try {
                    store.put("key" + idx, "value" + idx);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void read(ConcurrentReadStore store, int threadCount, ExecutorService threadPool) {
        for (int i = 0; i < threadCount; i++) {
            threadPool.execute(() -> store.get("key" + threadCount));
        }
    }
}
