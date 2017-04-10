package com.github.open96.primenumbergenerator.sieve;

import com.github.open96.primenumbergenerator.bitset.BitSetContainer;


public class LinearSieve implements com.github.open96.primenumbergenerator.sieve.Sieve {
    private final long limit;
    BitSetContainer sieve;


    public void printSieve() {
        System.out.println(2);
        long charactersCount = 3;
        while (charactersCount <= limit && charactersCount > 0) {
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
            long primesCount = 0, currentNumber;
            if (lowerRange <= 2) {
                currentNumber = 3;
                primesCount++;
            } else if (lowerRange % 2 == 0) {
                if (checkIfNumberIsPrime(lowerRange)) {
                    primesCount++;
                }
                currentNumber = lowerRange + 1;
            } else {
                currentNumber = lowerRange;
            }
            while (currentNumber <= upperRange) {
                if (checkIfNumberIsPrime(currentNumber)) {
                    primesCount++;
                }
                currentNumber += 2;
            }
            return primesCount;
        }
    }

    private long nextProbablePrime(long startingNumber) {
        long tmp = startingNumber + 1;
        if (tmp >= 0) {
            while (tmp <= limit) {
                if (sieve.get(tmp)) {
                    return tmp;
                }
                tmp++;
            }
        }
        return limit;
    }

    public void deleteNonPrimeNumbers() {
        long firstMultiplier = 2;
        while (firstMultiplier * firstMultiplier <= limit) {
            System.out.println("Please wait... " + firstMultiplier * firstMultiplier + " / " + limit);
            long secondMultiplier = firstMultiplier;
            while (firstMultiplier * secondMultiplier <= limit) {
                long x = firstMultiplier * secondMultiplier;
                while (x <= limit && x > 0) {
                    sieve.set(x, false);
                    x = firstMultiplier * x;
                }
                secondMultiplier = nextProbablePrime((int) secondMultiplier);
            }
            firstMultiplier = nextProbablePrime((int) firstMultiplier);
        }
    }

    @Override
    public boolean checkIfNumberIsPrime(long number) {
        try {
            return sieve.get(number);
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("Number is not in range so I can't specify if is it prime or ");
        }
        return false;
    }

    public LinearSieve(long upperLimit) {
        limit = upperLimit;
        sieve = new BitSetContainer(limit + 1);
        //Already delete 0 and 1 as they are not prime
        sieve.set(0, false);
        sieve.set(1, false);
    }
}
