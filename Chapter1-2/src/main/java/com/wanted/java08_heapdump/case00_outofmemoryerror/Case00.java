package com.wanted.java08_heapdump.case00_outofmemoryerror;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Case00 {
    /*
    # OutOfMemoryError를 발생시켜 힙덤프를 생성시키는 예시

    ## 현재 경로에서 실행
    * `java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./oom-heap.hprof -Xmx200m Case00.java`
      * `-Xmx200m` 옵션을 주지 않을 경우 너무 큰 크기의 `hprof` 파일이 생성됨

    ## 출력 결과
    java.lang.OutOfMemoryError: Java heap space
    Dumping heap to java_pid13858.hprof ...
    Heap dump file created [300401051 bytes in 0.763 secs]
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            at java.base/jdk.internal.misc.Unsafe.allocateUninitializedArray(Unsafe.java:1375)
            at java.base/java.lang.StringConcatHelper.newArray(StringConcatHelper.java:497)
            at java.base/java.lang.invoke.DirectMethodHandle$Holder.invokeStatic(DirectMethodHandle$Holder)
            at java.base/java.lang.invoke.LambdaForm$MH/0x00000008001cdc00.invoke(LambdaForm$MH)
            at java.base/java.lang.invoke.Invokers$Holder.linkToTargetMethod(Invokers$Holder)
            at com.wanted.java08_heapdump.case00_outofmemoryerror.Case00.main(Case00.java:39)

    ## 생성된 힙덤프(`hprof`) 파일 분석
    * `java_pid13858.hprof` 파일 더블 클릭

    ## 결과
    * Summary
      >>>> Total Bytes: 267,576,290        // (약 267MB)
      >>>> Total Classes: 2,692
      >>>> Total Instances: 7,371,143
      >>>> Classloaders: 4
      >>>> GC Roots: 1,096
    * Student 인스턴스 수
      * `com.wanted.java08_heapdump.case00_outofmemoryerror.Case00$Student 2,439,171 instances`
     */

    public static void main(String[] args) {
        final List<Student> students = new ArrayList<>();

        long count = 0;

        while (true) {
            final String name = "Student" + count;
            final long age = count + 1;
            students.add(new Student(name, age));
            count++;
        }
    }

    public record Student(String name, long age) {

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Student student = (Student) o;
            return age() == student.age() && Objects.equals(name(), student.name());
        }

        @Override
        public int hashCode() {
            return Objects.hash(name(), age());
        }
    }
}
