package com.wanted.java04_forkjoin;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Example04 {
    /*
    # 싱글/병렬 처리 방식에 따라 특정 범위의 숫자 배열에서 소수를 찾는 연산 속도 비교
    아래 결과를 보면 알 수 있듯 메서드 실행(연산) 속도는 데이터 소스의 크기에 따라 다름
    즉 처리해야 될 데이터 소스의 크기에 따라 각각 적합한 방식이 다르다는 것을 의미

    ## `UPPER_BOUND` 필드의 값이 `1_000`일 때 결과 비교
    ForkJoinPool을 사용한 메서드가 제일 빠른 결과 값을 보여줌

    >>>> Benchmark                                Mode  Cnt       Score      Error  Units
    >>>> Example04.findNumbersByForkJoinPool      avgt   25   88039.650 ±  195.818  ns/op
    >>>> Example04.findNumbersBySingleThread      avgt   25  159034.562 ±  895.912  ns/op
    >>>> Example04.findNumbersByWorkStealingPool  avgt   25  207901.080 ± 4906.515  ns/op

    ## `UPPER_BOUND` 필드의 값이 `10_000`일 때 결과 비교
    Benchmark                                Mode  Cnt         Score       Error  Units
    Example04.findNumbersByForkJoinPool      avgt   25   2236966.849 ± 32238.892  ns/op
    Example04.findNumbersBySingleThread      avgt   25  15927073.028 ± 55358.717  ns/op
    Example04.findNumbersByWorkStealingPool  avgt   25   2616012.717 ± 25337.010  ns/op

    ---

    ## 아래 실행 결과에 대해 각 항목 설명
    >>>> Result "com.wanted.java04_forkjoin.Example04.findNumbersBySingleThread":
    >>>>   159034.562 ±(99.9%) 895.912 ns/op [Average]
    >>>>   (min, avg, max) = (158010.838, 159034.562, 162266.841), stdev = 1196.018
    >>>>   CI (99.9%): [158138.649, 159930.474] (assumes normal distribution)

    * 메서드 한 번 실행 당 평균 소요 시간
      * <경과 시간> ±(<신뢰 구간>%) <오차 범위> <NanoSec/Operaion> [Average]
      * 159034.562 ±(99.9%) 895.912 ns/op [Average]
    * 메서드 실행 시간 통계
      * (min, avg, max) = (<min>, <avg>, <max>), stdev = <표준 편차>
      * (min, avg, max) = (158010.838, 159034.562, 162266.841), stdev = 1196.018
    * 신뢰도가 99.9%일 때 신뢰 구간
      * <Confidence Interval> (99.9%): <Range> (assumes normal distribution)
      * CI (99.9%): [158138.649, 159930.474] (assumes normal distribution)
     */

    private static final int UPPER_BOUND = 1_000;

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static void findNumbersBySingleThread() {
        final PrimeNumbers primeNumbers = new PrimeNumbers(UPPER_BOUND);
        primeNumbers.findPrimeNumbers();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static void findNumbersByForkJoinPool() {
        final PrimeNumbers primeNumbers = new PrimeNumbers(UPPER_BOUND);
        final ForkJoinPool pool = ForkJoinPool.commonPool();

        pool.invoke(primeNumbers);
        pool.shutdown();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public static void findNumbersByWorkStealingPool() {
        final PrimeNumbers primeNumbers = new PrimeNumbers(UPPER_BOUND);
        final int parallelism = ForkJoinPool.getCommonPoolParallelism();
        final ForkJoinPool stealer = (ForkJoinPool) Executors.newWorkStealingPool(parallelism);

        stealer.invoke(primeNumbers);
        stealer.shutdown();
    }

    /*
    # `UPPER_BOUND` 필드의 값이 `1_000`일 때 실행 전체 결과

    # JMH version: 1.36
    # VM version: JDK 17.0.7, OpenJDK 64-Bit Server VM, 17.0.7+7
    # VM invoker: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java
    # VM options: -Dfile.encoding=UTF-8 -Duser.country=KR -Duser.language=ko -Duser.variant
    # Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
    # Warmup: 5 iterations, 10 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.wanted.java04_forkjoin.Example04.findNumbersByForkJoinPool

    # Run progress: 0.00% complete, ETA 00:25:00
    # Fork: 1 of 5
    # Warmup Iteration   1: 87829.020 ns/op
    # Warmup Iteration   2: 88020.384 ns/op
    # Warmup Iteration   3: 88006.963 ns/op
    # Warmup Iteration   4: 88222.266 ns/op
    # Warmup Iteration   5: 87996.583 ns/op
    Iteration   1: 87943.443 ns/op
    Iteration   2: 87321.491 ns/op
    Iteration   3: 87568.151 ns/op
    Iteration   4: 88249.308 ns/op
    Iteration   5: 88297.793 ns/op

    # Run progress: 6.67% complete, ETA 00:23:23
    # Fork: 2 of 5
    # Warmup Iteration   1: 88226.603 ns/op
    # Warmup Iteration   2: 85741.721 ns/op
    # Warmup Iteration   3: 87762.159 ns/op
    # Warmup Iteration   4: 87960.571 ns/op
    # Warmup Iteration   5: 88209.758 ns/op
    Iteration   1: 88030.265 ns/op
    Iteration   2: 88206.204 ns/op
    Iteration   3: 88198.153 ns/op
    Iteration   4: 88143.113 ns/op
    Iteration   5: 88095.460 ns/op

    # Run progress: 13.33% complete, ETA 00:21:42
    # Fork: 3 of 5
    # Warmup Iteration   1: 88133.627 ns/op
    # Warmup Iteration   2: 87660.401 ns/op
    # Warmup Iteration   3: 87842.994 ns/op
    # Warmup Iteration   4: 87830.479 ns/op
    # Warmup Iteration   5: 87679.130 ns/op
    Iteration   1: 87524.704 ns/op
    Iteration   2: 88080.139 ns/op
    Iteration   3: 88116.947 ns/op
    Iteration   4: 88232.102 ns/op
    Iteration   5: 88129.728 ns/op

    # Run progress: 20.00% complete, ETA 00:20:02
    # Fork: 4 of 5
    # Warmup Iteration   1: 87599.090 ns/op
    # Warmup Iteration   2: 87771.498 ns/op
    # Warmup Iteration   3: 88136.409 ns/op
    # Warmup Iteration   4: 88345.557 ns/op
    # Warmup Iteration   5: 87841.993 ns/op
    Iteration   1: 87898.278 ns/op
    Iteration   2: 87685.085 ns/op
    Iteration   3: 88113.804 ns/op
    Iteration   4: 88130.825 ns/op
    Iteration   5: 88009.707 ns/op

    # Run progress: 26.67% complete, ETA 00:18:22
    # Fork: 5 of 5
    # Warmup Iteration   1: 88074.417 ns/op
    # Warmup Iteration   2: 88032.395 ns/op
    # Warmup Iteration   3: 87619.653 ns/op
    # Warmup Iteration   4: 88023.783 ns/op
    # Warmup Iteration   5: 87875.939 ns/op
    Iteration   1: 88257.722 ns/op
    Iteration   2: 88151.239 ns/op
    Iteration   3: 88212.558 ns/op
    Iteration   4: 88418.064 ns/op
    Iteration   5: 87976.954 ns/op


    Result "com.wanted.java04_forkjoin.Example04.findNumbersByForkJoinPool":
      88039.650 ±(99.9%) 195.818 ns/op [Average]
      (min, avg, max) = (87321.491, 88039.650, 88418.064), stdev = 261.412
      CI (99.9%): [87843.831, 88235.468] (assumes normal distribution)


    # JMH version: 1.36
    # VM version: JDK 17.0.7, OpenJDK 64-Bit Server VM, 17.0.7+7
    # VM invoker: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java
    # VM options: -Dfile.encoding=UTF-8 -Duser.country=KR -Duser.language=ko -Duser.variant
    # Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
    # Warmup: 5 iterations, 10 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.wanted.java04_forkjoin.Example04.findNumbersBySingleThread

    # Run progress: 33.33% complete, ETA 00:16:42
    # Fork: 1 of 5
    # Warmup Iteration   1: 158627.865 ns/op
    # Warmup Iteration   2: 159257.262 ns/op
    # Warmup Iteration   3: 158569.894 ns/op
    # Warmup Iteration   4: 158409.101 ns/op
    # Warmup Iteration   5: 158409.835 ns/op
    Iteration   1: 158145.156 ns/op
    Iteration   2: 158393.501 ns/op
    Iteration   3: 158484.340 ns/op
    Iteration   4: 158322.539 ns/op
    Iteration   5: 158529.193 ns/op

    # Run progress: 40.00% complete, ETA 00:15:02
    # Fork: 2 of 5
    # Warmup Iteration   1: 158603.515 ns/op
    # Warmup Iteration   2: 158979.949 ns/op
    # Warmup Iteration   3: 158616.000 ns/op
    # Warmup Iteration   4: 158633.134 ns/op
    # Warmup Iteration   5: 158516.596 ns/op
    Iteration   1: 158673.662 ns/op
    Iteration   2: 158556.120 ns/op
    Iteration   3: 158554.146 ns/op
    Iteration   4: 158602.668 ns/op
    Iteration   5: 158588.067 ns/op

    # Run progress: 46.67% complete, ETA 00:13:21
    # Fork: 3 of 5
    # Warmup Iteration   1: 158360.046 ns/op
    # Warmup Iteration   2: 158774.010 ns/op
    # Warmup Iteration   3: 158577.189 ns/op
    # Warmup Iteration   4: 158227.999 ns/op
    # Warmup Iteration   5: 158217.993 ns/op
    Iteration   1: 158164.612 ns/op
    Iteration   2: 158010.838 ns/op
    Iteration   3: 158432.750 ns/op
    Iteration   4: 158332.787 ns/op
    Iteration   5: 158736.146 ns/op

    # Run progress: 53.33% complete, ETA 00:11:41
    # Fork: 4 of 5
    # Warmup Iteration   1: 158842.650 ns/op
    # Warmup Iteration   2: 158971.331 ns/op
    # Warmup Iteration   3: 158677.697 ns/op
    # Warmup Iteration   4: 158671.855 ns/op
    # Warmup Iteration   5: 158632.880 ns/op
    Iteration   1: 158536.137 ns/op
    Iteration   2: 162266.841 ns/op
    Iteration   3: 158613.160 ns/op
    Iteration   4: 158605.212 ns/op
    Iteration   5: 158549.137 ns/op

    # Run progress: 60.00% complete, ETA 00:10:01
    # Fork: 5 of 5
    # Warmup Iteration   1: 159381.242 ns/op
    # Warmup Iteration   2: 160994.738 ns/op
    # Warmup Iteration   3: 158869.230 ns/op
    # Warmup Iteration   4: 160443.941 ns/op
    # Warmup Iteration   5: 159607.779 ns/op
    Iteration   1: 159011.356 ns/op
    Iteration   2: 161517.086 ns/op
    Iteration   3: 161854.457 ns/op
    Iteration   4: 160546.311 ns/op
    Iteration   5: 159837.815 ns/op


    Result "com.wanted.java04_forkjoin.Example04.findNumbersBySingleThread":
      159034.562 ±(99.9%) 895.912 ns/op [Average]
      (min, avg, max) = (158010.838, 159034.562, 162266.841), stdev = 1196.018
      CI (99.9%): [158138.649, 159930.474] (assumes normal distribution)


    # JMH version: 1.36
    # VM version: JDK 17.0.7, OpenJDK 64-Bit Server VM, 17.0.7+7
    # VM invoker: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java
    # VM options: -Dfile.encoding=UTF-8 -Duser.country=KR -Duser.language=ko -Duser.variant
    # Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
    # Warmup: 5 iterations, 10 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.wanted.java04_forkjoin.Example04.findNumbersByWorkStealingPool

    # Run progress: 66.67% complete, ETA 00:08:21
    # Fork: 1 of 5
    # Warmup Iteration   1: 228464.843 ns/op
    # Warmup Iteration   2: 218671.108 ns/op
    # Warmup Iteration   3: 212349.596 ns/op
    # Warmup Iteration   4: 207757.606 ns/op
    # Warmup Iteration   5: 198916.784 ns/op
    Iteration   1: 196781.863 ns/op
    Iteration   2: 204169.012 ns/op
    Iteration   3: 208023.692 ns/op
    Iteration   4: 199448.429 ns/op
    Iteration   5: 198696.597 ns/op

    # Run progress: 73.33% complete, ETA 00:06:40
    # Fork: 2 of 5
    # Warmup Iteration   1: 207026.533 ns/op
    # Warmup Iteration   2: 200986.022 ns/op
    # Warmup Iteration   3: 208973.000 ns/op
    # Warmup Iteration   4: 208985.189 ns/op
    # Warmup Iteration   5: 210997.962 ns/op
    Iteration   1: 209531.038 ns/op
    Iteration   2: 209602.497 ns/op
    Iteration   3: 208473.859 ns/op
    Iteration   4: 208280.093 ns/op
    Iteration   5: 198393.264 ns/op

    # Run progress: 80.00% complete, ETA 00:05:00
    # Fork: 3 of 5
    # Warmup Iteration   1: 203955.856 ns/op
    # Warmup Iteration   2: 207537.950 ns/op
    # Warmup Iteration   3: 209866.337 ns/op
    # Warmup Iteration   4: 200012.589 ns/op
    # Warmup Iteration   5: 206244.135 ns/op
    Iteration   1: 204509.517 ns/op
    Iteration   2: 204735.723 ns/op
    Iteration   3: 202953.156 ns/op
    Iteration   4: 209227.254 ns/op
    Iteration   5: 209850.697 ns/op

    # Run progress: 86.67% complete, ETA 00:03:20
    # Fork: 4 of 5
    # Warmup Iteration   1: 212345.317 ns/op
    # Warmup Iteration   2: 210828.986 ns/op
    # Warmup Iteration   3: 213020.714 ns/op
    # Warmup Iteration   4: 209046.094 ns/op
    # Warmup Iteration   5: 209803.396 ns/op
    Iteration   1: 216234.119 ns/op
    Iteration   2: 211602.903 ns/op
    Iteration   3: 218705.820 ns/op
    Iteration   4: 221822.310 ns/op
    Iteration   5: 217734.539 ns/op

    # Run progress: 93.33% complete, ETA 00:01:40
    # Fork: 5 of 5
    # Warmup Iteration   1: 208875.925 ns/op
    # Warmup Iteration   2: 201611.247 ns/op
    # Warmup Iteration   3: 207862.447 ns/op
    # Warmup Iteration   4: 201285.351 ns/op
    # Warmup Iteration   5: 210870.114 ns/op
    Iteration   1: 204361.987 ns/op
    Iteration   2: 207064.430 ns/op
    Iteration   3: 203579.282 ns/op
    Iteration   4: 207742.567 ns/op
    Iteration   5: 216002.356 ns/op


    Result "com.wanted.java04_forkjoin.Example04.findNumbersByWorkStealingPool":
      207901.080 ±(99.9%) 4906.515 ns/op [Average]
      (min, avg, max) = (196781.863, 207901.080, 221822.310), stdev = 6550.057
      CI (99.9%): [202994.565, 212807.595] (assumes normal distribution)


    # Run complete. Total time: 00:25:03

    REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
    why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
    experiments, perform baseline and negative tests that provide experimental control, make sure
    the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
    Do not assume the numbers tell you what you want them to tell.

    NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
    extra caution when trusting the results, look into the generated code to check the benchmark still
    works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
    different JVMs are already problematic, the performance difference caused by different Blackhole
    modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.

    Benchmark                                Mode  Cnt       Score      Error  Units
    Example04.findNumbersByForkJoinPool      avgt   25   88039.650 ±  195.818  ns/op
    Example04.findNumbersBySingleThread      avgt   25  159034.562 ±  895.912  ns/op
    Example04.findNumbersByWorkStealingPool  avgt   25  207901.080 ± 4906.515  ns/op
     */

    /*
    # `UPPER_BOUND` 필드의 값이 `10_000`일 때 실행 전체 결과

    # JMH version: 1.36
    # VM version: JDK 17.0.7, OpenJDK 64-Bit Server VM, 17.0.7+7
    # VM invoker: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java
    # VM options: -Dfile.encoding=UTF-8 -Duser.country=KR -Duser.language=ko -Duser.variant
    # Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
    # Warmup: 5 iterations, 10 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.wanted.java04_forkjoin.Example04.findNumbersByForkJoinPool

    # Run progress: 0.00% complete, ETA 00:25:00
    # Fork: 1 of 5
    # Warmup Iteration   1: 2225991.175 ns/op
    # Warmup Iteration   2: 2230797.883 ns/op
    # Warmup Iteration   3: 2204148.210 ns/op
    # Warmup Iteration   4: 2256203.532 ns/op
    # Warmup Iteration   5: 2212454.224 ns/op
    Iteration   1: 2217325.522 ns/op
    Iteration   2: 2196180.248 ns/op
    Iteration   3: 2199963.794 ns/op
    Iteration   4: 2220259.661 ns/op
    Iteration   5: 2174239.688 ns/op

    # Run progress: 6.67% complete, ETA 00:23:23
    # Fork: 2 of 5
    # Warmup Iteration   1: 2202025.042 ns/op
    # Warmup Iteration   2: 2184224.160 ns/op
    # Warmup Iteration   3: 2176738.715 ns/op
    # Warmup Iteration   4: 2213139.101 ns/op
    # Warmup Iteration   5: 2188958.124 ns/op
    Iteration   1: 2191933.158 ns/op
    Iteration   2: 2187334.955 ns/op
    Iteration   3: 2232084.114 ns/op
    Iteration   4: 2206045.012 ns/op
    Iteration   5: 2249074.144 ns/op

    # Run progress: 13.33% complete, ETA 00:21:43
    # Fork: 3 of 5
    # Warmup Iteration   1: 2196084.677 ns/op
    # Warmup Iteration   2: 2211826.380 ns/op
    # Warmup Iteration   3: 2177153.566 ns/op
    # Warmup Iteration   4: 2198391.053 ns/op
    # Warmup Iteration   5: 2168086.096 ns/op
    Iteration   1: 2190552.514 ns/op
    Iteration   2: 2233821.835 ns/op
    Iteration   3: 2259716.721 ns/op
    Iteration   4: 2233069.702 ns/op
    Iteration   5: 2243818.466 ns/op

    # Run progress: 20.00% complete, ETA 00:20:02
    # Fork: 4 of 5
    # Warmup Iteration   1: 2309558.562 ns/op
    # Warmup Iteration   2: 2255005.719 ns/op
    # Warmup Iteration   3: 2325553.441 ns/op
    # Warmup Iteration   4: 2266259.088 ns/op
    # Warmup Iteration   5: 2221568.649 ns/op
    Iteration   1: 2220147.955 ns/op
    Iteration   2: 2185793.634 ns/op
    Iteration   3: 2257822.447 ns/op
    Iteration   4: 2301548.356 ns/op
    Iteration   5: 2249560.627 ns/op

    # Run progress: 26.67% complete, ETA 00:18:22
    # Fork: 5 of 5
    # Warmup Iteration   1: 2304215.865 ns/op
    # Warmup Iteration   2: 2203855.411 ns/op
    # Warmup Iteration   3: 2187823.409 ns/op
    # Warmup Iteration   4: 2261004.217 ns/op
    # Warmup Iteration   5: 2269796.289 ns/op
    Iteration   1: 2256359.179 ns/op
    Iteration   2: 2338732.946 ns/op
    Iteration   3: 2300095.326 ns/op
    Iteration   4: 2306516.626 ns/op
    Iteration   5: 2272174.604 ns/op


    Result "com.wanted.java04_forkjoin.Example04.findNumbersByForkJoinPool":
      2236966.849 ±(99.9%) 32238.892 ns/op [Average]
      (min, avg, max) = (2174239.688, 2236966.849, 2338732.946), stdev = 43037.999
      CI (99.9%): [2204727.957, 2269205.742] (assumes normal distribution)


    # JMH version: 1.36
    # VM version: JDK 17.0.7, OpenJDK 64-Bit Server VM, 17.0.7+7
    # VM invoker: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java
    # VM options: -Dfile.encoding=UTF-8 -Duser.country=KR -Duser.language=ko -Duser.variant
    # Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
    # Warmup: 5 iterations, 10 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.wanted.java04_forkjoin.Example04.findNumbersBySingleThread

    # Run progress: 33.33% complete, ETA 00:16:42
    # Fork: 1 of 5
    # Warmup Iteration   1: 16159819.355 ns/op
    # Warmup Iteration   2: 16094408.695 ns/op
    # Warmup Iteration   3: 15933688.627 ns/op
    # Warmup Iteration   4: 15909157.855 ns/op
    # Warmup Iteration   5: 16000562.300 ns/op
    Iteration   1: 15916352.544 ns/op
    Iteration   2: 15913748.145 ns/op
    Iteration   3: 15973018.874 ns/op
    Iteration   4: 16022070.000 ns/op
    Iteration   5: 16068475.589 ns/op

    # Run progress: 40.00% complete, ETA 00:15:02
    # Fork: 2 of 5
    # Warmup Iteration   1: 15845965.123 ns/op
    # Warmup Iteration   2: 15898609.590 ns/op
    # Warmup Iteration   3: 15862536.252 ns/op
    # Warmup Iteration   4: 15885711.441 ns/op
    # Warmup Iteration   5: 15799927.248 ns/op
    Iteration   1: 15853089.068 ns/op
    Iteration   2: 15866293.517 ns/op
    Iteration   3: 15960239.965 ns/op
    Iteration   4: 15850265.756 ns/op
    Iteration   5: 15991466.588 ns/op

    # Run progress: 46.67% complete, ETA 00:13:21
    # Fork: 3 of 5
    # Warmup Iteration   1: 15791660.686 ns/op
    # Warmup Iteration   2: 15784104.035 ns/op
    # Warmup Iteration   3: 15792270.637 ns/op
    # Warmup Iteration   4: 15828628.291 ns/op
    # Warmup Iteration   5: 15802910.028 ns/op
    Iteration   1: 15831137.658 ns/op
    Iteration   2: 15834362.935 ns/op
    Iteration   3: 15965021.332 ns/op
    Iteration   4: 15963105.463 ns/op
    Iteration   5: 16041437.365 ns/op

    # Run progress: 53.33% complete, ETA 00:11:41
    # Fork: 4 of 5
    # Warmup Iteration   1: 15809417.523 ns/op
    # Warmup Iteration   2: 15841408.623 ns/op
    # Warmup Iteration   3: 15993985.757 ns/op
    # Warmup Iteration   4: 15911401.431 ns/op
    # Warmup Iteration   5: 15858416.865 ns/op
    Iteration   1: 15849048.259 ns/op
    Iteration   2: 15828692.141 ns/op
    Iteration   3: 15811526.395 ns/op
    Iteration   4: 15924130.763 ns/op
    Iteration   5: 16011902.866 ns/op

    # Run progress: 60.00% complete, ETA 00:10:01
    # Fork: 5 of 5
    # Warmup Iteration   1: 15964524.056 ns/op
    # Warmup Iteration   2: 15893230.489 ns/op
    # Warmup Iteration   3: 15943896.298 ns/op
    # Warmup Iteration   4: 16064906.568 ns/op
    # Warmup Iteration   5: 15942179.538 ns/op
    Iteration   1: 15941748.475 ns/op
    Iteration   2: 15962565.989 ns/op
    Iteration   3: 15874115.029 ns/op
    Iteration   4: 15924416.269 ns/op
    Iteration   5: 15998594.716 ns/op


    Result "com.wanted.java04_forkjoin.Example04.findNumbersBySingleThread":
      15927073.028 ±(99.9%) 55358.717 ns/op [Average]
      (min, avg, max) = (15811526.395, 15927073.028, 16068475.589), stdev = 73902.304
      CI (99.9%): [15871714.310, 15982431.745] (assumes normal distribution)


    # JMH version: 1.36
    # VM version: JDK 17.0.7, OpenJDK 64-Bit Server VM, 17.0.7+7
    # VM invoker: /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java
    # VM options: -Dfile.encoding=UTF-8 -Duser.country=KR -Duser.language=ko -Duser.variant
    # Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
    # Warmup: 5 iterations, 10 s each
    # Measurement: 5 iterations, 10 s each
    # Timeout: 10 min per iteration
    # Threads: 1 thread, will synchronize iterations
    # Benchmark mode: Average time, time/op
    # Benchmark: com.wanted.java04_forkjoin.Example04.findNumbersByWorkStealingPool

    # Run progress: 66.67% complete, ETA 00:08:21
    # Fork: 1 of 5
    # Warmup Iteration   1: 2602564.001 ns/op
    # Warmup Iteration   2: 2710915.109 ns/op
    # Warmup Iteration   3: 2606420.129 ns/op
    # Warmup Iteration   4: 2599767.429 ns/op
    # Warmup Iteration   5: 2608577.533 ns/op
    Iteration   1: 2600872.422 ns/op
    Iteration   2: 2597950.794 ns/op
    Iteration   3: 2608728.004 ns/op
    Iteration   4: 2600830.257 ns/op
    Iteration   5: 2601865.185 ns/op

    # Run progress: 73.33% complete, ETA 00:06:41
    # Fork: 2 of 5
    # Warmup Iteration   1: 2616117.593 ns/op
    # Warmup Iteration   2: 2594270.893 ns/op
    # Warmup Iteration   3: 2592886.229 ns/op
    # Warmup Iteration   4: 2596347.291 ns/op
    # Warmup Iteration   5: 2587246.671 ns/op
    Iteration   1: 2577724.734 ns/op
    Iteration   2: 2581132.921 ns/op
    Iteration   3: 2570347.044 ns/op
    Iteration   4: 2627426.402 ns/op
    Iteration   5: 2576434.067 ns/op

    # Run progress: 80.00% complete, ETA 00:05:00
    # Fork: 3 of 5
    # Warmup Iteration   1: 2633375.121 ns/op
    # Warmup Iteration   2: 2592141.429 ns/op
    # Warmup Iteration   3: 2642155.356 ns/op
    # Warmup Iteration   4: 2641019.926 ns/op
    # Warmup Iteration   5: 2636239.583 ns/op
    Iteration   1: 2583798.745 ns/op
    Iteration   2: 2573717.335 ns/op
    Iteration   3: 2638574.785 ns/op
    Iteration   4: 2618820.368 ns/op
    Iteration   5: 2613062.538 ns/op

    # Run progress: 86.67% complete, ETA 00:03:20
    # Fork: 4 of 5
    # Warmup Iteration   1: 2633704.187 ns/op
    # Warmup Iteration   2: 2625596.464 ns/op
    # Warmup Iteration   3: 2581625.559 ns/op
    # Warmup Iteration   4: 2580525.604 ns/op
    # Warmup Iteration   5: 2656495.045 ns/op
    Iteration   1: 2618106.396 ns/op
    Iteration   2: 2652782.487 ns/op
    Iteration   3: 2685429.508 ns/op
    Iteration   4: 2641257.227 ns/op
    Iteration   5: 2630378.801 ns/op

    # Run progress: 93.33% complete, ETA 00:01:40
    # Fork: 5 of 5
    # Warmup Iteration   1: 2665329.650 ns/op
    # Warmup Iteration   2: 2639705.816 ns/op
    # Warmup Iteration   3: 2642267.406 ns/op
    # Warmup Iteration   4: 2671512.380 ns/op
    # Warmup Iteration   5: 2690750.941 ns/op
    Iteration   1: 2665837.562 ns/op
    Iteration   2: 2596934.824 ns/op
    Iteration   3: 2690912.857 ns/op
    Iteration   4: 2646150.004 ns/op
    Iteration   5: 2601242.668 ns/op


    Result "com.wanted.java04_forkjoin.Example04.findNumbersByWorkStealingPool":
      2616012.717 ±(99.9%) 25337.010 ns/op [Average]
      (min, avg, max) = (2570347.044, 2616012.717, 2690912.857), stdev = 33824.183
      CI (99.9%): [2590675.708, 2641349.727] (assumes normal distribution)


    # Run complete. Total time: 00:25:03

    REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
    why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
    experiments, perform baseline and negative tests that provide experimental control, make sure
    the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
    Do not assume the numbers tell you what you want them to tell.

    NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
    extra caution when trusting the results, look into the generated code to check the benchmark still
    works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
    different JVMs are already problematic, the performance difference caused by different Blackhole
    modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.

    Benchmark                                Mode  Cnt         Score       Error  Units
    Example04.findNumbersByForkJoinPool      avgt   25   2236966.849 ± 32238.892  ns/op
    Example04.findNumbersBySingleThread      avgt   25  15927073.028 ± 55358.717  ns/op
    Example04.findNumbersByWorkStealingPool  avgt   25   2616012.717 ± 25337.010  ns/op

     */
}
