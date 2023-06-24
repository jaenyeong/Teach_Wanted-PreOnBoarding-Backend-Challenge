package com.wanted.java07_atomicoperation.case05_lock.usage01_reentrantlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Usage01 {
    /*
    # ReentrantLock 사용 예시
     */

    public static void main(String[] args) {
        validateExclusiveLock(2, printLockedStateWhenLockAcquired());
        validateExclusiveLock(4, printHasQueuedThreadsWhenLocked());
        validateExclusiveLock(4, printCorrectCountWhenGetCount());
    }

    private static void validateExclusiveLock(int threadCount, Consumer<ReentrantCounter> action) {
        final ReentrantCounter counter = new ReentrantCounter();
        final ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            threadPool.execute(counter::increase);
        }

        action.accept(counter);
        threadPool.shutdown();
    }

    private static Consumer<ReentrantCounter> printLockedStateWhenLockAcquired() {
        return (counter) -> System.out.println("ExclusiveLockCounter state is locked : " + counter.isLocked());
    }

    private static Consumer<ReentrantCounter> printHasQueuedThreadsWhenLocked() {
        return (counter) -> {
            try {
                Thread.sleep(1_000);
                System.out.println("ExclusiveLockCounter has queued threads : " + counter.hasQueuedThreads());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private static Consumer<ReentrantCounter> printCorrectCountWhenGetCount() {
        return (counter) -> {
            try {
                Thread.sleep(1_000);
                System.out.println("ExclusiveLockCounter final value is : " + counter.getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }
}
