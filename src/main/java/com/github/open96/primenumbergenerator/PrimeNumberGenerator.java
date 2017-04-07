package com.github.open96.primenumbergenerator;


import com.github.open96.primenumbergenerator.sieve.EratostenesSieve;
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
        int size = 100000;
        Sieve sieve;
        if (size <= 1000000) {
            sieve = new LinearSieve(size);
        } else {
            sieve = new EratostenesSieve(size);
        }
        sieve.deleteNonPrimeNumbers();
        System.out.println(sieve.checkIfNumberIsPrime(9));
        System.out.println(sieve.countPrimes(0, size));
        t.stop();
        t.showTimeInConsole();
    }
}
