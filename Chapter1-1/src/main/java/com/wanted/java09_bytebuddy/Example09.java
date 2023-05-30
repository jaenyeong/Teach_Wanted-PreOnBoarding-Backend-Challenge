package com.wanted.java09_bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Example09 {
    /*
    # Bytebuddy를 통해 바이트코드 조작
    https://bytebuddy.net/#/
    https://www.baeldung.com/byte-buddy

    ## ByteBuddy
    컴파일러의 도움 없이 Java 앱 런타임 중에 클래스를 생성/수정 및 Agent를 지원하는 라이브러리
    * 런타임에 프록시 생성을 위해 인터페이스 구현 여부에 제한받지 않음 (바이트코드를 직접 조작)
    * ASM 기반으로 작성된 라이브러리 (ASM은 Java를 동적으로 생성/변경할 수 있는 프레임워크)
     */

    private static final ClassLoader CURRENT_CLASS_LOADER = Example09.class.getClassLoader();

    public static void main(String[] args) {
        try {
            createNewInstanceWithoutDefinedClass();
            System.out.println();

            delegateToStaticMethod();
            System.out.println();

            defineMethodAndField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createNewInstanceWithoutDefinedClass() throws
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {

        final String expectedValue = "expected value";

        final DynamicType.Unloaded<Object> unloadedClass = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.isToString())
            .intercept(FixedValue.value(expectedValue))
            .make();

        final Object newInstance = unloadedClass.load(CURRENT_CLASS_LOADER)
            .getLoaded()
            .getDeclaredConstructor()
            .newInstance();

        final String resultValue = newInstance.toString();

        System.out.println("--> create a new instance without a defined class, and call a method");
        System.out.println(resultValue);
        System.out.println("(resultValue equals expectedValue) = " + resultValue.equals(expectedValue));
    }

    private static void delegateToStaticMethod() throws
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {

        final DynamicType.Unloaded<PreByteBuddy> unloadedPreByteBuddy = new ByteBuddy()
            .subclass(PreByteBuddy.class)
            .method(
                named("getByteBuddyName")
                    .and(isDeclaredBy(PreByteBuddy.class)
                        .and(returns(String.class))))
            .intercept(MethodDelegation.to(PostByteBuddy.class))
            .make();

        final String resultValue = unloadedPreByteBuddy
            .load(CURRENT_CLASS_LOADER)
            .getLoaded()
            .getDeclaredConstructor()
            .newInstance()
            .getByteBuddyName();

        final String postByteBuddyName = PostByteBuddy.getByteBuddyName();

        System.out.println("--> Delegate 'PreByteBuddy`s method' to 'PostByteBuddy`s method'");

        System.out.println(resultValue);
        System.out.println("(resultValue equals expectedValue) = " + resultValue.equals(postByteBuddyName));
    }

    private static void defineMethodAndField() throws
        NoSuchFieldException,
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {

        final String newByteBuddyClassName = "NewByteBuddyClass";
        final String newFieldName = "newField";
        final String newCustomMethodName = "newCustomMethod";

        final Class<?> newClassType = new ByteBuddy()
            .subclass(Object.class)
            .name(newByteBuddyClassName)
            .defineMethod(newCustomMethodName, String.class, Modifier.PUBLIC)
            .intercept(MethodDelegation.to(PostByteBuddy.class))
            .defineField(newFieldName, String.class, Modifier.PUBLIC)
            .make()
            .load(CURRENT_CLASS_LOADER, ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        final Method customMethod = newClassType.getDeclaredMethod(newCustomMethodName, (Class<?>[]) null);
        final Object newInstance = newClassType.getDeclaredConstructor().newInstance();
        final Field newField = newClassType.getDeclaredField(newFieldName);

        System.out.println("--> define new a field and a method");
        System.out.println(customMethod.invoke(newInstance));
        System.out.println(newField);
    }
}
