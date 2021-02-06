package main.java.john.ibcs34.students;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;


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
    LinkedList<Student> studentList =
        new LinkedList<>(Arrays.asList(this.students));

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
    this.students = StudentWindow.capArraySize(studentList.size(),
        this.students);
  }

  public void moveStudentsToFront(Student... students) {
    Student[] newStudents = new Student[getStudents().length];
    this.removeStudents(students);
    System.arraycopy(students, 0, newStudents, 0, students.length);

    for (int i = students.length; i < newStudents.length; i++)
      newStudents[i] = getStudents()[i - students.length];

    this.students = newStudents;

  }

  public void sortByLastName() {
    int size = getStudents().length;
    for (int j = 0; j < size; j++) {
      for (int h = size - 1; h > j; h--) {
        int curChar = students[h].getLastName().charAt(0);
        int prevChar = students[h - 1].getLastName().charAt(0);
        if (prevChar > curChar) {
          Student prevStudent = students[h - 1];
          students[h - 1] = students[h];
          students[h] = prevStudent;
        }
      }
    }
  }

  public Student[] getSortByLastName() {
    int size = getStudents().length;
    //This basically ensures that this class' students aren't being edited,
      // allowing for comparisons
    Student[] tempStudents = getStudents().clone();
    for (int j = 0; j < size; j++) {
      for (int h = size - 1; h > j; h--) {
        int curChar = tempStudents[h].getLastName().charAt(0);
        int prevChar = tempStudents[h - 1].getLastName().charAt(0);
        if (prevChar > curChar) {
          Student prevStudent = tempStudents[h - 1];
          tempStudents[h - 1] = tempStudents[h];
          tempStudents[h] = prevStudent;
        }
      }
    }
    return tempStudents;
  }

  public void sortByID() {
    int size = getStudents().length;
    for (int j = 0; j < size; j++) {
      for (int h = size - 1; h > j; h--) {
        long currentId = students[h].getID();
        long prevID = students[h - 1].getID();
        if (prevID > currentId) {
          Student prevStudent = students[h - 1];
          students[h - 1] = students[h];
          students[h] = prevStudent;
        }
      }
    }
  }

  public ClassRoom getSortByID() {
    int size = getStudents().length;
    for (int j = 0; j < size; j++) {
      for (int h = size - 1; h > j; h--) {
        long currentId = students[h].getID();
        long prevID = students[h - 1].getID();
        if (prevID > currentId) {
          Student prevStudent = students[h - 1];
          students[h - 1] = students[h];
          students[h] = prevStudent;
        }
      }
    }
    return this;
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
  public boolean equals(Object obj) {
    boolean equivalent = true;
    if (obj instanceof ClassRoom) {
      ClassRoom room = (ClassRoom) obj;
      int i = 0;
      if (room.getStudents().length != this.students.length)
        equivalent = false;
      for (Student student : ((ClassRoom) obj).students) {
        if (room.students[i] != this.students[i])
          equivalent = false;
        //No break statements!
      }
    }
    return super.equals(obj) || equivalent;
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
