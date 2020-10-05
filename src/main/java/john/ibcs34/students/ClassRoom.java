package main.java.john.ibcs34.students;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ClassRoom implements Serializable {

    private Student[] students;

    public ClassRoom(Student... students) {
        //Did I mention how much I hate mutable objects?
        this.students = students.clone();
    }

    public void addStudents(Student... students) {
        int totalSize = this.students.length + students.length;
        Student[] tempStudents = new Student[totalSize];

        for (int i = 0; i < totalSize; i++) {
            if (i < this.students.length)
                tempStudents[i] = this.students[i];
            else tempStudents[i] = students[i - this.students.length];
        }

        this.students = tempStudents;
    }

    public void addStudents(ClassRoom room) {
        this.addStudents(room.getStudents());
    }


    public void clearStudents() {
        this.students = StudentWindow.capArraySize(0, students);
    }

    public void removeStudents(Student... students) {
        LinkedList<Student> studentList = new LinkedList<>(Arrays.asList(this.students));

        for (Student value : this.students) {
            for (Student student : students) {
                if (value.equals(student)) {
                    studentList.remove(student);
                }
            }
        }

        int i = 0;
        for (Student student : studentList) {
            this.students[i] = student;
            i++;
        }
        this.students = StudentWindow.capArraySize(studentList.size(), this.students);


    }

    public String[] getFirstNames() {
        String[] strings = new String[students.length];
        int i = 0;
        for (Student student : students) {
            strings[i] = students[i].getFirstName();
        }
        return strings;
    }

    public String[] getLastNames() {
        String[] strings = new String[students.length];
        int i = 0;
        for (Student student : students) {
            strings[i] = students[i].getLastName();
        }
        return strings;
    }

    public String[] getFullNames() {
        String[] strings = new String[students.length];
        int i = 0;
        for (Student student : students) {
            strings[i] = students[i].getFullName();
        }
        return strings;
    }

    public String[] getGenders() {
        String[] strings = new String[students.length];
        int i = 0;
        for (Student student : students) {
            strings[i] = students[i].getGender();
        }
        return strings;
    }

    public int[] getYears() {
        int[] numbers = new int[students.length];
        int i = 0;
        for (Student student : students) {
            numbers[i] = students[i].getYear();
        }
        return numbers;
    }

    public long[] getIDs() {
        long[] numbers = new long[students.length];
        int i = 0;
        for (Student student : students) {
            numbers[i] = students[i].getID();
        }
        return numbers;
    }

    public Student[] getStudents() {
        return this.students;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Student student : students) {
            builder.append(student.toString());
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}
