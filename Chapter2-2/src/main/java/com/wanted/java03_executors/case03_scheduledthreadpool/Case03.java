package com.wanted.java03_executors.case03_scheduledthreadpool;

import com.wanted.java03_executors.TaskRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Case03 {
    /*
    # `newScheduledThreadPool(int corePoolSize)` 메서드 (`ScheduledThreadPoolExecutor` 클래스 반환)
    나중에 실행할 작업과 주기적으로 실행할 작업을 스케줄링(예약)할 수 있는 스레드 풀 생성
    `corePoolSize`는 `idle(유휴)` 상태일 때도 풀에 유지할 사이즈를 의미함 (작업을 쉬는 동안에도 유지할 최소 활성화 스레드 수)
    * 참고로 `maxPoolSize`는 말 그대로 생성할 수 있는 최대 스레드 수를 의미, `corePoolSize`와 다른 개념
     */

    public static void main(String[] args) {
        final ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(100);
        final TaskRunner runner = new TaskRunner(scheduledThreadPool);
        runner.runTasks();
    }
}
