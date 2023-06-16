package com.wanted.java02_lifecyclestate.case02_runnable;

public class Case02 {
    /*
    # 스레드 RUNNABLE 상태
     */

    public static void main(String[] args) {
        final Thread runnableStateThread = new Thread(() -> System.out.println("Thread is running..."));
        runnableStateThread.start();

        // 스레드 스케줄러에 의해 작업이 바로 완료될 수 있어서 항상 `RUNNABLE` 상태가 출력된다는 보장은 없음
        System.out.println(runnableStateThread.getState());
    }
}
