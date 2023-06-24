package com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice;

import com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice.domain.BadPracticeAnimal;
import com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice.domain.SolutionAnimal;

public class BadPractice04_class {
    /*
    # `synchronized`와 클래스를 함께 사용하는 것은 권장되지 않음

    ## 문제점
    JVM은 `this` 키워드로 동기화 메서드, 블록을 구현할 때 모니터 락을 활용
    이때 모든 `synchronized` 인스턴스 메서드, 블록이라면 다른 `synchronized` 인스턴스 영역까지 락이 한 번에 걸림

    ## 솔루션
    따라서 `this` 키워드를 사용하지 않고 클래스 내부의 별도로 락 전용 객체를 생성하여 활용할 것
    * `SolutionAnimal` 참조
     */

    private static void badPractice() {
        final BadPracticeAnimal badPracticeAnimal = new BadPracticeAnimal("Tommy", "John");
        synchronized (badPracticeAnimal) {
            while(true) {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void solution() {
        final SolutionAnimal solutionAnimal = new SolutionAnimal("Tommy", "John");
        synchronized (solutionAnimal) {
            while(true) {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
