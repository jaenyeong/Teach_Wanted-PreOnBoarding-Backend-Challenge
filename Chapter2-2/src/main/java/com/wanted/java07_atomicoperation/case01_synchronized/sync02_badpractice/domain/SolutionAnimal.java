package com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice.domain;

public class SolutionAnimal {
    private static int staticAge = 0;
    private static final Object increaseStaticAgeLock = new Object();

    private String name;
    private String owner;

    private final Object renameLock = new Object();
    private final Object hasChangedOwnerLock = new Object();

    public SolutionAnimal(final String name, final String owner) {
        this.name = name;
        this.owner = owner;
    }

    public void rename(String name) {
        synchronized (renameLock) {
            this.name = name;
        }
    }

    public void hasChangedOwner(String owner) {
        synchronized (hasChangedOwnerLock) {
            this.owner = owner;
        }
    }

    // 스태틱 필드를 수정하는 경우 스태틱 객체로 락을 걸어야 함
    public void increaseStaticAgeLock() {
        synchronized (increaseStaticAgeLock) {
            staticAge++;
        }
    }
}
