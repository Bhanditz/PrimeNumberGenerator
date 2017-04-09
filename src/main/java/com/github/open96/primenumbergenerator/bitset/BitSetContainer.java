package com.github.open96.primenumbergenerator.bitset;

import java.util.BitSet;
import java.util.LinkedList;

/**
 * Created by end on 07/04/17.
 */
public class BitSetContainer {
    private final long containerSize;
    private BitSet[] container;
    private int numberOfContainers;


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
            long positionInContainer;
            long containerNumber;
            if (position > Integer.MAX_VALUE) {
                containerNumber = position / Integer.MAX_VALUE;
                positionInContainer = position - Integer.MAX_VALUE * (containerNumber);
            } else {
                positionInContainer = position;
                containerNumber = 0;
            }
            container[(int) containerNumber].set((int) positionInContainer, value);
            return true;
        }
        return false;
    }

    private void populateContainer() {
        LinkedList<Thread> threads = new LinkedList<>();
        for (int x = 0; x < numberOfContainers; x++) {
            if (x != numberOfContainers - 1) {
                int finalX = x;
                threads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        container[finalX] = new BitSet(Integer.MAX_VALUE);
                        for (int y = 0; y < Integer.MAX_VALUE; y++) {
                            container[finalX].set(y, true);
                        }
                    }
                }));
            } else {
                if (numberOfContainers == 1) {
                    int finalX1 = x;
                    threads.add(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            container[finalX1] = new BitSet((int) containerSize);
                            for (int y = 0; y < containerSize; y++) {
                                container[finalX1].set(y, true);
                            }
                        }
                    }));
                } else {
                    int finalX2 = x;
                    threads.add(new Thread(new Runnable() {
                        @Override
                        public void run() {
                            container[finalX2] = new BitSet((int) (containerSize - (numberOfContainers - 1) * Integer.MAX_VALUE));
                            for (int y = 0; y < Integer.MAX_VALUE; y++) {
                                container[finalX2].set(y, true);
                            }
                        }
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
        int threadsAlive = 0;
        for (int x = 0; x < t.size(); x++) {
            if (t.get(x).isAlive())
                threadsAlive++;
        }
        return threadsAlive;
    }

    private void createContainer() {
        numberOfContainers = (int) (containerSize / Integer.MAX_VALUE + 1);
        container = new BitSet[numberOfContainers];
        populateContainer();
    }

    public BitSetContainer(long size) {
        containerSize = size;
        createContainer();
    }
}
