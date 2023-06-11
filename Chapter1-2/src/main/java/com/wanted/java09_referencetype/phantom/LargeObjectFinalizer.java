package com.wanted.java09_referencetype.phantom;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class LargeObjectFinalizer extends PhantomReference<Object> {
    // `finalize` 메서드 없이 종료 프로세스를 개선 시키는 예제
    public LargeObjectFinalizer(final Object referent, final ReferenceQueue<? super Object> q) {
        super(referent, q);
    }

    public void finalizeResources() {
        // Free resources
        System.out.println("clearing");
    }
}
