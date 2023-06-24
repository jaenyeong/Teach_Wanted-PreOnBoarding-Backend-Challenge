package com.wanted.java07_atomicoperation.case08_threadlocal.domain;

import java.util.Objects;

public class Context {
    private final String userName;

    public Context(final String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Context context = (Context) o;
        return Objects.equals(userName, context.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "Context{" +
            "userName='" + userName + '\'' +
            '}';
    }
}
