package com.wanted.java02_lifecyclestate.case03_blocked.thread;

public class BlockThread extends Thread {
    @Override
    public void run() {
        executeSyncMethod();
    }

    private static synchronized void executeSyncMethod() {
        while (true) {
            // 해당 메서드에 먼저 모니터 락을 획득하고 진입한 스레드는 무한 루프로 빠져나가지 못하고
            // 뒤이어 액세스를 시도하는 스레드는 블락 상태로 대기하게 됨
            // 실제 실행 시간이 소요되는 메서드라고 가정하여 무한 루프로 처리
        }
    }
}
