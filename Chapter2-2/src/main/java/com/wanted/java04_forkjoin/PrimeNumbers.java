package com.wanted.java04_forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeNumbers extends RecursiveAction {
    private final int lowerBound;
    private final int upperBound;
    private final int granularity;
    private final AtomicInteger noOfPrimeNumbers;

    PrimeNumbers(int upperBound) {
        this(1, upperBound, 100, new AtomicInteger(0));
    }

    private PrimeNumbers(int lowerBound, int upperBound, AtomicInteger noOfPrimeNumbers) {
        this(lowerBound, upperBound, 100, noOfPrimeNumbers);
    }

    private PrimeNumbers(int lowerBound, int upperBound, int granularity, AtomicInteger noOfPrimeNumbers) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.granularity = granularity;
        this.noOfPrimeNumbers = noOfPrimeNumbers;
    }

    // 연산을 수행하는 메서드
    @Override
    protected void compute() {
        // 충분히 작은 범위로 나눠지지 않은 경우 계속 분할
        if (((upperBound + 1) - lowerBound) > granularity) {
            ForkJoinTask.invokeAll(subTasks());
        } else {
            findPrimeNumbers();
        }
    }

    // `granularity` 기준으로 작업의 양을 적당한 크기로 나누는 메서드
    private List<PrimeNumbers> subTasks() {
        final List<PrimeNumbers> subTasks = new ArrayList<>();

        for (int i = 1; i <= this.upperBound / granularity; i++) {
            final int upper = i * granularity;
            final int lower = (upper - granularity) + 1;
            subTasks.add(new PrimeNumbers(lower, upper, noOfPrimeNumbers));
        }

        return subTasks;
    }

    // 주어진 범위에서 소수를 찾는 메서드
    void findPrimeNumbers() {
        for (int num = lowerBound; num <= upperBound; num++) {
            if (isPrime(num)) {
                noOfPrimeNumbers.getAndIncrement();
            }
        }
    }

    // 주어진 숫자가 소수인지 확인하는 메서드
    private boolean isPrime(int number) {
        if (number == 2) {
            return true;
        }

        if (number == 1 || number % 2 == 0) {
            return false;
        }

        int noOfNaturalNumbers = 0;

        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                noOfNaturalNumbers++;
            }
        }

        return noOfNaturalNumbers == 2;
    }
}
