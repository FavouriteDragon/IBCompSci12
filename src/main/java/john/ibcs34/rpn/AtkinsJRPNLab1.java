package main.java.john.ibcs34.rpn;

import java.util.Scanner;

public class AtkinsJRPNLab1 {

  public static void main(String[] args) {
    processInput();
  }

  public static void processInput() {
    Scanner input = new Scanner(System.in);
    while (input.hasNext()) {
      String output = "Identifier";
      if (input.hasNextDouble())
        output = "Double";
      String str = input.next();
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
