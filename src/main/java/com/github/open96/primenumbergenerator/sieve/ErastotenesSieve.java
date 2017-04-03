package com.github.open96.primenumbergenerator.sieve;

import java.io.*;
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
    public static final int BUFFER_SIZE = 8192;
    private static final String FILE_NAME = "sieve";

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
            File f = new File(FILE_NAME);
            f.delete();
            FileOutputStream output = new FileOutputStream(FILE_NAME);
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

    private static byte readByteFromFile(String filePath,int position) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath,"r");
        file.seek(position);
        byte[] bytes = new byte[1];
        file.read(bytes);
        file.close();
        return bytes[0];
    }

    private static void writeByteToFile(String filePath,int position, byte[] data) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath,"rw");
        file.seek(position);
        file.write(data);
        file.close();
    }

    public void countSieve(){
        System.out.println("Counting!!!");
        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(FILE_NAME));
            int currentCharacter;
            BigInteger charactersCount = BigInteger.ZERO;
            while((currentCharacter = input.read())!=-1) {
                System.out.println(charactersCount+" / " +limit + " Character is: "+currentCharacter);
                charactersCount=charactersCount.add(BigInteger.ONE);
            }
            input.close();
            System.out.println(charactersCount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ErastotenesSieve(BigInteger upperLimit){
        limit=upperLimit;
        populateSieve();
        sqrt=squareRootOfBigInteger(limit);
        try {
            writeByteToFile(FILE_NAME,999,new byte[]{3});
            System.out.println(readByteFromFile(FILE_NAME,999));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
