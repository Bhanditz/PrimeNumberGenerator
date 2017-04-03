package com.github.open96.primenumbergenerator.sieve;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by end on 03/04/17.
 * This sieve works on any positive number
 */
public class ErastotenesSieve {
    private final BigInteger limit;
    private final BigInteger sqrt;
    private List<Byte> sieve;
    public static final int BUFFER_SIZE = 16384;

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

    private byte[] createBuffer(int size){
        byte buffer[] = new byte[size];
        for(int x=0;x<buffer.length;x++){
            buffer[x]=1;
        }
        return buffer;
    }

    private void populateSieve(){
        try{
            FileOutputStream output = new FileOutputStream("sieve");
            byte buffer[] = createBuffer(BUFFER_SIZE);
            for(BigInteger x= BigInteger.ZERO;x.compareTo(limit)<=0;x=x.add(new BigInteger(String.valueOf(BUFFER_SIZE)))){
                if(x.add(new BigInteger(String.valueOf(BUFFER_SIZE))).compareTo(limit)==1){
                    int lastBufferSize=limit.subtract(x).intValue();
                    byte lastBuffer[]=createBuffer(lastBufferSize);
                    output.write(lastBuffer);
                    x=limit.add(new BigInteger(String.valueOf(BUFFER_SIZE*2)));
                }else {
                    output.write(buffer);
                }
            }
            output.close();
        } catch (IOException e) {
        }
    }


    public ErastotenesSieve(BigInteger upperLimit){
        limit=upperLimit;
        sieve=new LinkedList<Byte>();
        populateSieve();
        sqrt=squareRootOfBigInteger(limit);
    }
}
