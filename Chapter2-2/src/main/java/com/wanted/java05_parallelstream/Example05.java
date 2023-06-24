package com.wanted.java05_parallelstream;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

public class Example05 {
    /*
    # 패러럴 스트림으로 병렬 처리
    패러럴 스트림은 기본적으로 커먼 스레드 풀을 사용하나 커스텀 스레드 풀을 적용할 수 있음
    하지만 특별한 이유가 있을 때만 사용하는 것이 권장됨

    ## 코어 수만큼 스레드 풀 크기 설정
    * 맥북에서 코어 수 확인
      * `sysctl hw.physicalcpu hw.logicalcpu`
         >>>> hw.physicalcpu: 10
         >>>> hw.logicalcpu: 10

     ## 커스텀 스레드 풀 사용
     * `new ForkJoinPool(10);`
     * 사용 시 주의점
       * 커스텀 스레드 풀 사용은 명시적으로 리소스 반환(종료)하지 않으면 메모리 누수가 발생할 수 있음
       * 따라서 shutdown 명령으로 명시적으로 종료할 것
     */

    private static final int CORE = 10;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final long firstNum = 1;
        final long lastNum = 1_000_000;

        final List<Long> numbers = LongStream.rangeClosed(firstNum, lastNum).boxed().toList();
        final ForkJoinPool customThreadPool = new ForkJoinPool(CORE);

        try {
            long actualTotal = customThreadPool.submit(sum(numbers)).get();
            System.out.println((lastNum + firstNum) * lastNum / 2 == actualTotal);
        } finally {
            // 반드시 명시적으로 종료할 것
            customThreadPool.shutdown();
        }
    }

    private static Callable<Long> sum(final List<Long> numbers) {
        return () -> numbers.parallelStream().reduce(0L, Long::sum);
    }
}
