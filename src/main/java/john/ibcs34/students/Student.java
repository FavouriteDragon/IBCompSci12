package main.java.john.ibcs34.students;

import java.io.Serializable;

public class Student implements Serializable {

    private String firstName;
    private String lastName;
    private String gender;
    private int year;
    private int id;

    public Student() {
        //Empty constructor. Make sure to fix any Student object that uses this.
        this.firstName = "";
        this.lastName = "";
        this.gender = "";
        this.year = 1;
        this.id = 1;
    }

    public Student(String firstName, String lastName, String gender, int year, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.year = year;
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public int getYear() {
        return this.year;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return getID() + " " + getYear() +
                " " + getLastName() + " " + getFirstName() +
                " " + getGender();
    }
}
