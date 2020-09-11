package main.java.john.ibcs34.students;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class StudentWindow extends Application {

    public static void main(String[] args) {
        Student[] studentArray = new Student[]{
                new Student("John", "Atkins", "Male", 12, 759517),
                new Student("James", "Atkins", "Male", 12, 759516),
        };
        Students students = new Students(studentArray);
        //Just me testing 
        serializeStudents(students);
        //Make sure the read in is the same location as the write out!
        students = deserializeStudents(new File("ObjectStorage").getPath());
        writeTextFile(students, "StudentTest");
        launch(args);
    }


    public static void serializeStudents(Students students) {

        FileOutputStream fileStream = null;
        ObjectOutputStream objectStream = null;

        try {
            //Just create a temp file, literally does not matter
            fileStream = new FileOutputStream(new File("ObjectStorage").getPath());
            objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(students);

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (objectStream != null) {
                try {
                    objectStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static Students deserializeStudents(String filename) {

        Students students = null;

        FileInputStream fileStream = null;
        ObjectInputStream objectStream = null;

        try {
            fileStream = new FileInputStream(filename);
            objectStream = new ObjectInputStream(fileStream);
            students = (Students) objectStream.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectStream != null) {
                try {
                    objectStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return students;
    }

    /**
     * @param students Array of students to write
     * @param fileName The name of the sorted file
     */
    private static void writeTextFile(Students students, String fileName) {
        File file = new File(fileName + ".txt");
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(students.toString());
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Students readFile(String fileName) {
        String line;

        Student[] students = new Student[readFileLength(fileName)];
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                //Thanks, I hate it
                students[i] = new main.java.john.ibcs.lab8.Student(getIDFromString(line), getGradeFromString(line), getLastNameFromString(line),
                        getFirstNameFromString(line), getGenderFromString(line));
            i++;
            }

        } catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }
        return new Students(students);
    }

    public static Student parseStudentFromLine(String line) {
        Student student = new Student("", "", "", 1, 1);
        return student;

    }

    public static int readFileLength(String fileName) {
        int i = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
               i++;
            }

        } catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }
        return i;
    }
    //While arrays are technically mutable and this could be a void method, that just makes everything more confusing. Treat it like it's immutable
    //for the purpose of returning.
    public static Object[] capArraySize(int size, Object[] array) {
        Object[] newArray = new Object[size];
        int i = 0;

        while (i < size) {
            for (Object object : array) {
                newArray[i] = object;
                i++;
            }
        }

        return newArray;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Nice");
    }
}
