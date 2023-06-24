package com.wanted.java07_atomicoperation.case10_cyclicbarrier;

import com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage02_awaitbystep.NumberCrunchSimulator;
import com.wanted.java07_atomicoperation.case10_cyclicbarrier.usage01_barriersync.BarrierTripSyncCounter;

public class Case10 {
    /*
    # CyclicBarrier 사용 예시
     */

    public static void main(String[] args) {
        // 이 경우 결괏값으로 4가 출력됨
        final BarrierTripSyncCounter syncCounter = new BarrierTripSyncCounter(5, 20);
        syncCounter.printCountTrips();

        System.out.println("----------------------------------");

        final NumberCrunchSimulator crunchSimulator = new NumberCrunchSimulator(3, 5);
        crunchSimulator.run();
    }
}
