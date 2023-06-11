package com.wanted.java07_threaddump.case00_jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Case00 {
    /*
    # jmx(Java Management Extensions)로 스레드덤프 분석
    실행중인 앱의 상태를 모니터링, 설정을 변경할 수 있는 API

    ## 실행 결과 분석
    * 순서대로 각각 스레드명, 스레드 상태, 스레드 스택 트레이싱
    >>>> Thread name: pool-1-thread-1
    >>>> Thread State: TIMED_WAITING
    >>>> Stack trace:
    >>>>     java.base@17.0.7/java.lang.Thread.sleep(Native Method)
    >>>>     app//com.wanted.java07_threaddump.case00_jmx.Case00.lambda$main$0(Case00.java:125)
    >>>>     app//com.wanted.java07_threaddump.case00_jmx.Case00$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    >>>>     java.base@17.0.7/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
    >>>>     java.base@17.0.7/java.util.concurrent.FutureTask.run(FutureTask.java:264)
    >>>>     java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
    >>>>     java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
    >>>>     java.base@17.0.7/java.lang.Thread.run(Thread.java:833)

    # 전체 실행 결과
    Thread name: main
    Thread State: RUNNABLE
    Stack trace:
        java.management@17.0.7/sun.management.ThreadImpl.dumpThreads0(Native Method)
        java.management@17.0.7/sun.management.ThreadImpl.dumpAllThreads(ThreadImpl.java:521)
        java.management@17.0.7/sun.management.ThreadImpl.dumpAllThreads(ThreadImpl.java:509)
        app//com.wanted.java07_threaddump.case00_jmx.Case00.createThreadDump(Case00.java:148)
        app//com.wanted.java07_threaddump.case00_jmx.Case00.main(Case00.java:137)
    ----------------------
    Thread name: Reference Handler
    Thread State: RUNNABLE
    Stack trace:
        java.base@17.0.7/java.lang.ref.Reference.waitForReferencePendingList(Native Method)
        java.base@17.0.7/java.lang.ref.Reference.processPendingReferences(Reference.java:253)
        java.base@17.0.7/java.lang.ref.Reference$ReferenceHandler.run(Reference.java:215)
    ----------------------
    Thread name: Finalizer
    Thread State: WAITING
    Stack trace:
        java.base@17.0.7/java.lang.Object.wait(Native Method)
        java.base@17.0.7/java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:155)
        java.base@17.0.7/java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:176)
        java.base@17.0.7/java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:172)
    ----------------------
    Thread name: Signal Dispatcher
    Thread State: RUNNABLE
    Stack trace:
    ----------------------
    Thread name: Notification Thread
    Thread State: RUNNABLE
    Stack trace:
    ----------------------
    Thread name: Common-Cleaner
    Thread State: TIMED_WAITING
    Stack trace:
        java.base@17.0.7/java.lang.Object.wait(Native Method)
        java.base@17.0.7/java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:155)
        java.base@17.0.7/jdk.internal.ref.CleanerImpl.run(CleanerImpl.java:140)
        java.base@17.0.7/java.lang.Thread.run(Thread.java:833)
        java.base@17.0.7/jdk.internal.misc.InnocuousThread.run(InnocuousThread.java:162)
    ----------------------
    Thread name: pool-1-thread-1
    Thread State: TIMED_WAITING
    Stack trace:
        java.base@17.0.7/java.lang.Thread.sleep(Native Method)
        app//com.wanted.java07_threaddump.case00_jmx.Case00.lambda$main$0(Case00.java:125)
        app//com.wanted.java07_threaddump.case00_jmx.Case00$$Lambda$1/0x0000000800000a08.run(Unknown Source)
        java.base@17.0.7/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
        java.base@17.0.7/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        java.base@17.0.7/java.lang.Thread.run(Thread.java:833)
    ----------------------
    Thread name: pool-1-thread-2
    Thread State: TIMED_WAITING
    Stack trace:
        java.base@17.0.7/java.lang.Thread.sleep(Native Method)
        app//com.wanted.java07_threaddump.case00_jmx.Case00.lambda$main$0(Case00.java:125)
        app//com.wanted.java07_threaddump.case00_jmx.Case00$$Lambda$1/0x0000000800000a08.run(Unknown Source)
        java.base@17.0.7/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
        java.base@17.0.7/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        java.base@17.0.7/java.lang.Thread.run(Thread.java:833)
    ----------------------
    Thread name: pool-1-thread-3
    Thread State: TIMED_WAITING
    Stack trace:
        java.base@17.0.7/java.lang.Thread.sleep(Native Method)
        app//com.wanted.java07_threaddump.case00_jmx.Case00.lambda$main$0(Case00.java:125)
        app//com.wanted.java07_threaddump.case00_jmx.Case00$$Lambda$1/0x0000000800000a08.run(Unknown Source)
        java.base@17.0.7/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
        java.base@17.0.7/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        java.base@17.0.7/java.lang.Thread.run(Thread.java:833)
    ----------------------
    Thread name: pool-1-thread-4
    Thread State: TIMED_WAITING
    Stack trace:
        java.base@17.0.7/java.lang.Thread.sleep(Native Method)
        app//com.wanted.java07_threaddump.case00_jmx.Case00.lambda$main$0(Case00.java:125)
        app//com.wanted.java07_threaddump.case00_jmx.Case00$$Lambda$1/0x0000000800000a08.run(Unknown Source)
        java.base@17.0.7/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
        java.base@17.0.7/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        java.base@17.0.7/java.lang.Thread.run(Thread.java:833)
    ----------------------
    Thread name: pool-1-thread-5
    Thread State: TIMED_WAITING
    Stack trace:
        java.base@17.0.7/java.lang.Thread.sleep(Native Method)
        app//com.wanted.java07_threaddump.case00_jmx.Case00.lambda$main$0(Case00.java:125)
        app//com.wanted.java07_threaddump.case00_jmx.Case00$$Lambda$1/0x0000000800000a08.run(Unknown Source)
        java.base@17.0.7/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:539)
        java.base@17.0.7/java.util.concurrent.FutureTask.run(FutureTask.java:264)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
        java.base@17.0.7/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
        java.base@17.0.7/java.lang.Thread.run(Thread.java:833)

     */

    private static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        // 5개의 스레드를 가진 스레드 풀 생성
        // Executors를 사용하면 데몬 스레드가 아닌 사용자 스레드로 처리됨
        final ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 각 스레드에 시간이 오래 걸리는 작업 할당
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                while (running) {
                    try {
                        Thread.sleep(5_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // 메인 스레드가 조금 기다리도록 설정
        Thread.sleep(7_000);

        // ThreadDump 생성
        createThreadDump();

        // 스레드 풀 종료
        executorService.shutdown();

        running = false;
    }

    private static void createThreadDump() {
        // 모든 스레드 가져오기
        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        final ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);

        // 각 스레드 정보 출력
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("Thread name: " + threadInfo.getThreadName());
            System.out.println("Thread State: " + threadInfo.getThreadState());
            System.out.println("Stack trace: ");
            for (StackTraceElement stackTraceElement : threadInfo.getStackTrace()) {
                System.out.println("\t" + stackTraceElement);
            }
            System.out.println("----------------------");
        }
    }
}
