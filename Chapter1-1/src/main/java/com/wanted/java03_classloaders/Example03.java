package com.wanted.java03_classloaders;

import java.sql.DriverManager;
import java.util.ArrayList;

public class Example03 {
    /*
    # 로딩한 클래스별 클래스 로더 출력

    ## 실행 결과 분석
    Classloader of ArrayList: null
    Classloader of DriverManager: jdk.internal.loader.ClassLoaders$PlatformClassLoader@1996cd68
    Classloader of this class: jdk.internal.loader.ClassLoaders$AppClassLoader@42110406

    출력된 ArrayList의 클래스 로더의 값이 null이 나오는 이유는?
    * ArrayList 클래스의 로딩을 담당하는 Bootstrap 클래스 로더는 네이티브코드로 작성되어 Java 클래스로 표시되지 않기 때문
    * 참고로 부트스트랩 클래스 로더 동작은 JVM에 따라 달라질 수 있음
     */

    public static void main(String[] args) {
        System.out.println("Classloader of ArrayList: " + ArrayList.class.getClassLoader());
        System.out.println("Classloader of DriverManager: " + DriverManager.class.getClassLoader());
        System.out.println("Classloader of this class: " + Example03.class.getClassLoader());
    }
}
