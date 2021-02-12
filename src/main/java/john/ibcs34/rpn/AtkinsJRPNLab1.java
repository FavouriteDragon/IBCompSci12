package main.java.john.ibcs34.rpn;

import java.util.Scanner;

public class AtkinsJRPNLab1 {

    public static void main(String[] args) {
        processInput();
    }

    public static void processInput() {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String str = input.next();
            String output = "Identifier";
            if (isNumeric(str))
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

    //Util methods
    public static boolean isNumeric(String input) {
        try {
            Double.parseDouble(input);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }
}
