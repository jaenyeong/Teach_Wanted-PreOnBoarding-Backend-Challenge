package com.wanted.java06_memoryleak.case04_innerclass;

import com.wanted.java06_memoryleak.case04_innerclass.clazz.StaticNestedClassWrapper;

public class Case04 {
    /*
    # 아우터 클래스 안에 이너 클래스 사용으로 발생하는 메모리 누수
    이너 클래스는 아우터 클래스를 의존하고 있기 때문에 아우터 클래스의 사용(참조)이 없어도 GC 처리가 되지 않음

    ## 방지 요령
    * 이너 클래스에서 아우터 클래스의 필드 사용이나 메서드 호출을 하지 않는다면 스태틱 클래스로 구현할 것
      * 일반적으로 OOP 측면에서도 가급적 중첩된 클래스 파일 구조를 만들지 않는 것이 바람직함
     */
    public static void main(String[] args) {
        StaticNestedClassWrapper.StaticNestedClass staticNestedClass = new StaticNestedClassWrapper.StaticNestedClass("Nested Class");
        System.out.println("Debug point");

        // 또한 이너 클래스는 인스턴스화를 위해 아우터 클래스의 인스턴스가 필요함
//        InnerClassWrapper.InnerClass innerClass = new InnerClassWrapper().new InnerClass("Inner Class");
//        System.out.println("Debug point");
    }
}
