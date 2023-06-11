package com.wanted.java08_heapdump.case01_jmap;

import java.util.*;

public class Case01 {
    /*
    # jmap으로 힙덤프 분석
    최신 버전에는 jmap보다 jcmd 등을 활용하는 것이 권장됨

    ## 현재 경로에서 실행
    * `java Case01.java`
    * `jps` 명령으로 PID 확인 (위 명령으로 실행 시 `Main` 프로세스로 표시됨)

    ## 실행 주의
    * 해당 소스 코드의 로직은 `while`을 통해 무한 루프로 실행되고 있기 때문에 jmap 명령 실행 후 종료할 것

    ## jmap 명령 설명
    옵션 없이 사용하는 경우 프로세스, 코어 파일과 함께 공유 객체 목록 출력
    * `-clstats <pid>` : Java 힙의 클래스 로더 통계를 출력함
    * `-finalizerinfo <pid>` : 소멸 대기 중인 객체에 대한 정보 출력
    * `-histo[:live] <pid>` : Java 객체 힙의 히스토그램 출력, live 옵션이 지정되면 Live 객체만 처리
    * `-dump:<dump_options> <pid>` : Java 힙 덤프
      * `live` : Live 객체만 덤프 (지정되지 않으면 모든 객체 덤프)
      * `format=b` : Java 힙을 `hprof` 바이너리 포맷으로 덤프
      * `file=<filename>` : 힙 덤프 파일 이름 지정

    ## `jps`로 PID 확인 후 jmap 명령으로 덤프 파일 생성
    * `jps`
    * `jmap -dump:live,format=b,file=heap.bin <pid>`
      * `jmap -dump:live,format=b,file=jmap-heap.hprof 26235`

    ## 결과
    * Summary
      >>>> Total Bytes: 111,082,099
      >>>> Total Classes: 495
      >>>> Total Instances: 3,008,936
      >>>> Classloaders: 3
      >>>> GC Roots: 464
    * DumpRecord 인스턴스 수
      * `com.wanted.java08_heapdump.case01_jmap.Case01$DumpRecord 1,000,000 instances`
     */

    public static void main(String[] args) throws InterruptedException {
        final List<DumpRecord> records = new ArrayList<>();
        final Map<Integer, DumpRecord> recordMap = new HashMap<>();

        for (int i = 0; i < 1_000_000; i++) {
            final DumpRecord dumpRecord = new DumpRecord(i);
            records.add(dumpRecord);
            recordMap.put(i, dumpRecord);
        }

        // Do Not Exit
        while (true) {
            Thread.sleep(1_000);
            System.out.println("Running....");
        }
    }

    private record DumpRecord(int id) {
        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final DumpRecord dumpRecord = (DumpRecord) o;
            return id == dumpRecord.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
