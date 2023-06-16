package com.wanted.java02_lifecyclestate.case03_blocked;

import com.wanted.java02_lifecyclestate.case03_blocked.thread.BlockThread;

public class Case03 {
    /*
    # 스레드 BLOCKED 상태
    다른 스레드에 의해 락이 걸린 코드 영역에 접근하면서 모니터 락을 기다릴 때 이 상태가 됨
    * BlockThread 클래스의 `executeSyncMethod()` 메서드 설명 참조
     */

    private static final int SAFE_EXIT = 0;

    public static void main(String[] args) {
        final BlockThread firstBlockThread = new BlockThread();
        final BlockThread secondBlockThread = new BlockThread();

        firstBlockThread.start();
        secondBlockThread.start();

        try {
            Thread.sleep(1_000);

            System.out.println(secondBlockThread.getState());
            System.exit(SAFE_EXIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
