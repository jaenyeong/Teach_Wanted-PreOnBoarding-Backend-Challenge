package com.wanted.java01_stackandheap;

public class PersonBuilder {
    /*
    # 스택과 힙 예시
    main 메서드 > buildPerson 메서드 > Person 생성자 순으로 호출되며 이때
    어떤 순서로 어디 메모리영역에 무엇이 저장되는 지를 생각하며 소스를 파악해 볼 것
     */

    private static Person buildPerson(int id, String name) {
        return new Person(id, name);
    }

    public static void main(String[] args) {
        int id = 23;
        String name = "John";
        Person person = null;
        person = buildPerson(id, name);
    }
}
