package com.wanted.java03_executors.case05_singlethreadscheduledexecutor;

import com.wanted.java03_executors.TaskRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Case05 {
    /*
    # `newSingleThreadScheduledExecutor()` 메서드 (`DelegatedScheduledExecutorService` 클래스 반환)
    나중에 실행할 작업과 주기적으로 실행할 작업을 스케줄링(예약)할 수 있는 단일 스레드 Executor를 생성
    다만 해당 스레드가 실행 중 오류로 인해 종료되면 후속 작업을 위해 새 스레드가 생성됨
    작업이 순차적으로 진행되며 주어진 시간에 작업은 단 하나씩만 처리됨
    `newScheduledThreadPool(1)`과는 다르게 생성된 executor 객체는 추가 스레드를 사용할 수 없도록 강제함
     */

    public static void main(String[] args) {
        final ExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        final TaskRunner runner = new TaskRunner(singleThreadScheduledExecutor);
        runner.runTasks();
    }
}
