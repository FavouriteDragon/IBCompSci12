package main.java.john.ibcs34.students;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

    public static ClassRoom deserializeStudents(String filename) {

        ClassRoom classRoom = null;

        FileInputStream fileStream = null;
        ObjectInputStream objectStream = null;

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
     * @param fileName Name of the file. Include the extension, and the path if necessary.
     */
    public static ClassRoom readFile(String fileName) {
        String line;

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

    public static boolean isValid(String fileName, FileType type) {

        if (type.equals(FileType.IMAGE)) {
            FileInputStream input;
            try {
                input = new FileInputStream("src/main/resources/" + fileName + ".png");
            } catch (FileNotFoundException e) {
                return false;
            }
        } else if (type.equals(FileType.SERIALIZABLE)) {
            FileInputStream fileStream = null;
            ObjectInputStream objectStream = null;

            try {
                fileStream = new FileInputStream(fileName);
                objectStream = new ObjectInputStream(fileStream);
                objectStream.readObject();

            } catch (Exception ex) {
                return false;
            }
        } else if (type.equals(FileType.OTHER)) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            } catch (IOException exc) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Student Lab");

        //Edit is 3 dots, studentEdit and studentDelete are shown after clicking on the 3 dots.
        Button  //Student Editor
                sort, export, stats, add, delete, edit, toTitle,
                //Title
                create, importFile, load, save,
                //For Importing/Exporting students
                confirm, cancel, exportConfirm, exportCancel,
                //Student specific
                studentEdit, studentDelete;
        TextField   //General
                search, exportField,
                //Student specific
                lastName, firstName, gender, year, id;
        Image
                searchIcon;
        Text
                title, loadError,
                masterTitle, saveSuccessful, exportSuccessful;

        VBox    //Overall layout of the title screen
                vTitleLayout,
                //Overall layout of the functional screen
                vMasterLayout,
                //Layout of the student editor
                vStudentLayout, vStudentEditLayout;
        HBox
                hTitleLayout,
                hMasterLayout;
        Scene
                titleScene,
                masterScene, studentScene, studentEditScene;
        ScrollPane
                studentPane;
        ClassRoom
                currentStudents = new ClassRoom(), loadedStudents = new ClassRoom();
        Timer
                onUpdate, fadeError, fadeSave, fadeExport;
        AnimationTimer
                updateStudents;

        /* Title Screen */
        vTitleLayout = new VBox();
        hTitleLayout = new HBox();
        titleScene = new Scene(vTitleLayout, 400, 400);

        //Using the same Text object twice yeets it from the title scene. Not good.
        create = new Button("Create Classroom");
        importFile = new Button("Import Classroom");
        load = new Button("Load Classroom");
        confirm = new Button("Confirm", getImage("confirm_icon"));
        cancel = new Button("Cancel", getImage("cancel_icon"));
        exportConfirm = new Button("Confirm", getImage("confirm_icon"));
        exportCancel = new Button("Cancel", getImage("cancel_icon"));

        title = new Text("Students");
        masterTitle = new Text("Students");
        saveSuccessful = new Text("Saved!");
        exportSuccessful = new Text("Exported!");
        loadError = new Text("No Saved Progress");

        exportField = new TextField("Enter File1 Name");
        search = new TextField("Enter File Path");

        launchTitle(vTitleLayout, hTitleLayout, titleScene, primaryStage, title, create, importFile, load);

        /* Master Screen */
        vMasterLayout = new VBox();
        hMasterLayout = new HBox();
        studentPane = new ScrollPane();
        masterScene = new Scene(studentPane, 400, 400);

        add = new Button("Add Student", getImage("plus_icon"));
        delete = new Button("Delete Student", getImage("minus_icon"));
        save = new Button("Save");
        export = new Button("Export");
        stats = new Button("Statistics");
        toTitle = new Button("Main Menu");

        /* Student Screen */
        vStudentLayout = new VBox();
        vStudentEditLayout = new VBox();
        studentScene = new Scene(vStudentLayout, 400, 400);
        studentEditScene = new Scene(vStudentEditLayout, 400, 400);


        //Timer stuff for fading.
        //TODO: Abstractify this further so I don't have to manually create a timer per text I want to fade.
        fadeError = new Timer(5, action -> {
            loadError.setOpacity(loadError.getOpacity() - 0.0075F);
            if (loadError.getOpacity() < 0) {
                loadError.setStroke(Color.WHITE);
            }
        });

        fadeSave = new Timer(5, action -> {
            saveSuccessful.setOpacity(saveSuccessful.getOpacity() - 0.0075F);
            if (saveSuccessful.getOpacity() < 0) {
                saveSuccessful.setStroke(Color.WHITE);
            }

        });

        fadeExport = new Timer(5, action -> {
            exportSuccessful.setOpacity(exportSuccessful.getOpacity() - 0.0075F);
            if (exportSuccessful.getOpacity() < 0) {
                exportSuccessful.setStroke(Color.WHITE);
            }
        });

        updateStudents = new AnimationTimer() {
            @Override
            public void handle(long now) {
                launchMain(vMasterLayout, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                        primaryStage, masterTitle, studentPane, currentStudents, this, add, delete, export, stats, save, toTitle);
                this.stop();
            }
        };
        onUpdate = new Timer(2, action -> {
            cleanTimers(fadeError, loadError);
            cleanTimers(fadeSave, saveSuccessful);
            cleanTimers(fadeExport, exportSuccessful);
        });


        //This edits the buttons, launches the title screen
        onUpdate.start();

        //Title Screen Buttons
        create.setOnAction(event -> launchMain(vMasterLayout, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                primaryStage, masterTitle, studentPane, currentStudents, updateStudents, add, delete, export, stats, save, toTitle));

        load.setOnAction(event -> {
            if (!isValid("ObjectStorage", FileType.SERIALIZABLE)) {
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
                loadedStudents.addStudents(Objects.requireNonNull(deserializeStudents("ObjectStorage")));
                launchMain(vMasterLayout, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                        primaryStage, masterTitle, studentPane, currentStudents, updateStudents, add, delete, export, stats, save, toTitle);
            }
            if (!vTitleLayout.getChildren().contains(loadError)) {
                vTitleLayout.getChildren().add(loadError);
                vTitleLayout.setLayoutY(vTitleLayout.getLayoutY() + loadError.getScaleY() * 14);
            }
        });


        importFile.setOnAction(event -> launchReadFile(primaryStage, confirm, cancel, search));

        confirm.setOnAction(event -> {
            //Variables in lambdas have to be final which is a pain in the ass. So, you just clear everything then add students.

            if (isValid(search.getText(), FileType.OTHER)) {
                currentStudents.clearStudents();
                currentStudents.addStudents(Objects.requireNonNull(readFile(search.getText())));
                launchMain(vMasterLayout, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                        primaryStage, masterTitle, studentPane, currentStudents, updateStudents, add, delete, export, stats, save, toTitle);
            }
        });

        //Cancel just brings us back
        cancel.setOnAction(event -> switchScene(titleScene, primaryStage));

        //Master Screen
        save.setOnAction(event -> {
            serializeStudents(currentStudents);
            saveSuccessful.setStroke(Color.GREEN);
            saveSuccessful.setScaleY(2);
            saveSuccessful.setScaleX(2);
            saveSuccessful.setOpacity(1.0F);

            fadeSave.restart();

            if (!hMasterLayout.getChildren().contains(saveSuccessful))
                hMasterLayout.getChildren().add(saveSuccessful);
        });

        export.setOnAction(event -> {
            launchWriteFile(primaryStage, exportConfirm, exportCancel, exportField);
        });

        //Export screen
        exportConfirm.setOnAction(event -> {
            writeTextFile(currentStudents, exportField.getText());
            switchScene(masterScene, primaryStage);
            //Success message
            exportSuccessful.setStroke(Color.BLUE);
            exportSuccessful.setScaleY(2);
            exportSuccessful.setScaleX(2);
            exportSuccessful.setOpacity(1.0F);

            fadeExport.restart();
            if (!hMasterLayout.getChildren().contains(exportSuccessful))
                hMasterLayout.getChildren().add(exportSuccessful);
        });
        exportCancel.setOnAction(event -> switchScene(masterScene, primaryStage));

        //Back to Title Screen
        toTitle.setOnAction(event -> {
            serializeStudents(currentStudents);
            switchScene(titleScene, primaryStage);
        });

        add.setOnAction(event -> {
            createStudent(studentScene, masterScene, vStudentLayout, primaryStage, currentStudents, updateStudents);
        });


        primaryStage.show();
    }

    public void cleanTimers(Timer fadeError, Text fadeText) {
        if (fadeText.getOpacity() <= 0F) {
            fadeText.setStroke(Color.WHITE);
            fadeError.stop();
        }
    }

    public void updateScene(Timer timer) {
        timer.restart();
    }

    public void updateScene(AnimationTimer timer) {
        timer.stop();
        timer.start();
    }

    //Once each scene is properly loaded you only need to switch between them.
    public void switchScene(Scene scene, Stage stage) {
        stage.setScene(scene);
    }

    public void launchTitle(VBox vBox, HBox hBox, Scene scene, Stage stage, Text title, Button... buttons) {
        if (hBox.getChildren().size() > 0 || vBox.getChildren().size() > 0)
            switchScene(scene, stage);
        else {
            Scale scale = new Scale(1, 1);
            scale.setX(scale.getX() * 20);
            scale.setY(scale.getY() * 20);

            vBox.setAlignment(Pos.CENTER);
            hBox.setAlignment(Pos.CENTER);

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

            //Buttons below everything else.
            //Essentially ensures there are two buttons per line.
            //Grabs the array length.
            int length = buttons.length;
            //Gets the amount of HBox's necessary. Skips the first 4 buttons.
            int size = Math.round(length / 2F);

            HBox[] newLines = new HBox[size];
            for (int i = 0; i < size; i++) {
                newLines[i] = new HBox();
                newLines[i].setAlignment(Pos.CENTER);
                newLines[i].setSpacing(scale.getX() * 4);
                //Two buttons per line.
                int maxButtons = Math.min(Math.min(size - i, 2), size);
                Button[] newLineButtons = new Button[maxButtons];
                for (int j = 0; j < maxButtons; j++) {
                    int index = i * 2 + j;
                    //Safety check
                    if (index < length) {
                        newLineButtons[j] = buttons[index];
                    }
                }

                newLines[i].getChildren().addAll(newLineButtons);
            }

            vBox.setSpacing(scale.getY() * 2);
            hBox.setSpacing(scale.getX() * 4);

            vBox.getChildren().addAll(title);
            vBox.getChildren().addAll(newLines);

            Text author = new Text("Made by John, the slickest UI dev you've ever seen.");
            author.setTextAlignment(TextAlignment.CENTER);
            author.setStrokeWidth(2);

            VBox pos = new VBox();
            pos.setAlignment(Pos.BASELINE_CENTER);
            pos.getChildren().addAll(author);

            vBox.getChildren().addAll(pos);

            vBox.setLayoutY(-150 + 25 * vBox.getChildren().size() + scale.getY());

            stage.setScene(scene);
        }
    }

    public void launchMain(VBox vBox, VBox studentBox, HBox hBox, Scene scene, Scene studentScene, Stage stage, Text title, ScrollPane scrollPane, ClassRoom classRoom,
                           AnimationTimer timer, Button... buttons) {
        Scale scale = new Scale(1, 1);

        scale.setX(scale.getX() * 20);
        scale.setY(scale.getY() * 20);

        //Overall scroll pane, then a second one.
        ScrollPane studentPane = new ScrollPane();

        HBox topBox = new HBox();
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setSpacing(scale.getX() * 5);
        topBox.setLayoutX(scale.getX() * 5);


        Pane[] studentPanes = new Pane[classRoom.getStudents().length];
        VBox students = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        title.setFont(Font.font("System", FontPosture.ITALIC, 10));
        title.setStroke(Color.RED);
        title.setFill(Color.WHITE);
        title.setStrokeWidth(0.5);
        title.setScaleX(scale.getX() / 5);
        title.setScaleY(scale.getY() / 5);

        //Add and delete at top
        int l = 0;
        for (Button button : buttons) {
            button.setScaleX(scale.getX() / 12.5);
            button.setScaleY(scale.getY() / 12.5);
            if (l < 2) {
                //The add button is used first.
                button.setScaleX(scale.getX() / 30);
                button.setScaleY(scale.getY() / 30);
                if (l == 0) {
                    button.setStyle("-fx-background-color: #76D2FF");
                } else {
                    button.setStyle("-fx-background-color: #FFCD76");
                }
            } else {
                button.setStyle("-fx-background-color: #ff0000");
            }
            button.setTextFill(Color.WHITE);
            l++;
        }

        //Buttons below everything else.
        //Essentially ensures there are two buttons per line.
        //Grabs the array length.
        int length = buttons.length;
        //Gets the amount of HBox's necessary. Skips the first 4 buttons.
        int size = Math.round(length / 2F) - 1;

        HBox[] newLines = new HBox[size];
        for (int i = 0; i < size; i++) {
            newLines[i] = new HBox();
            newLines[i].setAlignment(Pos.CENTER);
            newLines[i].setSpacing(scale.getX() * 8);
            //Two buttons per line.
            int maxButtons = Math.min(size - i + 1, 2);
            Button[] newLineButtons = new Button[maxButtons];
            for (int j = 0; j < maxButtons; j++) {
                int index = (i + 1) * 2 + j;
                //Safety check
                if (index < length) {
                    newLineButtons[j] = buttons[index];
                }
            }

            newLines[i].getChildren().addAll(newLineButtons);
        }

        studentPane.setScaleX(scale.getX() / 20);
        studentPane.setScaleY(scale.getY() / 20);

        //ClassRoom room, ScrollPane studentPane, VBox controller, Scene scene, Scene masterScene, Pane[] studentPanes,
        //                                    Stage stage, AnimationTimer timer
        displayStudentPanes(classRoom, studentPane, students, studentBox, studentScene, scene, studentPanes, stage, timer);

        students.setSpacing(scale.getY() / 4);
        students.setAlignment(Pos.TOP_CENTER);

        studentPane.setContent(students);
        studentPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        studentPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        vBox.setSpacing(scale.getY() * 2);
        hBox.setSpacing(scale.getX() * 4);

        //Basically ensures that things aren't added twice, also allows for updating shiz
        topBox.getChildren().clear();
        vBox.getChildren().clear();

        topBox.getChildren().addAll(buttons[1], buttons[0]);


        vBox.getChildren().addAll(topBox, title, studentPane, hBox);
        vBox.getChildren().addAll(newLines);
        //Just ensures that there's blank space at the bottom; there's probably a better method but eh
        vBox.getChildren().addAll(new Text(""));
        vBox.setLayoutY(-150 + scale.getY() * (vBox.getChildren().size() + 1));


        scrollPane.setContent(vBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        if (!stage.getScene().equals(scene))
            stage.setScene(scene);
    }

    public void displayStudentPanes(ClassRoom room, ScrollPane studentPane, VBox controller, VBox studentEditor, Scene scene, Scene masterScene, Pane[] studentPanes,
                                    Stage stage, AnimationTimer timer) {
        int i = 0;
        Button[] editButtons = new Button[room.getStudents().length];
        Text[] names = new Text[room.getStudents().length];
        Text[] grades = new Text[room.getStudents().length];
        Text[] ids = new Text[room.getStudents().length];
        Text[] genders = new Text[room.getStudents().length];

        for (Student student : room.getStudents()) {
            studentPanes[i] = new Pane();
            names[i] = new Text(student.getFullName());
            grades[i] = new Text(String.valueOf(student.getYear()));
            genders[i] = new Text(student.getGender());
            ids[i] = new Text(String.valueOf(student.getID()));
            editButtons[i] = new Button("Edit");

            names[i].setLayoutX(15);
            names[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

            grades[i].setLayoutX(160);
            grades[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

            ids[i].setLayoutX(200);
            ids[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

            genders[i].setLayoutX(285);
            genders[i].setLayoutY(studentPanes[i].getLayoutY() + 20);


            editButtons[i].setStyle("-fx-background-color: #1BCCE4");
            editButtons[i].setTextFill(Color.WHITE);
            editButtons[i].setAlignment(Pos.CENTER_RIGHT);
            editButtons[i].setLayoutX(345);
            editButtons[i].setLayoutY(studentPanes[i].getLayoutY() + 5);

            //Scene scene, Scene masterScene, VBox controller, Stage stage, Student student, AnimationTimer masterUpdate
            editButtons[i].setOnAction(event -> editStudent(scene, masterScene, controller, stage, student, timer));
            studentPanes[i].getChildren().addAll(names[i], grades[i], genders[i], ids[i], editButtons[i]);

            //What the heck is this, java???? This is cursed
            studentPanes[i].setBorder(new Border(new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID,
                    new CornerRadii(5), BorderWidths.DEFAULT)));
            i++;
        }
        controller.setLayoutY(-400);
        controller.getChildren().addAll(studentPanes);
        studentPane.setContent(controller);
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
            if (!isValid(field.getText(), FileType.OTHER)) {
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

    public void launchWriteFile(Stage stage, Button confirm, Button cancel, TextField field) {
        VBox vbox = new VBox();
        HBox buttonBox = new HBox();
        Scale scale = new Scale(1, 1);
        Text error = new Text("Please enter a valid file name.");

        scale.setX(scale.getX() * 20);
        scale.setY(scale.getY() * 20);

        field.setMaxHeight(scale.getX() / 2);
        field.setOnAction(event -> {
            if (!isValid(field.getText(), FileType.OTHER)) {
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

    public void createStudent(Scene scene, Scene masterScene, VBox controller, Stage stage, ClassRoom room, AnimationTimer masterUpdate) {
        //VBox vBox, HBox hBox, Scene scene, Stage stage, Text title, ScrollPane scrollPane, ClassRoom classRoom, Button... buttons
        TextField[] studentFields = new TextField[5];
        HBox box = new HBox();
        Button confirm, cancel;
        Scale scale = new Scale(1, 1);

        scale.setX(scale.getX() * 20);
        scale.setY(scale.getY() * 20);

        int i = 0;
        for (TextField field : studentFields) {
            //Resets the text in between creating students
            switch (i) {
                case 0:
                    studentFields[i] = new TextField("First Name");
                    break;
                case 1:
                    studentFields[i] = new TextField("Last Name");
                    break;
                case 2:
                    studentFields[i] = new TextField("Gender");
                    break;
                case 3:
                    studentFields[i] = new TextField("Grade");
                    break;
                case 4:
                    studentFields[i] = new TextField("Student ID");
                    break;
            }
            if (field != null)
                field.clear();
            i++;
        }

        confirm = new Button("Confirm", getImage("confirm_icon"));
        cancel = new Button("Cancel", getImage("cancel_icon"));

        confirm.setScaleX(scale.getX() / 30);
        confirm.setScaleY(scale.getX() / 30);
        confirm.setStyle("-fx-background-color: #76D2FF");
        confirm.setOnAction(event -> {
            room.addStudents(new Student(studentFields[0].getText(), studentFields[1].getText(),
                    studentFields[2].getText(), Integer.parseInt(studentFields[3].getText()),
                    Long.parseLong(studentFields[4].getText())));
            updateScene(masterUpdate);
        });

        cancel.setStyle("-fx-background-color: #FFCD76");
        cancel.setScaleX(scale.getY() / 30);
        cancel.setScaleY(scale.getY() / 30);
        cancel.setOnAction(event -> switchScene(masterScene, stage));

        if (box.getChildren().size() == 0 && controller.getChildren().size() == 0) {
            box.getChildren().addAll(confirm, cancel);
            controller.getChildren().addAll(studentFields);
            controller.getChildren().add(box);
        }

        stage.setScene(scene);
    }

    public void editStudent(Scene scene, Scene masterScene, VBox controller, Stage stage, Student student, AnimationTimer masterUpdate) {
        //VBox vBox, HBox hBox, Scene scene, Stage stage, Text title, ScrollPane scrollPane, ClassRoom classRoom, Button... buttons
        TextField[] studentFields = new TextField[5];
        HBox box = new HBox();
        Button confirm, cancel;
        Scale scale = new Scale(1, 1);

        scale.setX(scale.getX() * 20);
        scale.setY(scale.getY() * 20);

        int i = 0;
        for (TextField field : studentFields) {
            //Resets the text in between creating students
            switch (i) {
                case 0:
                    studentFields[i] = new TextField("First Name");
                    break;
                case 1:
                    studentFields[i] = new TextField("Last Name");
                    break;
                case 2:
                    studentFields[i] = new TextField("Gender");
                    break;
                case 3:
                    studentFields[i] = new TextField("Grade");
                    break;
                case 4:
                    studentFields[i] = new TextField("Student ID");
                    break;
            }
            if (field != null)
                field.clear();
            i++;
        }

        confirm = new Button("Confirm", getImage("confirm_icon"));
        cancel = new Button("Cancel", getImage("cancel_icon"));

        confirm.setScaleX(scale.getX() / 30);
        confirm.setScaleY(scale.getX() / 30);
        confirm.setStyle("-fx-background-color: #76D2FF");
        confirm.setOnAction(event -> {
            student.setFullName(studentFields[0].getText() + " " + studentFields[1].getText());
            student.setGender(studentFields[2].getText());
            student.setYear(Integer.parseInt(studentFields[3].getText()));
            student.setID(Long.parseLong(studentFields[4].getText()));

            updateScene(masterUpdate);
        });

        cancel.setStyle("-fx-background-color: #FFCD76");
        cancel.setScaleX(scale.getY() / 30);
        cancel.setScaleY(scale.getY() / 30);
        cancel.setOnAction(event -> switchScene(masterScene, stage));


        box.getChildren().clear();
        controller.getChildren().clear();

        box.getChildren().addAll(confirm, cancel);
        controller.getChildren().addAll(studentFields);
        controller.getChildren().add(box);

        stage.setScene(scene);
    }


    public enum FileType {
        IMAGE,
        SERIALIZABLE,
        OTHER
    }
}
