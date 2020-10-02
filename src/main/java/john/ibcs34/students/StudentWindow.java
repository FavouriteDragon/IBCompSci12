package main.java.john.ibcs34.students;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.TimerTask;

public class StudentWindow extends Application {

    public static void main(String[] args) {
        Student[] studentArray = new Student[]{
                new Student("John", "Atkins", "Male", 12, 759517),
                new Student("James", "Atkins", "Male", 12, 759516),
        };
        ClassRoom classRoom = new ClassRoom(studentArray);
        //Just me testing
        //students = readFile("StudentTest.txt");
        //serializeStudents(students);
        //Make sure the read in is the same location as the write out!
        //students = deserializeStudents(new File("ObjectStorage").getPath());
        //writeTextFile(classRoom, "StudentTest");
        launch(args);
    }


    public static void serializeStudents(ClassRoom classRoom) {

        FileOutputStream fileStream = null;
        ObjectOutputStream objectStream = null;

        try {
            //Just create a temp file, literally does not matter
            fileStream = new FileOutputStream(new File("ObjectStorage").getPath());
            objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(classRoom);

            System.out.println("Done");

        } catch (Exception ex) {
            System.out.println("You have no saved progress!.");
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

    public static ClassRoom deserializeStudents(String filename, boolean check) {

        ClassRoom classRoom = null;

        FileInputStream fileStream = null;
        ObjectInputStream objectStream = null;

        if (check) {
            try {
                fileStream = new FileInputStream(filename);
                objectStream = new ObjectInputStream(fileStream);
                classRoom = (ClassRoom) objectStream.readObject();

            } catch (Exception ex) {
                return null;
            }
        } else {
            try {
                fileStream = new FileInputStream(filename);
                objectStream = new ObjectInputStream(fileStream);
                classRoom = (ClassRoom) objectStream.readObject();

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

        return classRoom;
    }

    /**
     * @param classRoom Array of students to write
     * @param fileName  The name of the sorted file
     */
    private static void writeTextFile(ClassRoom classRoom, String fileName) {
        File file = new File(fileName + ".txt");
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            //First clear the file.
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(classRoom.toString());
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName Name of the file. Include the extension, and the path if necesssary.
     * @param check    Whether you're checking to see if there's a valid file. Useful for users.
     * @return
     */
    public static ClassRoom readFile(String fileName, boolean check) {
        String line;

        if (check) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            } catch (IOException exc) {
                return null;
            }
        } else {

            Student[] students = new Student[readFileLength(fileName)];
            int i = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                while ((line = br.readLine()) != null) {
                    //Thanks, I hate it
                    students[i] = parseStudentFromLine(line);
                    i++;
                }

            } catch (IOException exc) {
                System.out.println("You goofed. ");
                exc.printStackTrace();
            }
            return new ClassRoom(students);
        }
        return new ClassRoom();
    }

    public static Student parseStudentFromLine(String line) {
        Student student = new Student();
        Scanner input = new Scanner(line);

        while (input.hasNext()) {
            int id, year;
            String gender, lastName, firstName;

            id = input.nextInt();
            year = input.nextInt();
            lastName = input.next();
            firstName = input.next();
            gender = input.next();

            student = new Student(firstName, lastName, gender, year, id);
        }

        return student;

    }

    public static int readFileLength(String fileName) {
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.readLine() != null) {
                i++;
            }

        } catch (IOException exc) {
            System.out.println("You goofed. ");
            exc.printStackTrace();
        }
        return i;
    }

    //While arrays are technically mutable and this could be a void method, that just makes everything more confusing. Treat it like it's immutable
    //for the purpose of returning. Although in order to return an object you have to treat it like it's mutable. It's whack.
    public static Object[] capArraySize(int size, Object[] array) {
        Object[] newArray = new Object[size];
        int i = 0;

        while (i < size) {
            newArray[i] = array[i];
            i++;
        }

        return newArray;
    }

    //While arrays are technically mutable and this could be a void method, that just makes everything more confusing. Treat it like it's immutable
    //for the purpose of returning. Although in order to return an object you have to treat it like it's mutable. It's whack.
    public static Student[] capArraySize(int size, Student[] students) {
        Student[] newArray = new Student[size];
        int i = 0;

        while (i < size) {
            newArray[i] = students[i];
            i++;
        }

        return newArray;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Student Lab");

        //Edit is 3 dots, studentEdit and studentDelete are shown after clicking on the 3 dots.
        Button  //Main Menu
                sort, export, stats, add, delete, edit, create, importFile, load, save,
                //For Importing/Exporting students
                confirm, cancel,
                //Student specific
                studentEdit, studentDelete;
        TextField   //General
                search,
                //Student specific
                lastName, firstName, gender, year, id;
        Image searchIcon;
        Text title, loadError;

        VBox    //Overall layout of the title screen
                vTitleLayout,
                //Overall layout of the functional screen
                vMasterLayout;
        HBox hMasterLayout, hTitleLayout, hButtonLayoutBottom;
        Scene titleScene, masterScene, studentScene;
        Pane masterPane = new Pane();
        ScrollPane studentPane = new ScrollPane();
        ClassRoom currentStudents = new ClassRoom(), loadedStudents = new ClassRoom();
        TimerTask fadeLoadError;
        Timer onUpdate, fadeError;

        /* Title Screen */
        vTitleLayout = new VBox();
        hTitleLayout = new HBox();
        titleScene = new Scene(vTitleLayout, 400, 400);
        title = new Text("Students");
        create = new Button("Create Classroom");
        importFile = new Button("Import Classroom");
        load = new Button("Load Classroom");
        confirm = new Button("Confirm", getImage("confirm_icon"));
        cancel = new Button("Cancel", getImage("cancel_icon"));
        search = new TextField("Enter File Path.");
        loadError = new Text("No Progress Saved.");

        //Timer stuff for fading.
        fadeError = new Timer(5, action -> {
            loadError.setOpacity(loadError.getOpacity() - 0.0075F);
            if (loadError.getOpacity() < 0) {
                loadError.setStroke(Color.WHITE);
            }
        });

        onUpdate = new Timer(2, action -> {
            cleanTimers(fadeError, loadError);
        });


        //This edits the buttons? I think
        onUpdate.start();
        launchTitle(vTitleLayout, hTitleLayout, titleScene, primaryStage, title, create, importFile, load);

        /* Master Screen */
        vMasterLayout = new VBox();
        hMasterLayout = new HBox();
        masterScene = new Scene(vMasterLayout, 400, 400);

        add = new Button("Add Student");
        delete = new Button("Delete Student");
        save = new Button("Save");
        export = new Button("Export");
        stats = new Button("Statistics");

        //Title Screen Buttons
        create.setOnAction(event -> launchMain(vMasterLayout, hMasterLayout, masterScene, primaryStage, title, studentPane, add, delete));

        load.setOnAction(event -> {
            if (deserializeStudents("ObjectStorage", true) == null) {
                loadError.setStroke(Color.RED);
                loadError.setScaleY(2);
                loadError.setScaleX(2);
                loadError.setOpacity(1.0F);

                fadeError.restart();
            } else {
                loadError.setStroke(Color.WHITE);
                loadError.setScaleY(0);
                loadError.setScaleX(0);
                loadedStudents.clearStudents();
                loadedStudents.addStudents(deserializeStudents("ObjectStorage", false));
            }
            if (!vTitleLayout.getChildren().contains(loadError)) {
                vTitleLayout.getChildren().add(loadError);
                vTitleLayout.setLayoutY(vTitleLayout.getLayoutY() + loadError.getScaleY() * 14);
            }
        });


        importFile.setOnAction(event -> launchReadFile(primaryStage, confirm, cancel, search));

        confirm.setOnAction(event -> {
            //Variables in lambdas have to be final which is a pain in the ass. So, you just clear everything then add students.
            currentStudents.clearStudents();
            currentStudents.addStudents(Objects.requireNonNull(readFile(search.getText(), false)));
            launchMain(vMasterLayout, hMasterLayout, masterScene, primaryStage, title, studentPane, add, delete);
        });

        //Cancel just brings us back
        cancel.setOnAction(event -> switchScene(titleScene, primaryStage));

        //Master Screen
        save.setOnAction(event -> serializeStudents(currentStudents));


        primaryStage.show();
    }

    public void cleanTimers(Timer fadeError, Text fadeText) {
        if (fadeText.getOpacity() <= 0F) {
            fadeText.setStroke(Color.WHITE);
            fadeError.stop();
        }
    }
    //Once each scene is properly loaded you only need to switch between them.
    public void switchScene(Scene scene, Stage stage) {
        stage.setScene(scene);
    }

    public void launchTitle(VBox vBox, HBox hBox, Scene scene, Stage stage, Text title, Button... buttons) {
        Scale scale = new Scale(1, 1);
        scale.setX(scale.getX() * 20);
        scale.setY(scale.getY() * 20);

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        title = new Text("Students");
        title.setFont(Font.font("System", FontPosture.ITALIC, 10));
        title.setStroke(Color.RED);
        title.setFill(Color.WHITE);
        title.setStrokeWidth(0.5);
        title.setScaleX(scale.getX() / 5);
        title.setScaleY(scale.getY() / 5);

        //Formats the buttons
        for (Button button : buttons) {
            button.setScaleX(scale.getX() / 12.5);
            button.setScaleY(scale.getY() / 12.5);
            button.setStyle("-fx-background-color: #ff0000");
            button.setTextFill(Color.WHITE);
        }

        //Essentially ensures there are two buttons per line.
        //Grabs the array length.
        int length = buttons.length;
        //Gets the amount of HBox's necessary.
        int size = Math.round(length / 2F) - 1;
        HBox[] newLines = new HBox[size];
        for (int i = 0; i < size; i++) {
            newLines[i] = new HBox();
            newLines[i].setAlignment(Pos.CENTER);
            newLines[i].setSpacing(scale.getX() * 4);
            //Two buttons per line.
            Button[] newLineButtons = new Button[size - i];
            for (int j = 0; j < size - i + 1; j++) {
                int index = (i + 1) * 2 + j - 1;
                //Safety check
                if (index < length)
                    newLineButtons[i] = buttons[(i + 1) * 2 + j - 1];
            }

            newLines[i].getChildren().addAll(newLineButtons);
        }

        vBox.setLayoutY(-125 + 35 * newLines.length + scale.getY());
        vBox.setSpacing(scale.getY() * 2);
        hBox.setSpacing(scale.getX() * 4);

        hBox.getChildren().addAll(buttons[0], buttons[1]);
        vBox.getChildren().addAll(title, hBox);
        vBox.getChildren().addAll(newLines);

        Text author = new Text("Made by John, the slickest UI dev you've ever seen.");
        author.setTextAlignment(TextAlignment.CENTER);
        author.setStrokeWidth(2);

        VBox pos = new VBox();
        pos.setAlignment(Pos.BASELINE_CENTER);
        pos.getChildren().addAll(author);

        vBox.getChildren().addAll(pos);

        stage.setScene(scene);
    }

    public void launchMain(VBox vBox, HBox hBox, Scene scene, Stage stage, Text title, ScrollPane pane, Button... buttons) {
        Scale scale = new Scale(1, 1);
        scale.setX(scale.getX() * 20);
        scale.setY(scale.getY() * 20);

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        title = new Text("Students");
        title.setFont(Font.font("System", FontPosture.ITALIC, 10));
        title.setStroke(Color.RED);
        title.setFill(Color.WHITE);
        title.setStrokeWidth(0.5);
        title.setScaleX(scale.getX() / 5);
        title.setScaleY(scale.getY() / 5);

        //Add and delete at top
        buttons[0] = new Button("Add Student");
        buttons[1] = new Button("Delete Student");
        for (Button button : buttons) {
            button.setScaleX(scale.getX() / 12.5);
            button.setScaleY(scale.getY() / 12.5);
            button.setStyle("-fx-background-color: #ff0000");
            button.setTextFill(Color.WHITE);
        }

        vBox.setLayoutY(-150 + scale.getY());
        vBox.setSpacing(scale.getY() * 2);
        hBox.setSpacing(scale.getX() * 4);

        hBox.getChildren().addAll(buttons);
        vBox.getChildren().addAll(title, hBox);

        stage.setScene(scene);
    }

    public void launchReadFile(Stage stage, Button confirm, Button cancel, TextField field) {
        VBox vbox = new VBox();
        HBox buttonBox = new HBox();
        Scale scale = new Scale(1, 1);
        Text error = new Text("Please enter a valid file name.");

        scale.setX(scale.getX() * 20);
        scale.setY(scale.getY() * 20);

        field.setMaxHeight(scale.getX() / 2);
        field.setOnAction(event -> {
            if (readFile(field.getText(), true) == null) {
                error.setStrokeWidth(1);
                error.setStroke(Color.RED);
                error.setScaleX(scale.getX() / 10);
                error.setScaleY(scale.getY() / 10);
            }
            //Makes it transparent
            else {
                error.setStroke(Color.WHITE);
                error.setScaleX(0);
                error.setScaleY(0);
            }
        });

        confirm.setStyle("-fx-background-color: #76D2FF");
        cancel.setStyle("-fx-background-color: #FFCD76");

        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setLayoutY(vbox.getLayoutY() - scale.getY() * 2);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(40);

        buttonBox.getChildren().addAll(cancel, confirm);
        vbox.getChildren().addAll(error, field, buttonBox);

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
    }

    public ImageView getImage(String fileName) {

        FileInputStream input;
        ImageView imageView = null;
        try {
            input = new FileInputStream("src/main/resources/" + fileName + ".png");
            Image image = new Image(input);
            imageView = new ImageView(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        return imageView;
    }
}
