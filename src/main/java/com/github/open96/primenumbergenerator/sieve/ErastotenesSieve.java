package com.github.open96.primenumbergenerator.sieve;

import java.io.*;
import java.math.BigInteger;
import java.util.LinkedList;

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
            output.write(createBuffer(1)); //Add one byte for zero.
            output.close();
        } catch (IOException e) {
        }
    }

    private static byte readByteFromFile(String filePath,long position) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath,"r");
        file.seek(position);
        byte[] bytes = new byte[1];
        file.read(bytes);
        file.close();
        return bytes[0];
    }

    private static void writeByteToFile(String filePath,long position, byte[] data) throws IOException {
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

    public void deleteNonPrimeNumbers() throws IOException {
        //Define threads
        LinkedList<Thread>allThreads = new LinkedList<>();
        for (BigInteger x= new BigInteger("2");x.compareTo(squareRootOfBigInteger(limit))!=1;x=x.add(BigInteger.ONE)){
            final BigInteger finalX = x;
            allThreads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(readByteFromFile(FILE_NAME, finalX.longValue())==1){
                            for(BigInteger y = finalX.add(finalX); y.compareTo(limit)!=1; y=y.add(finalX)){
                                try {
                                    writeByteToFile(FILE_NAME,y.longValue(),new byte[]{0});
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));
            //Run threads every 5 ms
            try {
                allThreads.getLast().start();
                if(finalX.intValue()==2){
                    Thread.sleep(2000);
                }else{
                    Thread.sleep(3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All threads were started...");
        boolean areAllThreadsDead=false;
        while (!areAllThreadsDead){
            int deadThreadCount=0;
            areAllThreadsDead=true;
            for(int x=0;x<allThreads.size();x++){
                if(allThreads.get(x).isAlive()){
                    areAllThreadsDead=false;
                }else{
                    deadThreadCount++;
                }
            }
            try {
                System.out.println("Please wait... "+deadThreadCount+" / "+allThreads.size());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }

    public ErastotenesSieve(BigInteger upperLimit){
        limit=upperLimit;
        populateSieve();
        sqrt=squareRootOfBigInteger(limit);

        //Already delete 0 and 1 as they are not prime
        try {
            writeByteToFile(FILE_NAME,0,new byte[]{0});
            writeByteToFile(FILE_NAME,1,new byte[]{0});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
