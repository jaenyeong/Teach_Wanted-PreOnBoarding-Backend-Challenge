package com.wanted.java01_threadexit.case01_exitmethodcall;

public class Case01 {
    /*
    # JVM이 스레드를 종료 시키는 경우

    ## `Runtime.getRuntime().exit()` 메서드가 호출된 경우
    System.exit(); 메서드가 내부적으로 `Runtime.getRuntime().exit();`를 호출
    해당 메서드를 주석처리하여 호출하지 않는다면 `System.out.println("[ This point is not called... ]");` 명령이 출력됨
     */

    private static final int SAFE_EXIT = 0;

    public static void main(String[] args) {
        final Thread lambdaThread = new Thread(createRunnableFrom(2_000));
        lambdaThread.start();

        try {
            Thread.sleep(5_000);
            // JVM 종료
            System.exit(SAFE_EXIT);

            // 호출되지 않음
            System.out.println("[ This point is not called... ]");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Runnable createRunnableFrom(int sleepTime) {
        return () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("[" + i + "]" + " Thread is running...");
                try {
                    // 너무 빨리 출력되고 프로세스가 종료되는 것을 막기 위해 슬립 실행
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
