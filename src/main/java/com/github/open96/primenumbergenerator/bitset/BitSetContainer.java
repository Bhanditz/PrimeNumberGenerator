package com.github.open96.primenumbergenerator.bitset;

import java.util.BitSet;
import java.util.LinkedList;

/**
 * My own extension of BitSet class that allows to create arrays up to long's maximum size.
 */
public class BitSetContainer {
    private final long containerSize;
    private BitSet[] container;
    private int numberOfContainers;


    public BitSetContainer(long size) {
        containerSize = size;
        createContainer();
    }

    public boolean get(long position) {
        int positionInContainer;
        long containerNumber;
        if (position >= Integer.MAX_VALUE) {
            containerNumber = position / Integer.MAX_VALUE;
            positionInContainer = (int) (position - Integer.MAX_VALUE * containerNumber);
        } else {
            positionInContainer = (int) position;
            containerNumber = 0;
        }
        return container[(int) containerNumber].get(positionInContainer);
    }

    public boolean set(long position, boolean value) {
        if (position < containerSize) {
            int positionInContainer;
            long containerNumber;
            if (position > Integer.MAX_VALUE) {
                containerNumber = position / Integer.MAX_VALUE;
                positionInContainer = (int) (position - Integer.MAX_VALUE * containerNumber);
            } else {
                positionInContainer = (int) position;
                containerNumber = 0;
            }
            container[(int) containerNumber].set(positionInContainer, value);
            return true;
        }
        return false;
    }

    private void populateContainer() {
        LinkedList<Thread> threads = new LinkedList<>();
        for (int x = 0; x < numberOfContainers; x++) {
            if (x != numberOfContainers - 1) {
                int finalX = x;
                threads.add(new Thread(() -> {
                    container[finalX] = new BitSet(Integer.MAX_VALUE);
                }));
            } else {
                if (numberOfContainers == 1) {
                    int finalX1 = x;
                    threads.add(new Thread(() -> {
                        container[finalX1] = new BitSet((int) containerSize);
                    }));
                } else {
                    int finalX2 = x;
                    threads.add(new Thread(() -> {
                        container[finalX2] = new BitSet((int) (containerSize - (numberOfContainers - 1) * Integer.MAX_VALUE));
                    }));
                }
                for (Thread t : threads) {
                    t.start();
                }
                boolean waitUntilAllThreadAreDead = true;
                while (waitUntilAllThreadAreDead) {
                    if (aliveThreads(threads) == 0) {
                        waitUntilAllThreadAreDead = false;
                    }
                }
            }
        }
    }

    int aliveThreads(LinkedList<Thread> t) {
        final int[] threadsAlive = {0};
        t.forEach(thread -> {
            if (thread.isAlive())
                threadsAlive[0]++;
        });
        return threadsAlive[0];
    }

    private void createContainer() {
        numberOfContainers = (int) (containerSize / Integer.MAX_VALUE + 1);
        container = new BitSet[numberOfContainers];
        populateContainer();
    }
}
