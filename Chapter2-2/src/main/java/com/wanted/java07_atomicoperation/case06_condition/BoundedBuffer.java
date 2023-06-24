package com.wanted.java07_atomicoperation.case06_condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<E> {
    /*
    # `BoundedBuffer` 클래스 설명
    * `put` 메서드는 아이템을 삽입하는 메서드이고, `take` 메서드는 아이템을 추출하는 메서드
    * 버퍼가 비었을 때 `take`메서드 호출 시 버퍼의 아이템이 삽입될 때까지 스레드가 차단됨
    * 버퍼가 가득 찼을 때 `put` 메서드 호출 시 버퍼의 빈 공간이 생길 때까지 스레드가 차단됨
    * 한 번에 한 스레드에게만 알릴 수 있음
     */

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Object[] items = new Object[10];
    private int itemCount = 0;
    private int putOffset;
    private int takeOffset;

    public void put(E item) throws InterruptedException {
        // 잠금
        lock.lock();

        try {
            // 배열이 가득 찼다면 현재 스레드를 대기 상태로 변경
            while (itemCount == items.length) {
                notFull.await();
            }

            // 공간이 남았다면 배열의 putOffset 위치에 데이터 삽입
            items[putOffset] = item;

            // putOffset 값이 배열 크기와 같다면 0으로 초기화
            if (++putOffset == items.length) {
                putOffset = 0;
            }

            // 제대로 데이터가 삽입되었다면 데이터 개수 증가
            ++itemCount;
            System.out.println("Produced : " + item);
            // `notEmpty` 컨디션(wait-set)에 대기중인 스레드에게 배열이 비어있지 않다는 것을 알림
            notEmpty.signal();
        } finally {
            // 모든 처리 후 잠금 해제
            lock.unlock();
        }
    }

    // `E x = (E) items[takeOffset];` 부분의 경고를 지우기 위해 `SuppressWarnings` 애너테이션 사용
    @SuppressWarnings("unchecked")
    public E take() throws InterruptedException {
        // 잠금
        lock.lock();

        try {
            // 배열이 비었다면 현재 스레드를 대기 상태로 변경
            while (itemCount == 0) {
                notEmpty.await();
            }

            // 배열 안에 데이터가 존재한다면 takeOffset 위치의 데이터를 추출
            E item = (E) items[takeOffset];
            // takeOffset 값이 배열 크기와 같다면 0으로 초기화
            if (++takeOffset == items.length) {
                takeOffset = 0;
            }

            // 제대로 데이터가 추출되었다면 데이터 개수 감소
            --itemCount;
            System.out.println("Consumed : " + item);
            // `notFull` 컨디션(wait-set)에 대기중인 스레드에게 배열이 비었다는 것을 알림
            notFull.signal();

            // 추출한 데이터 반환
            return item;
        } finally {
            // 모든 처리 후 잠금 해제
            lock.unlock();
        }
    }
}
