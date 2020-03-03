package main.java.john.ibcs.lab9;

import java.util.Random;
import java.util.Scanner;

public class AtkinsJLab9 {
    private static Scanner input = new Scanner(System.in);

    /* Util Methods */
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
            d = Double.NEGATIVE_INFINITY;
        }
        return d;
    }

    private static boolean sanitizeDecimals(Number input) {
        return input.intValue() == input.doubleValue();
    }

    private static boolean sanitizeInputs(String input) {
        return isNumeric(input) && sanitizeDecimals(getNumFromString(input));
    }

    //The same thing as above, but only takes integers.
    private static int handleInputs(String typed) {
        Number number = sanitizeInputs(typed) ? getNumFromString(typed).intValue() : -2;
        while (number.doubleValue() == Double.NEGATIVE_INFINITY) {
            System.out.println("Please enter a valid number. That number is invalid.");
            typed = input.next();
            number = sanitizeInputs(typed) ? getNumFromString(typed).intValue() : -2;
        }
        return number.intValue();
    }

    /* End util methods */

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

        for (int j = 0; j < firstNum; j++) {
            for (int i = 0; i < secondNum; i++) {
                //Height of the table
                if (j == 0)
                    System.out.print(i + 1 + " ");
                else {
                    //Width of the table
                    if (i == 0)
                        System.out.print(j + 1 + " ");
                    else
                        System.out.print((i + 1) * (j + 1) + " ");

                }
            }
            System.out.println();
        }
    }

    private static void fibonacciSequence() {
        int[] fibNumbers = new int[Integer.MAX_VALUE];

        System.out.println("Welcome to the Fibonacciinator 10,000! Please enter what fibonacci number you want, below the max value of an integer in java.");
        int number = handleInputs(input.next());

        for (int i = 0; i < number; i++) {
            if (i > 1) {
                fibNumbers[i] = fibNumbers[i - 1] + fibNumbers[i - 2];
            } else fibNumbers[i] = i;
        }
        System.out.println("The fibonacci number you want is " + fibNumbers[number - 1] + ".");
    }

    private static void interestRateCalculator() {
        double startAmount, interestRate, endAmount;

        System.out.println("Welcome to the interestinator 2000! Please input your starting balance.");
        startAmount = handleInputs(input.next());

        System.out.println("Please enter the percent interest rate you want as a whole number.");
        interestRate = handleInputs(input.next());

        endAmount = startAmount * Math.pow(1 + interestRate / 100, 30);
        System.out.println("Your balance after 30 years is $" + endAmount + ".");
    }


    private static void picturePattern() {
        double height, width;

        System.out.println("Welcome to the picture patterninator 2000!");
        System.out.println("Please enter a height, then a width.");

        height = handleInputs(input.next());
        width = handleInputs(input.next());


        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                //Top and bottom of the rectangle.
                if (j == 0) {// || j == height - 1) {
                    System.out.print(i > 0 ? " *" : "*");
                } else {
                    if (j % 2 != 0) {
                        //Width of the rectangle;
                        if (i == 0)
                            System.out.print("*");//i == 0 ? "*" : " *");
                        else {
                            if (i % 2 != 0)
                                System.out.print(" 0");
                            else System.out.print(" *");
                        }
                    } else {
                        //Width of the rectangle;
                        if (i == 0)
                            System.out.print("*");//i == 0 ? "*" : " *");
                        else
                            System.out.print(" *");
                    }

                }
            }
            System.out.println();
        }
    }

    private static void morePicturePatterns() {
        int height = 0;
        String type;

        System.out.println("Welcome to the patterninator 9000!");
        System.out.println("Please type 'x' or 'diamond' to choose the shape.");
        type = input.next();

        System.out.println("Please type an odd integer for the height.");
        height = handleInputs(input.next());
        while (height % 2 == 0) {
            height = handleInputs(input.next());
            System.out.println("Please enter a valid odd integer.");
        }

        switch (type) {
            case "x":
                drawX(height);
                break;
            case "diamond":
                printDiamond(height);
                break;
            default:
                break;
        }
    }

    private static void printDiamond(int height) {
        int print;
        for (int i = -((height - 1) / 2); i < ((height - 1) / 2) + 1; i++) { // "Centres" the numbers
            for (int j = 0; j < Math.abs(i); j++) {// Edge spacing
                System.out.print(" ");
            }
            System.out.print("*");
            print = 0;
            for (int j = Math.abs(i) * 2 + 2; j < height; j++) { // Centre spacing
                print = 1;
                System.out.print(" ");
            }
            if (print == 1) {
                System.out.print("*"); // If a second asterisk is needed, it goes there
            }
            for (int j = 0; j < Math.abs(i); j++) {
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static void drawX(int height) {
        int print;
        for (int i = -((height - 1) / 2); i < ((height - 1) / 2) + 1; i++) {
            for (int j = 0; j < ((height - 1) / 2) - Math.abs(i); j++) { // Edge spacing
                System.out.print(" ");
            }
            System.out.print("*");
            print = 0;
            for (int j = 0; j < Math.abs(i) * 2 - 1; j++) { // Centre spacing
                print = 1;
                System.out.print(" ");
            }
            if (print == 1) {
                System.out.print("*"); // Again, if it needs another asterisk
            }
            for (int j = 0; j < ((height - 1) / 2) - Math.abs(i); j++) { // More edge spacing
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static void potShotsAtPi() {
        long number, totalDartsInCircle = 0;
        double xPos, yPos, circleArea, piGuess;

        System.out.println("How many dart throws do you want?");
        number = handleInputs(input.next());
        int i;

        for (i = 0; i < number; i++) {
            xPos = new Random().nextDouble();
            xPos = clip(xPos, 0, 1);
            yPos = new Random().nextDouble();
            yPos = clip(yPos, 0, 1);

            if (xPos * xPos + yPos * yPos <= 1) {
                totalDartsInCircle++;
            }
        }

        circleArea = ((float) totalDartsInCircle / (float) i) * 4;
        piGuess = circleArea;

        System.out.println("The area of your circle, based on " + number + " guesses, is " + piGuess + "cm^2.");

    }


    public static void main(String[] args) {
        int number = 0;

        while (number != -1) {
            System.out.println();
            System.out.println("Please choose a program, using numbers 1 to 8. Choose -1 to terminate the program.");
            System.out.println();
            System.out.println("1: Add up all numbers from 7 to 70,000.");
            System.out.println("2: Create a multiplication table!");
            System.out.println("3: Get any number in the fibonacci sequence!");
            System.out.println("4: Basic interest rate calculator.");
            System.out.println("6: Picture pattern 1.");
            System.out.println("7: More picture patterns!");
            System.out.println("8: Potshots at Pi!");
            number = handleInputs(input.next());
            switch (number) {
                case 1:
                    summationTo70000();
                    break;
                case 2:
                    multiplicationTable();
                    break;
                case 3:
                    fibonacciSequence();
                    break;
                case 4:
                    interestRateCalculator();
                    break;
                case 6:
                    picturePattern();
                    break;
                case 7:
                    morePicturePatterns();
                    break;
                case 8:
                    potShotsAtPi();
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

    private static double clip(double originalNum, double min, double max) {
        return originalNum > max ? max : Math.max(originalNum, min);
    }

}
