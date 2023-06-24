package com.wanted.java07_atomicoperation.case03_atomicvariable.usage04_atomicreference;

public record ValueRecord(int atomicValue) {
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ValueRecord that = (ValueRecord) o;
        return atomicValue == that.atomicValue;
    }
}
