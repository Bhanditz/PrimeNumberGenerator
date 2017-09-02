package com.github.open96.primenumbergenerator.sieve;

import com.github.open96.primenumbergenerator.bitset.BitSetContainer;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Implementation of linear sieve.
 */
public class LinearSieve implements Sieve {
    private final long limit;
    private BitSetContainer sieve;
    private long firstMultiplier;

    public LinearSieve(long upperLimit) {
        if (upperLimit > Long.MAX_VALUE || upperLimit < 2)
            throw new IllegalArgumentException("Please provide number smaller than long's maximum value but bigger than 1");
        limit = upperLimit;
        sieve = new BitSetContainer(limit + 1);
        //Already delete 0 and 1 as they are not prime
        sieve.set(0, true);
        sieve.set(1, true);
    }

    private long getFirstMultiplier() {
        return firstMultiplier;
    }

    public void printSieve() {
        try (PrintWriter writer = new PrintWriter("Sieve-" + limit + ".txt", "UTF-8")) {
            writer.println(2);
            long counter = 3;
            while (counter <= limit && counter > 0) {
                if (!sieve.get(counter)) {
                    writer.println(counter);
                }
                counter += 2;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                if (!sieve.get(tmp)) {
                    return tmp;
                }
                tmp++;
            }
        }
        return limit;
    }

    public long deleteNonPrimeNumbers() {
        long counter = 0;
        firstMultiplier = 2;
        Thread thread = new Thread(() -> {
            while (Math.pow(getFirstMultiplier(), 2) <= limit) {
                try {
                    long multiplier = getFirstMultiplier();
                    System.out.println("Please wait... " + multiplier * multiplier + " / " + limit);
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        while (firstMultiplier * firstMultiplier <= limit) {
            long secondMultiplier = firstMultiplier;
            while (firstMultiplier * secondMultiplier <= limit) {
                long x = firstMultiplier * secondMultiplier;
                while (x <= limit && x > 0) {
                    counter++;
                    sieve.set(x, true);
                    x = firstMultiplier * x;
                }
                secondMultiplier = nextProbablePrime(secondMultiplier);
            }
            firstMultiplier = nextProbablePrime(firstMultiplier);
        }
        return limit - counter - 1;
    }

    @Override
    public boolean checkIfNumberIsPrime(long number) {
        try {
            return !sieve.get(number);
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("Number is not in range so I can't specify if is it prime or not");
        }
        return false;
    }
}
