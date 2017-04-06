package com.github.open96.primenumbergenerator;


import com.github.open96.primenumbergenerator.sieve.ErastotenesSieve;
import com.github.open96.primenumbergenerator.sieve.LinearSieve;
import com.github.open96.primenumbergenerator.sieve.Sieve;

import java.math.BigInteger;

/**
 * Hello world!
 */
public class PrimeNumberGenerator {
    public static void main(String[] args) {
        long startingProgramExecutionTime = System.currentTimeMillis();
        int size = 150000;
        Sieve sieve;
        if (size <= 200000) {
            sieve = new LinearSieve(size);
        } else {
            sieve = new ErastotenesSieve(size);
        }
        sieve.deleteNonPrimeNumbers();
        sieve.printSieve();
        long endingProgramExecutionTime = System.currentTimeMillis();
        System.out.println("This program took " + (new Double(endingProgramExecutionTime - startingProgramExecutionTime) / 1000) + " seconds to complete");
        System.out.println(sieve.checkIfNumberIsPrime(9));
        System.out.println(sieve.countPrimes(0, size));
    }
}
