package com.wanted.java05_specialmethods;

public class InstanceInitializationMethods {
    /*
    # 인스턴스 초기화 메서드

    ## 조건
    * 클래스 안에 정의
    * <init> 이라는 이름을 가진 스페셜 메서드
    * `void` 반환 타입
     */

    // <init>
    private String firstName = "Foo";
    private String lastName = "Bar";

    // <init>
    {
        System.out.println("Call instance initialization block " + firstName + " : " + lastName);
    }

    // <init>
    public InstanceInitializationMethods(String firstName, String lastName) {
        System.out.println("Call instance constructor " + firstName + " : " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // <init>
    public InstanceInitializationMethods() {
        System.out.println("Call instance constructor without parameters");
    }
}
