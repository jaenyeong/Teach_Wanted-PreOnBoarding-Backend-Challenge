package com.wanted.java02_nativememorytracking;

public class Example02 {
    /*
    # 네이티브 메모리 트래킹 방법 정리

    ## 필요한 튜닝 플래그 찾기
    * `java -XX:+PrintFlagsFinal -version | grep <concept>`
    * 예시
      `java -XX:+PrintFlagsFinal -version | grep Metaspace`
              size_t MaxMetaspaceExpansion                    = 5439488                                   {product} {default}
                uintx MaxMetaspaceFreeRatio                   = 70                                        {product} {default}
               size_t MaxMetaspaceSize                        = 18446744073709551615                      {product} {default}
                ccstr MetaspaceReclaimPolicy                  = balanced                                  {product} {default}
               size_t MetaspaceSize                           = 22020096                                  {product} {default}
               size_t MinMetaspaceExpansion                   = 327680                                    {product} {default}
                uintx MinMetaspaceFreeRatio                   = 40                                        {product} {default}
            openjdk version "17.0.7" 2023-04-18
            OpenJDK Runtime Environment Temurin-17.0.7+7 (build 17.0.7+7)
            OpenJDK 64-Bit Server VM Temurin-17.0.7+7 (build 17.0.7+7, mixed mode)

    # NMT(Native Memory Tracking) 옵션 확인

    ## 옵션 확인을 위해 `WantedSampleModule.jar` 모듈 실행
    <<< [주의] 약 16분 실행되므로 예제 확인 후 반드시 종료할 것! (종료 방법 하단 참조) >>>

    * `WantedSampleModule.jar` 파일을 해당 경로에서 실행 (단순히 스레드를 16분 슬립시킨 로직을 담은 jar 파일)
      * `java -XX:NativeMemoryTracking=summary -Xms300m -Xmx300m -XX:+UseG1GC -jar WantedSampleModule.jar &`
    * 실행 시 주의 위 명령은 연속으로 계속 실행할 수 있기 때문에 실행할 수록 프로세스가 그만큼 실행됨
    * 따라서 여러번 실행했다면 종료시 모든 `WantedSampleModule` 프로세스를 찾아 종료할 것!

    ## 실행한 `WantedSampleModule.jar` 모듈의 `pid` 확인
    * `jps`
       ### 아래는 목록은 예시이므로 실제 Pid 값을 잘 확인할 것!
           28609 Jps
           25348 RemoteMavenServer36
           25337 GradleDaemon
           30707 WantedSampleModule.jar
           24990

    ## 인스턴스 스냅샷 확인
    * NMT 기본 스냅샷 확인 명령
      * `jcmd <pid> VM.native_memory`
    * `baseline` 옵션으로 시간 경과에 따라 변경되는 정보 확인 가능
      * `jcmd <pid> VM.native_memory baseline`
    * 그 후에 현재 기준점과 메모리 비교
      * `jcmd <pid> VM.native_memory summary.diff`
    * 또한 아래 옵션 플래그로 상세 정보를 활성화할 수 있음
      * `-XX:NativeMemoryTracking=detail`

    ## 프로세스 종료
    * 위에서 확인한 pid를 기준으로 프로세스 종료
    * `kill -9 <pid>` 명령으로 `WantedSampleModule.jar` 프로세스 종료
      * 예시 명령 `kill -9 30707`
    * 종료 후 반드시 `jps` 명령으로 한 번 더 확인할 것!

    ## 또는 아래 명령으로 한 번에 종료 (대괄호 안에 `를 포함한 모든 명령을 복사해서 실행할 것)
    * [kill `jps -l | grep "WantedSampleModule.jar" | cut -d " " -f 1`]

    ---스냅샷 정보---

    ### Total Allocations
    * `reserved`는 향후 사용할 수 있는 잠재적 메모리 양, `committed`는 현재 사용중인 메모리 양을 나타냄
    * 위에서 300MB를 할당했으나 둘다 설정값보다 많은 메모리가 할당됨
    >>>> Native Memory Tracking:
    >>>> (Omitting categories weighting less than 1KB)
    >>>> Total: reserved=1779499KB, committed=429947KB

    ### Heap
    * `reserved`, `committed` 모두 설정값(300MB)과 일치
    >>>> Java Heap (reserved=311296KB, committed=311296KB)
    >>>>           (mmap: reserved=311296KB, committed=311296KB)

    ### Metaspace
    * 약 80KB가 620개의 클래스 데이터를 로딩하는 데 사용됨
    >>>> Class (reserved=80KB, committed=80KB)
    >>>>       (classes #620)
    >>>>       (  instance classes #539, array classes #81)
    >>>>       (malloc=80KB #688)

    ### Thread
    * 약 39MB가 19개의 스레드 스택(JVM stack)에 할당됨
    * JVM은 스레드 생성 시 스택 메모리를 할당하므로 `reserved`와 `committed` 사용량이 같음
    >>>> Thread (reserved=39187KB, committed=39187KB)
    >>>>        (thread #19)
    >>>>        (stack: reserved=39140KB, committed=39140KB)
    >>>>        (malloc=27KB #118)
    >>>>        (arena=20KB #36)

    ### Code Cache
    * 약 7MB가 코드 캐싱을 위해 사용된 양이며 최대 247MB까지 사용할 수 있음
    >>>> Code (reserved=247734KB, committed=7622KB)
    >>>>      (malloc=38KB #661)
    >>>>      (mmap: reserved=247696KB, committed=7584KB)

    ### GC
    * G1 GC의 경우 약 63MB가 예약됨
    * GC에 따라 기본 메모리 사용량이 다를 수 있으나 그렇다고 이것이 GC 알고리즘을 선택하는 기준이 되면 안됨
    >>>> GC (reserved=63782KB, committed=63782KB)
    >>>>    (malloc=19382KB #1117)
    >>>>    (mmap: reserved=44400KB, committed=44400KB)

    ### Symbol
    * 약 1MB가 스트링 테이블과 상수 풀 등에 사용됨
    >>>> Symbol (reserved=1930KB, committed=1930KB)
    >>>>        (malloc=1186KB #23827)
    >>>>        (arena=744KB #1)
     */

    public static void main(String[] args) {
        // Nothing
    }

    /*
    # jcmd 명령 전체 결과

    ### 스냅샷 결과
    30707:

    Native Memory Tracking:

    (Omitting categories weighting less than 1KB)

    Total: reserved=1779499KB, committed=429947KB
           malloc: 22791KB #31826
           mmap:   reserved=1756708KB, committed=407156KB

    -                 Java Heap (reserved=311296KB, committed=311296KB)
                                (mmap: reserved=311296KB, committed=311296KB)

    -                     Class (reserved=80KB, committed=80KB)
                                (classes #620)
                                (  instance classes #539, array classes #81)
                                (malloc=80KB #688)
                                (  Metadata:   )
                                (    reserved=65536KB, committed=4224KB)
                                (    used=4115KB)
                                (    waste=109KB =2.59%)
                                (  Class space:)
                                (    reserved=1048576KB, committed=448KB)
                                (    used=377KB)
                                (    waste=71KB =15.93%)

    -                    Thread (reserved=39187KB, committed=39187KB)
                                (thread #19)
                                (stack: reserved=39140KB, committed=39140KB)
                                (malloc=27KB #118)
                                (arena=20KB #36)

    -                      Code (reserved=247734KB, committed=7622KB)
                                (malloc=38KB #661)
                                (mmap: reserved=247696KB, committed=7584KB)

    -                        GC (reserved=63782KB, committed=63782KB)
                                (malloc=19382KB #1117)
                                (mmap: reserved=44400KB, committed=44400KB)

    -                  Compiler (reserved=168KB, committed=168KB)
                                (malloc=4KB #31)
                                (arena=165KB #5)

    -                  Internal (reserved=191KB, committed=191KB)
                                (malloc=159KB #1575)
                                (mmap: reserved=32KB, committed=32KB)

    -                     Other (reserved=2KB, committed=2KB)
                                (malloc=2KB #1)

    -                    Symbol (reserved=1930KB, committed=1930KB)
                                (malloc=1186KB #23827)
                                (arena=744KB #1)

    -    Native Memory Tracking (reserved=501KB, committed=501KB)
                                (malloc=4KB #55)
                                (tracking overhead=497KB)

    -               Arena Chunk (reserved=175KB, committed=175KB)
                                (malloc=175KB)

    -                    Module (reserved=155KB, committed=155KB)
                                (malloc=155KB #1207)

    -                 Safepoint (reserved=32KB, committed=32KB)
                                (mmap: reserved=32KB, committed=32KB)

    -           Synchronization (reserved=139KB, committed=139KB)
                                (malloc=139KB #2439)

    -            Serviceability (reserved=1KB, committed=1KB)
                                (malloc=1KB #6)

    -                 Metaspace (reserved=65546KB, committed=4234KB)
                                (malloc=10KB #8)
                                (mmap: reserved=65536KB, committed=4224KB)

    -      String Deduplication (reserved=1KB, committed=1KB)
                                (malloc=1KB #8)

    -           Object Monitors (reserved=1KB, committed=1KB)
                                (malloc=1KB #3)

    -                   Unknown (reserved=1048576KB, committed=448KB)
                                (mmap: reserved=1048576KB, committed=448KB)
     */
}
