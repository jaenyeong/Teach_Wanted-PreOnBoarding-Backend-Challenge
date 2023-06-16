package com.wanted.java03_executors.case04_signlethreadexecutor;

import com.wanted.java03_executors.TaskRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Case04 {
    /*
    # `newSingleThreadExecutor()` 메서드 (`FinalizableDelegatedExecutorService` 클래스 반환)
    무제한 크기의 작업 큐에서 가져온 작업을 단일 워커 스레드로 처리하는 Executor를 생성
    다만 해당 스레드가 실행 중 오류로 인해 종료되면 후속 작업을 위해 새 스레드가 생성됨
    작업이 순차적으로 진행되며 주어진 시간에 작업은 단 하나씩만 처리됨
    `newFixedThreadPool(1)`과는 다르게 생성된 executor 객체는 추가 스레드를 사용할 수 없도록 강제함
     */

    public static void main(String[] args) {
        final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        final TaskRunner runner = new TaskRunner(singleThreadExecutor);
        runner.runTasks();
    }
}
