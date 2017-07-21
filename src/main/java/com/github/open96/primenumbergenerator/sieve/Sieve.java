package com.github.open96.primenumbergenerator.sieve;


public interface Sieve {
    long deleteNonPrimeNumbers();

    void printSieve();

    boolean checkIfNumberIsPrime(long number);

    long countPrimes(long lowerRange, long upperRange);
}
