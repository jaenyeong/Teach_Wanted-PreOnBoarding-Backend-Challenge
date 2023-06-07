package com.wanted.java06_memoryleak.case04_innerclass.clazz;

public class StaticNestedClassWrapper {
    private BulkyObject bulkyObject = new BulkyObject();

    public static class StaticNestedClass {
        private final String name;

        public StaticNestedClass(final String name) {
            this.name = name;
        }
    }
}
