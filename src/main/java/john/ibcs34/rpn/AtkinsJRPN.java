package main.java.john.ibcs34.rpn;

import main.java.john.ibcs34.SystemUtils;

import java.util.Scanner;

public class AtkinsJRPN {

    public static void main(String[] args) {
        processInput();
    }

    public static void processInput() {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String str = input.next();
            String output = "Identifier";
            if (SystemUtils.isNumeric(str))
                output = "Double";
            if (isOperator(str))
                output = "Operator";

            System.out.println(str + " : " + output);
        }
    }

    public static boolean isOperator(String string) {
        switch (string) {
            case "=":
            case "-":
            case "+":
            case "/":
            case "*":
                return true;
            default:
                return false;
        }
    }
}
