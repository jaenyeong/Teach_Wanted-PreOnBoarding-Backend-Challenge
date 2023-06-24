package com.wanted.java07_atomicoperation.case06_condition;

import java.util.function.IntConsumer;

public class Case06 {
    /*
    # Condition 인터페이스의 사용 예시
    락 객체의 Condition 인스턴스를 두 개 생성해 각각 `put`, `take` 작업 별로 스레드를 처리할 수 있게 핸들링 하는 예시
    * Condition 통해 각각 `put` 작업 스레드와 `take` 작업 스레드 별로 wait-set을 만들어 처리
     */

    public static void main(String[] args) {
        final BoundedBuffer<Integer> buffer = new BoundedBuffer<>();

        final Thread producer = generateThread(idx -> {
            try {
                buffer.put(idx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 50);

        final Thread consumer = generateThread(idx -> {
            try {
                buffer.take();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 10);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread generateThread(IntConsumer intFunction, int sleepTime) {
        return new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    intFunction.accept(i);
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
