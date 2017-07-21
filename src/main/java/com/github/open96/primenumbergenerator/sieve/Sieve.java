package com.github.open96.primenumbergenerator.sieve;


/**
 * Created by end on 06/04/17.
 */
public interface Sieve {
    long deleteNonPrimeNumbers();

    void printSieve();

    boolean checkIfNumberIsPrime(long number);

    long countPrimes(long lowerRange, long upperRange);
}
