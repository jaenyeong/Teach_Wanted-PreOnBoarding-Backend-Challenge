# jcmd로 스레드덤프 분석
`jps`, `jstat`처럼 분석, 트러블슈팅을 위한 도구

## 앱 실행 후 PID 확인
* `Teach_Wanted-PreOnBoarding-Challenge/Chapter1-2/src/main/java/com/wanted/java07_threaddump` 경로에서 `java Example07.java` 명령 실행
* `jps` 명령으로 PID 확인 (위 명령으로 실행 시 `Main` 프로세스로 표시됨)

## jcmd 명령 설명
`jcmd <pid>` 실행 시 옵션 목록 출력
* `jcmd <pid> help` : 실행 가능한 옵션 목록 모두 출력
* `jcmd <pid> VM.version` : JVM 기본 세부 정보 출력
* `jcmd <pid> VM.system_properties` : VM 관련 모든 시스템 속성 출력
* `jcmd <pid> VM.flags` : 실행 시 직접 입력한 인수나 JVM 기본 값으로 사용되는 모든 인수 출력
* `jcmd <pid> Thread.print` : 인스턴스 스레드 덤프, 실행중인 모든 스레드의 스택을 출력
* `jcmd <pid> GC.class_histogram` : 인스턴스 개수가 많은 클래스를 출력
* `jcmd <pid> GC.heap_dump` : JVM 힙 덤프를 파일로 제공
* `jcmd <pid> JFR.start name=demo_recording settings=profile delay=10s duration=20s filename=./demorecording.jfr` : JFR 파일 생성
   * Java Flight Recorder (내장된 프로파일링 프레임워크)
   * JMC를 통해 JFR에서 수집한 데이터를 시각화할 수 있고, 로우 레벨의 런타임 정보를 지속적으로 수집 가능
* `jcmd <pid> VM.native_memory` : 네이티브 메모리 정보를 출력
   * 이 옵션을 사용하려면 다음과 같은 VM 옵션을 주고 실행된 상태여야 함
     * `-XX:NativeMemoryTracking=summary` 또는 `-XX:NativeMemoryTracking=detail`
* `jcmd <pid> VM.native_memory baseline` & `jcmd <pid> VM.native_memory summary.diff` : 메모리 누수를 확인하기 위해 지속적인 모니터링
   * 메모리 모니터링 시작점을 지정하고 후에 특정 시점에서 변경 사항 확인

## jcmd 명령으로 해당 프로세스에 대한 스레드덤프 출력
* `jcmd <pid> Thread.print`
  * `jcmd 17749 Thread.print`

## `jcmd 17749 Thread.print` 실행 전체 결과

```
17749:
2023-06-12 02:00:40
Full thread dump OpenJDK 64-Bit Server VM (17.0.7+7 mixed mode):

Threads class SMR info:
_java_thread_list=0x000060000050f580, length=112, elements={
0x000000012e80c000, 0x000000012e909a00, 0x000000012e90c200, 0x000000012e914000,
0x000000012b011800, 0x000000012b011e00, 0x000000012b015600, 0x000000012f80f600,
0x000000012f80b400, 0x000000012e914600, 0x000000012e8f4400, 0x0000000138898a00,
0x000000012b09ea00, 0x000000012b09f000, 0x000000012e910e00, 0x000000012e911400,
0x000000012e911a00, 0x000000012e912000, 0x000000012e917800, 0x000000012e917e00,
0x000000012b09f600, 0x000000012e918400, 0x000000012b09fc00, 0x000000012b0a8400,
0x000000012b0a8a00, 0x000000012b0a9000, 0x000000012b0a9600, 0x000000012f80ba00,
0x000000012f816a00, 0x000000012f817000, 0x000000012f817600, 0x000000012b0a9c00,
0x000000012b098400, 0x000000012b098a00, 0x000000012b099000, 0x000000012b099600,
0x000000012b099c00, 0x000000012b09a200, 0x000000012b09a800, 0x000000012b09ae00,
0x000000012b09b400, 0x000000012e918a00, 0x000000012e919000, 0x000000012e919600,
0x000000012e91fe00, 0x000000012b09ba00, 0x000000012b09c000, 0x000000012e920400,
0x000000012e920a00, 0x000000012e921000, 0x000000012e921600, 0x000000012e921c00,
0x000000012e922200, 0x000000012e922800, 0x000000012e922e00, 0x000000012e923400,
0x000000012e923a00, 0x000000012b09c600, 0x000000012b09cc00, 0x000000012e924000,
0x000000012e924600, 0x000000012e924c00, 0x000000012b09d200, 0x000000012b09d800,
0x000000012b09de00, 0x000000012b09e400, 0x000000012e94d600, 0x000000012e94dc00,
0x000000012e94e200, 0x000000012f817c00, 0x000000012f818200, 0x000000012f818800,
0x000000012f818e00, 0x000000012f819400, 0x000000012f819a00, 0x000000012f81a000,
0x000000012f81a600, 0x000000012b059400, 0x000000012b059a00, 0x000000012b05a000,
0x000000012b05a600, 0x000000012e94e800, 0x000000012e94ee00, 0x000000012f81ac00,
0x000000012f82b200, 0x000000012f82b800, 0x000000012e94f400, 0x000000012b05ac00,
0x000000012e94fa00, 0x000000012e950000, 0x000000012f82be00, 0x000000012f00be00,
0x000000012e950600, 0x000000012f00c400, 0x000000012f01dc00, 0x000000012f01e200,
0x0000000138893200, 0x000000012f82c400, 0x000000012f82ca00, 0x000000012f82d000,
0x000000012e950c00, 0x000000012f82d600, 0x000000012f82dc00, 0x000000012e951200,
0x000000012e951800, 0x000000012e951e00, 0x000000012e952400, 0x000000012f82e200,
0x0000000138893800, 0x0000000138893e00, 0x0000000138894400, 0x000000012e952a00
}

"main" #1 prio=5 os_prio=31 cpu=128.59ms elapsed=224.55s tid=0x000000012e80c000 nid=0x2203 waiting on condition  [0x000000016b256000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
        at com.wanted.java07_threaddump.Example07.main(Example07.java:19)

"Reference Handler" #2 daemon prio=10 os_prio=31 cpu=0.04ms elapsed=224.54s tid=0x000000012e909a00 nid=0x4903 waiting on condition  [0x000000016c0aa000]
   java.lang.Thread.State: RUNNABLE
        at java.lang.ref.Reference.waitForReferencePendingList(java.base@17.0.7/Native Method)
        at java.lang.ref.Reference.processPendingReferences(java.base@17.0.7/Reference.java:253)
        at java.lang.ref.Reference$ReferenceHandler.run(java.base@17.0.7/Reference.java:215)

"Finalizer" #3 daemon prio=8 os_prio=31 cpu=0.12ms elapsed=224.54s tid=0x000000012e90c200 nid=0x4c03 in Object.wait()  [0x000000016c2b6000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(java.base@17.0.7/Native Method)
        - waiting on <0x000000061fc0bff0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(java.base@17.0.7/ReferenceQueue.java:155)
        - locked <0x000000061fc0bff0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(java.base@17.0.7/ReferenceQueue.java:176)
        at java.lang.ref.Finalizer$FinalizerThread.run(java.base@17.0.7/Finalizer.java:172)

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 cpu=0.13ms elapsed=224.54s tid=0x000000012e914000 nid=0x7a03 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Service Thread" #5 daemon prio=9 os_prio=31 cpu=9.25ms elapsed=224.54s tid=0x000000012b011800 nid=0x7903 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Deflation Thread" #6 daemon prio=9 os_prio=31 cpu=3.92ms elapsed=224.54s tid=0x000000012b011e00 nid=0x5c03 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #7 daemon prio=9 os_prio=31 cpu=10.21ms elapsed=224.54s tid=0x000000012b015600 nid=0x7603 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"C1 CompilerThread0" #10 daemon prio=9 os_prio=31 cpu=24.73ms elapsed=224.54s tid=0x000000012f80f600 nid=0x5d03 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"Sweeper thread" #11 daemon prio=9 os_prio=31 cpu=0.01ms elapsed=224.54s tid=0x000000012f80b400 nid=0x7303 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Notification Thread" #12 daemon prio=9 os_prio=31 cpu=0.02ms elapsed=224.52s tid=0x000000012e914600 nid=0x7103 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Common-Cleaner" #13 daemon prio=8 os_prio=31 cpu=0.38ms elapsed=224.52s tid=0x000000012e8f4400 nid=0x6003 in Object.wait()  [0x000000016d63a000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
        at java.lang.Object.wait(java.base@17.0.7/Native Method)
        - waiting on <0x000000061fd826a0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(java.base@17.0.7/ReferenceQueue.java:155)
        - locked <0x000000061fd826a0> (a java.lang.ref.ReferenceQueue$Lock)
        at jdk.internal.ref.CleanerImpl.run(java.base@17.0.7/CleanerImpl.java:140)
        at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)
        at jdk.internal.misc.InnocuousThread.run(java.base@17.0.7/InnocuousThread.java:162)

"Attach Listener" #1514 daemon prio=9 os_prio=31 cpu=0.46ms elapsed=77.42s tid=0x0000000138898a00 nid=0x5807 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Thread-2200" #2215 prio=5 os_prio=31 cpu=0.05ms elapsed=4.31s tid=0x000000012b09ea00 nid=0xfc1f waiting on condition  [0x000000016d846000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
        at com.wanted.java07_threaddump.Example07.lambda$createThreads$0(Example07.java:32)
        at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
        at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-2201" #2216 prio=5 os_prio=31 cpu=0.04ms elapsed=4.31s tid=0x000000012b09f000 nid=0x10c5b waiting on condition  [0x000000016da52000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
        at com.wanted.java07_threaddump.Example07.lambda$createThreads$0(Example07.java:32)
        at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
        at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

 .... <스레드가 너무 많아 생략> ....
```
