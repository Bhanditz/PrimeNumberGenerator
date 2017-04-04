package com.github.open96.primenumbergenerator;

import com.github.open96.primenumbergenerator.sieve.ErastotenesSieve;

import java.math.BigInteger;
/**
 * Hello world!
 */
public class PrimeNumberGenerator
{
    public static void main( String[] args )
    {
        int size=100000000;
        Double sizeinDouble= new Double(size)/(1048576+1);
        System.out.println("Approx size of cache file in megabytes: "+sizeinDouble);
        System.out.println("Generating...");
        ErastotenesSieve sieve = new ErastotenesSieve(new BigInteger(String.valueOf(size)));
        sieve.deleteNonPrimeNumbers();
        sieve.printSieve();
    }
}
