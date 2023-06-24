package com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage;

import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage03_reentrancy.ReentrantSyncIncrementer;

public class Usage02 extends AbstractUsage {
    /*
    # `synchronized` 영역 재진입 예시
    `synchronized` 메서드와 블록 내에 또 Synchronized 영역이 있는 경우 이때는 기존에 획득한 락을 유지
    동일한 락에 대해 여러 번 재진입이 가능하다는 것을 의미함
     */

    public static void main(String[] args) {
        // 락을 이미 획득한 경우 계속 획득할 수 있음
        increaseCountAsUpperBoundBy(new ReentrantSyncIncrementer());
    }
}
