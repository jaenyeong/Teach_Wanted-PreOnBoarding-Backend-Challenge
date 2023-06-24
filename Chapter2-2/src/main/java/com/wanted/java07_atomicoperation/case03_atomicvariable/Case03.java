package com.wanted.java07_atomicoperation.case03_atomicvariable;

import com.wanted.java07_atomicoperation.case03_atomicvariable.usage01_atomicinteger.AtomicIntValue;
import com.wanted.java07_atomicoperation.case03_atomicvariable.usage02_atomiclong.AtomicLongValue;
import com.wanted.java07_atomicoperation.case03_atomicvariable.usage03_atomicboolean.AtomicBoolValue;
import com.wanted.java07_atomicoperation.case03_atomicvariable.usage04_atomicreference.AtomicRefValue;

public class Case03 {
    /*
    # Atomic 연산을 위해 CAS 알고리즘을 활용한 객체 사용 예시
    CAS(Compare And Swap)는 로우 레벨의 인스트럭션을 활용한 동시성 처리를 논블록킹 알고리즘
    다른 락 획득 방식과 다르게 스레드들이 블록, 서스펜드 상태로 변경(중단)되지 않음

    ## CAS 알고리즘
    * 3개의 파라미터 사용(메모리 위치, 원본 값, 저장해야 할 연산 결과 값)
    * 작업 공간(메모리)에 있는 값을 읽어옴
    * 연산을 처리하고 해당 영역에 있는 값이 처음에 읽었던 값이라면 저장
    * 만약 다르다면 다시 해당 값을 읽어 연산 처리, 이 과정 반복

    ## 주의
    해당 Atomic 클래스를 갖고 있는 클래스들은 사용 예시일 뿐
    실제로도 이런 래퍼 클래스를 활용한다면 구현된 Atomic 래퍼 클래스들의 메서드에 대한 동기화도 별도로 처리되어야 함
     */

    private static final int UPPER_BOUND = 1_000;

    public static void main(String[] args) {
        assertAtomicInteger();
        assertAtomicLong();
        assertAtomicBoolean();
        assertAtomicReference();
    }

    private static void assertAtomicInteger() {
        final AtomicIntValue atomicInteger = new AtomicIntValue();
        executeByMultiThread(atomicInteger::increment);

        System.out.println("AtomicInteger value is " + atomicInteger.getValue());
    }

    private static void assertAtomicLong() {
        final AtomicLongValue atomicLong = new AtomicLongValue();
        executeByMultiThread(atomicLong::increment);

        System.out.println("AtomicLong value is " + atomicLong.getValue());
    }

    private static void assertAtomicBoolean() {
        final AtomicBoolValue atomicBool = new AtomicBoolValue();
        executeByMultiThread(atomicBool::toggleValue);

        System.out.println("AtomicBoolean value is " + atomicBool.getValue());
    }

    private static void assertAtomicReference() {
        final AtomicRefValue atomicRef = new AtomicRefValue();
        executeByMultiThread(atomicRef::increment);

        System.out.println("AtomicReference value is " + atomicRef.getValue().atomicValue());
    }

    private static void executeByMultiThread(Runnable task) {
        final Thread[] atomicThreads = new Thread[UPPER_BOUND];

        for (int i = 0; i < UPPER_BOUND; i++) {
            final Thread atomicThread = new Thread(task);
            atomicThreads[i] = atomicThread;
        }

        for (final Thread thread : atomicThreads) {
            thread.start();
        }

        for (Thread atomicThread : atomicThreads) {
            try {
                atomicThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
