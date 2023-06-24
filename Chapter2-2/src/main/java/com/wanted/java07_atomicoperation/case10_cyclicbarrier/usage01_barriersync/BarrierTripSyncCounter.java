package com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage01_barriersync;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BarrierTripSyncCounter {
    /*
    # 설명
    배리어 설정 수와 스레드 수가 다를 때 배리어 설정 수만큼 도달하면 설정 값이 다시 초기화됨
     */

    private final AtomicInteger barrierReachCount;
    private final int barrierLimitCount;
    private final int threadCount;

    public BarrierTripSyncCounter(int barrierLimitCount, int threadCount) {
        barrierReachCount = new AtomicInteger(0);
        this.barrierLimitCount = barrierLimitCount;
        this.threadCount = threadCount;
    }

    public void printCountTrips() {
        // 배리어 설정 (배리어에 도달할 때마다 카운트 증가)
        final CyclicBarrier barrier = new CyclicBarrier(barrierLimitCount, barrierReachCount::incrementAndGet);
        final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            threadPool.execute(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();

        try {
            threadPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Barrier reach count : " + barrierReachCount.get());
    }
}
