package com.wanted.java07_atomicoperation.case04_semaphore.usage01_semaphore;

import java.util.concurrent.Semaphore;

public class LoginQueue {
    private final Semaphore semaphore;

    public LoginQueue(int slotLimit) {
        semaphore = new Semaphore(slotLimit);
    }

    // 퍼밋이 가능한 상태면 true, 아니면 false 반환
    // `acquire()` 메서드는 퍼밋을 획득, 사용할 수 있을 때까지 차단
    public boolean tryLogin() {
        return semaphore.tryAcquire();
    }

    // 획득한 퍼밋을 반환
    public void logout() {
        semaphore.release();
    }

    // 현재 사용 가능한 퍼밋 개수 반환
    public int availableSlots() {
        return semaphore.availablePermits();
    }
}
