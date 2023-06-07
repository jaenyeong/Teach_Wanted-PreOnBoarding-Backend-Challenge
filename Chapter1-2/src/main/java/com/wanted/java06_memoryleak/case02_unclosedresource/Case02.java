package com.wanted.java06_memoryleak.case02_unclosedresource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Case02 {
    /*
    # 새로 생성한 리소스를 제대로 닫지 않아서 발생하는 메모리 누수
    아래 소스에서 생성한 Reader 리소스 등을 사용한 후에 올바르게 해제 시키지 않으면
    리소스 생성 시 할당되었던 메모리가 누수가 발생함

    ## 방지 요령
    * 따라서 해당 리소스를 명시적으로 해제하거나 try-with-resources를 사용해 해제할 것
     */
    private static final String FILE_PATH = "./Chapter1-2/src/main/java/com/wanted/java06_memoryleak/case02_unclosedresource/test.txt";

    public static void main(String[] args) {
        // try-with-resources block 사용
        System.out.println(System.getProperty("user.dir"));
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String readLine;
            while ((readLine = reader.readLine()) != null) {
                System.out.println(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void releaseReaderResource() {
        // 명시적으로 리소스 해제
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(FILE_PATH));
            String readLine;
            while ((readLine = reader.readLine()) != null) {
                System.out.println(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
