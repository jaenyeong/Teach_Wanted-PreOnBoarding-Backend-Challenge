package com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice;

public class BadPractice03_boxedprimitive {
    /*
    # `synchronized`와 `Boxed Primitive type`을 함께 사용하는 것은 권장되지 않음

    ## 문제점
    박싱된 기본 타입 객체는 Boolean 리터럴과 유사하게 인스턴스 값을 재사용할 수 있음
    * JVM이 바이트로 표현할 수 있는 값을 캐싱해 공유

    ## 솔루션
    현재는 Integer와 같은 `Value-Based Classes`를 동기화 객체로 사용하는 것이 막혀 있음
     */

    private int badPracticeCount = 0;
    private final Integer badPracticeIntLock = badPracticeCount;

    // 컴파일 에러
//    private void badPractice() {
//        synchronized (badPracticeIntLock) {
//            badPracticeCount++;
//            System.out.println(badPracticeIntLock);
//        }
//    }
}
