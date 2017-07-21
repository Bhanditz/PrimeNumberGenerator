# Prime number generator [![Build Status](https://travis-ci.org/Open96/PrimeNumberGenerator.svg?branch=master)](https://travis-ci.org/Open96/PrimeNumberGenerator)

Have you ever wanted to know how many primes are in range from 0 to 9223372036854775807?

Me neither.

Still, you can do it with my program (But I doubt you have enough RAM in your PC to do that).

## Dependencies

To run stable release you need at least Java Runtime Environment 8 (8u121).

To compile it yourself you will need at least Java Development Kit 8 (8u121) and Maven 3.3.3 or above.

This program may run on older versions of Java, but it should be ran on those listed above.

## How to run

You can either play this game by typing these commands in Linux terminal:
```
git clone https://github.com/Open96/PrimeNumberGenerator.git
cd PrimeNumberGenerator
mvn clean install
java -Xmx3G -Xms3G -jar target/prime-number-generator-1.1.jar <There you have to specify upper range>
```
Above commands will download code from that repository into your computer and compile it.


Or you can download latest release from [here](https://github.com/Open96/PrimeNumberGenerator/releases). In this situation all you need to do is open a .jar file by running only last command from above code snippet.
