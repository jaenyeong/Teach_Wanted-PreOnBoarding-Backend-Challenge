package com.wanted.java04_constantvariable;

import com.wanted.java04_constantvariable.variable.ConstantVariable;
import com.wanted.java04_constantvariable.variable.NotConstantVariable;

public class Example04 {
    /*
    # 컴파일 타임 상수의 `Constant folding` 또는 `Inlining`

    컴파일 타임 상수는 Java 컴파일러에 의해 최적화될 수도 있음 (최적화 방식에 따라 다름)
    따라서 호출 시점에 호출하는 코드에 값이 직접적으로 삽입될 `수도` 있음
     */
    public static void main(String[] args) {
        System.out.println(ConstantVariable.CONST_VAR_NUMBER);
        System.out.println(ConstantVariable.CONST_VAR_STRING);
        System.out.println(plus(10, 20));

        System.out.println(NotConstantVariable.NOT_CONST_VAR_DATE);
        System.out.println(NotConstantVariable.notConstVarNumber);
    }

    private static int plus(int firstOp, int secondOp) {
        return firstOp + secondOp;
    }
}
