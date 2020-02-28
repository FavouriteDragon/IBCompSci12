package main.java.john.ibcs.lab8;

import java.io.*;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AtkinsJLab8 {
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
            d = Double.NEGATIVE_INFINITY;
        }
        return d;
    }

    private static boolean sanitizeDecimals(Number input) {
        return input.intValue() == input.doubleValue();
    }

    private static boolean sanitizeInputs(String input) {
        return isNumeric(input) && sanitizeDecimals(getNumFromString(input));
    }


    //The same thing as above, but only takes integers.
    private static int handleInputs(String typed) {
        Number number = sanitizeInputs(typed) ? getNumFromString(typed).intValue() : -2;
        while (number.doubleValue() == Double.NEGATIVE_INFINITY) {
            System.out.println("Please enter a valid number. That number is invalid.");
            typed = input.next();
            number = sanitizeInputs(typed) ? getNumFromString(typed).intValue() : -2;
        }
        return number.intValue();
    }

    /**
     @formatter: off
     */


    /**
     * Splits the string at the first space.
     *
     * @param toSplit The string to split.
     * @return Returns the phrase before the split/space.
     */
    private static String splitAtString(String toSplit) {
        String toReturn = "";
        for (int i = 0; i < toSplit.length(); i++) {
            char character = toSplit.charAt(i);

            if (Character.toString(character).equalsIgnoreCase(" ")) {
                return toReturn;
            }
            toReturn += character;
        }
        return toReturn;
    }

    /**
     * @param toSplit The string to split.
     * @return Returns the rest of the string after the split.
     */
    private static String getRestSplitAtString(String toSplit) {
        StringBuilder toRemove = new StringBuilder();
        StringBuilder toReturn = new StringBuilder();
        toReturn.append(toSplit);

        boolean addChar = true;
        for (int i = 0; i < toSplit.length(); i++) {
            if (addChar) {
                char character = toSplit.charAt(i);
                if (Character.toString(character).equalsIgnoreCase(" ") || Character.toString(character).equalsIgnoreCase("")) {
                    toRemove.append(character);
                    addChar = false;
                }
                toRemove.append(character);
            }
        }
        return toReturn.delete(0, toRemove.length() - 1).toString();
    }

    private static long getIDFromString(String line) {
        return Long.parseLong(splitAtString(line));
    }

    private static int getGradeFromString(String line) {
        return Integer.parseInt(splitAtString(getRestSplitAtString(line)));
    }

    private static String getLastNameFromString(String line) {
        return splitAtString(getRestSplitAtString(getRestSplitAtString(line)));
    }

    private static String getFirstNameFromString(String line) {
        return splitAtString(getRestSplitAtString(getRestSplitAtString(getRestSplitAtString(line))));
    }

    private static String getGenderFromString(String line) {
        return splitAtString(getRestSplitAtString(getRestSplitAtString(getRestSplitAtString(getRestSplitAtString(line)))));
    }

    /**
     * @formatter: on
     */

    private static void printGenderAndStats() {
        String line;
        int boys = 0, girls = 0, i = 0, year9 = 0, year10 = 0, year11 = 0, year12 = 0;
        Student[] students = new Student[10000];
        int[] lastNames = new int[26];

        try (BufferedReader br = new BufferedReader(new FileReader("classlist.txt"))) {
            while ((line = br.readLine()) != null) {
                //Thanks, I hate it
                students[i] = new Student(getIDFromString(line), getGradeFromString(line), getLastNameFromString(line),
                        getFirstNameFromString(line), getGenderFromString(line));
                i++;
            }

        } catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }

        for (int j = 0; j < i; j++) {
            if (students[j] != null) {
                Student student = students[j];
                if (student.getGender().equalsIgnoreCase("male"))
                    boys++;
                else girls++;
                int charCode = student.getLastName().charAt(0);
                charCode -= 65;
                lastNames[charCode]++;

                switch (student.getGrade()) {
                    case 9:
                        year9++;
                        break;
                    case 10:
                        year10++;
                        break;
                    case 11:
                        year11++;
                        break;
                    case 12:
                        year12++;
                        break;

                    default:
                        break;
                }
            }
        }
        System.out.println("Boys: " + boys);
        System.out.println("Girls: " + girls);
        System.out.println("9th Graders: " + year9);
        System.out.println("10th Graders: " + year10);
        System.out.println("11th Graders: " + year11);
        System.out.println("12th Graders: " + year12);

        System.out.println("Last names beginning with: ");
        for (int j = 0; j < 26; j++) {
            System.out.println((char) (j + 65) + " : " + lastNames[j]);
        }
    }

    private static void bubbleSortLastName() {
        Student[] students = new Student[100000];
        String line;
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("classlist.txt"))) {
            while ((line = br.readLine()) != null) {
                br.lines().collect(Collectors.toList());
                //Thanks, I hate it
                students[i] = new Student(getIDFromString(line), getGradeFromString(line), getLastNameFromString(line),
                        getFirstNameFromString(line), getGenderFromString(line));
                i++;

            }
        } catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }

        //Bubble sort based on last name
        int size = i;
        for (int j = 0; j < size; j++) {
            for (int h = size - 1; h > j; h--) {
                int curChar = students[h].getLastName().charAt(0);
                int prevChar = students[h - 1].getLastName().charAt(0);
                if (prevChar > curChar) {
                    Student prevStudent = students[h - 1];
                    students[h - 1] = students[h];
                    students[h] = prevStudent;
                }
            }
        }

        writeFile(students, "lastNameClassList", size);
        System.out.println("Last Names sorted!");

        //Bubble sort based on student id
        for (int j = 0; j < size; j++) {
            for (int h = size - 1; h > j; h--) {
                long curChar = students[h].getStudentId();
                long prevChar = students[h - 1].getStudentId();
                if (prevChar > curChar) {
                    Student prevStudent = students[h - 1];
                    students[h - 1] = students[h];
                    students[h] = prevStudent;
                }
            }
        }

        writeFile(students, "studentIDClassList", size);
        System.out.println("Student ID's sorted!");

    }

    //Quick file writing method.

    /**
     *
     * @param students Array of students to write
     * @param fileName The name of the sorted file
     * @param length The amount of students to sort into the file
     */
    private static void writeFile(Student[] students, String fileName, int length) {
        File file = new File(fileName + ".txt");
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file)) {
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        Student student = students[i];
                        fileWriter.write(student.getStudentId() + " ");
                        fileWriter.write(student.getGrade() + " ");
                        fileWriter.write(student.getLastName() + " ");
                        fileWriter.write(student.getFirstName() + " ");
                        fileWriter.write(student.getGender() + " ");
                        fileWriter.write(System.getProperty("line.separator"));
                    }
                }
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int number = 0;
        while (number != -1) {
            System.out.println();
            System.out.println("Please choose a program, using numbers 1 to 3. Choose -1 to terminate the program.");
            System.out.println();
            System.out.println("1: Print student's genders, grade levels, and first letters of their last name.");
            System.out.println("2: Bubble sort students based on their last name, then generate files.");
            number = handleInputs(input.next());
            switch (number) {
                case 1:
                    printGenderAndStats();
                    break;
                case 2:
                    bubbleSortLastName();
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
}
