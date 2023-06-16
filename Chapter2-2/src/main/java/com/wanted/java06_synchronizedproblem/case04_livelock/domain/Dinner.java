package com.wanted.java06_synchronizedproblem.case04_livelock.domain;

public class Dinner {
    private final String name;
    private boolean isHungry;

    public Dinner(String name) {
        this.name = name;
        isHungry = true;
    }

    public void eatWith(Spoon spoon, Dinner spouse) {
        while (isHungry) {
            // 숟가락을 들고 있지 않으면 대기
            if (spoon.getOwner() != this) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    continue;
                }
                continue;
            }

            // 배우자가 배가 고프면 스푼을 넘기고 대기 (`continue`)
            if (spouse.isHungry()) {
                System.out.printf("%s: You eat first %s!%n", name, spouse.getName());
                spoon.handOver(spouse);
                continue;
            }

            // 배우자가 배가 고프지 않으면 식사 시작
            spoon.use();
            isHungry = false;
            System.out.printf("%s: I am stuffed, my darling %s!%n", name, spouse.getName());
            spoon.handOver(spouse);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isHungry() {
        return isHungry;
    }
}
