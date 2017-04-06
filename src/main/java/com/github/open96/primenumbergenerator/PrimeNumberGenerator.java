package com.github.open96.primenumbergenerator;


import com.github.open96.primenumbergenerator.sieve.LinearSieve;

import java.math.BigInteger;

/**
 * Hello world!
 */
public class PrimeNumberGenerator {
    public static void main(String[] args) {
        long startingProgramExecutionTime = System.currentTimeMillis();
        int size = 1000000;
        LinearSieve sieve = new LinearSieve(new BigInteger(String.valueOf(size)));
        sieve.deleteNonPrimeNumbers();
        sieve.printSieve();
        long endingProgramExecutionTime = System.currentTimeMillis();
        System.out.println("This program took " + (new Double(endingProgramExecutionTime - startingProgramExecutionTime) / 1000) + " seconds to complete");
    }
}
