package main.java.john.ibcs34.rpn;

import java.util.Scanner;

public class AtkinsJRPNLab3 {

  public static void main(String[] args) {
    processInput();
  }

  public static void processInput() {
    Scanner input = new Scanner(System.in);
    Stack stack = new Stack();
    SymbolTable table = new SymbolTable();
    while (input.hasNextLine()) {
      boolean pop = true;
      String line = input.nextLine();
      for (String str : line.split("\\s")) {
        if (isNumeric(str)) {
          stack.push(Double.parseDouble(str));
          pop = false;
        } else if (isOperator(str)) {
          handleOperator(str, stack, table);
          pop = !str.equals("P");
        } else {
          if (table.getSymbol(str) != null)
            stack.push(table.getSymbol(str).getSymbVal());
          else stack.push(table.insertSymbol(str).getSymbVal());
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
      case "C":
      case "D":
        return true;
      default:
        return false;
    }
  }

  public static void handleOperator(String string, Stack stack,
                                    SymbolTable table) {
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
      case "C":
        stack.clearStack();
        break;
      case "D":
        table.dumpTable();
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

    public void clearStack() {
      while (topOfStack > -1)
        pop();
    }

    public void printTop() {
      if (topOfStack > -1)
        System.out.println(arStack[topOfStack]);
    }
  }

  public static class Symbol {

    private String symbID;
    private double symbVal;

    public Symbol(String id, double val) {
      this.symbID = id;
      this.symbVal = val;
    }

    public Symbol(String id) {
      this.symbID = id;
      this.symbVal = 0.0;
    }

    public String getSymbID() {
      return this.symbID;
    }

    public double getSymbVal() {
      return this.symbVal;
    }

    public void setSymbVal(double val) {
      this.symbVal = val;
    }
  }

  public static class SymbolTable {

    private Symbol[] symbols;
    //How many symbosl are in the array
    private int symbolNumber;

    public SymbolTable() {
      this.symbolNumber = 0;
      this.symbols = new Symbol[100];
    }

    public Symbol getSymbol(String id) {
      Symbol toReturn = null;
      for (int i = 0; i < symbolNumber; i++) {
        Symbol symbol = symbols[i];
        if (id.equals(symbol.getSymbID()))
          toReturn = new Symbol(id);
      }
      return toReturn;
    }

    public Symbol insertSymbol(String id) {
      Symbol symbol = new Symbol(id);
      symbols[symbolNumber] = symbol;
      symbolNumber++;
      return symbol;
    }

    public void dumpTable() {
      for (int i = 0; i < symbolNumber; i++) {
        Symbol symbol = symbols[i];
        System.out.println("Symbol: " + symbol.getSymbID() + ", Value: " + symbol.getSymbVal());
      }
    }
  }
}

