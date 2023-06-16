package com.wanted.java03_executors.case01_cachedthreadpool;

import com.wanted.java03_executors.TaskRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Case01 {
    /*
    # `newCachedThreadPool()` 메서드 (`ThreadPoolExecutor` 클래스 반환)
    필요한 경우에만 새 스레드를 생성하고 기존 스레드를 재사용하는 스레드 풀 생성
    많은 양의 짧은 비동기 작업을 수행함에 있어 성능 향상에 도움을 줌
    execute 메서드 실행 시 이전에 생성됐던 스레드 재사용
    일반적으로 60초 동안 사용되지 않은 스레드는 종료 후 풀에서 제거되며 사용 가능한 스레드가 없으면 새로 생성해서 풀에 추가됨
    따라서 오랫동안 해당 스레드 풀을 사용하지 않으면 리소스를 사용하지 않음
     */

    public static void main(String[] args) {
        final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        final TaskRunner runner = new TaskRunner(cachedThreadPool);
        runner.runTasks();
    }
}
