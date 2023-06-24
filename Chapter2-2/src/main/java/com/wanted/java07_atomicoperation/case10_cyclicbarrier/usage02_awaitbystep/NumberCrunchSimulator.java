package com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage02_awaitbystep;

import com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage02_awaitbystep.thread.ResultAggregateTask;
import com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage02_awaitbystep.thread.NumberCrunchTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class NumberCrunchSimulator {
    /*
    # 설명
    멀티 스레드 별로 랜덤한 정수를 생성, 리스트에 누적한 후 모든 워커 스레드가 배리어에 도달하면
    ResultAggregateTask에 의해 랜덤 생성한 점수를 집계함
     */

    private final List<List<Integer>> partialResults = Collections.synchronizedList(new ArrayList<>());
    private final int numberPartialResults;
    private final int numberOfWorkers;

    public NumberCrunchSimulator(final int numberPartialResults, final int numberOfWorkers) {
        this.numberPartialResults = numberPartialResults;
        this.numberOfWorkers = numberOfWorkers;
    }

    public void run() {
        System.out.println("Spawning " + numberOfWorkers + " worker threads to compute " + numberPartialResults + " partial results each");

        // 배리어 설정 (스레드 수, 트리거 태스크)
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfWorkers, new ResultAggregateTask(partialResults, numberPartialResults, numberOfWorkers));

        // 생성된 워커 스레드가 랜덤한 정수를 생성, partialResults 리스트에 값을 누적
        for (int i = 0; i < numberOfWorkers; i++) {
            final Thread worker = new Thread(new NumberCrunchTask(partialResults, cyclicBarrier, numberPartialResults));
            worker.setName("Thread " + i);
            worker.start();
        }
    }
}
