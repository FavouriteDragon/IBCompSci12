package main.java.john.ibcs34.students.labtests;

import java.io.Serializable;

public class StudentClass implements Serializable {
    Student[] myStudent;

    StudentClass(Student... students) {
        int a = 0;
        this.myStudent = new Student[students.length];

        for (Student temp : students) {
            myStudent[a] = temp;
            a++;
        }
    }

    public int[] getGrades() {
        int i;
        int[] grades = new int[4];

        for (i = 0; i < myStudent.length; i++) {

            if (myStudent[i].grade == 9) {
                grades[0]++;
            } else if (myStudent[i].grade == 10) {
                grades[1]++;
            } else if (myStudent[i].grade == 11) {
                grades[2]++;
            } else {
                grades[3]++;
            }
        }
        return grades;
    }

    public int getGender() {
        int boys = 0;
        int test = 0;
        int i;

        for (i = 0; i < myStudent.length; i++) {
            test = myStudent[i].gender.compareToIgnoreCase("male");

            if (test == 0) {
                boys++;
            }
        }
        return boys;
    }

    void add(Student newStudent) {
        int i;
        Student[] temp = new Student[myStudent.length + 1];

        for (i = 0; i < myStudent.length; i++) {
            temp[i] = myStudent[i];
        }
        temp[myStudent.length] = newStudent;
        myStudent = temp;
    }

    void delete(long deleteID) {
        int i;
        int iTemp = 0;
        Student[] temp = new Student[myStudent.length - 1];

        for (i = 0; i < myStudent.length; i++) {

            if (myStudent[i].studentnumber != deleteID) {
                temp[iTemp] = myStudent[i];
                iTemp++;
            }
        }
        myStudent = temp;
    }

    void edit(Student oldInfo, Student newInfo) {
        int i;

        for (i = 0; i < myStudent.length; i++) {

            if (myStudent[i].studentnumber == oldInfo.studentnumber) {
                myStudent[i] = newInfo;
            }
        }
    }
}//StudentClass

