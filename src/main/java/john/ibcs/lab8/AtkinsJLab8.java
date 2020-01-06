package main.java.john.ibcs.lab8;

import java.io.*;

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

        try (BufferedReader br = new BufferedReader(new FileReader("classlist.txt"))) {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }
    }
}
