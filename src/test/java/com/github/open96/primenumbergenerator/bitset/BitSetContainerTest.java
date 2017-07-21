package com.github.open96.primenumbergenerator.bitset;

import junit.framework.TestCase;

/**
 * Created by end on 07/04/17.
 */
public class BitSetContainerTest extends TestCase {

    public void testGet4() throws Exception {
        Long size = new Long(String.valueOf(Integer.MAX_VALUE)) * new Long(3);
        BitSetContainer container = new BitSetContainer(size);
        long expected = new Long(String.valueOf(Integer.MAX_VALUE)) * new Long(3);
        assertFalse(container.get(new Long(String.valueOf(expected))));
        expected++;
        assertFalse(container.get(new Long(String.valueOf(expected))));
        expected -= 2;
        assertFalse(container.get(new Long(String.valueOf(expected))));
    }

    public void testGet3() throws Exception {
        BitSetContainer container = new BitSetContainer((new Long(String.valueOf((Integer.MAX_VALUE)))));
        for (int x = 0; x < Integer.MAX_VALUE; x++) {
            assertFalse(container.get(x));
        }
    }


    public void testGet2() throws Exception {
        BitSetContainer container = new BitSetContainer((new Long(String.valueOf(Integer.MAX_VALUE)) + 1));
        long expected = Integer.MAX_VALUE;
        assertFalse(container.get(new Long(String.valueOf(expected))));
        expected++;
        assertFalse(container.get(new Long(String.valueOf(expected))));
        expected -= 2;
        assertFalse(container.get(new Long(String.valueOf(expected))));
    }

    public void testGet1() throws Exception {
        BitSetContainer container = new BitSetContainer(new Long("100"));
        for (int x = 0; x < 100; x++) {
            assertFalse(container.get(x));
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