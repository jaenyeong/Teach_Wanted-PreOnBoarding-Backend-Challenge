package com.wanted.java07_atomicoperation.case04_semaphore;

import com.wanted.java07_atomicoperation.case04_semaphore.usage01_semaphore.LoginQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Case04_1 {
    /*
    # Semaphore 사용 예시
    각 요소에 엑세스하기 위해 권한을 획득 후 엑세스 가능하며 작업 종료 후 풀로 권한 반환
    바이너리 세마포어는 뮤텍스와 같은 개념(상호 배제)으로 사용될 수 있으나 소유자가 아닌 스레드에 의해 락이 해제될 수 있기 때문에 주의가 필요함
    생성자에서 받는 `fairness` 파라미터는 스레드 허가 획득에 순서(FIFO)를 결정짓는 파라미터

    ## 주의
    구현된 로그인 큐는 사용 예시일 뿐
    실제로도 이런 래퍼 클래스를 활용한다면 로그인 큐의 메서드 진입에 대한 동기화도 별도로 처리되어야 함
     */

    private static final String AVAILABLE_SLOT_NOT_EXIST_MESSAGE = "LoginQueue available slots size is [0] : ";
    private static final String AVAILABLE_SLOT_EXIST_MESSAGE = "LoginQueue available slots size is [greater than 0] : ";
    private static final String CAN_NOT_TRY_TO_LOGIN_MESSAGE = "[Can not try] to login : ";
    private static final String CAN_TRY_TO_LOGIN_MESSAGE = "[Can try] to login : ";

    public static void main(String[] args) {
        validateBlockedStateWhenReachLimitSlots();
        System.out.println("--------------------");

        validateAvailableStateWhenLogout();
    }

    private static void validateBlockedStateWhenReachLimitSlots() {
        final LoginQueue loginQueue = generateLoginQ();

        System.out.println(AVAILABLE_SLOT_NOT_EXIST_MESSAGE + (0 == loginQueue.availableSlots()));
        System.out.println(CAN_NOT_TRY_TO_LOGIN_MESSAGE + !loginQueue.tryLogin());
    }

    private static void validateAvailableStateWhenLogout() {
        final LoginQueue loginQueue = generateLoginQ();

        System.out.println(AVAILABLE_SLOT_NOT_EXIST_MESSAGE + (0 == loginQueue.availableSlots()));
        loginQueue.logout();

        System.out.println(AVAILABLE_SLOT_EXIST_MESSAGE + (loginQueue.availableSlots() > 0));
        System.out.println(CAN_TRY_TO_LOGIN_MESSAGE + loginQueue.tryLogin());
    }

    private static LoginQueue generateLoginQ() {
        final int slots = 10;
        final ExecutorService threadPool = Executors.newFixedThreadPool(slots);
        final LoginQueue loginQueue = new LoginQueue(slots);

        IntStream.range(0, slots)
            .forEach(user -> threadPool.execute(loginQueue::tryLogin));
        threadPool.shutdown();

        return loginQueue;
    }
}
