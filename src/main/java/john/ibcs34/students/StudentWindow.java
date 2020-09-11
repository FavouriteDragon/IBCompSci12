package main.java.john.ibcs34.students;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StudentWindow extends Application {

    public static void main(String[] args) {
        Students students = new Students(new Student("John", "Atkins", "Male", 12, 759517));
        writeFile(students, "StudentTest");
        launch(args);
    }

    /**
     * @param students Array of students to write
     * @param fileName The name of the sorted file
     */
    private static void writeFile(Students students, String fileName) {
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Nice");
    }
}
