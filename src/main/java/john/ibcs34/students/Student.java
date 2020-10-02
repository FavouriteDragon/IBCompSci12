package main.java.john.ibcs34.students;

import java.io.Serializable;

public class Student implements Serializable {

    private String firstName;
    private String lastName;
    private final String gender;
    private final int year;
    private final int id;

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

    //Legitimately whomst the truck
    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof Student) {
            Student student = (Student) obj;
            if (getYear() == student.getYear() && getID() == student.getID()
                    && getGender().equals(student.getGender()) && getFullName().equals(student.getFullName()))
                equals = true;
        }
        return super.equals(obj) || equals;
    }
}
