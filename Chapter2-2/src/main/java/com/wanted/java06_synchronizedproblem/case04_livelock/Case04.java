package com.wanted.java06_synchronizedproblem.case04_livelock;

import com.wanted.java06_synchronizedproblem.case04_livelock.domain.Dinner;
import com.wanted.java06_synchronizedproblem.case04_livelock.domain.Spoon;

public class Case04 {
    /*
    #  라이브락
    데드락과는 조금 다르게 프로세스 측면에서는 무한 대기하는 것은 같지만 락의 해제, 획득을 반복하며 상태는 계속 바뀌는 상태
    해당 예시 소스는 결과적으로 무한정 대기하며 프로세스가 종료되지 않음
     */

    public static void main(String[] args) {
        final Dinner husband = new Dinner("Bob");
        final Dinner wife = new Dinner("Alice");

        final Spoon spoon = new Spoon(husband);

        new Thread(() -> husband.eatWith(spoon, wife)).start();
        new Thread(() -> wife.eatWith(spoon, husband)).start();
    }
}
