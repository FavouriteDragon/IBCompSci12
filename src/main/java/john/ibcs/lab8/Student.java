package main.java.john.ibcs.lab8;

public class Student {
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
