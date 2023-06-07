package com.wanted.java06_memoryleak.case04_innerclass.clazz;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class BulkyObject {
    private final String[] data;

    public BulkyObject() {
        data = new String[1_000_000];

        for (int i = 0; i < 1_000_000; i++) {
            data[i] = getRandomString();
        }
    }

    private String getRandomString() {
        byte[] array = new byte[1_000];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
