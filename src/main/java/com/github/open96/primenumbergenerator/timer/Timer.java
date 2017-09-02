package com.github.open96.primenumbergenerator.timer;

/**
 * Simple timer class that counts time since calling start() to calling stop() methods.
 */
public class Timer {
    private long startingTime, endingTime;
    private double result;
    private boolean hasStarted, hasEnded;

    public boolean start() {
        if (!hasStarted) {
            startingTime = System.currentTimeMillis();
            hasStarted = true;
            return true;
        }
        return false;
    }

    public boolean stop() {
        if (hasStarted && !hasEnded) {
            endingTime = System.currentTimeMillis();
            hasEnded = true;
            result = endingTime - startingTime;
            return true;
        }
        return false;
    }

    public long getTimeInMilliseconds(boolean outputToConsole) {
        if (hasEnded) {
            if (outputToConsole)
                System.out.println("Timer has ended. Time spent is " + (result) + " milliseconds");
            return endingTime - startingTime;
        }
        return -1;
    }

    public double getTimeInSeconds(boolean outputToConsole) {
        if (hasEnded) {
            if (outputToConsole)
                System.out.println("Timer has ended. Time spent is " + (result / 1000) + " seconds");
            return result / 1000;
        }
        return -1;
    }

    public double getTimeInMinutes(boolean outputToConsole) {
        if (hasEnded) {
            if (outputToConsole)
                System.out.println("Timer has ended. Time spent is " + ((result / 1000) / 60) + " minutes");
            return (result / 1000) / 60;
        }
        return -1;
    }

    public void showTimeInConsole() {
        if (result < 1000) {
            getTimeInMilliseconds(true);
        } else if (result < 60000) {
            getTimeInSeconds(true);
        } else {
            getTimeInMinutes(true);
        }
    }

    public Timer() {
        hasEnded = false;
        hasStarted = false;
    }
}
