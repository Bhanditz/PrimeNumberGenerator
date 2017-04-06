package com.github.open96.primenumbergenerator.sieve;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by end on 06/04/17.
 * A lot slower than my implemenation of erastotenes sieve on numbers above 100000
 */
public class LinearSieve implements com.github.open96.primenumbergenerator.sieve.Sieve {
    private final BigInteger limit;
    public static final int BUFFER_SIZE = 8192;
    private static final String FILE_NAME = "linear_sieve";


    private static byte readByteFromFile(String filePath, long position) {
        byte[] bytes = new byte[1];
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek(position);
            file.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes[0];
    }

    private static void writeByteToFile(RandomAccessFile file, long position, byte[] data) throws IOException {
        file.seek(position);
        file.write(data);
    }

    private byte[] createBuffer(int size) {
        byte buffer[] = new byte[size];
        for (int x = 0; x < buffer.length; x++) {
            buffer[x] = 1;
        }
        return buffer;
    }

    private void populateSieve() {
        try {
            File f = new File(FILE_NAME);
            f.delete();
            FileOutputStream output = new FileOutputStream(FILE_NAME);
            output.write(createBuffer(1)); //Add one byte for zero.
            byte buffer[] = createBuffer(BUFFER_SIZE);
            for (BigInteger x = BigInteger.ZERO; x.compareTo(limit) <= 0; x = x.add(new BigInteger(String.valueOf(BUFFER_SIZE)))) {
                if (x.add(new BigInteger(String.valueOf(BUFFER_SIZE))).compareTo(limit) == 1) {
                    int lastBufferSize = limit.subtract(x).intValue();
                    byte lastBuffer[] = createBuffer(lastBufferSize);
                    output.write(lastBuffer);
                    x = limit.add(new BigInteger(String.valueOf(BUFFER_SIZE * 2)));
                } else {
                    output.write(buffer);
                }
            }
            output.close();
        } catch (IOException e) {
        }
    }

    public void printSieve() {
        int primesCounter = 0;
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(FILE_NAME))) {
            int currentCharacter;
            BigInteger charactersCount = BigInteger.ZERO;
            while ((currentCharacter = input.read()) != -1 && charactersCount.compareTo(limit) <= 0) {
                if (currentCharacter == 1) {
                    System.out.println(charactersCount);
                    primesCounter++;
                }
                charactersCount = charactersCount.add(BigInteger.ONE);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("Found " + primesCounter + " primes in that range");
    }

    private BigInteger nextProbablePrime(BigInteger startingNumber) {
        BigInteger tmp = startingNumber.add(BigInteger.ONE);
        byte template[] = new byte[]{1};
        while (tmp.compareTo(limit) <= 0) {
            if (readByteFromFile(FILE_NAME, tmp.longValue()) == template[0]) {
                return tmp;
            }
            tmp = tmp.add(BigInteger.ONE);
        }
        return new BigInteger("-1");
    }

    public void deleteNonPrimeNumbers() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            BigInteger firstMultiplier = new BigInteger("2");
            while (firstMultiplier.multiply(firstMultiplier).compareTo(limit) <= 0) {
                BigInteger secondMultiplier = firstMultiplier;
                while (firstMultiplier.multiply(secondMultiplier).compareTo(limit) <= 0) {
                    BigInteger x = firstMultiplier.multiply(secondMultiplier);
                    while (x.compareTo(limit) <= 0) {
                        writeByteToFile(file, x.longValue(), new byte[]{0});
                        x = firstMultiplier.multiply(x);
                    }
                    secondMultiplier = nextProbablePrime(secondMultiplier);
                }
                firstMultiplier = nextProbablePrime(firstMultiplier);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinearSieve(BigInteger upperLimit) {
        limit = upperLimit;
        populateSieve();
        //Already delete 0 and 1 as they are not prime
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            writeByteToFile(file, 0, new byte[]{0});
            writeByteToFile(file, 1, new byte[]{0});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
