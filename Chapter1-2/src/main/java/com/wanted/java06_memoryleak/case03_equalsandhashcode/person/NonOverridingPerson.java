package com.wanted.java06_memoryleak.case03_equalsandhashcode.person;

public class NonOverridingPerson extends Person {
    private final String name;

    public NonOverridingPerson(String name) {
        this.name = name;
    }
}
