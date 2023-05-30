package com.wanted.java07_invokedynamic;

public class Example07 {
    /*
    # 람다 호출 시 `invokedynamic` 확인
    익명클래스와 람다가 내부적으로 다르게 처리됨을 확인

    람다는 내부적으로 해당 메서드를 캡처하여 `lambda$...$...` 포맷의 이름을 가진 프라이빗 메서드로 생성됨
    해당 메서드가 `MethodHandle`을 통해 `invokedynamic` 명령에 참조되며 런타임에 바인딩됨
     */

    /*
    생성자

    public com.wanted.java07_invokedynamic.Example07();
      Code:
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
     */

    /*
    public static void main(java.lang.String[]);
      Code:
         0: new           #7                  // class com/wanted/java07_invokedynamic/Example07$1
         3: dup
         4: invokespecial #9                  // Method com/wanted/java07_invokedynamic/Example07$1."<init>":()V
         7: astore_1
         8: aload_1
         9: invokeinterface #10,  1           // InterfaceMethod java/lang/Runnable.run:()V
        14: invokedynamic #15,  0             // InvokeDynamic #0:run:()Ljava/lang/Runnable;
        19: astore_2
        20: aload_2
        21: invokeinterface #10,  1           // InterfaceMethod java/lang/Runnable.run:()V
        26: new           #18                 // class com/wanted/java07_invokedynamic/Example07
        29: dup
        30: invokespecial #20                 // Method "<init>":()V
        33: invokevirtual #21                 // Method printThisInAnonymousClassAndLambda:()V
        36: return
     */
    public static void main(String[] args) {
        // 익명 클래스
        Runnable anonymousClassInstance = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous class!");
            }
        };
        anonymousClassInstance.run();

        // 람다
        Runnable lambdaInstance = () -> System.out.println("Hello from lambda!");
        lambdaInstance.run();

        new Example07().printThisInAnonymousClassAndLambda();
    }

    /*
    private void printThisInAnonymousClassAndLambda();
      Code:
         0: new           #24                 // class com/wanted/java07_invokedynamic/Example07$2
         3: dup
         4: aload_0
         5: invokespecial #26                 // Method com/wanted/java07_invokedynamic/Example07$2."<init>":(Lcom/wanted/java07_invokedynamic/Example07;)V
         8: astore_1
         9: aload_1
        10: invokeinterface #10,  1           // InterfaceMethod java/lang/Runnable.run:()V
        15: aload_0
        16: invokedynamic #29,  0             // InvokeDynamic #1:run:(Lcom/wanted/java07_invokedynamic/Example07;)Ljava/lang/Runnable;
        21: astore_2
        22: aload_2
        23: invokeinterface #10,  1           // InterfaceMethod java/lang/Runnable.run:()V
        28: return
     */
    private void printThisInAnonymousClassAndLambda() {
        // 익명 클래스
        Runnable anonymousClassInstance = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous class!");
                System.out.println(this);
            }
        };
        anonymousClassInstance.run();

        // 람다
        Runnable lambdaInstance = () -> {
            System.out.println("Hello from lambda!");
            System.out.println(this);
        };
        lambdaInstance.run();
    }

    /*
    람다 메서드

    private void lambda$printThisInAnonymousClassAndLambda$1();
      Code:
         0: getstatic     #32                 // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #38                 // String Hello from lambda!
         5: invokevirtual #40                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: getstatic     #32                 // Field java/lang/System.out:Ljava/io/PrintStream;
        11: aload_0
        12: invokevirtual #46                 // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
        15: return

    private static void lambda$main$0();
      Code:
         0: getstatic     #32                 // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #38                 // String Hello from lambda!
         5: invokevirtual #40                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
     */
}
