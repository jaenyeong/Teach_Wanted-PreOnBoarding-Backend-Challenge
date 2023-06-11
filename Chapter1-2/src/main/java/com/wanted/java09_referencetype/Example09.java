package com.wanted.java09_referencetype;

import com.wanted.java09_referencetype.phantom.LargeObjectFinalizer;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

public class Example09 {
    /*
    # Java에서 제공하는 `java.lang.ref.Reference` 추상클래스를 구현하는 4개의 레퍼런스 클래스
    Java의 GC가 수집하는 기준인 참조 타입에 따라 수집이 달라짐

    ## Strong(Hard) Reference
    일반적인 참조 형태

    ## Soft Reference
    문서에 의하면 OutOfMemoryError가 발생되기 전에 GC에 의해 제거되는 참조 형태
    * 또한 연이은 해당 참조에 연결된 모든 Soft 참조들도 같이 제거
    * 제거 순서는 가장 최근에 생성(참조)된 순서로 제거됨(LRU)
    * 이 객체는 마지막으로 참조된 후 일정시간 동안 활성화 상태로 존재하고 남은 메가바이트 당 1초이며
      `-XX:SoftRefLRUPolicyMSPerMB=2500` 옵션으로 설정 가능
    * 아래 Weak 참조보다는 오래 보관될 확률이 높음
    * 대표적 사용 예시는 센서티브 메모리 캐시 구현
      실제로 꺼내 Strong 참조가 되면 제거되지 않아 가장 최근 참조된 값이 제거되는 걸 막을 수 있음

    ## Weak Reference
    Weak Reference 참조가 Strong, Soft 참조가 없는 경우라면 GC가 수집될 때 제거되는 참조 형태
    * GC에 의해 수집, 제거되어 더이상 참조 객체에 액세스할 수 없음
    * 제거된 후 ReferenceQueue에 삽입됨
    * 동시에 Weak 참조로 연결된 객체들도 소멸됨
    * 대표적 사용 예시는 정규화 매핑 구현
      매핑이 특정 값 인스턴스를 하나만 보유하면 정규화라고 불림
      새 객체를 생성하지 않고 매핑에서 기존 객체를 찾아 사용 (대표적으로 WeakHashMap)
      모든 키가 주어진 키의 Weak 참조로 저장된 맵 (GC가 해당 키를 제거하면 연결된 객체도 제거됨)

    ### Soft vs Weak
    * Soft는 LRU 알고리즘으로 가까운 시점에 재사용될 가능성이 높을 때 Soft 참조 사용
      * Soft 참조는 참조 객체 자체가 아니어도 Soft 참조가 캐시 역할을 하기 때문에 계속 참조되어 있음
      * 아래와 같은 경우에만 GC가 Soft 참조를 수집함
        * 참조 대상에 Strong 방식으로 참조되지 못함
        * Soft 참조가 최근에 액세스되지 않음
      * 결론적으로 Soft 참조는 참조가 끊긴 후 몇 분에서 몇 시간동안 참조할 수 있음 (정확하지 않음)
    * 반면 Weak 참조는 참조 대상이 여전히 주변에서 참조되는 경우에만 액세스 가능

    ## Phantom Reference
    Soft, Weak 참조와 다르게 참조 객체에 액세스 할 수 없는 참조 형태
    * 직접 액세스(참조)할 수 없고 이를 위해 referenceQueue가 필요함
    * GC는 참조 객체의 `finalize` 메서드가 실행된 후에 Phantom 참조 객체를 ReferenceQueue에 삽입함
    * 대표적 사용 예시 첫번째는 센서티브 메모리 작업을 위한 객체가 제거되는 시점 확인
      예를 들어 다른 객체를 로딩하기 전에 큰 객체가 제거될 때까지 기다리는 경우
    * 대표적 사용 예시 두번째는 `finalize` 메서드 호출 없이 종료 프로세스 향상
     */

    public static void main(String[] args) {
        // NOTHING
    }

    private static void useToStrongReference() {
        // StrongReference은 일반적인 초기화 형태
        List<String> list = new ArrayList<>();
        list.add("Strong Reference");
        list = null;
    }

    private static void useToSoftReference() {
        // 첫번째 초기화 방법 - SoftReference 객체에게 참조 대상 전달
        final Object referent = new Object();
        final SoftReference<Object> softRef = new SoftReference<>(referent);

        // 두번째 초기화 방법 - SoftReference 객체에게 참조 대상과 referenceQueue 전달
        final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        final SoftReference<Object> softRefWithRefQueue = new SoftReference<>(referent, referenceQueue);

        // get 메서드 사용
        // 실제 사용할 때는 get 메서드로 반환된 참조 변수가 null인지 확인 후 사용할 것)
        final Object firstSoftReferent = softRefWithRefQueue.get();

        // clear 메서드 사용
        softRefWithRefQueue.clear();
        final Object secondSoftReferent = softRefWithRefQueue.get();
    }

    private static void useToWeakReference() {
        // 첫번째 초기화 방법 - WeakReference 객체에게 참조 대상 전달
        final Object referent = new Object();
        final WeakReference<Object> weakRef = new WeakReference<>(referent);

        // 두번째 초기화 방법 - WeakReference 객체에게 참조 대상과 referenceQueue 전달
        final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        final WeakReference<Object> weakRefWithRefQueue = new WeakReference<>(referent, referenceQueue);

        // get 메서드 사용
        // 실제 사용할 때는 get 메서드로 반환된 참조 변수가 null인지 확인 후 사용할 것)
        final Object firstWeakReferent = weakRefWithRefQueue.get();

        // clear 메서드 사용
        weakRefWithRefQueue.clear();
    }

    private static void useToPhantomReference() {
        // `finalize` 메서드 없이 종료 프로세스를 개선 시키는 예제
        // 여기서 LargeObjectFinalizer 객체는 PhantomReference 서브 클래스

        final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>(); // 큐에 추가된 참조 추적용
        final List<LargeObjectFinalizer> references = new ArrayList<>();      // 클리닝(종료 프로세스) 작업 수행용
        List<Object> largeObjects = new ArrayList<>();                        // 크기가 큰 자료구조용

        // 참조가 되는 오브젝트 객체를 references 리스트와 largeObjects 리스트에 모두 삽입
        for (int i = 0; i < 10; ++i) {
            final Object largeObject = new Object();
            largeObjects.add(largeObject);
            references.add(new LargeObjectFinalizer(largeObject, referenceQueue));
        }

        // dereferencing(역참조 - 참조를 끊음)
        // 이를 통해 largeObjects 리스트에서 참조하던 객체(largeObject)들이 GC 대상이 되게 처리
        // 즉 이로써 참조 객체들을 referenceQueue에서만 참조하고 있음
        largeObjects = null;

        // 내부적으로 `Runtime.getRuntime().gc();` 메서드가 호출됨
        // 하지만 GC를 명시적으로 호출한다고 해서 즉시 수집(제거)되지 않음
        System.gc();

        // 모든 참조가 `referenceQueue`에 있는 지 확인하는 로직이며 각 참조에 대해 `true` 값이 출력됨
        // `true`가 출력된다는 건 각 객체(largeObject)들이 GC가 처리된 후 `referenceQueue`에 추가 되었다는 것을 의미함
        Reference<?> referenceFromQueue;
        for (PhantomReference<Object> reference : references) {
            System.out.println(reference.refersTo(null));
        }

        // `referenceQueue`에 참조들을 폴링, 클리닝 작업 수행
        while ((referenceFromQueue = referenceQueue.poll()) != null) {
            ((LargeObjectFinalizer) referenceFromQueue).finalizeResources();
            referenceFromQueue.clear();
        }
    }
}
