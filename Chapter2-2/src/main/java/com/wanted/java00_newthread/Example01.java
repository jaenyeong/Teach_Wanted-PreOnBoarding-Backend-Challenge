package com.wanted.java00_newthread;

import com.wanted.java00_newthread.thread.RunnableSubclass;
import com.wanted.java00_newthread.thread.ThreadSubclass;

public class Example01 {
    /*
    # 새로운 스레드를 생성하는 법
    자세한 내용은 공식 문서 참조 (https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Thread.html)

    ## 기본적인 방법
    * Thread 클래스 상속
    * Thread 생성 시 구현한 Runnable 인터페이스를 파라미터로 사용
     */

    public static void main(String[] args) {
        final Thread threadSub = new ThreadSubclass();
        threadSub.start();

        final Thread runnableSub = new Thread(new RunnableSubclass("RunnableSub thread"));
        runnableSub.start();

        final Thread lambdaThread = new Thread(() -> System.out.println("Lambda thread is running..."));
        lambdaThread.start();

        System.out.println(Thread.currentThread().getName() + " is running...");

        System.out.println("!!! [ Main method is finished ] !!!");
    }
}
