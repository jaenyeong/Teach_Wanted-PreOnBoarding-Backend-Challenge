package com.wanted.java06_memoryleak.case03_equalsandhashcode;

import com.wanted.java06_memoryleak.case03_equalsandhashcode.person.OverridingPerson;
import com.wanted.java06_memoryleak.case03_equalsandhashcode.person.Person;

import java.util.HashMap;
import java.util.Map;

public class Case03 {
    /*
    # equals, hashCode 메서드를 오버라이딩 하지 않아 발생하는 메모리 누수
    위 메서드를 재정의한 OverridingPerson 인스턴스는 해시 값을 기준으로 동일한 객체로 판단되어 people의 사이즈가 1로 나오나
    재정의하지 않은 NonOverridingPerson의 인스턴스는 서로 다른 객체로 판단되어 people의 사이즈가 100으로 출력됨

    정리하자면 people이 가리키는 객체가(객체 참조) 늘어날수록 메모리를 더 사용하게 됨
    그리고 아래와 같은 상황에서는 새로 생성한 OverridingPerson 인스턴스들이 HashMap에 삽입되지 않는다면
    이를 가리키는 포인터(참조) 변수가 없기 때문에 GC의 대상이 됨
    또한 이런 상황은 추가적인 내부 연산이 일어날 수 있어 비효율적 (사이즈가 커지면서 내부 자료구조 리사이징 발생)

    ## 방지 요령
    * 아래와 같이 equals, hashCode 메서드를 항상 오버라이딩할 것 또한 이때 hashCode 메서드의 알고리즘 성능을 신경쓸 것
      * hashCode 메서드 내용은 OverridingPerson 클래스 참조
     */

    public static void main(String[] args) {
        final Map<Person, Integer> people = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            people.put(new OverridingPerson("John"), i);
//            people.put(new NonOverridingPerson("John"), i);
        }

        System.out.println(people.size());
    }
}
