package com.wanted.java07_atomicoperation.case04_semaphore;

import com.wanted.java07_atomicoperation.case04_semaphore.usage02_timedsemaphore.DelayQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Case04_2 {
    /*
    # 아파치의 TimedSemaphore 사용 예시
    특정 제한된 기간(시간) 동안에 요청한 수만큼의 작업을 수행하기 위해 활용

    ## 주의
    * 제한된 시간의 주기마다 요청 가능한 횟수를 재설정하지만 횟수 초과를 허용하는 `버스트`는 허용되지 않음
    * 스레드가 아닌 작업을 기준으로 횟수를 제한함
     */

    private static final String AVAILABLE_SLOT_NOT_EXIST_MESSAGE = "DelayQueue available slots size is 0 : ";
    private static final String AVAILABLE_SLOT_EXIST_MESSAGE = "DelayQueue available slots size is greater than 0 : ";
    private static final String CAN_TRY_TO_ADD_REQUEST_MESSAGE = "Can try to add : ";

    private static final int SAFE_EXIT = 0;

    public static void main(String[] args) {
        final int slots = 50;
        final ExecutorService threadPool = Executors.newFixedThreadPool(slots);
        final DelayQueue delayQueue = new DelayQueue(1, slots);

        IntStream.range(0, slots)
            .forEach(user -> threadPool.execute(delayQueue::tryAdd));
        threadPool.shutdown();

        System.out.println(AVAILABLE_SLOT_NOT_EXIST_MESSAGE + (0 == delayQueue.availableSlots()));

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(AVAILABLE_SLOT_EXIST_MESSAGE + (delayQueue.availableSlots() > 0));
        System.out.println(CAN_TRY_TO_ADD_REQUEST_MESSAGE + delayQueue.tryAdd());

        // 프로세스 종료를 위해 추가
        System.exit(SAFE_EXIT);
    }
}
