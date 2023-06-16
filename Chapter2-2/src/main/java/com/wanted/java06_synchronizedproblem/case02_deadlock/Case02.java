package com.wanted.java06_synchronizedproblem.case02_deadlock;

public class Case02 {
    /*
    # 데드락 예시
    synchronized 키워드로 파라미터 객체를 순서대로 락을 걸어 데드락 상태에서 빠져나오지 못함
     */

    public static void main(String[] args) {
        final LockedRecord firstLockRecord = new LockedRecord("Locked Record 1");
        final LockedRecord secondLockRecord = new LockedRecord("Locked Record 2");

        final Thread thread1 = new Thread(generateJob(firstLockRecord, secondLockRecord, "Thread 1"));
        final Thread thread2 = new Thread(generateJob(secondLockRecord, firstLockRecord, "Thread 2"));

        thread1.start();
        thread2.start();
    }

    private static Runnable generateJob(LockedRecord lockedRecord1, LockedRecord lockedRecord2, String threadName) {
        return () -> {
            synchronized (lockedRecord1) {
                System.out.println(threadName + ": Holding " + lockedRecord1.name);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + ": Waiting for " + lockedRecord2.name);

                synchronized (lockedRecord2) {
                    // 두 스레드에 교착상태로 인해 아래 명령은 실행되지 않음
                    System.out.println(threadName + ": Holding lock 1 & 2...");
                }
            }
        };
    }

    private record LockedRecord(String name) {
    }
}
