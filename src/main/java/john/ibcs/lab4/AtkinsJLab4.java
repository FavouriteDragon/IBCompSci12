package main.java.john.ibcs.lab4;

import java.util.Scanner;

public class AtkinsJLab4 {

    //Global scanner variable. Technically bad practice, but it makes life easier.
    private static Scanner input = new Scanner(System.in);

    //Program 1. Adds everything up.
    private static void summation() {
        int number, i = 0, sum = 0;

        System.out.println("Please enter what number you want the summation of.");
        String typed = input.next();

        number = handleInputs(typed);

        while (i <= number) {
            sum += i;
            i++;
        }

        for (int j = 0; j <= number; j++) {
            System.out.print(j);
            if (j < number)
                System.out.print(" + ");
        }
        System.out.print(" = " + sum);
        System.out.println();

    }

    //Program 2. Calculates grades.
    private static void calculateGrades() {
        int studentGrade = 0, sum = 0, i = 0;
        double avgGrade;
        String letterGrade;
        System.out.println("Welcome to the student-grade-averaginator! Enter -1 to stop adding grades.");

        while (studentGrade != -1) {
            System.out.println("Please enter a whole percentage grade for a student.");
            String typed = input.next();

            studentGrade = handleInputs(typed);

            if (studentGrade == -1) {
                System.out.println("Do you wish to stop adding grades? Enter yes or no.");
                typed = input.next();
                if (typed.equalsIgnoreCase("No"))
                    studentGrade = 0;
            } else {
                sum += studentGrade;
                i++;
            }
        }
        avgGrade = (float) sum / (float) i;
        letterGrade = handleGrades(avgGrade);
        System.out.println("The average student grade is " + avgGrade + ", and their letter grade is a(n) " + letterGrade + ".");

    }

    //Program 3. Calculates student grades with averages.
    private static void calculateStudentGrades() {
        int studentGrade = 0, sum = 0, i = 0, A = 0, B = 0, C = 0, D = 0, F = 0;
        double avgGrade;
        String letterGrade;

        System.out.println("Welcome to the student-grade-averaginator 9000! Enter -1 to stop adding grades.");

        while (studentGrade != -1) {
            System.out.println("Please enter a whole percentage grade for a student.");
            String typed = input.next();

            studentGrade = handleInputs(typed);

            if (studentGrade == -1) {
                System.out.println("Do you wish to stop adding grades? Enter yes or no.");
                typed = input.next();
                if (typed.equalsIgnoreCase("No"))
                    studentGrade = 0;
            } else {
                if (studentGrade < 60)
                    F++;
                else if (studentGrade < 70)
                    D++;
                else if (studentGrade < 80)
                    C++;
                else if (studentGrade < 90)
                    B++;
                else
                    A++;
                sum += studentGrade;
                i++;
            }
        }
        avgGrade = (float) sum / (float) i;
        letterGrade = handleGrades(avgGrade);

        System.out.println("The average student grade is " + avgGrade + ", and their letter grade is a(n) " + letterGrade + ".");
        System.out.println("They got " + A + " A('s), " + B + " B('s), " + C + " C('s), " + D + " D('s), and " + F + " F('s).");

    }


    //Program 4. Calculates the reciprocals of numbers and prints it.
    private static void calculateReciprocal() {
        double number, i = 0, sum = 0;

        while (i < 10) {
            System.out.println("Please enter what number you want the reciprocal of. Enter 0 to stop adding reciprocals.");
            String typed = input.next();

            number = handleInputs(typed);

            if (number == 0) {
                System.out.println("Do you wish to stop adding reciprocals? Enter yes or no.");
                typed = input.next();
                if (typed.equalsIgnoreCase("Yes"))
                    i = 10;
            } else {
                sum += 1 / number;
                i++;
            }

        }
        System.out.println("The sum of your reciprocals is " + sum + ".");
    }

    //Program 5. Calculates the least common multiple and the greatest common divisor.
    private static void leastAndGreatestMD() {
        double gcD = 0, lcM, n1, n2, remainder, max, min, originalMin;

        System.out.println("Welcome to the least common multiple and greatest common divisor calculator!");
        System.out.println("Please enter the first number, and make sure it's an integer.");
        String typed = input.next();
        n1 = handleInputs(typed);

        System.out.println("Please enter the second number you want the greatest common divisor and least common multiple of with the first.");
        typed = input.next();
        n2 = handleInputs(typed);

        max = Math.max(n1, n2);
        min = Math.min(n1, n2);
        originalMin = min;

        while (gcD == 0) {
            remainder = max % min;
            if (remainder == 0) {
                //This ensures that the minimum is actually a factor, as some combinations mess up the gcD.
                if (originalMin % min == 0)
                    gcD = min;
                else gcD = originalMin % min;
            } else if (remainder == 1)
                gcD = 1;
            else
                min = remainder;
        }
        lcM = (n1 * n2) / gcD;
        System.out.println("The greatest common divisor is " + gcD + ", and the least common multiple is " + lcM + ".");

    }

    //Program 6. Calculates the square root based on input, iterations, and an initial guess.
    private static void squareRoot() {
        double number, iterations, i = 0, initialGuess;

        System.out.println("Please enter what number you want the square root of.");
        String typed = input.next();

        number = handleDecimalInputs(typed);

        System.out.println("Please enter the number of iterations you want. Make sure it's an integer.");
        typed = input.next();

        iterations = handleInputs(typed);

        System.out.println("Please enter your initial guess.");
        typed = input.next();

        initialGuess = handleDecimalInputs(typed);

        while (i < iterations) {
            System.out.print("(" + initialGuess + " + " + number + " / " + initialGuess + ")" + " / " + 2);
            initialGuess = (initialGuess + number / initialGuess) / 2;
            System.out.println(" = " + initialGuess);
            i++;
        }

        System.out.println("The square root of " + number + ", based on " + iterations + " iterations, is " + initialGuess + ".");


    }

    //Program 7. Calculates reciprocals, but expresses them as a fraction.
    private static void fractionalReciprocal() {
        double number, i = 0, denominator = 1, max, min, gcD, remainder, numeratorSum = 0, initialD, originalMin;

        while (i < 10) {
            gcD = 0;
            System.out.println("Please enter what number you want the fractional reciprocal of. Enter 0 to stop adding.");
            String typed = input.next();

            number = handleInputs(typed);

            if (number == 0) {
                System.out.println("Do you wish to stop adding reciprocals? Enter yes or no.");
                typed = input.next();
                if (typed.equalsIgnoreCase("Yes"))
                    i = 10;
            } else {
                max = Math.max(denominator, number);
                min = Math.min(denominator, number);
                originalMin = min;

                while (gcD == 0) {
                    remainder = max % min;
                    if (remainder == 0) {
                        //This ensures that the minimum is actually a factor, as some combinations mess up the gcD.
                        if (originalMin % min == 0)
                            gcD = min;
                        else gcD = originalMin % min;
                    } else if (remainder == 1)
                        gcD = 1;
                    else
                        min = remainder;
                }
                initialD = denominator;

                denominator = (number * initialD) / (gcD);

                if (initialD != 1)
                    /*
                        Seems pretty complex, but this is actually pretty simple.
                        You add the new fraction based on the principles of adding fractions (cross multiplication) to the original value.
                        You then subtract the number of times the loop has iterated, in order to account for the extra initial values.
                        It's just a ternary operator.
                     */
                    numeratorSum = initialD == number ? numeratorSum + number / denominator : (denominator / number) + (numeratorSum * (denominator / initialD));
                else numeratorSum += 1;
                //Find lmD for denominator, apply numerator to it.
                i++;
                System.out.println("The running sum of your reciprocals is " + numeratorSum + " / " + denominator + ".");
            }

        }
        if (numeratorSum % denominator == 0) {
            numeratorSum = numeratorSum / denominator;
            denominator = 1;
        } else {
            double min1 = Math.min(numeratorSum, denominator);
            double originalMin1 = min1;
            double max1 = Math.max(numeratorSum, denominator);
            gcD = 0;
            while (gcD == 0) {
                remainder = max1 % min1;
                if (remainder == 0) {
                    //This ensures that the minimum is actually a factor, as some combinations mess up the gcD.
                    if (originalMin1 % min1 == 0)
                        gcD = min1;
                    else gcD = originalMin1 % min1;
                } else if (remainder == 1)
                    gcD = 1;
                else
                    min1 = remainder;
            }

            denominator /= (gcD);
            numeratorSum /= gcD;
        }
        System.out.println();
        System.out.println("The final sum of your reciprocals is " + numeratorSum + " / " + denominator + ".");
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

    private static String handleGrades(double input) {
        if (input >= 90)
            return "A";
        if (input < 60)
            return "F";
        if (input < 70)
            return "D";
        if (input < 80)
            return "C";
        if (input < 90)
            return "B";
        return "";
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

    //Main
    public static void main(String[] args) {
        String typed;
        Number number = 0;
        while (number.intValue() != -1) {
            System.out.println();
            System.out.println("Please choose a program, using numbers 1 to 7. Choose -1 to terminate the program.");
            System.out.println();
            System.out.println("1: Summation.");
            System.out.println("2: Average of student grades.");
            System.out.println("3: Average of student grades, with the number of each grade.");
            System.out.println("4: Reciprocals.");
            System.out.println("5: Least common multiple and greatest common denominator.");
            System.out.println("6: Square Roots.");
            System.out.println("7: Fractional Reciprocals.");

            typed = input.next();

            number = getNumFromString(typed);

            switch (number.intValue()) {
                case 1:
                    summation();
                    break;
                case 2:
                    calculateGrades();
                    break;
                case 3:
                    calculateStudentGrades();
                    break;
                case 4:
                    calculateReciprocal();
                    break;
                case 5:
                    leastAndGreatestMD();
                    break;
                case 6:
                    squareRoot();
                    break;
                case 7:
                    fractionalReciprocal();
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
} //AtkinsJLab4
