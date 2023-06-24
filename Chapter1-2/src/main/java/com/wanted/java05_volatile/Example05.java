package com.wanted.java05_volatile;

public class Example05 {
    /*
    # volatile 필드 사용 예시
    volatile 키워드가 적용된 필드 값을 가져올 땐 항상 레지스터(캐시) 메모리가 아닌 메인 메모리에서 가져옴

    ## 소스 설명
    1. 워커 스레드(workerThread)는 flag가 true인 동안 계속 반복하며 i를 증가하는 작업을 수행
       * 이때 워커 스레드는 flag 변수가 volatile 이라면 캐시가 아닌 메인 메모리에서 항상 값을 가져옴
    2. 메인 스레드는 잠시 슬립
    3. 슬립에서 깬 메인 스레드가 flag 변수의 값을 false로 바꾸고 메인 메모리에 저장
    4. 워커 스레드가 flag 변수 값이 false인 것을 확인하고 i 변수 증가 연산을 그만두고 i 값 출력 후 종료
    * 이때 flag 필드가 volatile이 `아니라면` 언제 루프가 멈추게 될지 알 수 없음

    ## 방지 요령
    * 여러 스레드에 의해 공유되는 변수에 대해 Read/Write 작업이 일어나는 경우 상황에 따라 volatile 필드로 선언할 것
      * 하지만 이게 모든 메모리 일관성을 보장하는 것이 아니기 때문에
        둘 이상의 스레드에서 Write 작업을 동시에 처리한다면 값의 원자성을 확보하기 위해 별도의 처리가 필요함
     */
    private static volatile boolean flag = true;
//    private static boolean flag = true;

    public static void main(String[] args) {
        final Thread workerThread = new Thread(() -> {
            int i = 0;
            while (flag) {
                i++;
            }
            System.out.println("Stopped at i = " + i);
        });

        workerThread.start();

        try {
            // 워커 스레드의 실행을 확인하기 위해 메인 스레드를 슬립시킴
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
        System.out.println("Set flag to false");
    }
}
