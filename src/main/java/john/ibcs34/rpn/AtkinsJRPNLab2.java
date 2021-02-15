package main.java.john.ibcs34.rpn;

import java.util.Scanner;

public class AtkinsJRPNLab2 {
  public static void main(String[] args) {
    processInput();
  }

  public static void processInput() {
    Scanner input = new Scanner(System.in);
    Stack stack = new Stack();
    while (input.hasNextLine()) {
      boolean pop = true;
      String line = input.nextLine();
      for (String str : line.split("\\s")) {
        if (isNumeric(str)) {
          stack.push(Double.parseDouble(str));
          pop = false;
        }
        if (isOperator(str)) {
          handleOperator(str, stack);
          pop = !str.equals("P");
        }
      }
      if (pop)
        stack.printTop();
    }

  }

  public static boolean isOperator(String string) {
    switch (string) {
      case "=":
      case "-":
      case "+":
      case "/":
      case "*":
      case "P":
        return true;
      default:
        return false;
    }
  }

  public static void handleOperator(String string, Stack stack) {
    double top, bottom;
    switch (string) {
      case "-":
        //Subtraction is bottom then top
        top = stack.pop();
        bottom = stack.pop();
        stack.push(bottom - top);
        break;
      case "+":
        stack.push(stack.pop() + stack.pop());
        break;
      case "*":
        stack.push(stack.pop() * stack.pop());
        break;
      case "/":
        //Division is bottom then top
        top = stack.pop();
        bottom = stack.pop();
        stack.push(bottom / top);
        break;
      case "P":
        stack.dumpStack();
        break;
      default:
        break;
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

  public static class Stack {

    private final double[] arStack;
    private int topOfStack = -1;

    public Stack() {
      this.arStack = new double[100];
    }

    public void push(double pushItem) {
      topOfStack++;
      if (topOfStack < arStack.length)
        arStack[topOfStack] = pushItem;
      else {
        System.out.println("You've pushed too many items! " +
            "Stack is overflowed!");
        dumpStack();
      }
    }

    public double pop() {
      double value = Double.NaN;
      if (topOfStack > -1) {
        if (topOfStack < arStack.length) {
          value = arStack[topOfStack];
          topOfStack--;
          arStack[topOfStack + 1] = Double.NaN;
        } else {
          System.out.println("You've popped too many items! " +
              "Stack is underflowed!");
          dumpStack();
        }
      }
      return value;
    }

    public void dumpStack() {
      System.out.println("Stack Dump! ");
      try {
        for (int i = 0; i < topOfStack + 1; i++)
          System.out.print(arStack[i] + " ");
      } catch (IndexOutOfBoundsException e) {
        //General catch statement
        e.printStackTrace();
      }
    }

    public void printTop() {
      System.out.println(arStack[topOfStack]);
    }
  }
}
