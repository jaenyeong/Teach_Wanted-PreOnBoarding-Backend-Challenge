package com.wanted.java07_atomicoperation.case07_concurrenthashmap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;

public class Case07 {
    /*
    # concurrentHashMap 사용 예시

    ## validateGetValueDuringWrite 메서드
    쓰기(수정) 작업과 읽기 작업이 동시에 처리되는 경우 쓰기 작업 이전에 기존 값을 읽어옴

    ## validateWaitingToWriteSameTargetDuringWriting 메서드
    여러 스레드에서 같은 객체에 접근하여 쓰기 작업을 하는 경우 순서대로 처리하게 되어 값이 반영된 것을 확인

    ## validateWritingToNotSameTargetDuringWriting 메서드
    여러 스레드에서 쓰기 작업을 하더라도 다른 객체에 접근하는 경우 대기하지 않고 처리됨
     */

    private static final String KEY1 = "Key1";
    private static final String KEY2 = "Key2";

    public static void main(String[] args) {
        validateGetValueDuringWriting();
        System.out.println("-------------------------");

        validateWaitingToWriteSameTargetDuringWriting();
        System.out.println("-------------------------");

        validateWritingToNotSameTargetDuringWriting();
    }

    // 한 스레드가 쓰기 작업 중일 때 다른 읽기 스레드에서 가져오는 값 확인
    private static void validateGetValueDuringWriting() {
        final Map<String, Integer> frequencyMap = generateFrequencyMap();
        final ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // 1ms 슬립 후 쓰기(수정) 작업 처리 (값을 바로 가져오는 읽기 작업을 위해 슬립)
        final Runnable writeAfter1ms = () -> frequencyMap.computeIfPresent(KEY1, (k, v) -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 기존 값 0을 1 증가
            return v + 1;
        });

        // 현재 값 바로 가져오기
        final Callable<Integer> readNow = () -> frequencyMap.get(KEY1);
        // 2ms 슬립 후 값을 가져와서 위 쓰기(수정) 작업과 동시에 해당 값을 가져옴
        final Callable<Integer> readAfter2ms = () -> {
            Thread.sleep(2);
            return frequencyMap.get(KEY1);
        };

        // 쓰기 작업 실행
        threadPool.submit(writeAfter1ms);

        try {
            final List<Future<Integer>> results = threadPool.invokeAll(asList(readNow, readAfter2ms));
            final Integer nowValue = results.get(0).get();
            final Integer after2msValue = results.get(1).get();

            System.out.println("nowValue is [" + nowValue + "]");
            // 동시 실행되는 쓰기 작업의 최종 값이 아닌 직전의 값을 읽음
            System.out.println("after2msValue is [" + after2msValue + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }

    // 한 스레드가 쓰기 작업 중일 때 다른 스레드가 같은 객체에 쓰기 작업 시도 시 대기 후 처리
    private static void validateWaitingToWriteSameTargetDuringWriting() {
        final Map<String, Integer> frequencyMap = generateFrequencyMap();
        final ExecutorService threadPool = Executors.newFixedThreadPool(2);

        final String sameKey = KEY1;

        // 같은 객체에 접근하는 쓰기 작업 2개 생성
        final Callable<Integer> writeAfter5ms = createWritingTask(frequencyMap, sameKey, 5);
        final Callable<Integer> writeAfter1ms = createWritingTask(frequencyMap, sameKey, 1);

        try {
            // 순차적으로 쓰기 작업을 실행
            final Future<Integer> result1 = threadPool.submit(writeAfter5ms);
            Thread.sleep(1);
            final Future<Integer> result2 = threadPool.submit(writeAfter1ms);

            // 같은 객체 작업 시 순서대로 처리하기 때문에 writeAfter1ms 결과 값이 writeAfter5ms 결과 값을 반영한 결과라는 것을 확인
            System.out.println("writeAfter5ms task result : " + result1.get());
            System.out.println("writeAfter1ms task result : " + result2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }

    // 한 스레드가 쓰기 작업 중일 때 다른 스레드가 다른 객체에 쓰기 작업 시도 시 바로 처리
    private static void validateWritingToNotSameTargetDuringWriting() {
        final Map<String, Integer> frequencyMap = generateFrequencyMap();
        final ExecutorService threadPool = Executors.newFixedThreadPool(2);

        // KEY1을 통해 한 객체에 접근하는 쓰기 작업 생성
        final Callable<Integer> writeAfter3Sec = createWritingTask(frequencyMap, KEY1, 3_000);

        final AtomicLong time = new AtomicLong(System.currentTimeMillis());
        final Callable<Integer> writeAfter1Sec = createWritingTaskWithTime(frequencyMap, time);

        try {
            threadPool.invokeAll(asList(writeAfter3Sec, writeAfter1Sec));
            System.out.println("Total running time : " + time.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }

    private static Callable<Integer> createWritingTask(Map<String, Integer> frequencyMap, String sameKey, int millis) {
        return () -> frequencyMap.computeIfPresent(sameKey, (k, v) -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return v + 1;
        });
    }

    private static Callable<Integer> createWritingTaskWithTime(Map<String, Integer> frequencyMap, AtomicLong time) {
        return () -> frequencyMap.computeIfPresent(KEY2, (k, v) -> {
            try {
                Thread.sleep(1_000);
                time.set((System.currentTimeMillis() - time.get()) / 1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return v + 1;
        });
    }

    private static Map<String, Integer> generateFrequencyMap() {
        final Map<String, Integer> frequencyMap = new ConcurrentHashMap<>();
        frequencyMap.put("Key1", 0);
        frequencyMap.put("Key2", 0);
        frequencyMap.put("Key3", 0);
        return frequencyMap;
    }
}
