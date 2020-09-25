package main.java.john.ibcs34.students.labtests;

import java.io.Serializable;

//Renamed from student to Student. ClassName!
public class Student implements Serializable {

    //Use the getters and setters! Don't directly call the variables like you're currently doing!
    long studentnumber;
    int grade;
    String lastname;
    String firstname;
    String gender;

    Student(long studentnumber, int grade, String lastname, String firstname, String gender) { //creates student object
        this.studentnumber = studentnumber;
        this.grade = grade;
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
    }//constructor for student

    public long getStudentNumber() {
        return this.studentnumber;
    }

    public int getGrade() {
        return this.grade;
    }

    public String getLastName() {
        return this.lastname;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getGender() {
        return this.gender;
    }
}//student

