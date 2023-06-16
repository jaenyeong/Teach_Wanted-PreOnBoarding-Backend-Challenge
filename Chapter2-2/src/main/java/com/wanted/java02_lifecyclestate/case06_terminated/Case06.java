package com.wanted.java02_lifecyclestate.case06_terminated;

public class Case06 {
    /*
    # 스레드 TERMINATED 상태
    실행을 완료했거나, 비정상적으로 종료된 후 스레드가 죽어있는(종료된) 상태
     */

    public static void main(String[] args) {
        final Thread terminatedThread = new Thread(() -> System.out.println("Thread is running..."));
        terminatedThread.start();

        try {
            Thread.sleep(1_000);
            System.out.println(terminatedThread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
