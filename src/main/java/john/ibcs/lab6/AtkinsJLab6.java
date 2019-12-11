package main.java.john.ibcs.lab6;

import java.util.Random;
import java.util.Scanner;

public class AtkinsJLab6 {

    private static Scanner input = new Scanner(System.in);


    private static void numberGuessingGame() {
        int target = new Random().nextInt(1000), guess = -1, guesses = 0;

        while (guess != target) {
            System.out.println("Please guess a number between 0 and 1000.");
            guess = handleInputs(input.next());
            if (guess == target) {
                System.out.println("Congratulations! You guessed the number!");
                System.out.println("You guessed " + guesses + " time(s).");
            } else {
                if (guess > target)
                    System.out.println("Your guess is too high.");
                else
                    System.out.println("Your guess is too low.");
                guesses++;
            }
        }
        System.out.println("The maximum amount of guesses this should take a logical person is 10. This is because log base 2 of 1000 is almost 10.");
    }

    private static void numberStackingGame() {
        int number;

        System.out.println("Please input a whole number above 0.");
        number = handlePositiveInputs(input.next());

        for (int i = 0; i < number; i++) {
            for (int j = 0; j < i + 1; j++) {
                System.out.print(number - i);
                //Tab
                System.out.print((char) 9);
            }
            System.out.println();
        }
    }

    private static void rectangleDrawing() {
        int width, height;

        System.out.println("Please input the width of the rectangle.");
        width = handlePositiveInputs(input.next());

        System.out.println("Please input the height of the rectangle.");
        height = handlePositiveInputs(input.next());

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                //Top and bottom of the rectangle.
                if (j == 0 || j == height - 1)
                    System.out.print("* ");
                else {
                    //Width of the rectangle;
                    if (i == 0 || i == (width - 1))
                        System.out.print(i == 0 ? "*" : " *");
                    else
                        System.out.print("  ");

                }
            }
            System.out.println();
        }
    }

    private static void fabulousFactorials() {
        int number, total;
        System.out.println("Welcome to the fantastic factorialinator!");
        System.out.println("Please input a positive whole number to fabulously factorialinate.");
        number = handlePositiveInputs(input.next());

        for (int i = 0; i < number; i++) {
            //First part of the factorial thing: number! =
            System.out.print(((i + 1) + "! = "));
            total = 1;
            for (int j = 0; j < i + 1; j++) {
                System.out.print(j + 1);
                if (j < i)
                    //Second part: number * number * number
                    System.out.print(" * ");
                total *= (j + 1);
            }
            //Last part
            System.out.print(" = " + total);
            System.out.println();
        }
    }

    private static void thousandthPrime() {
        //Make it 1001 to prevent out of bounds exceptions.
        int[] primes = new int[1001];
        int number, index = 0;
        boolean isPrime = false;

        primes[0] = 2;

        System.out.println("Welcome to the primeinator!");
        System.out.println("Please choose which prime number you want, up to the 1000th prime number.");
        number = handlePositiveInputs(input.next());

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

        System.out.println("Your prime number is " + primes[number - 1] + ".");
    }

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

    private static int handlePositiveInputs(String typed) {
        int number = handleInputs(typed);
        while (number <= 0) {
            System.out.println("Please input a whole number greater than 0.");
            number = handleInputs(input.next());
        }
        return number;
    }

    //Main
    public static void main(String[] args) {
        String typed;
        Number number = 0;
        while (number.intValue() != -1) {
            System.out.println();
            System.out.println("Please choose a program, using numbers 1 to 5. Choose -1 to terminate the program.");
            System.out.println();
            System.out.println("1: Number guessing game.");
            System.out.println("2: Number stacking game.");
            System.out.println("3: Rectangle drawing.");
            System.out.println("4: Factorials.");
            System.out.println("5: Prime numbers.");

            typed = input.next();

            number = getNumFromString(typed);

            switch (number.intValue()) {
                case 1:
                    numberGuessingGame();
                    break;
                case 2:
                    numberStackingGame();
                    break;
                case 3:
                    rectangleDrawing();
                    // calculateStudentGrades();
                    break;
                case 4:
                    fabulousFactorials();
                    // calculateReciprocal();
                    break;
                case 5:
                    thousandthPrime();
                    // leastAndGreatestMD();
                    break;

                case -1:
                    System.out.println("Terminated.");
                    input.close();
                    break;

                default:
                    System.out.println("That number is invalid. Please type another number.");
                    break;
            }
        }
    } //Main.
}
