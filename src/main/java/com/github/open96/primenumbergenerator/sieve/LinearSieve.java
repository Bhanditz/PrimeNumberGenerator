package com.github.open96.primenumbergenerator.sieve;

import java.io.*;
import java.math.BigInteger;

/**
 * Created by end on 06/04/17.
 * A lot slower than my implemenation of erastotenes sieve on numbers above 100000
 */
public class LinearSieve implements com.github.open96.primenumbergenerator.sieve.Sieve {
    private final long limit;
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
            for (long x = 0; x <= limit; x += BUFFER_SIZE) {
                if (x + BUFFER_SIZE > limit) {
                    long lastBufferSize = limit - x;
                    byte lastBuffer[] = createBuffer((int) lastBufferSize);
                    output.write(lastBuffer);
                    x = limit + 1;
                } else {
                    output.write(buffer);
                }
            }
            output.close();
        } catch (IOException e) {
        }
    }

    public void printSieve() {
        long charactersCount = 0;
        while (charactersCount <= limit) {
            if (readByteFromFile(FILE_NAME, charactersCount) == 1) {
                System.out.println(charactersCount);
            }
            charactersCount++;
        }
    }

    @Override
    public long countPrimes(long lowerRange, long upperRange) {
        if (lowerRange < 0 || upperRange > limit) {
            return -1;
        } else {
            long primesCount = 0;
            long currentNumber = lowerRange;
            while (currentNumber <= upperRange) {
                if (readByteFromFile(FILE_NAME, currentNumber) == 1) {
                    primesCount++;
                }
                currentNumber++;
            }
            return primesCount;
        }
    }

    private long nextProbablePrime(long startingNumber) {
        long tmp = startingNumber + 1;
        while (tmp <= limit) {
            if (readByteFromFile(FILE_NAME, tmp) == 1) {
                return tmp;
            }
            tmp++;
        }
        return -1;
    }

    public void deleteNonPrimeNumbers() {
        System.out.println("Deleting non-prime numbers...");
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            long firstMultiplier = 2;
            while (firstMultiplier * firstMultiplier <= limit) {
                long secondMultiplier = firstMultiplier;
                while (firstMultiplier * secondMultiplier <= limit) {
                    long x = firstMultiplier * secondMultiplier;
                    while (x <= limit) {
                        writeByteToFile(file, x, new byte[]{0});
                        x = firstMultiplier * x;
                    }
                    secondMultiplier = nextProbablePrime(secondMultiplier);
                }
                firstMultiplier = nextProbablePrime(firstMultiplier);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfNumberIsPrime(long number) {
        byte isPrime = readByteFromFile(FILE_NAME, number);
        if (isPrime == 1)
            return true;
        return false;
    }

    public LinearSieve(long upperLimit) {
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
