package com.github.open96.primenumbergenerator.bitset;

import junit.framework.TestCase;

/**
 * Created by end on 07/04/17.
 */
public class BitSetContainerTest extends TestCase {
    public void testGet() throws Exception {
        BitSetContainer container = new BitSetContainer((new Long(String.valueOf(Integer.MAX_VALUE)) + 1));
        long expected = Integer.MAX_VALUE;
        assertTrue(container.get(new Long(String.valueOf(expected))));
        expected++;
        assertTrue(container.get(new Long(String.valueOf(expected))));
        expected -= 2;
        assertTrue(container.get(new Long(String.valueOf(expected))));
    }

    public void testGet2() throws Exception {
        BitSetContainer container = new BitSetContainer(new Long("100"));
        for (int x = 0; x < 100; x++) {
            assertTrue(container.get(x));
        }
    }

    public void testSet() throws Exception {
        BitSetContainer container = new BitSetContainer(new Long("100"));
        for (int x = 0; x < 100; x++) {
            container.set(x, false);
            assertFalse(container.get(x));
        }
    }

}