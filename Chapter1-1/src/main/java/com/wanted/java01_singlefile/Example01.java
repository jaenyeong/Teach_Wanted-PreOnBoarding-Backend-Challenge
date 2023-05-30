package com.wanted.java01_singlefile;

public class Example01 {
    /*
    # Single-File Source-Code Program (단일 .java 파일 컴파일 없이 실행)
    JDK 11에서 추가된 기능 (이하 버전에서는 컴파일 필요)

    (1) `jenv` 등을 통해 java 버전 설정
    (2) 현재 경로에서 아래 명령 실행
        [경로] `Teach_Wanted-PreOnBoarding-Challenge/Chapter1-1/src/main/java/com/wanted/java01_singlefile`
        [실행] `java Example01.java`

    (에러) JDK 10 이하 버전이라면
    (1) 현재 경로에서 `java Example01.java` 실행 시 에러 발생
        * `오류: 기본 클래스 Example01.java을(를) 찾거나 로드할 수 없습니다.`
    (2) 모듈 루트 경로에서 실행 시 에러 발생
        * `java.lang.ClassNotFoundException: Example01`
     */

    public static void main(String[] args) {
        System.out.println("Single file running is success without compilation");

        // 아래와 같이 다른 클래스파일을 참조한다면 에러가 발생
        //
        // Example01.java:19: error: cannot find symbol
        //        System.out.println(new OneMoreClass("How about it?").getClassName());
        //                               ^
        //   symbol:   class OneMoreClass
        //   location: class Example01
        // 1 error

//        System.out.println(new OneMoreClass("How about it?").getClassName());
    }
}
