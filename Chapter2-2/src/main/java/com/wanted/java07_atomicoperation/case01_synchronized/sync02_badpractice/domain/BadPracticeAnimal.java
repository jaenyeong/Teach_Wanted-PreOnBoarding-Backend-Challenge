package com.wanted.java07_atomicoperation.case01_synchronized.sync02_badpractice.domain;

public class BadPracticeAnimal {
    private String name;
    private String owner;

    public BadPracticeAnimal(final String name, final String owner) {
        this.name = name;
        this.owner = owner;
    }

    public synchronized void rename(String name) {
        this.name = name;
    }

    public void hasChangedOwner(String owner) {
        synchronized (this) {
            this.owner = owner;
        }
    }
}
