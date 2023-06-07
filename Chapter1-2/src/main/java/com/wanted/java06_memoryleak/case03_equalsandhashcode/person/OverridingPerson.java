package com.wanted.java06_memoryleak.case03_equalsandhashcode.person;

import java.util.Objects;

public class OverridingPerson extends Person {
    private final String name;

    public OverridingPerson(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OverridingPerson that = (OverridingPerson) o;
        return Objects.equals(name, that.name);
    }

    /*
    # 해시코드 값을 도출할 때 31을 사용하는 이유
    당연하게도 해시코드를 구현하는 알고리즘 성능이 좋을수록 해시 테이블의 성능이 향상됨
    그래서 기존에는 성능 향상을 위해 홀수(예를 들어 31)를 사용해 해시코드를 연산하는 관례가 있음
    * 곱셈을 비트단위 쉬프트 연산으로 대체 가능함 `31 * i == (i << 5) - i`
    * 짝수를 곱하면 쉬프트 연산이 계속 진행되면 우측 값들이 0으로 가득 채워지면서 오버 플로우 발생하기 때문에 홀수로 연산
    * 홀수 중 소수로 연산할 뿐 31 자체에 특별한 의미가 있지 않음 (또한 꼭 소수가 아니어도 됨)

    예를 들어 String 클래스의 hashcode() 메서드는 아래와 같이 계산됨 (Oracle JDK 17 문서 기준)
    * `s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]`

    하지만 최근 JDK에서는 이를 내부적으로 구현하여 지원하기 때문에 별도로 31을 사용한 연산을 구현할 필요는 없음
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // Objects의 hash(Object... values) 메서드 (JDK 17 기준)
//    public static int hash(Object... values) {
//        return Arrays.hashCode(values);
//    }

    // Arrays의 hashCode 메서드 (JDK 17 기준)
//    public static int hashCode(Object a[]) {
//        if (a == null)
//            return 0;
//
//        int result = 1;
//
//        for (Object element : a)
//            result = 31 * result + (element == null ? 0 : element.hashCode());
//
//        return result;
//    }
}
