package com.wanted.java05_specialmethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassInitializationMethods {
    /*
    # 클래스 초기화 메서드

    ## 조건
    * <clinit> 이라는 이름을 가진 스페셜 메서드
    * `void` 반환 타입
    * 클래스 파일의 버전이 51.0 이상인 경우 해당 인자를 사용하지 않고 ACC_STATIC 플래그가 설정된 메서드
     */

    // <clinit>
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassInitializationMethods.class);

    // <clinit>
    static {
        System.out.println("static initialization block");
    }
}
