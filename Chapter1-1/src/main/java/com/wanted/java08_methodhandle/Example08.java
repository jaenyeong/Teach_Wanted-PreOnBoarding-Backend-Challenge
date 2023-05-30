package com.wanted.java08_methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class Example08 {
    public static void main(String[] args) throws Throwable {
        // [1] Creating the Lookup
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
//        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // [2] Creating a MethodType
        MethodType methodType = MethodType.methodType(String.class, char.class, char.class);

        // [3] Finding a MethodHandle
        MethodHandle replaceMethodHandle = publicLookup.findVirtual(String.class, "replace", methodType);

        // [4] Invoking a Method Handle
        // `invokeExact` 메서드는 메서드 시그니처가 정확하게 일치해야 호출 가능 (WrongMethodTypeException 예외 발생)
        // `invoke` 메서드는 `invokeExact` 메서드보다 다소 덜 정확하더라도 호출 가능 (ClassCastException, WrongMethodTypeException 예외 발생)
        // `invokeWithArguments` 메서드는 인수를 담은 List, 배열을 사용해 `MethodHandle` 바인딩 메서드 호출 (내부적으로 invoke 호출)

        String originKeyword = "jovo";
        String output = (String) replaceMethodHandle.invoke(originKeyword, 'o', 'a');

        // Assert
        System.out.println("java".equals(originKeyword));
        System.out.println("java".equals(output));
    }
}
