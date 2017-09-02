package com.github.open96.primenumbergenerator.sieve;

/**
 * Sieve interface, contains every method any sieve should have in my program.
 */
public interface Sieve {
    long deleteNonPrimeNumbers();

    void printSieve();

    boolean checkIfNumberIsPrime(long number);

    long countPrimes(long lowerRange, long upperRange);
}
