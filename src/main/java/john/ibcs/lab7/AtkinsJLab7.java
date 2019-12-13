package main.java.john.ibcs.lab7;

import java.util.Scanner;

public class AtkinsJLab7 {
    private static long[] primes = new long[1001];
    private static Scanner input = new Scanner(System.in);

    //Util methods
    private static boolean isNumeric(String input) {
        try {
            Double.parseDouble(input);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static Number getNumFromString(String input) {
        double d;
        try {
            d = Double.parseDouble(input);
        } catch (NullPointerException | NumberFormatException e) {
            d = -2;
        }
        return d;
    }

    private static boolean sanitizeDecimals(Number input) {
        return input.intValue() == input.doubleValue();
    }

    private static boolean sanitizeInputs(String input) {
        return isNumeric(input) && sanitizeDecimals(getNumFromString(input));
    }

    //Makes sure the number is a decimal and not a string.
    private static double handleDecimalInputs(String typed) {
        Number number = isNumeric(typed) ? getNumFromString(typed).doubleValue() : -2;
        while (number.intValue() == -2) {
            System.out.println("Please enter a valid number. That number is invalid.");
            typed = input.next();
            number = isNumeric(typed) ? getNumFromString(typed).doubleValue() : -2;
        }
        return number.doubleValue();
    }

    //The same thing as above, but only takes integers.
    private static int handleInputs(String typed) {
        Number number = sanitizeInputs(typed) ? getNumFromString(typed).intValue() : -2;
        while (number.intValue() == -2) {
            System.out.println("Please enter a valid number. That number is invalid.");
            typed = input.next();
            number = sanitizeInputs(typed) ? getNumFromString(typed).intValue() : -2;
        }
        return number.intValue();
    }

    private static int handlePositiveInputs(String typed, int min, int max) {
        int number = handleInputs(typed);
        while (number <= 0) {
            System.out.println("Please input a whole number greater than " + min + " and less than " + max + ".");
            number = handleInputs(input.next());
        }
        return number;
    }

    private static void fillPrimes() {
        int number = 1000, index = 0;
        boolean isPrime = false;

        primes[0] = 2;

        for (int i = 0; i < number; i++) {
            //Iterates up to 10,000 as the 1000th prime is less than 10,000.
            for (int j = 2; j < 10000; j++) {
                //Don't make it iterate an unnecessary amount of times, it just massively slows it down.
                if (index < number) {
                    for (int h = 0; h < index + 1; h++) {
                        //Ensures that all existing prime numbers don't go into the number. For example, 3 doesn't go into 4, but 2 does.
                        if (j % primes[h] != 0) {
                            if (h == 0)
                                isPrime = true;
                        }
                        //Ensures that no numbers go into the number provided.
                        if (j % primes[h] == 0)
                            isPrime = false;
                    }
                    if (isPrime) {
                        index++;
                        primes[index] = j;
                    }
                }
            }
        }
    }

    private static void getPrime() {
        int number = 0;
        System.out.println("Please enter a number between 1 and 1000.");
        while (number < 1 || number > 1000) {
            number = handlePositiveInputs(input.next(), 0, 1001);
        }
        System.out.println(primes[number - 1]);
    }

    private static void getPrimes() {
        int number = 0, number2 = 0, index = 0;
        boolean print = false;

        System.out.println("Please enter a number between 1 and 1000.");
        while (number < 1 || number > 1000) {
            number = handlePositiveInputs(input.next(), 0, 1001);
        }

        System.out.println("Please enter another number between 1 and 1000.");
        while (number2 < 1 || number2 > 1000) {
            number2 = handlePositiveInputs(input.next(), 0, 1001);
        }

        int max = Math.max(number, number2);
        int min = Math.min(number, number2);

        for (int i = min; i <= max; i++) {
            for (int j = 0; j < 1000; j++) {
                if (!print) {
                    if (primes[j] >= i && primes[j] <= max) {
                        if (index < j || index == 0 && i == min) {
                            print = true;
                            index = j;
                        }
                    }
                }
            }
            if (print)
                System.out.println(primes[index]);
            print = false;
        }
    }

    private static void primeFactors() {
        int number, digits = 0, runningTotal;
        int[] primeFactors = new int[10000];

        System.out.println("Please enter a number greater than 1.");
        number = handlePositiveInputs(input.next(), 1, Integer.MAX_VALUE);

        runningTotal = number;

        for (int i = 0; i < 1000; ) {
            if (runningTotal != 0) {
                if (runningTotal % primes[i] == 0 && primes[i] != 0) {
                    runningTotal /= primes[i];
                    primeFactors[digits] = (int) primes[i];
                    digits++;
                } else {
                    i++;
                }
            }
        }

        System.out.print("The prime factor is ");
        for (int i = 0; i < digits; i++) {
            if (primeFactors[i] != 0) {
                if (digits == 1)
                    System.out.print(+primeFactors[i] + ".");
                else if (i == digits - 1)
                    System.out.print(" and " + primeFactors[i] + ".");
                else
                    System.out.print(" " + primeFactors[i] + ",");
            }
        }


    }

    public static void main(String[] args) {
        fillPrimes();
        int number = 0;
        while (number != -1) {
            System.out.println();
            System.out.println("Please choose a program, using numbers 1 to 3. Choose -1 to terminate the program.");
            System.out.println();
            System.out.println("1: Prime number obtainer.");
            System.out.println("2: Prime number list obtainer.");
            System.out.println("3: Prime factors.");
            number = handlePositiveInputs(input.next(), 0, Integer.MAX_VALUE);
            switch (number) {
                case 1:
                    getPrime();
                    break;
                case 2:
                    getPrimes();
                    break;
                case 3:
                    primeFactors();
                    break;
                case -1:
                    System.out.print("Process terminated");
                    break;
                default:
                    System.out.println("That number is invalid. Please type another number.");
                    break;
            }
        }
    }
}
