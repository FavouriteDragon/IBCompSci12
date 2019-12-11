package main.java.john.ibcs.lab7;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AtkinsJNumberTheory {
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

    public static void main(String[] args) {
        System.out.print("Number Theory!.");
        int number = 1;
        while (numberTheory(number) < 1000000000 || numberTheory(number) % 10 != 0) {
            number = numberTheory(number);
        }
        System.out.print(number);
    }

    public static int numberTheory(int number) {
        boolean valid = false;
        //Whether or not the digit works.
        boolean numWorks;
        int digits = (int) Math.log10(number) + 1;
        int runningSum = number;
        Map<Integer, Integer> invalidDigits = new HashMap<>();
        int[] digitArray = new int[10];

        for (int i = (int) (runningSum / Math.pow(10, (int) Math.log10(number))); i < 10; i++) {

        }
        return runningSum;
    }

    public static int[] toArray(int number, int index) {
        int digits = (int) Math.log10(number) + 1;
        int[] numArray = new int[digits];
        for (int i = 0; i < digits; i++) {
            int num = digits - 1 == 0 ? 1 : number % 10 == 0 ? 0 : (int) (number / Math.pow(10, i) - Math.pow(10, digits - i - 1));
            numArray[digits - 1 - i] = num;
        }
        return numArray;
    }
}
