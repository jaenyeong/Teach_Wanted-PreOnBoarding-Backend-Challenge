package com.wanted.java00_classpath;

public class Example00 {
    /*
    # 클래스 파일 실행 경로

    (1) `jenv` 등을 통해 java 버전 설정
    (2) 현재 경로에서 `.java` 파일 컴파일
        [경로] `Teach_Wanted-PreOnBoarding-Challenge/Chapter1-1/src/main/java/com/wanted/java00_classpath`
        [실행] `javac Example00.java`
    (3) 컴파일된 `.class` 파일 실행
        [경로] `Teach_Wanted-PreOnBoarding-Challenge/Chapter1-1/src/main/java`
        [실행] `java com.wanted.java00_classpath.Example00`
     */

    public static void main(String[] args) {
        System.out.println("Classpath Check");
    }
}
