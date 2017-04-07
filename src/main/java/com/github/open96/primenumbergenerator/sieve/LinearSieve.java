package com.github.open96.primenumbergenerator.sieve;

import java.io.*;
import java.util.BitSet;

/**
 * Created by end on 06/04/17.
 * Only works up to 3000000
 */
public class LinearSieve implements com.github.open96.primenumbergenerator.sieve.Sieve {
    private final int limit;
    BitSet sieve;


    public void printSieve() {
        System.out.println(2);
        int charactersCount = 3;
        while (charactersCount <= limit) {
            if (sieve.get(charactersCount)) {
                System.out.println(charactersCount);
            }
            charactersCount += 2;
        }
    }

    @Override
    public long countPrimes(long lowerRange, long upperRange) {
        if (lowerRange < 0 || upperRange > limit) {
            return -1;
        } else {
            int primesCount = 0, currentNumber;
            if (lowerRange <= 2) {
                currentNumber = 3;
                primesCount++;
            } else if (lowerRange % 2 == 0) {
                currentNumber = (int) lowerRange + 1;
            } else {
                currentNumber = (int) lowerRange;
            }
            while (currentNumber <= upperRange) {
                if (sieve.get(currentNumber)) {
                    primesCount++;
                }
                currentNumber += 2;
            }
            return primesCount;
        }
    }

    private int nextProbablePrime(int startingNumber) {
        int tmp = startingNumber + 1;
        while (tmp <= limit) {
            if (sieve.get(tmp)) {
                return tmp;
            }
            tmp++;
        }
        return limit;
    }

    void populateSieve() {
        for (int x = 0; x <= limit + 1; x++) {
            sieve.set(x);
        }
    }

    public void deleteNonPrimeNumbers() {
        int firstMultiplier = 2;
        while (firstMultiplier * firstMultiplier <= limit) {
            System.out.println("Please wait... " + firstMultiplier * firstMultiplier + " / " + limit);
            int secondMultiplier = firstMultiplier;
            while (firstMultiplier * secondMultiplier <= limit) {
                int x = firstMultiplier * secondMultiplier;
                while (x <= limit) {
                    sieve.set(x, false);
                    x = firstMultiplier * x;
                    if (x < 0) {
                        x = limit + 1;
                    }
                }
                secondMultiplier = nextProbablePrime(secondMultiplier);
            }
            firstMultiplier = nextProbablePrime(firstMultiplier);
        }
    }

    @Override
    public boolean checkIfNumberIsPrime(long number) {
        boolean isPrime = sieve.get((int) number);
        if (isPrime)
            return true;
        return false;
    }

    public LinearSieve(int upperLimit) {
        limit = upperLimit;
        sieve = new BitSet(limit + 1);
        populateSieve();
        //Already delete 0 and 1 as they are not prime
        sieve.set(0, false);
        sieve.set(1, false);
    }
}
