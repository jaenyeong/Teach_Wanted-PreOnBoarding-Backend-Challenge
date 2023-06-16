package com.wanted.java01_threadexit.case02_userthreadhavedied;

import com.wanted.java01_threadexit.case02_userthreadhavedied.thread.CustomThread;

public class Case02_1 {
    /*
    # JVM이 스레드를 종료 시키는 경우

    ## User Thread가 모두 종료된 경우
    진행중인 유저 스레드가 `없다면` 데몬 스레드가 실행되는 중에도 프로세스가 종료됨
     */

    public static void main(String[] args) {
        final CustomThread daemonThread = new CustomThread("Daemon Thread", 1_000);
        daemonThread.setDaemon(true);
        daemonThread.start();

        System.out.println("!!! [ main method is finished ] !!!");
    }
}
