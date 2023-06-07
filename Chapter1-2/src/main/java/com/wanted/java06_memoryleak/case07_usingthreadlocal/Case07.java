package com.wanted.java06_memoryleak.case07_usingthreadlocal;

public class Case07 {
    /*
    # ThreadLocal 사용 시 데이터를 명시적으로 제거하지 않아 발생하는 메모리 누수

    ## 방지 요령
    * remove 메서드를 호출하여 명시적으로 참조를 제거할 것
     */

    private static final ThreadLocal<BulkyObject> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        final Thread thread = new Thread(() -> {
            final BulkyObject bulkyObject = new BulkyObject();
            threadLocal.set(bulkyObject);

            // 명시적으로 제거
            threadLocal.remove();
        });

        thread.start();
    }

    private static class BulkyObject {
        // 100MB size array
        private final byte[] largeArray = new byte[1024 * 1024 * 100];
    }
}


