package com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage;

import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage01_methodlevel.DefaultIncrementer;
import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage01_methodlevel.StaticSyncIncrementer;
import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage01_methodlevel.SyncIncrementer;
import com.wanted.java07_atomicoperation.case01_synchronized.sync01_usage.usage02_blocklevel.SyncBlockIncrementer;

public class Usage01 extends AbstractUsage {
    /*
    # `synchronized` 사용 예시
    일반 메서드를 호출 했을 때는 특정 필드의 원자성을 보장할 수 없지만
    `synchronized` 인스턴스 메서드와 스태틱 메서드를 사용하는 경우 원자성 보장
     */

    public static void main(String[] args) throws InterruptedException {
        increaseCountAsUpperBoundBy(new DefaultIncrementer());
        System.out.println("--------------------");

        increaseCountAsUpperBoundBy(new SyncIncrementer());
        System.out.println("--------------------");

        increaseCountAsUpperBoundBy(new StaticSyncIncrementer());
        System.out.println("--------------------");

        increaseCountAsUpperBoundBy(new SyncBlockIncrementer());
        System.out.println("--------------------");
    }
}
