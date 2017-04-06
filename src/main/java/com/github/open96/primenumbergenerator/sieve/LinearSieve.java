package com.github.open96.primenumbergenerator.sieve;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;

/**
 * Created by end on 06/04/17.
 */
public class LinearSieve {
    private final BigInteger limit;
    public static final int BUFFER_SIZE = 8192;
    private static final String FILE_NAME = "linear_sieve";

    private enum Mode{
        ZERO,ONE,NORMAL
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

    private byte[] createBuffer(int size, ErastotenesSieve.Mode mode){
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
            output.write(createBuffer(1, ErastotenesSieve.Mode.ZERO)); //Add one byte for zero.
            byte buffer[] = createBuffer(BUFFER_SIZE, ErastotenesSieve.Mode.NORMAL);
            for(BigInteger x= BigInteger.ZERO;x.compareTo(limit)<=0;x=x.add(new BigInteger(String.valueOf(BUFFER_SIZE)))){
                if(x.add(new BigInteger(String.valueOf(BUFFER_SIZE))).compareTo(limit)==1){
                    int lastBufferSize=limit.subtract(x).intValue();
                    byte lastBuffer[]=createBuffer(lastBufferSize, ErastotenesSieve.Mode.NORMAL);
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




    public LinearSieve(BigInteger upperLimit){
        limit=upperLimit;
        sqrt=squareRootOfBigInteger(limit);
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
