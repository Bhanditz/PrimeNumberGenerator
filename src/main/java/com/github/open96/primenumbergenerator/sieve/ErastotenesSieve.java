package com.github.open96.primenumbergenerator.sieve;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by end on 03/04/17.
 * This sieve works on any positive number
 */
public class ErastotenesSieve {
    private final BigInteger limit;
    private final BigInteger sqrt;
    private List<Boolean> sieve;

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

    private void populateSieve(){
        for(BigInteger x= BigInteger.ZERO;x.compareTo(limit)!=0;x=x.add(BigInteger.ONE)){
            sieve.add(new Boolean(true));
            System.out.println(x);
        }
    }


    public ErastotenesSieve(BigInteger upperLimit){
        limit=upperLimit;
        sieve=new ArrayList<Boolean>();
        populateSieve();
        sqrt=squareRootOfBigInteger(limit);
    }
}
