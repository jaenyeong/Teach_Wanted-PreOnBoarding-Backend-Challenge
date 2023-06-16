package com.wanted.java03_executors.case06_workstealingpool;

import com.wanted.java03_executors.TaskRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Case06 {
    /*
    # `newWorkStealingPool()` 메서드 (`ForkJoinPool` 클래스 반환)
    사용 가능한 프로세서 수를 병렬 처리에 필요한 수준으로 사용하는 서로간 작업을 뺏어서 처리하는 스레드 풀 생성
     */

    public static void main(String[] args) {
        final ExecutorService workStealingPool = Executors.newWorkStealingPool();
        final TaskRunner runner = new TaskRunner(workStealingPool);
        runner.runTasks();
    }
}
