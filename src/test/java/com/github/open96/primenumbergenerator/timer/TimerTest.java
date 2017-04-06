package com.github.open96.primenumbergenerator.timer;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by end on 06/04/17.
 */
public class TimerTest extends TestCase {
    public void testStart() throws Exception {
        Timer t = new Timer();
        t.start();
        Thread.sleep(5);
        t.stop();
        assertFalse(t.start());
    }

    public void testStop() throws Exception {
        Timer t = new Timer();
        assertFalse(t.stop());
    }

    public void testGetTimeInMilliseconds() throws Exception {
        Timer t1 = new Timer();
        t1.start();
        Thread.sleep(10);
        t1.stop();
        assertTrue(t1.getTimeInMilliseconds(false) >= 10);

        Timer t2 = new Timer();
        t2.start();
        Thread.sleep(1000);
        t2.stop();
        assertTrue(t2.getTimeInMilliseconds(false) >= 1000);
    }

    //Over 11 second test.
    public void testGetTimeInSeconds() throws Exception {
        Timer t1 = new Timer();
        t1.start();
        Thread.sleep(1000);
        t1.stop();
        assertTrue(t1.getTimeInSeconds(false) >= 1);

        Timer t2 = new Timer();
        t2.start();
        Thread.sleep(10000);
        t2.stop();
        assertTrue(t2.getTimeInSeconds(false) >= 10);
    }

    //Over 11 second test.
    public void testGetTimeInMinutes() throws Exception {
        Timer t1 = new Timer();
        t1.start();
        Thread.sleep(60000);
        t1.stop();
        assertTrue(t1.getTimeInMinutes(false) >= 1);
    }

}