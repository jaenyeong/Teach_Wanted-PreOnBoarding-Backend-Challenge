package com.wanted.java07_atomicoperation.case08_threadlocal.domain;

import com.wanted.java07_atomicoperation.case08_threadlocal.repository.UserRepository;

public class UserContext implements Runnable {
    private static final ThreadLocal<Context> USER_CONTEXT = new ThreadLocal<>();
    private final UserRepository userRepository = new UserRepository();
    private final int userId;

    public UserContext(final int userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        final String userName = userRepository.getUserNameByUserId(userId);
        USER_CONTEXT.set(new Context(userName));

        System.out.println("thread context for given userId: [" + userId + "] is: " + USER_CONTEXT.get());
    }
}
