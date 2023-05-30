package com.wanted;

public class Main1 {
    /*
    코드 캐시 옵션 설정 예시

      main  java -XX:+PrintCodeCache com.wanted.Main1
    Chapter 1
    CodeHeap 'non-profiled nmethods': size=120032Kb used=11Kb max_used=11Kb free=120020Kb
     bounds [0x0000000115df8000, 0x0000000116068000, 0x000000011d330000]
    CodeHeap 'profiled nmethods': size=120016Kb used=80Kb max_used=80Kb free=119935Kb
     bounds [0x000000010e330000, 0x000000010e5a0000, 0x0000000115864000]
    CodeHeap 'non-nmethods': size=5712Kb used=1015Kb max_used=1019Kb free=4696Kb
     bounds [0x0000000115864000, 0x0000000115ad4000, 0x0000000115df8000]
     total_blobs=338 nmethods=60 adapters=195
     compilation: enabled
                  stopped_count=0, restarted_count=0
     full_count=0

      main  java -XX:+PrintCodeCache -XX:InitialCodeCacheSize=240m -XX:ReservedCodeCacheSize=1g com.wanted.Main1
    Chapter 1
    CodeHeap 'non-profiled nmethods': size=521440Kb used=12Kb max_used=12Kb free=521427Kb
     bounds [0x00000001412c8000, 0x00000001502c8000, 0x0000000161000000]
    CodeHeap 'profiled nmethods': size=521424Kb used=84Kb max_used=84Kb free=521339Kb
     bounds [0x0000000121000000, 0x0000000130000000, 0x0000000140d34000]
    CodeHeap 'non-nmethods': size=5712Kb used=1015Kb max_used=1020Kb free=4696Kb
     bounds [0x0000000140d34000, 0x00000001412c8000, 0x00000001412c8000]
     total_blobs=338 nmethods=60 adapters=195
     compilation: enabled
                  stopped_count=0, restarted_count=0
     full_count=0
    */

    public static void main(String[] args) {
        System.out.println("Chapter 1");
    }
}
