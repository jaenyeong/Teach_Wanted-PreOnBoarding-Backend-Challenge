package com.wanted.java07_atomicoperation.case08_threadlocal;

import com.wanted.java07_atomicoperation.case08_threadlocal.domain.UserContext;

public class Case08 {
    /*
    # ThreadLocal 사용 예시
    스레드 별 저장소로 각 스레드는 자신만 볼 수 있는 값(복사본)을 가져 스레드간 데이터 격리를 위한 용도로 사용
     */

    public static void main(String[] args) {
        final UserContext firstUser = new UserContext(1);
        final UserContext secondUser = new UserContext(2);

        new Thread(firstUser).start();
        new Thread(secondUser).start();

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
