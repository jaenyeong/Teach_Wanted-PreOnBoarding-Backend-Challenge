package com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice;

public class BadPractice02_boolean {
    /*
    # `synchronized`와 Boolean을 함께 사용하는 것은 권장되지 않음

    ## 문제점
    Boolean 클래스의 `FALSE`, `TRUE` 필드 또한 String 리터럴과 마찬가지로 JVM 전역에서 공유되는 인스턴스

    ## 솔루션
    가급적 Boolean의 `FALSE`나 `TRUE`를 동기화 락으로 사용하지 말 것
     */

    private final Boolean boolLock = Boolean.FALSE;

    // 컴파일 에러
//    private void badPractice() {
//        synchronized (boolLock) {
//            System.out.println(boolLock);
//        }
//    }
}
