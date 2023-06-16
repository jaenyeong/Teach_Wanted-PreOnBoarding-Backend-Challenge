package com.wanted.java06_synchronizedproblem.case03_starvation;

public class Case03 {
    /*
    # 스타베이션 예시
    모종의 이유로 특정 스레드에서 공유 리소스에 대한 액세스 권한을 계속 획득할 수 없음
    * 높은 우선순위를 가진 스레드가 리소스를 계속 점유
    * 특정 스레드가 공유 리소스에 대한 락 권한을 획득하지 못하고 대기 (예시 소스)
     */

    private static final Object SHARED_OBJECT = new Object();

    public static void main(String[] args) throws InterruptedException {
        final Thread[] workerThreads = new Thread[5];

        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i] = createWorkerThread();
            workerThreads[i].start();
        }

        // 워커 스레드를 위해 메인 스레드 5초간 슬립
        Thread.sleep(5_000);

        synchronized (SHARED_OBJECT) {
            while (true) {
                // 메인 스레드는 SHARED_OBJECT에 대해 획득한 락을 계속 유지
                System.out.println("Main thread is working...");
                Thread.sleep(500); // Simulate some work
            }
        }
    }

    private static Thread createWorkerThread() {
        return new Thread(() -> {
            synchronized (SHARED_OBJECT) {
                try {
                    // 메인 스레드가 SHARED_OBJECT에 대한 락을 해제하지 않아서 아래 명령들은 출력되지 않음
                    // 즉 메인 스레드가 해당 리소스를 계속 반환하지 않아 발생하는 스타베이션
                    System.out.println(Thread.currentThread().getName() + " is about to wait.");
                    SHARED_OBJECT.wait();
                    System.out.println(Thread.currentThread().getName() + " has woken up.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
