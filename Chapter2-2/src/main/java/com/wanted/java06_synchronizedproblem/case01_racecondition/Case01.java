package com.wanted.java06_synchronizedproblem.case01_racecondition;

import com.wanted.java06_synchronizedproblem.case01_racecondition.domain.Counter;

public class Case01 {
    /*
    # 레이스 컨디션 예시
    레이스 컨디션이 발생하여 Counter 객체의 value 값이 올바른 값이 나오지 않음
     */

    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();

        final Thread firstThread = createThread(counter);
        final Thread secondThread = createThread(counter);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        System.out.println(counter.getValue());
    }

    private static Thread createThread(final Counter counter) {
        return new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                counter.increment();
            }
        });
    }
}
