package com.wanted.java03_memoryconsistencyerror;

import java.util.function.IntBinaryOperator;

public class Example03 {
    /*
    # 메모리 일관성 에러 예시
    덧셈 스레드와 뺄셈 스레드가 있고 각각 덧셈 횟수와 뺄셈 횟수가 같은 상태이니
    count 변수의 결과값이 0이 되어야 할 것 같지만 실행할 때마다 다른 값이 출력됨

    ## 대표적인 메모리 일관성 문제
    메모리 일관성 문제는 멀티 스레드 환경에서 공유되는 변수에 접근 시 일관성있는 값을 획득하지 못해 발생하는 문제
    아래 예시 소스에 있는 증감 연산은 실제로 3단계로 구성됨
    1. 해당 값을 읽고
    2. 덧셈(뺄셈) 연산 후
    3. 결과값을 저장

    따라서 해당 과정 내에서 여러 스레드가 접근, 값을 바꾸면서 일관성이 지켜지지 못하게 됨
    해당 문제는 volatile 키워드로도 해결할 수 없음

    ## 방지 요령
    * 별도의 원자적 처리가 필요함
      * 또한 프로그래밍 측면에서도 하나의 값을 여러 스레드에서 공유하는 형태로 구현하지 않는 것을 권장함
     */

    private static int counter = 0;
//    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable incrementTask = performTask(Integer::sum);
        Runnable decrementTask = performTask((c, i) -> c - i);

        Thread incrementThread = new Thread(incrementTask);
        Thread decrementThread = new Thread(decrementTask);

        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();

        System.out.println("Final counter value: " + counter);
    }

    private static Runnable performTask(IntBinaryOperator operation) {
        return () -> {
            for (int i = 0; i < 100_000; i++) {
                counter = operation.applyAsInt(counter, 1);
            }
        };
    }

    // 원자성을 위해 synchronized 메서드 선언 후 호출
//    private static synchronized void increaseCounter() {
//        counter++;
//    }
}
