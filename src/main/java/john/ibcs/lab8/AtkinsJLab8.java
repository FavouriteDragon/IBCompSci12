package main.java.john.ibcs.lab8;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

    private static int handlePositiveInputs(String typed, int min, int max) {
        int number = handleInputs(typed);
        while (number <= 0) {
            System.out.println("Please input a whole number greater than " + min + " and less than " + max + ".");
            number = handleInputs(input.next());
        }
        return number;
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
        List<Student> students = new java.util.ArrayList<>(Collections.emptyList());
        int[] lastNames = new int[26];

        try (BufferedReader br = new BufferedReader(new FileReader("classlist.txt"))) {
            while ((line = br.readLine()) != null) {
                //Thanks, I hate it
                students.add(new Student(getIDFromString(line), getGradeFromString(line), getLastNameFromString(line),
                        getFirstNameFromString(line), getGenderFromString(line)));
                i++;

            }
        } catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }

        for (int j = 0; j < i; j++) {
            if (students.get(j) != null) {
                Student student = students.get(j);
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
        Student[] students = new Student[88];
        String line;
        int i = 0;

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

        //Lol
        //  students = students.stream().sorted(Comparator.comparingInt(student -> student.getLastName().charAt(0))).collect(Collectors.toList());

        //Bubble sort based on last name
        for (int j = 0; j < 88; j++) {
            for (int h = 87; h > j; h--) {
                int curChar = students[h].getLastName().charAt(0);
                int prevChar = students[h - 1].getLastName().charAt(0);
                if (prevChar > curChar) {
                    Student prevStudent = students[h - 1];
                    students[h - 1] = students[h];
                    students[h] = prevStudent;
                }
            }
        }

        writeFile(students, "lastNameClassList");

        //Bubble sort based on student id
        for (int j = 0; j < 88; j++) {
            for (int h = 87; h > j; h--) {
                long curChar = students[h].getStudentId();
                long prevChar = students[h - 1].getStudentId();
                if (prevChar > curChar) {
                    Student prevStudent = students[h - 1];
                    students[h - 1] = students[h];
                    students[h] = prevStudent;
                }
            }
        }

        writeFile(students, "studentIDClassList");

    }

    private static void writeFile(Student[] students, String fileName) {
        File file = new File(fileName + ".txt");
        try {
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file)) {
                for (int j = 0; j < 88; j++) {
                    fileWriter.write(students[j].getStudentId() + " ");
                    fileWriter.write(students[j].getGrade() + " ");
                    fileWriter.write(students[j].getLastName() + " ");
                    fileWriter.write(students[j].getFirstName() + " ");
                    fileWriter.write(students[j].getGender() + " ");
                    fileWriter.write(System.getProperty("line.separator"));
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
            System.out.println("2: Bubble sort students based on their last name.");
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


    public static class Student {
        long studentId;
        int gradeLevel;
        String firstName, lastName, gender;

        Student(long studentId, int gradeLevel, String lastName, String firstName, String gender) {
            this.studentId = studentId;
            this.gradeLevel = gradeLevel;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
        }

        public int getGrade() {
            return this.gradeLevel;
        }

        public long getStudentId() {
            return this.studentId;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public String getLastName() {
            return this.lastName;
        }

        public String getGender() {
            return this.gender;
        }
    }
}
