package com.github.open96.primenumbergenerator;

import com.github.open96.primenumbergenerator.sieve.ErastotenesSieve;


import java.io.IOException;
import java.math.BigInteger;
/**
 * Hello world!
 */
public class PrimeNumberGenerator
{
    public static void main( String[] args )
    {
        int size=Integer.MAX_VALUE;
        Double sizeinDouble= new Double(size)/(1048576+1);
        System.out.println("Approx size of cache file in megabytes: "+sizeinDouble);
        System.out.println("Generating...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ErastotenesSieve sieve = new ErastotenesSieve(new BigInteger(String.valueOf(size)));
        try {
            sieve.deleteNonPrimeNumbers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sieve.countSieve();
    }
}
