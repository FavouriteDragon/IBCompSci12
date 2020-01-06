package main.java.john.ibcs.lab8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AtkinsJLab8 {

    public class Student {
        long studentId;
        int gradeLevel;
        String firstName, lastName, gender;

        Student(long studentId, int gradeLevel, String firstName, String lastName, String gender) {
            this.studentId = studentId;
            this.gradeLevel = gradeLevel;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
        }
    }

    public static void main(String[] args) {
        String line;
        int boys, girls, year9, year10, year11, year12, i = 0;
        String id, firstName, lastName, gender;
        List<Student> students = Collections.emptyList();
        int[] lastNames = new int[26];

        try (BufferedReader br = new BufferedReader(new FileReader("classlist.txt"))) {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String studentLine = line;
                students.add(i, new Student(id = splitAtString(studentLine), firstName = ,
                       splitAtString(getRestSplitAtString(line)), lastName = spl )
                        i++;

            }
        }
        catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }
    }

    /**
     * Splits the string at the first space.
     * @param toSplit The string to split.
     * @return Returns the phrase before the split/space.
     */
    private static String splitAtString(String toSplit) {
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < toSplit.length(); i++) {
            char character = toSplit.charAt(i);
            if (Character.toString(character).equalsIgnoreCase(" ")) {
                return toReturn.toString();
            }
            toReturn.append(character);
        }
        return toReturn.toString();
    }

    /**
     *
     * @param toSplit The string to split.
     * @return Returns the rest of the string after the split.
     */
    private static String getRestSplitAtString(String toSplit) {
        StringBuilder toRemove = new StringBuilder();
        StringBuilder toReturn = new StringBuilder();
        toReturn = toReturn.append(toSplit);

        boolean addChar = true;
        for (int i = 0; i < toSplit.length(); i++) {
            char character = toSplit.charAt(i);
            if (Character.toString(character).equalsIgnoreCase(" ")) {
                addChar = false;
            }
            if (addChar)
                toRemove.append(character);
        }
        return toReturn.delete(0, toRemove.length() - 1).toString();
    }
}
