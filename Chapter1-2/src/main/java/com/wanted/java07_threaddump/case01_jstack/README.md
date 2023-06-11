# jstack으로 스레드덤프 분석
Java 프로세스, 코어 파일, 스레드 스택 트레이싱 결과 등의 덤프를 생성하는 도구

## 앱 실행 후 PID 확인
* `Teach_Wanted-PreOnBoarding-Challenge/Chapter1-2/src/main/java/com/wanted/java07_threaddump` 경로에서 `java Example07.java` 명령 실행
* `jps` 명령으로 PID 확인 (위 명령으로 실행 시 `Main` 프로세스로 표시됨)

## jstack 명령으로 해당 프로세스에 대한 스레드덤프 출력
* `jstack <pid>`
  * `jstack 24892`

### 출력된 정보 중에 "main" 스레드 분석
* <스레드명> : "main"
* <스레드의 ID> : #1
* <Java 스레드의 우선순위> : prio=5
* <OS에서 제공하는 스레드의 우선순위> : os_prio=31
* <스레드가 사용한 CPU 사용시간> : cpu=42.90ms
* <스레드가 생성(시작)되고 경과한 시간> : elapsed=11.92s
* <JVM 내부에서 사용되는 스레드 ID> : tid=0x000000011f008e00
* <스레드의 네이티브 OS 스레드 ID> : nid=0x2803
* <스레드의 현재 상태> : waiting on condition
* <스레드의 스택 상위 주소> : [0x000000016d87e000]
* <스레드의 Java 상태> : java.lang.Thread.State: TIMED_WAITING (sleeping)
* <스레드가 현재 실행중인 코드 위치> : at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
* <스택 트레이싱 (메인 메서드를 의미)> : at com.wanted.java07_threaddump.Example07.main(Example07.java:12)

```
"main" #1 prio=5 os_prio=31 cpu=42.90ms elapsed=11.92s tid=0x000000011f008e00 nid=0x2803 waiting on condition  [0x000000016d87e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.main(Example07.java:12)
```

## jstack 명령 전체 결과

```console
2023-06-11 22:10:17
Full thread dump OpenJDK 64-Bit Server VM (17.0.7+7 mixed mode):

Threads class SMR info:
_java_thread_list=0x0000600002664160, length=22, elements={
0x000000011f008e00, 0x000000013f009200, 0x000000013f809200, 0x000000011f810200,
0x000000011f812a00, 0x000000011f813000, 0x000000011f051800, 0x000000013f00e600,
0x000000011f053400, 0x00000001030b3600, 0x000000013f00c000, 0x000000012f80a400,
0x000000012f80aa00, 0x000000012f80b000, 0x000000013a00a400, 0x000000013a00aa00,
0x000000013a00b000, 0x000000013a00b600, 0x000000012f80b600, 0x000000013a00bc00,
0x000000013a00c200, 0x000000012f80bc00
}

"main" #1 prio=5 os_prio=31 cpu=42.90ms elapsed=11.92s tid=0x000000011f008e00 nid=0x2803 waiting on condition  [0x000000016d87e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.main(Example07.java:12)

"Reference Handler" #2 daemon prio=10 os_prio=31 cpu=0.06ms elapsed=11.91s tid=0x000000013f009200 nid=0x4a03 waiting on condition  [0x000000016e6d2000]
   java.lang.Thread.State: RUNNABLE
    at java.lang.ref.Reference.waitForReferencePendingList(java.base@17.0.7/Native Method)
    at java.lang.ref.Reference.processPendingReferences(java.base@17.0.7/Reference.java:253)
    at java.lang.ref.Reference$ReferenceHandler.run(java.base@17.0.7/Reference.java:215)

"Finalizer" #3 daemon prio=8 os_prio=31 cpu=0.21ms elapsed=11.91s tid=0x000000013f809200 nid=0x4b03 in Object.wait()  [0x000000016e8de000]
   java.lang.Thread.State: WAITING (on object monitor)
    at java.lang.Object.wait(java.base@17.0.7/Native Method)
    - waiting on <0x000000061fc0bff0> (a java.lang.ref.ReferenceQueue$Lock)
    at java.lang.ref.ReferenceQueue.remove(java.base@17.0.7/ReferenceQueue.java:155)
    - locked <0x000000061fc0bff0> (a java.lang.ref.ReferenceQueue$Lock)
    at java.lang.ref.ReferenceQueue.remove(java.base@17.0.7/ReferenceQueue.java:176)
    at java.lang.ref.Finalizer$FinalizerThread.run(java.base@17.0.7/Finalizer.java:172)

"Signal Dispatcher" #4 daemon prio=9 os_prio=31 cpu=0.46ms elapsed=11.90s tid=0x000000011f810200 nid=0x7c03 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Service Thread" #5 daemon prio=9 os_prio=31 cpu=0.07ms elapsed=11.90s tid=0x000000011f812a00 nid=0x5d03 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Monitor Deflation Thread" #6 daemon prio=9 os_prio=31 cpu=0.17ms elapsed=11.90s tid=0x000000011f813000 nid=0x5f03 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"C2 CompilerThread0" #7 daemon prio=9 os_prio=31 cpu=4.71ms elapsed=11.90s tid=0x000000011f051800 nid=0x6103 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"C1 CompilerThread0" #10 daemon prio=9 os_prio=31 cpu=9.54ms elapsed=11.90s tid=0x000000013f00e600 nid=0x6303 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
   No compile task

"Sweeper thread" #11 daemon prio=9 os_prio=31 cpu=0.01ms elapsed=11.90s tid=0x000000011f053400 nid=0x6503 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Notification Thread" #12 daemon prio=9 os_prio=31 cpu=0.02ms elapsed=11.89s tid=0x00000001030b3600 nid=0x6603 runnable  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"Common-Cleaner" #13 daemon prio=8 os_prio=31 cpu=0.05ms elapsed=11.89s tid=0x000000013f00c000 nid=0x6a03 in Object.wait()  [0x000000016fc62000]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
    at java.lang.Object.wait(java.base@17.0.7/Native Method)
    - waiting on <0x000000061fd76e10> (a java.lang.ref.ReferenceQueue$Lock)
    at java.lang.ref.ReferenceQueue.remove(java.base@17.0.7/ReferenceQueue.java:155)
    - locked <0x000000061fd76e10> (a java.lang.ref.ReferenceQueue$Lock)
    at jdk.internal.ref.CleanerImpl.run(java.base@17.0.7/CleanerImpl.java:140)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)
    at jdk.internal.misc.InnocuousThread.run(java.base@17.0.7/InnocuousThread.java:162)

"Thread-10" #24 prio=5 os_prio=31 cpu=0.16ms elapsed=1.87s tid=0x000000012f80a400 nid=0xa707 waiting on condition  [0x000000016fe6e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-11" #25 prio=5 os_prio=31 cpu=0.12ms elapsed=1.87s tid=0x000000012f80aa00 nid=0x7807 waiting on condition  [0x0000000178206000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-12" #26 prio=5 os_prio=31 cpu=0.11ms elapsed=1.87s tid=0x000000012f80b000 nid=0x7507 waiting on condition  [0x0000000178412000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-13" #27 prio=5 os_prio=31 cpu=0.11ms elapsed=1.87s tid=0x000000013a00a400 nid=0x7207 waiting on condition  [0x000000017861e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-14" #28 prio=5 os_prio=31 cpu=0.08ms elapsed=1.87s tid=0x000000013a00aa00 nid=0x7607 waiting on condition  [0x000000017882a000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-15" #29 prio=5 os_prio=31 cpu=0.11ms elapsed=1.87s tid=0x000000013a00b000 nid=0x8007 waiting on condition  [0x0000000178a36000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-16" #30 prio=5 os_prio=31 cpu=0.10ms elapsed=1.87s tid=0x000000013a00b600 nid=0xa907 waiting on condition  [0x0000000178c42000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-17" #31 prio=5 os_prio=31 cpu=0.11ms elapsed=1.87s tid=0x000000012f80b600 nid=0xa807 waiting on condition  [0x0000000178e4e000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-18" #32 prio=5 os_prio=31 cpu=0.08ms elapsed=1.87s tid=0x000000013a00bc00 nid=0x7707 waiting on condition  [0x000000017905a000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Thread-19" #33 prio=5 os_prio=31 cpu=0.10ms elapsed=1.87s tid=0x000000013a00c200 nid=0x6c07 waiting on condition  [0x0000000179266000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.7/Native Method)
    at com.wanted.java07_threaddump.Example07.lambda$createThread$0(Example07.java:21)
    at com.wanted.java07_threaddump.Example07$$Lambda$1/0x0000000800000a08.run(Unknown Source)
    at java.lang.Thread.run(java.base@17.0.7/Thread.java:833)

"Attach Listener" #34 daemon prio=9 os_prio=31 cpu=1.20ms elapsed=0.11s tid=0x000000012f80bc00 nid=0x5907 waiting on condition  [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

"VM Thread" os_prio=31 cpu=0.81ms elapsed=11.91s tid=0x000000013ef09f40 nid=0x4903 runnable

"GC Thread#0" os_prio=31 cpu=0.10ms elapsed=11.92s tid=0x000000013ef069d0 nid=0x3203 runnable

"G1 Main Marker" os_prio=31 cpu=0.01ms elapsed=11.92s tid=0x0000000140004680 nid=0x3303 runnable

"G1 Conc#0" os_prio=31 cpu=0.01ms elapsed=11.92s tid=0x000000011ee041a0 nid=0x3603 runnable

"G1 Refine#0" os_prio=31 cpu=0.02ms elapsed=11.91s tid=0x000000011ee062d0 nid=0x5403 runnable

"G1 Service" os_prio=31 cpu=1.22ms elapsed=11.91s tid=0x000000011ee06b70 nid=0x5303 runnable

"VM Periodic Task Thread" os_prio=31 cpu=5.58ms elapsed=11.89s tid=0x000000011ee09cd0 nid=0x6803 waiting on condition

JNI global refs: 6, weak refs: 0
```
