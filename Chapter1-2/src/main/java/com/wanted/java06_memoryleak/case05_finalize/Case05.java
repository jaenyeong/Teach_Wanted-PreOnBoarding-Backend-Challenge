package com.wanted.java06_memoryleak.case05_finalize;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Case05 {
    /*
    # finalize 메서드 호출로 인해 발생하는 메모리 누수
    finalize 메서드를 오버라이딩하는 경우 GC에 의해 바로 제거되는 것이 아닌 종료를 위한 대기열에 삽입되는데
    문제는 오버라이딩한 소스 코드가 올바르지 않은 경우 해당 객체가 제대로 제거되지 않아 메모리 누수가 발생할 수 있음

    ## 방지 요령
    finalize 메서드를 오버라이딩하지 말 것
     */
    private final String[] data;

    public Case05() {
        data = new String[1_000_000];

        for (int i = 0; i < 1_000_000; i++) {
            data[i] = getRandomString();
        }
    }

    private String getRandomString() {
        byte[] array = new byte[1_000];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

//    @Override
//    public void finalize() {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Finalizer called");
//    }
}
