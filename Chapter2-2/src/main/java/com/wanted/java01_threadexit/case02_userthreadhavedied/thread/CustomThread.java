package com.wanted.java01_threadexit.case02_userthreadhavedied.thread;

public class CustomThread extends Thread {
    /*
    # Thread 클래스를 상속하여 사용할 스레드 생성 (User Thread)
    */
    private final int sleepTime;
    private static final int SHUTDOWN_LIMITED_TIME = 3_000;

    public CustomThread(String name, int sleepTime) {
        this.setName(name);
        this.sleepTime = sleepTime;
    }

    public void run() {
        sleepUntilSleepTime();
    }

    private void sleepUntilSleepTime() {
        final long startTime = System.currentTimeMillis();

        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + " is running..." + i);
                try {
                    // 너무 빨리 출력되고 프로세스가 종료되는 것을 막기 위해 슬립 실행
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 가독성을 위해 변수로 추출
            final long endTime = System.currentTimeMillis();
            final long runningTime = endTime - startTime;

            // 스레드가 무한히 실행되는 것을 방지
            if (runningTime > SHUTDOWN_LIMITED_TIME) {
                // 스레드가 일시 정지 상태인 경우에만 InterruptedException 예외 발생
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
