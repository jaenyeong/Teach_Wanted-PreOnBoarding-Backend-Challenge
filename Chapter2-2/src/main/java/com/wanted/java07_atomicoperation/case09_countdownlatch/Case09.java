package com.wanted.java07_atomicoperation.case09_countdownlatch;

import com.wanted.java07_atomicoperation.case09_countdownlatch.domain.BrokenWorker;
import com.wanted.java07_atomicoperation.case09_countdownlatch.domain.WaitingWorker;
import com.wanted.java07_atomicoperation.case09_countdownlatch.domain.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.Collections.synchronizedList;

public class Case09 {
    /*
    # CountDownLatch 사용 예시

    ## validateMainThreadBlockUntilCompletionWhenParallelProcessing 메서드
    모든 워커 스레드의 작업이 완료될 때까지 기다린 후 데이터를 삽입해 마지막에 결괏값이 있는지 확인

    ## validateMainThreadShouldTimeOutWhenFailingToParallelProcessing 메서드
    모든 워커 스레드의 작업을 주어진 시간까지 기다리지만 시간 초과가 발생한 경우 확인

    ## validateStartThemAtTheSameTimeWhenDoingLotsOfThreadsInParallel 메서드
    CountDownLatch를 필요한만큼 선언/사용하여 모든 워커 스레드들이 단계마다 같이 진행되는 지 확인
     */

    public static void main(String[] args) {
        validateMainThreadBlockUntilCompletionWhenParallelProcessing();
        System.out.println("----------------------------------------");

        validateMainThreadShouldTimeOutWhenFailingToParallelProcessing();
        System.out.println("----------------------------------------");

        validateStartThemAtTheSameTimeWhenDoingLotsOfThreadsInParallel();
    }

    // 병렬 처리가 완료될 때까지 메인 스레드가 대기하는지 확인
    private static void validateMainThreadBlockUntilCompletionWhenParallelProcessing() {
        final List<String> outputScraper = synchronizedList(new ArrayList<>());
        final CountDownLatch countDownLatch = new CountDownLatch(5);

        final Runnable task = new Worker(outputScraper, countDownLatch);
        final List<Thread> workers = generateWorkers(task);

        // 스레드 작업 실행
        workers.forEach(Thread::start);
        try {
            // 모든 워커 스레드가 작업을 종료할 때까지 블록(대기)
            countDownLatch.await();
            // 위 명령으로 모든 워커 스레드 작업이 마친 후 실행되기 때문에 "Latch released"는 맨 마지막에 삽입됨
            outputScraper.add("Latch released");

            System.out.println("outputScraper size : " + outputScraper.size());
            System.out.println("[Counted down] equals outputScraper[0] : " + "Counted down".equals(outputScraper.get(0)));
            printOutPutScraper(outputScraper, 1, 5);
            System.out.println("[Latch released] equals outputScraper[5]" + "Latch released".equals(outputScraper.get(5)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 병렬 처리 작업이 모종의 이유로 끝나지 않을 때 메인 스레드가 타임아웃을 발생시키는지 확인
    private static void validateMainThreadShouldTimeOutWhenFailingToParallelProcessing() {
        final List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        final Runnable task = new BrokenWorker(outputScraper, countDownLatch);

        final List<Thread> workers = generateWorkers(task);

        workers.forEach(Thread::start);
        try {
            // Broken Worker의 run 메서드는 4초간 스레드 슬립으로 타임아웃
            final boolean result = countDownLatch.await(3L, TimeUnit.SECONDS);
            System.out.println("result is [" + result + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 너무 많은 스레드가 병렬 처리 작업 시 스레드들이 동시에 작업을 시작하는지 확인
    private static void validateStartThemAtTheSameTimeWhenDoingLotsOfThreadsInParallel() {
        final List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        final CountDownLatch waitUntilThreadsReadyState = new CountDownLatch(5);
        final CountDownLatch wakeUpBlockedThreads = new CountDownLatch(1);
        final CountDownLatch waitUntilThreadsCompletedState = new CountDownLatch(5);

        final Runnable task = new WaitingWorker(outputScraper, waitUntilThreadsReadyState, wakeUpBlockedThreads, waitUntilThreadsCompletedState);
        final List<Thread> workers = generateWorkers(task);

        // 실행
        workers.forEach(Thread::start);
        try {
            // 모든 워커 스레드가 작업을 시작할 때까지 블록(대기)
            waitUntilThreadsReadyState.await();
            outputScraper.add("Workers ready");

            // 블록됐던 모든 워커 스레드를 깨워 작업 시작
            wakeUpBlockedThreads.countDown();

            // 모든 워커 스레드가 작업을 종료할 때까지 블록(대기)
            waitUntilThreadsCompletedState.await(); // Block until workers finish
            outputScraper.add("Workers complete");

            // 확인
            System.out.println("outputScraper size : " + outputScraper.size());
            System.out.println("[Workers ready] equals outputScraper[0] : " + "Workers ready".equals(outputScraper.get(0)));
            printOutPutScraper(outputScraper, 1, 6);
            System.out.println("[Workers complete] equals outputScraper[6] : " + "Workers complete".equals(outputScraper.get(6)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printOutPutScraper(List<String> outputScraper, int from, int to) {
        final String expectedValue = "Counted down";

        for (int i = from; i < to; i++) {
            System.out.println("[" + expectedValue + "] equals outputScraper[" + i + "] value : " + expectedValue.equals(outputScraper.get(i)));
        }
    }

    private static List<Thread> generateWorkers(Runnable task) {
        return Stream.generate(() -> new Thread(task))
            .limit(5)
            .toList();
    }
}
