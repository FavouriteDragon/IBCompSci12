package main.java.john.ibcs34;

public class SystemUtils {
    //Util methods
    public static boolean isNumeric(String input) {
        try {
            Double.parseDouble(input);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Number getNumFromString(String input) {
        double d;
        try {
            d = Double.parseDouble(input);
        } catch (NullPointerException | NumberFormatException e) {
            d = -2;
        }
        return d;
    }

    public static boolean sanitizeDecimals(Number input) {
        return input.intValue() == input.doubleValue();
    }

    public static boolean sanitizeInputs(String input) {
        return isNumeric(input) && sanitizeDecimals(getNumFromString(input));
    }
}
