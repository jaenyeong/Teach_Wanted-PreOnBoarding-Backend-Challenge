package com.wanted.java00_epsilon;

public class Example00 {
    /*
    # Epsilon GC 예제
    전체적으로 총 10GB 크기의 메모리를 할당 후 테스트
    * 10GB는 기본적인 heap 크기보다 클 것이라고 예상

    ## 실행 테스트
    * 현재 경로에서 `java -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC Example00.java` 으로 실행 시 에러 발생
       ```
       [0.003s][warning][gc,init] Consider setting -Xms equal to -Xmx to avoid resizing hiccups
       [0.003s][warning][gc,init] Consider enabling -XX:+AlwaysPreTouch to avoid memory commit hiccups
       Starting pollution
       Terminating due to java.lang.OutOfMemoryError: Java heap space
       ```
    * 하지만 그냥 실행하면 문제 없이 실행됨
      * `java Example00.java`

    ## 실패 원인
    * 기본적으로 Epsilon GC는 GC가 작동하지 않음
      * 문서에 의하면 메모리 할당은 하지만 회수를 하지 않음
    * 따라서 OutOfMemoryError 발생

    ## Epsilon GC가 필요한 이유는 무엇일까?
    * 힙의 크기가 충분한 경우 GC 작업을 원치 않는 상황에 사용
      * 퍼포먼스 테스팅
      * 메모리 부하 테스팅
      * VM 인터페이스 테스팅
      * 실행 주기가 굉장히 짧은 잡 실행
      * 최종 실패 레이턴시 개선 시도
      * 최종 실패 처리량 개선 시도
     */

    private static final int ONE_MEGABYTE = 1024 * 1024;
    private static final int ITERATION_COUNT = 1024 * 10;

    public static void main(String[] args) {
        System.out.println("Starting pollution");

        // 1MB 크기의 배열을 10_240번 반복 생성하여 약 10GB 메모리를 할당
        for (int i = 0; i < ITERATION_COUNT; i++) {
            byte[] array = new byte[ONE_MEGABYTE];
        }

        System.out.println("Terminating");
    }
}
