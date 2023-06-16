package com.wanted.java06_synchronizedproblem.case04_livelock.domain;

public class Spoon {
    private Dinner owner;

    public Spoon(Dinner owner) {
        this.owner = owner;
    }

    public Dinner getOwner() {
        return owner;
    }

    // handOver와 use 메서드는 둘다 `synchronized` 메서드이기 때문에
    // 하나의 스레드가 둘 중 한 메서드를 호출했다면 다른 스레드는 두 메서드 모두 호출 시킬 수 없음
    public synchronized void handOver(Dinner spouse) {
        owner = spouse;
    }

    public synchronized void use() {
        System.out.printf("%s has eaten!", owner.getName());
    }
}
