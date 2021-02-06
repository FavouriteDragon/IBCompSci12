package main.java.john.ibcs34.students;

import java.io.Serializable;
import java.util.Scanner;

public class Student implements Serializable {

  private String firstName;
  private String lastName;
  private String gender;
  private int year;
  private long id;

  public Student() {
    //Empty constructor. Make sure to fix any Student object that uses this.
    this.firstName = "";
    this.lastName = "";
    this.gender = "";
    this.year = 1;
    this.id = 1;
  }

  public Student(String firstName, String lastName, String gender, int year,
                 long id) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.year = year;
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  //Setters
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  public void setFullName(String fullName) {
    Scanner input = new Scanner(fullName);
    this.firstName = input.next();
    this.lastName = input.next();
    input.close();
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public long getID() {
    return this.id;
  }

  //Getters
  public void setID(long id) {
    this.id = id;
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
          && getGender().equals(student.getGender()) &&
          getFullName().equals(student.getFullName()))
        equals = true;
    }
    return super.equals(obj) || equals;
  }
}
