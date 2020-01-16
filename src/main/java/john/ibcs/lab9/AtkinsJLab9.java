package main.java.john.ibcs.lab9;

import java.util.Scanner;

public class AtkinsJLab9 {
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

    private static void summationTo70000() {
        System.out.println("Summationinator 70000!");
        long sum = 0;
        for (int i = 7; i <= 70000; i++) {
            sum += i;
        }
        System.out.println("The sum of numbers from 7 to 70,000 is " + sum + ".");
    }

    private static void multiplicationTable() {
        System.out.println("Please enter two numbers for the multiplicationinator 9000!");
        int firstNum = handleInputs(input.next()), secondNum = handleInputs(input.next());

        for (int width = 1; width < firstNum + 1; width++) {
            for (int height =1 ; height < secondNum + 1; height++) {

            }
        }

    }

    public static void main(String[] args) {
        int number = 0;
        while (number != -1) {
            System.out.println();
            System.out.println("Please choose a program, using numbers 1 to 3. Choose -1 to terminate the program.");
            System.out.println();
            System.out.println("1: Print student's genders, grade levels, and first letters of their last name.");
            System.out.println("2: Bubble sort students based on their last name.");
            number = handleInputs(input.next());
            switch (number) {
                case 1:
                    summationTo70000();
                    break;
                case 2:
                    // bubbleSortLastName();
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
