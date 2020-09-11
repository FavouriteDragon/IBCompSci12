package main.java.john.ibcs34.students;

public class Students {

    private Student[] students;

    public Students(Student... students) {
        this.students = students;
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

    public int[] getIDs() {
        int[] numbers = new int[students.length];
        int i = 0;
        for (Student student : students) {
            numbers[i] = students[i].getID();
        }
        return numbers;
    }

}
