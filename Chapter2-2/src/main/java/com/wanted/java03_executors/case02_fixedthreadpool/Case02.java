package com.wanted.java03_executors.case02_fixedthreadpool;

import com.wanted.java03_executors.TaskRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Case02 {
    /*
    # `newFixedThreadPool(int nThreads)` 메서드 (`ThreadPoolExecutor` 클래스 반환)
    무제한 크기의 작업 큐에서 가져온 작업을 고정된 스레드 수만큼만 사용하며 처리하는 스레드 풀
    어떤 상황이든 파라미터로 받은 스레드 수(nThreads)까지만 사용해 작업을 처리함
    그래서 모든 스레드가 작업을 진행하는 활성화 상태라면 제출된 태스크는 대기열에서 대기
    실행 중 발생한 오류로 스레드가 종료되면 새 스레드를 생성하여 후속 작업을 마저 처리함
    스레드 풀 내에 스레드는 명시적으로 종료할 때까지 존재
     */

    public static void main(String[] args) {
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
        final TaskRunner runner = new TaskRunner(fixedThreadPool);
        runner.runTasks();
    }
}
