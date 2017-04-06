package com.github.open96.primenumbergenerator;


import com.github.open96.primenumbergenerator.sieve.LinearSieve;

import java.math.BigInteger;
/**
 * Hello world!
 */
public class PrimeNumberGenerator
{
    public static void main( String[] args )
    {
        int size=100;
        Double sizeInDouble= new Double(size)/(1048576+1);
        System.out.println("Approx size of cache file in megabytes: "+sizeInDouble);
        System.out.println("Generating...");
        LinearSieve sieve = new LinearSieve(new BigInteger(String.valueOf(size)));
        sieve.printSieve();
    }
}
