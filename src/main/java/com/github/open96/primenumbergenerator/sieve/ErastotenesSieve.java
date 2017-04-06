package com.github.open96.primenumbergenerator.sieve;

import java.io.*;
import java.math.BigInteger;
import java.util.LinkedList;

/**
 * Created by end on 03/04/17.
 * This sieve works on any sensible positive number
 * Currently works pretty well up to 9-digit numbers, above that it gets pretty slow
 */
public class ErastotenesSieve implements com.github.open96.primenumbergenerator.sieve.Sieve {
    private final long limit;
    private final long sqrt;
    public static final int BUFFER_SIZE = 8192;
    private static final String FILE_NAME = "erastotenes_sieve";
    private RandomAccessFile file;

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
            for(long x= 0;x<=limit;x+=BUFFER_SIZE){
                if(x+BUFFER_SIZE>limit){
                    long lastBufferSize=limit-x;
                    byte lastBuffer[]=createBuffer((int)lastBufferSize,Mode.NORMAL);
                    output.write(lastBuffer);
                    x=limit+1;
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

    private static void writeByteToFile(RandomAccessFile file,long position, byte[] data) throws IOException {
            file.seek(position);
            file.write(data);
    }

    public void printSieve(){
            long charactersCount = 0;
            while(charactersCount <= limit) {
                if(readByteFromFile(FILE_NAME, charactersCount)==1){
                    System.out.println(charactersCount);
                }
                charactersCount++;
        }
    }

    @Override
    public long countPrimes(long lowerRange, long upperRange) {
        if(lowerRange<0 || upperRange>limit){
            return -1;
        }else{
            long primesCount=0;
            long currentNumber=lowerRange;
            while (currentNumber<=upperRange){
                if(readByteFromFile(FILE_NAME, currentNumber)==1){
                    primesCount++;
                }
                currentNumber++;
            }
            return primesCount;
        }
    }

    @Override
    public boolean checkIfNumberIsPrime(long number) {
        byte isPrime=readByteFromFile(FILE_NAME,number);
        if(isPrime==1)
            return true;
        return false;
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
        for (long x= 3;x<=sqrt;x+=2){
            final long finalX = x;
                allThreads.add(new Thread(() -> {
                        if(readByteFromFile(FILE_NAME, finalX)==1) {
                            try(RandomAccessFile file = new RandomAccessFile(FILE_NAME,"rw");) {
                                for (long y = finalX*2; y<limit; y+=finalX) {
                                    writeByteToFile(file, y, new byte[]{0});
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
            }));
        }
        //Start threads, but don't start them all at once otherwise program will crash
        new Thread(() -> {
            int cores = Runtime.getRuntime().availableProcessors();
            for(int x=0;x<allThreads.size();x++){
                boolean startNext=false;
                int runningThreads=runningThreads(allThreads);
                while (!startNext){
                    if(runningThreads<=cores*3){
                        allThreads.get(x).start();
                        startNext=true;
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        runningThreads=runningThreads(allThreads);
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


    public ErastotenesSieve(long upperLimit){
        limit=upperLimit;
        sqrt=squareRootOfBigInteger(new BigInteger(String.valueOf(limit))).longValue();
        populateSieve();
        //Already delete 0 and 1 as they are not prime, also set 2 as prime
        try(RandomAccessFile file = new RandomAccessFile(FILE_NAME,"rw");) {
            writeByteToFile(file,0,new byte[]{0});
            writeByteToFile(file,1,new byte[]{0});
            writeByteToFile(file,2,new byte[]{1});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
