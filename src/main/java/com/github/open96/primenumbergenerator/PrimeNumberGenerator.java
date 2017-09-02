package com.github.open96.primenumbergenerator;


import com.github.open96.primenumbergenerator.sieve.LinearSieve;
import com.github.open96.primenumbergenerator.sieve.Sieve;
import com.github.open96.primenumbergenerator.timer.Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PrimeNumberGenerator {
    private static void printHelp() {
        System.out.println("You can use following commands to interact with this program:");
        System.out.println("print - Print all prime numbers found in sieve to .txt file");
        System.out.println("count - Counts prime numbers in specific range");
        System.out.println("check - Check if number is prime");
        System.out.println("help - print this dialog");
        System.out.println("exit - Exit program");
    }

    /**
     * Simple "CLI" that allows user to interact with program.
     * @param args - Upper limit to which program will calculate primes.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to PrimeNumberGenerator. Keep in mind range you specify may be too high for your JVM, therefore you should increase heap space even more if you want to operate on really big numbers.");
        if (args.length != 1) {
            System.out.println("Please run application with valid argument e.g. java ../PrimeNumberGenerator 100 or java -jar ../prime-number-generator.jar 100");
        } else {
            if (Long.parseLong(args[0]) >= 2) {
                Timer t = new Timer();
                t.start();
                long size = new Long(args[0]);
                Sieve sieve;
                sieve = new LinearSieve(size);
                sieve.deleteNonPrimeNumbers();
                t.stop();
                t.showTimeInConsole();
                boolean userInteracts = true;
                System.out.println("------------------------");
                printHelp();
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (userInteracts) {
                        String input = bufferedReader.readLine();
                        switch (input) {
                            case "print":
                                sieve.printSieve();
                                System.out.println("Printed sieve to .txt file");
                                break;
                            case "count":
                                System.out.println("Enter start of range:");
                                String start = bufferedReader.readLine();
                                System.out.println("Enter end of range:");
                                String end = bufferedReader.readLine();
                                System.out.println("Number of primes in range:");
                                try {
                                    long primes = sieve.countPrimes(Long.parseLong(start), Long.parseLong(end));
                                    if (primes == -1) {
                                        System.out.println("invalid range");
                                    } else {
                                        System.out.println(primes);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter positive numbers.");
                                }
                                break;
                            case "check":
                                System.out.println("Enter number you wish to check");
                                String number = bufferedReader.readLine();
                                System.out.printf(number + " is ");
                                try {
                                    boolean isPrime = sieve.checkIfNumberIsPrime(Long.parseLong(number));
                                    if (isPrime) {
                                        System.out.println("prime");
                                    } else {
                                        System.out.println("not prime");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("not a valid number");
                                }
                                break;
                            case "help":
                                printHelp();
                                break;
                            case "exit":
                                userInteracts = false;
                                break;
                            default:
                                System.out.println("Command was not recognized, try again.");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Please enter any number bigger than 1.");
            }
        }
    }
}

