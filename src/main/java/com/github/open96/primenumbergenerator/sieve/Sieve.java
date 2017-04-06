package com.github.open96.primenumbergenerator.sieve;

import java.math.BigInteger;

/**
 * Created by end on 06/04/17.
 */
public interface Sieve {
    void deleteNonPrimeNumbers();
    void printSieve();
    boolean checkIfNumberIsPrime(BigInteger number);
}
