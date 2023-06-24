package com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice;

public class BadPractice01_string {
    /*
    # `synchronized`와 String을 함께 사용하는 것은 권장되지 않음

    ## 문제점
    * `badPractice01`, `badPractice02` 메서드는 String 상수 풀을 참조하기 때문에 "LOCK_STRING" 문자열은 락이 걸림
      * String 상수 풀은 JVM 전역적으로 공유됨
    * `badPractice03` 메서드는 새로 생성한 String 인스턴스가 `intern` 메서드를 통해 String 상수 풀로 추가되고
      그 참조를 반환하기 때문에 "LOCK_STRING" 문자열이 락이 걸림

    ## 솔루션
    * `solution` 메서드처럼 String을 반드시 `synchronized`와 함게 사용해야 한다면 새로운 String 인스턴스를 생성해 사용할 것
     */

    private final String badPractice02Lock = "LOCK_STRING";
    private final String badPractice03Lock = new String("LOCK_STRING").intern();
    private final String solutionLock = new String("LOCK_STRING");

    private static void badPractice01() {
        final String case01Lock = "LOCK_STRING";
        synchronized (case01Lock) {
            System.out.println(case01Lock);
        }
    }

    private void badPractice02() {
        synchronized (badPractice02Lock) {
            System.out.println(badPractice02Lock);
        }
    }

    private void badPractice03() {
        synchronized (badPractice03Lock) {
            System.out.println(badPractice03Lock);
        }
    }

    private void solution() {
        synchronized (solutionLock) {
            System.out.println(solutionLock);
        }
    }
}
