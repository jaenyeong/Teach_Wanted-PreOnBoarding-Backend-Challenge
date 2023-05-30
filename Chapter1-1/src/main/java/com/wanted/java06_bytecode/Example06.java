package com.wanted.java06_bytecode;

public class Example06 {
    /*
    # 바이트코드 분석해보기
    https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions
    https://docs.oracle.com/en/java/javase/17/docs/specs/man/javap.html

    ## 바이트코드 확인하는 방법
    (1) `javap` CLI
    (2) `jclasslib Bytecode Viewer` 플러그인
    (3) `ASM Bytecode Viewer` 플러그인

    ### `javap` CLI
    (1) `.class` 파일이 있는 루트 경로로 이동
    (2) `javap` 명령으로 바이트코드 확인
        * `-v` (verbose) : 최대 정보 출력
        * `-c`           : 디스어셈블(Disassemble)된 코드 출력
        * `-l`           : 라인 번호, 지역 변수 출력
        * `-p` (private) : 모든 클래스, 멤버 출력 (private을 제외하고 출력)
        * `-sysinfo`     : 시스템 정보 출력 (클래스파일 버전, 플래그, 크기 등)
        * `-constants`   : 상수 풀 출력

    ### `jclasslib Bytecode Viewer` 플러그인
    (0) 플러그인 설치
    (1) 상단 메뉴에서 `View` > `Show Bytecode with Jclasslib` 클릭

    ### `ASM Bytecode Viewer` 플러그인
    (0) 플러그인 설치
    (1) 소스 코드에서 마우스 우클릭 > `ASM Bytecode Viewer` 클릭

    ## `javap` CLI를 통해 바이트코드 확인
    (1) 경로로 이동 `Teach_Wanted-PreOnBoarding-Challenge/Chapter1-1/build/classes/java`
    (2) 명령 실행 `javap -c main.com.wanted.java06_bytecode.Example06`
        * `javap -verbose -c -s -constants ${CLASS FQCN}`
     */

    /*
    생성자

    public com.wanted.java06_bytecode.Example06();
      Code:
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
     */

    /*
    public static void main(java.lang.String[]);
      Code:
         0: invokestatic  #7                  // Method printOnePlusTwo:()V
         3: return
     */
    public static void main(String[] args) {
        printOnePlusTwo();
    }

    /*
    private static void printOnePlusTwo();
      Code:
         0: iconst_1                   // int 값 `1`을 스택에 로딩
         1: istore_0                   // 위 int 값을 `변수0`에 저장 (first)
         2: iconst_2                   // int 값 `2`을 스택에 로딩
         3: istore_1                   // 위 int 값을 `변수1`에 저장 (second)
         4: iinc          0, 1         // 부호 있는 바이트 상수만큼 지역변수 인덱스 증가 (first++ -> `변수0`을 1만큼 증가)
         7: iinc          1, 1         // 부호 있는 바이트 상수만큼 지역변수 인덱스 증가 (second++ -> `변수1`을 1만큼 증가)
        10: getstatic     #12    // Field java/lang/System.out:Ljava/io/PrintStream;               // 스태틱 필드 (System.out)를 가져옴
        13: iload_0                    // 지역변수 `변수0`에서 int 값 로딩
        14: iload_1                    // 지역변수 `변수1`에서 int 값 로딩
        15: iadd                       // 두 개의 int 값 덧셈
        16: invokevirtual #18    // Method java/io/PrintStream.println:(I)V                        // 메서드 호출 (PrintStream.println)
        19: iinc          0, 1         // 부호 있는 바이트 상수만큼 지역변수 인덱스 증가 (first += 1 -> `변수0`을 1만큼 증가)
        22: iinc          1, 2         // 부호 있는 바이트 상수만큼 지역변수 인덱스 증가 (second += 2 -> `변수1`을 2만큼 증가)
        25: getstatic     #12    // Field java/lang/System.out:Ljava/io/PrintStream;               // 스태틱 필드 (System.out)를 가져와 오퍼랜드 스택에 푸시
        28: iload_0                    // 지역변수 `변수0`에서 int 값 로딩
        29: iload_1                    // 지역변수 `변수1`에서 int 값 로딩
        30: iadd                       // 두 개의 int 값 덧셈
        31: invokevirtual #18    // Method java/io/PrintStream.println:(I)V                        // 메서드 호출 (PrintStream.println)
        34: iload_0                    // 지역변수 `변수0`에서 int 값 로딩
        35: iconst_3                   // int 값 `상수3`을 스택에 로딩
        36: iadd                       // 두 개의 int 값 덧셈
        37: istore_0                   // 위 결과 int 값을 `변수0`에 저장 (first)
        38: iload_1                    // 지역변수 `변수1`에서 int 값 로딩
        39: iconst_4                   // int 값 `상수4`을 스택에 로딩
        40: iadd                       // 두 개의 int 값 덧셈
        41: istore_1                   // 위 결과 int 값을 `변수1`에 저장 (second)
        42: getstatic     #12    // Field java/lang/System.out:Ljava/io/PrintStream;                // 스태틱 필드 (System.out)를 가져와 오퍼랜드 스택에 푸시
        45: iload_0                    // 지역변수 `변수0`에서 int 값 로딩
        46: iload_1                    // 지역변수 `변수1`에서 int 값 로딩
        47: iadd                       // 두 개의 int 값 덧셈
        48: invokevirtual #18    // Method java/io/PrintStream.println:(I)V                         // 메서드 호출 (PrintStream.println)
        51: return                     // 메서드로부터 `void` 반환
     */
    private static void printOnePlusTwo() {
        int first = 1;
        int second = 2;

        first++;
        second++;
        System.out.println(first + second);

        first += 1;
        second += 2;
        System.out.println(first + second);

        first = first + 3;
        second = second + 4;
        System.out.println(first + second);
    }
}
