package main.java.john.ibcs.lab9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        List<Integer> fibNumbers = new ArrayList<>();

        System.out.println("Welcome to the Fibonacciinator 10,000! Please enter what fibonacci number you want.");
        int number = handleInputs(input.next());

        for (int i = 0; i < number; i++) {
            if (i > 1) {
                fibNumbers.add(fibNumbers.get(i - 1) + fibNumbers.get(i - 2));
            } else fibNumbers.add(i);
        }
        System.out.println("The fibonacci number you want is " + fibNumbers.get(number - 1) + ".");
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

    private static void superInterestRateCalculator() {
        double startAmount, interestRate, numberOfCompounds, monthlyPayments, endAmount;
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
                if (j == 0 || j == height - 1) {
                    System.out.print(i > 0 ? " *" : "*");
                } else {
                    if (height % 2 != 0) {
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
        while (height % 2 == 0) {
            height = handleInputs(input.next());
            System.out.println("Please enter a valid odd integer.");
        }

        switch (type) {
            case "x":
                break;
            case "diamond":
                printDiamond(height);
                break;
            default:
                break;
        }
    }

    private static void printDiamond(int height) {
        int middle = (height + 1) / 2;
        int spacing = (middle + 1) / 2;
        //Height
        for (int j = 0; j < height; j++) {
            //Width
            for (int i = 0; i < j + 1; i++) {
                if (j < middle) {
                    for (int h = j + 1; h < spacing; h++)
                        System.out.print(" ");
                    if (i == 0 || i == j)
                        System.out.print("*");
                    else System.out.print(" ");
                }
                else if (j > middle) {
                  for (int h = 0; h < j + 1 - middle; h++)
                       System.out.print(" ");
                    if (i == 0 || i == j)
                        System.out.print("*");
                    else System.out.print(" ");
                }
                else {
                    for (int h = 0; h < middle; h++) {
                        if (h == 0 || h == middle - 1)
                            System.out.print("*");
                        else System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    private static void potShotsAtPi() {
        long number, totalDartsinCircle = 0;
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
                totalDartsinCircle++;
            }
        }

        circleArea = ((float) totalDartsinCircle / (float) i) * 4;
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
            System.out.println("5: Super interest rate calculator.");
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
                case 5:
                    superInterestRateCalculator();
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
