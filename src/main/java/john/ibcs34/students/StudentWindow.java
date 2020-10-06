package main.java.john.ibcs34.students;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;


public class StudentWindow extends Application {

    public static void main(String[] args) {
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

    public static Pane[] capArraySize(int size, Pane[] panes) {
        Pane[] newArray = new Pane[size];
        int i = 0;

        while (i < size) {
            newArray[i] = panes[i];
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
                search, exportField;
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
                vStudentLayout, vStudentEditLayout, vStudents, vStatsLayout;
        HBox
                hTitleLayout,
                hMasterLayout;
        Scene
                titleScene,
                masterScene, studentScene, studentEditScene, statsScene;
        ScrollPane
                studentPane, statsPlane;
        ClassRoom
                currentStudents = new ClassRoom(), loadedStudents = new ClassRoom();
        Timer
                onUpdate, fadeError, fadeSave, fadeExport;
        AnimationTimer
                //Used for updating the main screen
                updateStudents,
                //Used for updating
                sortLastName, sortID;

        /* Title Screen */
        vTitleLayout = new VBox();
        hTitleLayout = new HBox();
        titleScene = new Scene(vTitleLayout, 600, 600);

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
        masterScene = new Scene(studentPane, 600, 600);

        add = new Button("Add Student", getImage("plus_icon"));
        delete = new Button("Delete Student", getImage("minus_icon"));
        save = new Button("Save");
        export = new Button("Export");
        stats = new Button("Statistics");
        toTitle = new Button("Main Menu");
        sort = new Button("Sort");

        /* Student Screen */
        vStudentLayout = new VBox();
        vStudentEditLayout = new VBox();
        vStudents = new VBox();
        studentScene = new Scene(vStudentLayout, 600, 600);
        studentEditScene = new Scene(vStudentEditLayout, 600, 600);

        /* Misc */
        vStatsLayout = new VBox();
        statsPlane = new ScrollPane();
        statsScene = new Scene(statsPlane, 600, 600);


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
                launchMain(vMasterLayout, vStudents, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                        primaryStage, masterTitle, studentPane, currentStudents, this, false, add, delete, export, stats, save, toTitle, sort);
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
        create.setOnAction(event -> {
            currentStudents.clearStudents();
            launchMain(vMasterLayout, vStudents, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                    primaryStage, masterTitle, studentPane, currentStudents, updateStudents, false, add, delete, export, stats, save, toTitle, sort);

        });

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
                currentStudents.clearStudents();
                currentStudents.addStudents(loadedStudents);
                launchMain(vMasterLayout, vStudentEditLayout, vStudents, hMasterLayout, masterScene, studentEditScene,
                        primaryStage, masterTitle, studentPane, currentStudents, updateStudents, false, add, delete, export, stats, save, toTitle, sort);
            }
            if (!vTitleLayout.getChildren().contains(loadError)) {
                vTitleLayout.getChildren().add(loadError);
                vTitleLayout.setLayoutY(vTitleLayout.getLayoutY() + loadError.getScaleY() * 14);
            }
        });


        importFile.setOnAction(event -> launchReadFile(primaryStage, confirm, cancel, search));

        confirm.setOnAction(event -> {
            //Variables in lambdas have to be final which is a pain in the ass. So, you just clear everything then add students.
            if (isValid(search.getText(), FileType.SERIALIZABLE)) {
                currentStudents.clearStudents();
                currentStudents.addStudents(Objects.requireNonNull(deserializeStudents(search.getText())));
                launchMain(vMasterLayout, vStudents, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                        primaryStage, masterTitle, studentPane, currentStudents, updateStudents, false, add, delete, export, stats, save, toTitle, sort);
            } else if (isValid(search.getText(), FileType.OTHER)) {
                currentStudents.clearStudents();
                currentStudents.addStudents(Objects.requireNonNull(readFile(search.getText())));
                launchMain(vMasterLayout, vStudents, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                        primaryStage, masterTitle, studentPane, currentStudents, updateStudents, false, add, delete, export, stats, save, toTitle, sort);
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

        stats.setOnAction(event -> displayStatistics(masterTitle, statsScene, statsPlane, vStatsLayout, primaryStage, currentStudents, updateStudents));

        sort.setOnAction(event -> {
            if (Arrays.equals(currentStudents.getStudents(), currentStudents.getSortByLastName()))
                currentStudents.sortByID();
            else currentStudents.sortByLastName();
            updateTimer(updateStudents);
        });
        //Back to Title Screen
        toTitle.setOnAction(event -> {
            if (currentStudents.getStudents().length > 0)
                serializeStudents(currentStudents);
            switchScene(titleScene, primaryStage);
        });

        add.setOnAction(event -> {
            createStudent(studentScene, masterScene, vStudentLayout, primaryStage, currentStudents, updateStudents);
        });
        delete.setOnAction(event -> {
            launchMain(vMasterLayout, vStudents, vStudentEditLayout, hMasterLayout, masterScene, studentEditScene,
                    primaryStage, masterTitle, studentPane, currentStudents, updateStudents, true, add, delete, export, stats, save, toTitle, sort);
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

    public void updateTimer(AnimationTimer timer) {
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

            vBox.setLayoutY(-250 + 25 * vBox.getChildren().size() + scale.getY());

            stage.setScene(scene);
        }
    }

    public void launchMain(VBox vBox, VBox students, VBox studentBox, HBox hBox, Scene scene, Scene studentScene, Stage stage, Text title, ScrollPane scrollPane, ClassRoom classRoom,
                           AnimationTimer timer, boolean delete, Button... buttons) {
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
                int index = (i + 1) * 2 + j - 1;
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
        displayStudentPanes(classRoom, studentPane, students, studentBox, studentScene, scene, studentPanes, stage, timer, delete);

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
        vBox.setLayoutY(-250 + scale.getY() * (vBox.getChildren().size() + 1));


        scrollPane.setContent(vBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        if (!stage.getScene().equals(scene))
            stage.setScene(scene);
    }

    public void displayStudentPanes(ClassRoom room, ScrollPane studentPane, VBox controller, VBox studentEditor, Scene scene, Scene masterScene, Pane[] studentPanes,
                                    Stage stage, AnimationTimer timer, boolean delete) {
        Button[] editButtons = new Button[room.getStudents().length];
        Text[] names = new Text[room.getStudents().length];
        Text[] grades = new Text[room.getStudents().length];
        Text[] ids = new Text[room.getStudents().length];
        Text[] genders = new Text[room.getStudents().length];
        ImageView search = getImage("search_icon");
        TextField searchField = new TextField("");
        Pane topPane = new Pane();


        search.setLayoutX(500);
        searchField.setLayoutY(topPane.getLayoutY() + 15);
        searchField.setLayoutX(240);
        topPane.getChildren().clear();
        topPane.getChildren().addAll(search, searchField);

        int i = 0;
        for (Student student : room.getStudents()) {
            studentPanes[i] = new Pane();
            //This is for action events
            Pane pane = studentPanes[i];
            names[i] = new Text(student.getFullName());
            grades[i] = new Text(String.valueOf(student.getYear()));
            genders[i] = new Text(student.getGender());
            ids[i] = new Text(String.valueOf(student.getID()));


            names[i].setLayoutX(15);
            names[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

            grades[i].setLayoutX(200);
            grades[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

            ids[i].setLayoutX(320);
            ids[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

            genders[i].setLayoutX(480);
            genders[i].setLayoutY(studentPanes[i].getLayoutY() + 20);


            if (delete) {
                editButtons[i] = new Button("Delete");
                editButtons[i].setStyle("-fx-background-color: #FF0000");
                editButtons[i].setOnAction(event -> {
                    deleteStudent(room, student, studentPanes, pane);
                    updateTimer(timer);
                });

            } else {
                editButtons[i] = new Button("Edit");
                editButtons[i].setStyle("-fx-background-color: #1BCCE4");
                editButtons[i].setOnAction(event -> editStudent(scene, masterScene, studentEditor, stage, student, timer));

            }
            editButtons[i].setTextFill(Color.WHITE);
            editButtons[i].setAlignment(Pos.CENTER_RIGHT);
            editButtons[i].setLayoutX(545);
            editButtons[i].setLayoutY(studentPanes[i].getLayoutY() + 5);

            studentPanes[i].getChildren().clear();
            studentPanes[i].getChildren().addAll(names[i], grades[i], genders[i], ids[i], editButtons[i]);

            //What the heck is this, java???? This is cursed
            studentPanes[i].setBorder(new Border(new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID,
                    new CornerRadii(5), BorderWidths.DEFAULT)));
            i++;
        }


        searchField.setOnAction(event -> {
            //Moves the students
            for (int j = 0; j < room.getStudents().length; j++) {
                Student student = room.getStudents()[j];
                if (!(searchField.getText().equals(" ") || searchField.getText().isEmpty())) {
                    //Else ifs ensure that things are properly unhighlighted.

                    if (student.getFullName().contains(searchField.getText()))
                        room.moveStudentsToFront(student);

                    else if (student.getFirstName().contains(searchField.getText()))
                        room.moveStudentsToFront(student);

                    else if (student.getLastName().contains(searchField.getText()))
                        room.moveStudentsToFront(student);

                    else if (student.getGender().contains(searchField.getText()))
                        room.moveStudentsToFront(student);

                    else if (String.valueOf(student.getYear()).contains(searchField.getText()))
                        room.moveStudentsToFront(student);

                    else if (String.valueOf(student.getID()).equals(searchField.getText()))
                        room.moveStudentsToFront(student);

                }
            }
            //Redisplays them
            displayStudentPanes(room, studentPane, controller, studentEditor, scene, masterScene, studentPanes, stage, timer, delete);

            //Visual Display (highlighting)
            for (int j = 0; j < room.getStudents().length; j++) {
                Student student = room.getStudents()[j];
                studentPanes[j].setBackground(Background.EMPTY);
                //For some reason Background.EMPTY doesn't work. Great.
                Background toSet = new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(5), new Insets(0)));
                if (!(searchField.getText().equals(" ") || searchField.getText().isEmpty())) {
                    //Else ifs ensure that things are properly unhighlighted.

                    if (student.getFullName().contains(searchField.getText()))
                        studentPanes[j].setBackground(toSet);

                    else if (student.getFirstName().contains(searchField.getText()))
                        studentPanes[j].setBackground(toSet);

                    else if (student.getLastName().contains(searchField.getText()))
                        studentPanes[j].setBackground(toSet);

                    else if (student.getGender().contains(searchField.getText()))
                        studentPanes[j].setBackground(toSet);

                    else if (String.valueOf(student.getYear()).contains(searchField.getText()))
                        studentPanes[j].setBackground(toSet);

                    else if (String.valueOf(student.getID()).equals(searchField.getText()))
                        studentPanes[j].setBackground(toSet);

                }
            }
        });

        controller.getChildren().clear();
        controller.getChildren().add(topPane);
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
            if (isValid(field.getText(), FileType.OTHER) || isValid(field.getText(), FileType.SERIALIZABLE)) {
                error.setStroke(Color.WHITE);
                error.setScaleX(0);
                error.setScaleY(0);
            }
            //Makes it transparent
            else {
                error.setStrokeWidth(1);
                error.setStroke(Color.RED);
                error.setScaleX(scale.getX() / 10);
                error.setScaleY(scale.getY() / 10);
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

        Scene scene = new Scene(vbox, 600, 600);
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

        Scene scene = new Scene(vbox, 600, 600);
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
            if (studentFields[i] != null)
                studentFields[i].clear();
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
            updateTimer(masterUpdate);
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
            if (studentFields[i] != null)
                studentFields[i].clear();
            switch (i) {
                case 0:
                    studentFields[i] = new TextField(student.getFirstName());
                    break;
                case 1:
                    studentFields[i] = new TextField(student.getLastName());
                    break;
                case 2:
                    studentFields[i] = new TextField(student.getGender());
                    break;
                case 3:
                    studentFields[i] = new TextField(String.valueOf(student.getYear()));
                    break;
                case 4:
                    studentFields[i] = new TextField(String.valueOf(student.getID()));
                    break;
            }
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

            updateTimer(masterUpdate);
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

    public void deleteStudent(ClassRoom classRoom, Student student, Pane[] studentPanes, Pane pane) {

        classRoom.removeStudents(student);

        LinkedList<Pane> paneList = new LinkedList<>(Arrays.asList(studentPanes));

        for (Pane allPanes : studentPanes) {
            paneList.removeIf(toRemove -> allPanes.equals(toRemove) && toRemove.equals(pane));
        }

        int i = 0;
        for (Pane toAdd : paneList) {
            studentPanes[i] = toAdd;
            i++;
        }
        studentPanes = StudentWindow.capArraySize(paneList.size(), studentPanes);

    }

    public void displayStatistics(Text title, Scene statsScene, ScrollPane displayPane, VBox display, Stage stage, ClassRoom room, AnimationTimer timer) {
        //Contains everything; displays stuff coolly
        Pane[] displayPanes = new Pane[36];
        Button exit;
        Text boy, girl, grade9, grade10, grade11, grade12, lastName, studentNumber;
        Text[] lastNameLetters = new Text[26];
        Scale scale = new Scale(20, 20);

        int boys = 0, girls = 0, year9 = 0, year10 = 0, year11 = 0, year12 = 0;
        Student[] students = room.getStudents();
        int[] lastNames = new int[26];

        for (int j = 0; j < room.getStudents().length; j++) {
            if (students[j] != null) {
                Student student = students[j];
                if (student.getGender().equalsIgnoreCase("male"))
                    boys++;
                else girls++;
                int charCode = student.getLastName().charAt(0);
                charCode -= 65;
                lastNames[charCode]++;

                switch (student.getYear()) {
                    case 9:
                        year9++;
                        break;
                    case 10:
                        year10++;
                        break;
                    case 11:
                        year11++;
                        break;
                    case 12:
                        year12++;
                        break;

                    default:
                        break;
                }
            }
        }

        int i = 0;
        for (Pane pane : displayPanes) {
            displayPanes[i] = new Pane();
            i++;
        }

        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setTranslateX(100);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTranslateY(20);
        titleBox.getChildren().add(title);


        studentNumber = new Text("Total Number of Students: " + students.length);
        studentNumber.setLayoutX(50);
        studentNumber.setLayoutY(displayPanes[0].getLayoutY() + 50);
        displayPanes[0].getChildren().add(studentNumber);

        boy = new Text("Boys: " + boys);
        boy.setLayoutX(50);
        boy.setLayoutY(displayPanes[1].getLayoutY() + 20);
        displayPanes[1].getChildren().add(boy);

        girl = new Text("Girls: " + girls);
        girl.setLayoutX(50);
        girl.setLayoutY(displayPanes[2].getLayoutY() + 20);
        displayPanes[2].getChildren().add(girl);

        grade9 = new Text("9th Graders: " + year9);
        grade9.setLayoutX(50);
        grade9.setLayoutY(displayPanes[3].getLayoutY() + 20);
        displayPanes[3].getChildren().add(grade9);

        grade10 = new Text("10th Graders: " + year10);
        grade10.setLayoutX(50);
        grade10.setLayoutY(displayPanes[4].getLayoutY() + 20);
        displayPanes[4].getChildren().add(grade10);

        grade11 = new Text("11th Graders: " + year11);
        grade11.setLayoutX(50);
        grade11.setLayoutY(displayPanes[5].getLayoutY() + 20);
        displayPanes[5].getChildren().add(grade11);

        grade12 = new Text("12th Graders: " + year12);
        grade12.setLayoutX(50);
        grade12.setLayoutY(displayPanes[6].getLayoutY() + 20);
        displayPanes[6].getChildren().add(grade12);

        lastName = new Text("Last names beginning with: ");
        lastName.setLayoutX(50);
        lastName.setLayoutY(displayPanes[7].getLayoutY() + 20);
        displayPanes[7].getChildren().add(lastName);


        for (int j = 0; j < 26; j++) {
            lastNameLetters[j] = new Text((char) (j + 65) + " : " + lastNames[j]);
            lastNameLetters[j].setLayoutY(displayPanes[j + 8].getLayoutY() + 20);
            lastNameLetters[j].setLayoutX(50);
            displayPanes[j + 8].getChildren().add(lastNameLetters[j]);
        }

        exit = new Button("Exit");
        exit.setStyle("-fx-background-color: #FF0000");
        exit.setOnAction(event -> updateTimer(timer));
        exit.setTextFill(Color.WHITE);
        exit.setScaleX(scale.getX() / 12.5);
        exit.setScaleY(scale.getY() / 12.5);
        exit.setLayoutX(280);
        exit.setLayoutY(displayPanes[35].getLayoutY() + 20);
        displayPanes[35].getChildren().add(exit);

        int j = 0;
        for (Pane pane : displayPanes) {
            if (j < 35) {
                pane.setBorder(new Border(new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID,
                        new CornerRadii(10), BorderWidths.DEFAULT)));
            }
            j++;
        }


        VBox topBox = new VBox();
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().add(titleBox);

        VBox otherBox = new VBox();
        otherBox.setAlignment(Pos.BOTTOM_CENTER);
        otherBox.getChildren().addAll(displayPanes);
        //Ensures there's space at the bottom
        otherBox.getChildren().add(new Text(""));

        display.setLayoutY(0);
        display.setAlignment(Pos.CENTER);
        display.getChildren().clear();
        display.getChildren().addAll(topBox, otherBox);
        display.setSpacing(60);

        displayPane.setContent(display);
        displayPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        displayPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        stage.setScene(statsScene);
    }

    public void clearPanes(Pane[] panes) {
        for (Pane pane : panes)
            pane.getChildren().clear();
    }

    public enum FileType {
        IMAGE,
        SERIALIZABLE,
        OTHER
    }
}
