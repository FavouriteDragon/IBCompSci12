package main.java.john.ibcs34.students;

public class Student {

    private String firstName;
    private String lastName;
    private String gender;
    private int year;
    private int id;

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
}
