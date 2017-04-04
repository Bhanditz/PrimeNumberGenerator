package com.github.open96.primenumbergenerator.sieve;

import java.io.*;
import java.math.BigInteger;
import java.util.LinkedList;

/**
 * Created by end on 03/04/17.
 * This sieve works on any sensible positive number
 * Currently works pretty well up to 9-digit numbers
 */
public class ErastotenesSieve {
    private final BigInteger limit;
    private final BigInteger sqrt;
    public static final int BUFFER_SIZE = 8192;
    private static final String FILE_NAME = "sieve";

    private enum Mode{
        ZERO,ONE,NORMAL
    }

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

    private byte[] createBuffer(int size, Mode mode){
        byte buffer[] = new byte[size];
        for(int x=0;x<buffer.length;x++) {
            switch (mode) {
                case ZERO:
                    buffer[x] = 0;
                    break;
                case ONE:
                    buffer[x] = 1;
                    break;
                case NORMAL:
                    if(x%2==0){
                        buffer[x] = 1;
                    }
                    else
                        buffer[x]=0;
                    break;
            }
        }
        return buffer;
    }

    private void populateSieve(){
        try{
            File f = new File(FILE_NAME);
            f.delete();
            FileOutputStream output = new FileOutputStream(FILE_NAME);
            output.write(createBuffer(1,Mode.ZERO)); //Add one byte for zero.
            byte buffer[] = createBuffer(BUFFER_SIZE,Mode.NORMAL);
            for(BigInteger x= BigInteger.ZERO;x.compareTo(limit)<=0;x=x.add(new BigInteger(String.valueOf(BUFFER_SIZE)))){
                if(x.add(new BigInteger(String.valueOf(BUFFER_SIZE))).compareTo(limit)==1){
                    int lastBufferSize=limit.subtract(x).intValue();
                    byte lastBuffer[]=createBuffer(lastBufferSize,Mode.NORMAL);
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

    private static byte readByteFromFile(String filePath,long position){
        byte[] bytes = new byte[1];
        try(RandomAccessFile file = new RandomAccessFile(filePath,"r")){
            file.seek(position);
            file.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes[0];
    }

    private static void writeByteToFile(String filePath,long position, byte[] data){
        try(RandomAccessFile file = new RandomAccessFile(filePath,"rw")){
            file.seek(position);
            file.write(data);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printSieve(){
        int primesCounter=0;
        try(BufferedInputStream input = new BufferedInputStream(new FileInputStream(FILE_NAME))) {
            int currentCharacter;
            BigInteger charactersCount = BigInteger.ZERO;
            while((currentCharacter = input.read())!=-1 && charactersCount.compareTo(limit)<0) {
                if(currentCharacter==1){
                    System.out.println(charactersCount);
                    primesCounter++;
                }
                charactersCount=charactersCount.add(BigInteger.ONE);
        }
            } catch (IOException e1) {
            e1.printStackTrace();
    }
        System.out.println("Found "+primesCounter+" primes in that range");
    }


    int runningThreads(LinkedList<Thread> threads){
        int count=0;
        for(int x=0;x<threads.size();x++){
            if(threads.get(x).isAlive()){
                count++;
            }
        }
        return count;
    }

    public void deleteNonPrimeNumbers() {
        //Define threads
        LinkedList<Thread>allThreads = new LinkedList<>();
        for (BigInteger x= new BigInteger("3");x.compareTo(squareRootOfBigInteger(limit))!=1;x=x.add(new BigInteger("2"))){
            final BigInteger finalX = x;
            //Dont run thread for number 5 as it was already optimized
                allThreads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                            if(readByteFromFile(FILE_NAME, finalX.longValue())==1) {
                                for (BigInteger y = finalX.add(finalX); y.compareTo(limit) != 1; y = y.add(finalX)) {
                                        writeByteToFile(FILE_NAME, y.longValue(), new byte[]{0});
                                }
                            }
                }}));
        }
        //Start threads, but don't start them all at once otherwise program will crash
        new Thread(new Runnable() {
            @Override
            public void run() {
                int cores = Runtime.getRuntime().availableProcessors();
                for(int x=0;x<allThreads.size();x++){
                    boolean startNext=false;
                    int runningThreads=runningThreads(allThreads);
                    while (!startNext){
                        if(runningThreads<cores*4){
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            allThreads.get(x).start();
                            startNext=true;
                        }
                        else{
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runningThreads=runningThreads(allThreads);
                        }
                    }
                }
            }
        }).start();
        boolean areAllThreadsDead=false;
        while (!areAllThreadsDead){
            int deadThreadCount=0;
            areAllThreadsDead=true;
            for(int c=0;c<allThreads.size();c++){
                if(allThreads.get(c).isAlive() || allThreads.get(c).getState()== Thread.State.NEW){
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
        sqrt=squareRootOfBigInteger(limit);
        populateSieve();
        //Already delete 0 and 1 as they are not prime, also set 2 as prime
        writeByteToFile(FILE_NAME,0,new byte[]{0});
        writeByteToFile(FILE_NAME,1,new byte[]{0});
        writeByteToFile(FILE_NAME,2,new byte[]{1});
    }
}
