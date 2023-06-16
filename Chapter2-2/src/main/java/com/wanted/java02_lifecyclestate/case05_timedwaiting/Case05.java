package com.wanted.java02_lifecyclestate.case05_timedwaiting;

public class Case05 {
    /*
    # 스레드 TIMED_WAITING 상태
    지정된 시간 내에서 다른 스레드의 특정 작업이 수행되는 것을 기다리는 상태

    ## TIMED_WAITING 상태가 되는 조건
    * `Thread.sleep()`
       * 시스템 시간을 기준으로 수면 상태로 변경되지만 모니터의 소유권은 `유지함`
    * `Object.wait()` (with time)
       * `Object.notify()` 또는 `Object.notifyAll()`가 호출될 때까지 대기
    * `Thread.join()` (with time)
       * 현재 스레드에서 지정된 스레드가 종료될 때까지 대기
    * `LockSupport.parkNanos()`
       * 권한이 없는 경우 주어진 시간만큼 스레드를 비활성화
    * `LockSupport.parkUntil()`
       * 권한이 없는 경우 지정한 시간(데드라인)까지 스레드를 비활성화
     */

    public static void main(String[] args) {
        final Thread timedWatingThread = new Thread(() -> {
            System.out.println("Thread is running...");

            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });

        timedWatingThread.start();

        try {
            // 스레드 스케줄러가 timedWatingThread를 충분히 실행할 시간을 주기 위해서 메인 스레드를 1초간 슬립시킴
            Thread.sleep(1_000);
            System.out.println(timedWatingThread.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
