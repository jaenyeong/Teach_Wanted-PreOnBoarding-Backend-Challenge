package com.wanted.java07_atomicoperation.case04_semaphore;

import com.wanted.java07_atomicoperation.case04_semaphore.usage03_mutex.MutexCounter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Case04_3 {
    /*
    # 뮤텍스 대신 바이너리 세마포어를 활용해 구현한 예시
    세마포어의 퍼밋 값을 `1`로 설정하여 바이너리 세마포어로 사용
    * 간단하게 사용하며 데드락을 방지할 수 있다는 장점이 있음
    * 하지만 병렬 처리가 되지 않고, 잘못하면 기아 상태의 스레드를 생성하게 될 수 있음

    ## 주의
    * 스레드의 퍼밋 획득 순서에 대한 처리가 필요함
    * 데드락을 피하기 위해 요청 작업 완료 시 퍼밋 반납을 적절히 처리할 것
    * ReentrantLock과 달리 재진입을 허용하지 않기 때문에 재귀호출 등 상황을 잘 고려하여 사용할 것
     */

    private static final int COUNT = 5;
    private static final String MUTEX_HAS_QUEUED_THREADS_MESSAGE = "Mutex Counter [has] queued threads : ";
    private static final String MUTEX_HAS_NOT_QUEUED_THREADS_MESSAGE = "Mutex Counter [has not] queued threads : ";
    private static final String MUTEX_VALUE_EQUALS_EXPECTED_VALUE_MESSAGE = "Mutex Counter value equals expected value : ";

    public static void main(String[] args) {
        validateMutexCountAfterBlocked();
        System.out.println("-----------------------------------");

        validateMutexCountWithDelay();
    }

    private static void validateMutexCountAfterBlocked() {
        final MutexCounter counter = generateMutexCounter();

        System.out.println(MUTEX_HAS_QUEUED_THREADS_MESSAGE + counter.hasQueuedThreads());
    }

    private static void validateMutexCountWithDelay() {
        final MutexCounter counter = generateMutexCounter();

        System.out.println(MUTEX_HAS_QUEUED_THREADS_MESSAGE + counter.hasQueuedThreads());

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(MUTEX_HAS_NOT_QUEUED_THREADS_MESSAGE + (!counter.hasQueuedThreads()));
        System.out.println(MUTEX_VALUE_EQUALS_EXPECTED_VALUE_MESSAGE + (COUNT == counter.getValue()));
    }

    private static MutexCounter generateMutexCounter() {
        final ExecutorService threadPool = Executors.newFixedThreadPool(COUNT);
        final MutexCounter counter = new MutexCounter();

        IntStream.range(0, COUNT)
            .forEach(user -> threadPool.execute(counter::increase));
        threadPool.shutdown();

        return counter;
    }
}
