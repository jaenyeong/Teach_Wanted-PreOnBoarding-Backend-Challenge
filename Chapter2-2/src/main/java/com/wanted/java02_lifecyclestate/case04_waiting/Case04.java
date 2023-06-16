package com.wanted.java02_lifecyclestate.case04_waiting;

import com.wanted.java02_lifecyclestate.case04_waiting.thread.WaitingThread;

public class Case04 {
    /*
    # 스레드 WAITING 상태
    다른 스레드의 특정 작업이 수행되는 것을 기다리는 상태

    ## WAITING 상태가 되는 조건
    * `Object.wait()` (no time)
      * `Object.notify()` 또는 `Object.notifyAll()`가 호출될 때까지 대기
    * `Thread.join()` (no time)
      * 현재 스레드가 지정된 스레드가 종료될 때까지 대기
    * `LockSupport.park()`
      * 퍼밋이 유효하지 않은 경우 해당 스레드를 비활성화하는 명령
     */

    public static void main(String[] args) {
        final WaitingThread waitingThread = new WaitingThread();
        waitingThread.start();
    }
}
