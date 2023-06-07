package com.wanted.java06_memoryleak.case04_innerclass.clazz;

public class InnerClassWrapper {
    private BulkyObject bulkyObject = new BulkyObject();

    public class InnerClass {
        private final String name;

        public InnerClass(final String name) {
            this.name = name;
        }
    }
}
