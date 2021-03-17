package main.java.john.ibcs34.rpn;

import java.util.Scanner;

public class AtkinsJRPNLab4 {

  public static void main(String[] args) {
    processInput();
  }

  public static void processInput() {
    Scanner input = new Scanner(System.in);
    Stack stack = new Stack();
    SymbolTable table = new SymbolTable();

    System.out.println("Welcome to the RPN! Operators consist of: ");
    System.out.println("+, -, *, /, ^, and =. The non-intuitive operators " +
        "are:");
    System.out.println("P: Dump Stack.");
    System.out.println("C: Clear Stack.");
    System.out.println("D: Dump Table.");
    System.out.println("S: Clear Table.");
    while (input.hasNextLine()) {
      boolean pop = true;
      String line = input.nextLine();
      for (String str : line.split("\\s")) {
        if (isNumeric(str)) {
          stack.push(new StackItem(Double.parseDouble(str)));
          pop = false;
        } else if (isSymbol(str)) {
          if (table.getSymbol(str) != null)
            stack.push(new StackItem(table.getSymbol(str).getSymbID()));
          else stack.push(new StackItem(table.insertSymbol(str).getSymbID()));
        } else if (isOperator(str)) {
          handleOperator(str, stack, table);
          pop = isMathsOperator(str);
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
      case "^":
      case "P":
      case "C":
      case "D":
      case "S":
        return true;
      default:
        return false;
    }
  }

  public static boolean isMathsOperator(String str) {
    return str.equals("*") || str.equals("+") || str.equals("-") || str.equals("^")
        || str.equals("/") || str.equals("=");
  }
  public static boolean isSymbol(String str) {
    return str.length() > 1;
  }

  public static void handleOperator(String string, Stack stack,
                                    SymbolTable table) {
    double top = Double.NaN, bottom = Double.NaN;
    StackItem topItem = null, bottomItem = null;
    if (isMathsOperator(string)) {
      topItem = stack.pop();
      bottomItem = stack.pop();
      //Top Item
      top = getTrueValue(topItem, table);
      //Bottom Item
      bottom = getTrueValue(bottomItem, table);
    }

    switch (string) {
      case "-":
        //Subtraction is bottom then top
        stack.push(new StackItem(bottom - top));
        break;
      case "+":
        stack.push(new StackItem(bottom + top));
        break;
      case "*":
        stack.push(new StackItem(bottom * top));
        break;
      case "/":
        //Division is bottom then top
        stack.push(new StackItem(bottom / top));
        break;
      case "=":
        //Assigns values to symbols
        if (topItem != null) {
          if (topItem.getStackType() == StackType.ID) {
            if (bottomItem.getStackType() == StackType.DOUBLE) {
              table.getSymbol(topItem.getIdString()).setSymbVal(bottom);
              //Keeps the item in the stack
              stack.push(topItem);
            }
          } else if (bottomItem.getStackType() == StackType.ID) {
            if (topItem.getStackType() == StackType.DOUBLE) {
              table.getSymbol(bottomItem.getIdString()).setSymbVal(top);
              //Keeps the item in the stack
              stack.push(bottomItem);
            }
          }
        }
        break;
      case "^":
        //Exponents
        stack.push(new StackItem(Math.pow(bottom, top)));
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
      case "S":
        table.clearTable();
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

  public static double getTrueValue(StackItem item, SymbolTable table) {
    if (item.getStackType() == StackType.DOUBLE)
      return item.getIdVal();
    return table.getSymbol(item.getIdString()).getSymbVal();
  }

  enum StackType {
    ID,
    DOUBLE,
    OPERATOR
  }

  public static class Stack {

    private final StackItem[] arStack;
    private int topOfStack = -1;

    public Stack() {
      this.arStack = new StackItem[100];
    }

    public void push(StackItem pushItem) {
      topOfStack++;
      if (topOfStack < arStack.length)
        arStack[topOfStack] = pushItem;
      else {
        System.out.println("You've pushed too many items! " +
            "Stack is overflowed!");
        dumpStack();
      }
    }

    public StackItem pop() {
      StackItem item = null;
      if (topOfStack > -1) {
        if (topOfStack < arStack.length) {
          item = arStack[topOfStack];
          topOfStack--;
          arStack[topOfStack + 1] = new StackItem(Double.NaN);
        } else {
          System.out.println("You've popped too many items! " +
              "Stack is underflowed!");
          dumpStack();
        }
      }
      return item;
    }

    public void dumpStack() {
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
        System.out.println(arStack[topOfStack].toString());
    }
  }

  public static class StackItem {

    StackType stackType;
    private String idString;
    private double idVal;

    StackItem(String id) {
      this.idString = id;
      this.stackType = StackType.ID;
    }

    StackItem(double val) {
      this.idVal = val;
      this.stackType = StackType.DOUBLE;
    }

    public String getIdString() {
      return this.idString;
    }

    public double getIdVal() {
      return this.idVal;
    }

    public StackType getStackType() {
      return this.stackType;
    }

    public String toString() {
      String string;
      if (stackType == StackType.ID)
        string = idString;
      else string = "" + idVal;
      return string;
    }
  }

  public static class Symbol {

    private final String symbID;
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

    private final Symbol[] symbols;
    //How many symbols are in the array
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
          toReturn = symbol;
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

    public void clearTable() {
      for (int i = 0; i < symbolNumber; i++) {
        symbols[i].setSymbVal(0);
      }
    }
  }
}

