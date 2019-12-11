package main.java.john.ibcs.lab5;

import java.io.IOException;
import java.util.Scanner;

public class AtkinsJLab5 {

    private static Scanner input = new Scanner(System.in);

    private static void ASCIIDecoder() throws IOException {
        int characterVal = 0;
        char letter;
        System.out.println("Please enter a character. Enter space if you wish to exit.");
        //32 is space.
        while (characterVal != 32) {
            characterVal = System.in.read();
            //10 is enter. We want it to ignore the person inputting enter.
            if (characterVal != 10 && characterVal != 32) {
                letter = (char) characterVal;
                System.out.println("The ASCII value is " + characterVal + ", and the character is " + letter + ".");
                System.out.println("Please enter a character. Enter space if you wish to exit.");
            }
        }
    }

    private static void ASCIIPhraseDecoder() throws IOException {
        int cVal1 = 0, cVal2 = 0;
        char letter;

        while (cVal1 != 126 && cVal2 != 126) {
            System.out.println("Please enter two characters. All objects with an ASCII code between them will be printed, including those two numbers. Use a tilde to exit.");
            System.out.println("Please enter the first character. Make sure its ASCII code is smaller than the second number.");

            cVal1 = System.in.read();
            //Ignores enter and space.
            while (cVal1 == 10 || cVal1 == 32)
                cVal1 = System.in.read();
            if (cVal1 != 126) {
                System.out.println("Please enter the 2nd character. Make sure the ASCII code is bigger.");
                System.out.println();
                cVal2 = System.in.read();
                //Ignores enter and space.
                while (cVal2 == 10 || cVal2 == 32)
                    cVal2 = System.in.read();
            }
            if (cVal1 == 126 || cVal2 == 126) {
                System.out.println("Process terminated.");
            } else {
                //Prints out the values between the two characters.
                for (int i = cVal1; i <= cVal2; i++) {
                    letter = (char) i;
                    System.out.println("The ASCII value is " + i + ", and the character is " + letter + ".");
                }

                //If the value of the 2nd character is less than the value of the first, let the user know, and do nothing.
                if (cVal1 > cVal2) {
                    letter = (char) cVal1;
                    char letter1 = (char) cVal2;
                    System.out.println("There are no ASCII values between " + letter + ", with an ASCII code of " + cVal1 + ", and " + letter1 + ", with an ASCII code of " + cVal2 + ".");
                    System.out.println();
                }
            }
        }
    }

    private static void ASCIINumberDecoder() throws IOException {
        boolean n1 = false, n2 = false, leave = false;
        int[] num1 = new int[6], num2 = new int[6];
        int character = 0, count1 = 0, count2 = 0, sum, number1 = 0, number2 = 0;

        while (!leave) {
            System.out.println("Please enter two integers that are up to six digits. They can be negative. Enter a backslash to exit.");

            //32 is space, 10 is enter, 47 is a backslash. 45 is negative.

            //While loop to add the characters to the array of integers.
            while (character != 32 && character != 10) {
                character = System.in.read();

                if (character == 47)
                    leave = true;

                if (character == 45)
                    n1 = true;
                else if (character != 32 && character != 10 && !leave) {
                    num1[count1] = character - (int) '0';
                    count1++;
                }
            }
            //Turns the array of integers into an actual number.
            for (int i = 0; i < count1; i++)
                number1 += Math.pow(10, count1 - i - 1) * num1[i];

            if (n1)
                number1 *= -1;

            if (!leave) {
                character = 0;

                //While loop to add the characters to the array of integers.
                while (character != 32 && character != 10)  {
                    character = System.in.read();

                    if (character == 47)
                        leave = true;

                    if (character == 45)
                        n2 = true;
                    else if (character != 32 && character != 10 && !leave) {
                        num2[count2] = character - (int) '0';
                        count2++;
                    }
                }
                //Turns the array of integers into an actual number.
                for (int i = 0; i < count2; i++)
                    number2 += Math.pow(10, count2 - i - 1) * num2[i];
                if (n2)
                    number2 *= -1;

                if (!leave) {
                    sum = number1 + number2;
                    System.out.print("Your first number is " + number1 + ", your second number is " + number2 + ", and your sum is " + sum + ". ");
                    System.out.println();
                } else System.out.println("Process terminated.");
            } else System.out.println("Process terminated.");
        }
    }

    //Util method
    private static Number getNumFromString(String input) {
        double d;
        try {
            d = Double.parseDouble(input);
        } catch (NullPointerException | NumberFormatException e) {
            d = -2;
        }
        return d;
    }


    public static void main(String[] args) throws IOException {
        String typed;
        Number number = 0;
        while (number.intValue() != -1) {
            System.out.println();
            System.out.println("Please choose a program, using numbers 1 to 3. Choose -1 to terminate the program.");
            System.out.println();
            System.out.println("1: ASCII code returner.");
            System.out.println("2: ASCII codes of characters in between two characters.");
            System.out.println("3: Sum of two numbers through the ASCII value.");

            typed = input.next();

            number = getNumFromString(typed);

            switch (number.intValue()) {
                case 1:
                    ASCIIDecoder();
                    break;
                case 2:
                    ASCIIPhraseDecoder();
                    break;
                case 3:
                    ASCIINumberDecoder();
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
    }
}
