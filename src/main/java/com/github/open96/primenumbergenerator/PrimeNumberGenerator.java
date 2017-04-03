package com.github.open96.primenumbergenerator;

import com.github.open96.primenumbergenerator.sieve.ErastotenesSieve;


import java.math.BigInteger;
/**
 * Hello world!
 *
 */
public class PrimeNumberGenerator
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ErastotenesSieve sieve = new ErastotenesSieve(new BigInteger(String.valueOf(Long.MAX_VALUE)));
    }
}
