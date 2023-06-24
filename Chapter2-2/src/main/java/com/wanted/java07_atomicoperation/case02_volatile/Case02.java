package com.wanted.java07_atomicoperation.case02_volatile;

public class Case02 {
    /*
    # volatile 사용 예시
    ready 변수에 `volatile` 키워드를 붙이지 않는 경우 CPU 캐시의 갱신이 더 늦게 일어나기 때문에 `ready is false` 출력이 좀 더 늘어남

    ## ReOrdering
    성능 향상을 위한 JVM의 최적화에 의해 실행 명령의 순서가 재정렬되는 경우
    * 프로세서가 프로그램 실행 순서대로가 아닌 다른 순서로 쓰기 버퍼를 플러시할 수 있음
    * 프로세서가 비 순차적으로 실행할 수 있음
    * JIT 컴파일러가 성능 향상을 목적으로 재정렬하여 성능 최적화
    해당 예시에서는 발생하지 않지만 만약 재정렬된다면 `number`의 값이 0이 출력된다거나 할 수 있음

    ## Piggybacking
    특정 필드가 `volatile`인 경우 다른 일반 필드 또한 `volatile` 변수에 편승하여 같이 메모리 가시성이 갱신되는 경우
    * 원래 네트워크에서 주로 사용되는 용어로 기존 데이터 프레임의 추가 데이터를 위한 필드를 덧붙여 전송(Piggyback)하는 방식
      오버헤드를 줄이고 효율성을 높이기 위해 사용됨
     */

    private static int number = 0;
    private volatile static boolean ready = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (!ready) {
                // 다른 스레드에게 실행 기회를 주는 힌트 적용
                // JVM, OS, 하드웨어 등 다양한 요인에 의해 결과가 다를 수 있어 크게 의미가 없는 경우도 존재함
                Thread.yield();
                System.out.println("ready is false");
            }
            System.out.println(number);
        }).start();

        try {
            // 위 스레드를 실행시키기 위해 인위적으로 메인 스레드 슬립
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        number = 42;
        ready = true;
    }
}
