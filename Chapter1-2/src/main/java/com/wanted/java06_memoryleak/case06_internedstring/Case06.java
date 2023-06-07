package com.wanted.java06_memoryleak.case06_internedstring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Case06 {
    /*
    # 대용량의 문자열을 String intern 메서드 사용할 경우 발생하는 메모리 누수 (JDK 6 이하 버전일 때 해당)
    String의 intern 메서드는 해당 문자열 값을 String pool로 이동 시키는데
    옛날 버전에서 String pool이 PermGen 영역(GC가 되지 않는 영구 메모리)에 저장되어 소멸되지 않아 메모리 누수가 발생할 수 있음

    ## 방지 요령
    * 예전 버전일 때 해당하는 경우라서 크게 신경쓰지 않아도 되지만 JDK 버전이 낮을 경우에는 주의할 것
     */
    private static final String FILE_PATH = "./Chapter1-2/src/main/java/com/wanted/java06_memoryleak/case06_internedstring/test.txt";

    public static void main(String[] args) {
        // (참고) intern 메서드는 네이티브 메서드
//        String s1 = Objects.requireNonNull(read()).intern();
//        String s2 = Objects.requireNonNull(read()).intern();

        String s1 = read();
        String s2 = read();

        String printMessage = "Both the strings objects are ";
        printMessage += Objects.equals(s1, s2) ? "same" : "different";

        System.out.println(printMessage);
    }

    private static String read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                builder.append(line);
                builder.append("\n");
                line = reader.readLine();
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
