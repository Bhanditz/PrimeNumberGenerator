package com.github.open96.primenumbergenerator.sieve;

import junit.framework.TestCase;

/**
 * Created by end on 07/04/17.
 */
public class EratostenesSieveTest extends TestCase {
    public void testCountPrimes() throws Exception {
        int excepted[]= new int[]{
                4,25,168,1229,9592,78498,664579
        };
        int exceptedCounter=0;
        for (int x=10;x<=10000000;x*=10){
            Sieve s = new EratostenesSieve(x);
            s.deleteNonPrimeNumbers();
            assertEquals(s.countPrimes(0,(long)x),excepted[exceptedCounter]);
            assertEquals(s.countPrimes(-1,x),-1);
            assertEquals(s.countPrimes(0,x+5),-1);
            exceptedCounter++;
        }
    }

    public void testCheckIfNumberIsPrime() throws Exception {
        Sieve s = new EratostenesSieve(10);
        s.deleteNonPrimeNumbers();
        assertFalse(s.checkIfNumberIsPrime(0));
        assertFalse(s.checkIfNumberIsPrime(1));
        assertTrue(s.checkIfNumberIsPrime(2));
        assertTrue(s.checkIfNumberIsPrime(3));
        assertFalse(s.checkIfNumberIsPrime(4));
        assertTrue(s.checkIfNumberIsPrime(5));
        assertFalse(s.checkIfNumberIsPrime(6));
        assertTrue(s.checkIfNumberIsPrime(7));
        assertFalse(s.checkIfNumberIsPrime(8));
        assertFalse(s.checkIfNumberIsPrime(9));
        assertFalse(s.checkIfNumberIsPrime(10));
    }

    public void testDeleteNonPrimeNumbers() throws Exception {
        Sieve s = new EratostenesSieve(10);
        assertFalse(s.checkIfNumberIsPrime(0));
        assertFalse(s.checkIfNumberIsPrime(1));
        assertTrue(s.checkIfNumberIsPrime(2));
        assertTrue(s.checkIfNumberIsPrime(3));
        assertFalse(s.checkIfNumberIsPrime(4));
        assertTrue(s.checkIfNumberIsPrime(5));
        assertFalse(s.checkIfNumberIsPrime(6));
        assertTrue(s.checkIfNumberIsPrime(7));
        assertFalse(s.checkIfNumberIsPrime(8));
        assertTrue(s.checkIfNumberIsPrime(9));
        assertFalse(s.checkIfNumberIsPrime(10));
        s.deleteNonPrimeNumbers();
        assertFalse(s.checkIfNumberIsPrime(0));
        assertFalse(s.checkIfNumberIsPrime(1));
        assertTrue(s.checkIfNumberIsPrime(2));
        assertTrue(s.checkIfNumberIsPrime(3));
        assertFalse(s.checkIfNumberIsPrime(4));
        assertTrue(s.checkIfNumberIsPrime(5));
        assertFalse(s.checkIfNumberIsPrime(6));
        assertTrue(s.checkIfNumberIsPrime(7));
        assertFalse(s.checkIfNumberIsPrime(8));
        assertFalse(s.checkIfNumberIsPrime(9));
        assertFalse(s.checkIfNumberIsPrime(10));
    }

}