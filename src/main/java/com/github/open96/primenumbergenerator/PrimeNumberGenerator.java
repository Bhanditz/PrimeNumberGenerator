package com.github.open96.primenumbergenerator;


import com.github.open96.primenumbergenerator.sieve.LinearSieve;
import com.github.open96.primenumbergenerator.sieve.Sieve;
import com.github.open96.primenumbergenerator.timer.Timer;


/**
 * Hello world!
 */
public class PrimeNumberGenerator {
    public static void main(String[] args) {
            Timer t = new Timer();
            t.start();
            long size = new Long(String.valueOf("3000000000"));
            Sieve sieve;
            sieve = new LinearSieve(size);
            sieve.deleteNonPrimeNumbers();
            //sieve.printSieve();
            System.out.println(sieve.checkIfNumberIsPrime(9));
            System.out.println(sieve.countPrimes(0, size));
            t.stop();
            t.showTimeInConsole();
        }
    }

