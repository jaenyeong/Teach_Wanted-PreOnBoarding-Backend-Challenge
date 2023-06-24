package com.wanted.java07_atomicoperation.case08_threadlocal.repository;

import java.util.UUID;

public class UserRepository {
    public String getUserNameByUserId(int userId) {
        return UUID.randomUUID().toString();
    }
}
