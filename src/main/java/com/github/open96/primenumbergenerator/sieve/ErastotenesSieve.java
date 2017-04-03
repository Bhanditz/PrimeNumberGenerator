package com.github.open96.primenumbergenerator.sieve;

import java.math.BigInteger;

/**
 * Created by end on 03/04/17.
 * This sieve works on any positive number
 */
public class ErastotenesSieve {
    private final BigInteger limit;
    private final BigInteger sqrt;

    private BigInteger squareRootOfBigInteger(BigInteger number){
        BigInteger a = BigInteger.ONE;
        BigInteger b = number.shiftRight(5).add(BigInteger.valueOf(8));
        while(b.compareTo(a)>=0){
            BigInteger mid=a.add(b).shiftRight(1);
            if(mid.multiply(mid).compareTo(number)>0){
                b=mid.subtract(BigInteger.ONE);
            } else{
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }
    

    public ErastotenesSieve(BigInteger upperLimit){
        limit=upperLimit;
        sqrt=squareRootOfBigInteger(limit);
    }
}
