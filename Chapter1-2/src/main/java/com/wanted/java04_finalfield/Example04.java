package com.wanted.java04_finalfield;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Example04 {
    /*
    # final 키워드 성능 측정
    final 키워드의 유무가 성능에 영향을 주는지 확인하기 위해 String concat 메서드에 대한 성능 측정
    JMH는 JIT 컴파일러의 최적화가 시작되도록 웜업을 반복 실행 처리

    ## 실행 결과 비교
    Benchmark                        Mode  Cnt  Score   Error  Units
    Example05.concatFinalStrings     avgt   25  0.563 ± 0.004  ns/op
    Example05.concatNonFinalStrings  avgt   25  3.315 ± 0.040  ns/op

    ## Java 바이트코드 비교

    ### concatNonFinalStrings 메서드
     0: ldc           #13                 // String x
     2: astore_0
     3: ldc           #15                 // String y
     5: astore_1
     6: aload_0
     7: aload_1
     8: invokedynamic #17,  0             // InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    13: areturn

    ### concatFinalStrings 메서드
     0: ldc           #13                 // String x
     2: astore_0
     3: ldc           #15                 // String y
     5: astore_1
     6: ldc           #21                 // String xy
     8: areturn

     ## `FinalVariables` 클래스와 `NonFinalVariables` 클래스의 바이트코드 비교
     */

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static String concatNonFinalStrings() {
        String x = "x";
        String y = "y";
        return x + y;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static String concatFinalStrings() {
        final String x = "x";
        final String y = "y";
        return x + y;
    }
}
