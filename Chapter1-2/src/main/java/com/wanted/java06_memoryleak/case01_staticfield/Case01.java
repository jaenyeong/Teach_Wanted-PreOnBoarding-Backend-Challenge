package com.wanted.java06_memoryleak.case01_staticfield;

import java.util.ArrayList;
import java.util.List;

public class Case01 {
    /*
    # 스태틱 필드 사용으로 인한 메모리 누수
    아래 소스에서 list를 static으로 선언 후 실행하고 힙 메모리를 분석해보면
    첫번째 디버그 포인트와 두번째 디버그 포인트 사이에 힙 메모리가 확 증가
    하지만 populateList 메서드 종료 후에도 GC가 바로 수집되지 않음

    이때 list 객체의 `static` 키워드를 빼 인스턴스 필드로 변경하면
    힙 메모리 사용량이 크게 바뀌는 데 첫번째 디버그 포인트까지는 사용량이 비슷하나
    populateList 메서드 종료 후에는 list에 대한 참조가 없기 때문에 GC가 발생함

    이를 통해 알 수 있는 건 언제나 스태틱 필드를 사용할 때 주의해야 한다는 것!
    스태틱으로 필드를 선언하면 앱이 종료될 때까지 메모리에 남아 있기 때문에
    다른 곳에서 필요한 메모리를 선점하고 놔주지 않는 것과 같음

    ## 방지 요령
    * 스태틱 필드는 정말 필요한 경우에만 사용할 것
    * 싱글톤 객체 등을 구현할 때는 lazy loading 기법으로 구현할 것
      * LazyLoadingSingleton, EagerLoadingSingleton 클래스 참조
     */

    private final List<Double> list = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("First Debug Point");
        new Case01().populateList();
        System.out.println("Third Debug Point");
    }

    private void populateList() {
        for (int i = 0; i < 10_000_000; i++) {
            list.add(Math.random());
        }
        System.out.println("Second Debug Point");
    }
}
