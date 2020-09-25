package main.java.john.ibcs34.students.labtests;
/*

Author: April Hsu
Date: 9/13/20

*/

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

//ClassName.
public class Test extends Application {

    //If you don't camelCase this file I will find you and systematically blow up all of your code.

    //Method names are *always* camelCase, not UpperCase.
    public static StudentClass SortFile(StudentClass myClass, String sortType) {
        //Just use the primitive data type. Please. boolean, not Boolean.
        Boolean done = false;
        int i;
        int test;

        //No. Never ever ever. Always, always use .equals() instead of == for comparing strings/objects.
        //Only use == for primitive data types or specific instances of custom objects, never strings.
        //Changed to sortType.equals().
        if (sortType.equals("Last Name")) {

            while (!done) { //sorts students by last name using bubble sort
                done = true;

                //Don't directly call the variable! Use getters and setters! E.g students.getStudents(), which returns an array of students.
                for (i = 0; i < myClass.myStudent.length - 1; i++) {

                    test = myClass.myStudent[i].lastname.compareToIgnoreCase(myClass.myStudent[i + 1].lastname);

                    if (test > 0) {

                        Student temp = myClass.myStudent[i];
                        myClass.myStudent[i] = myClass.myStudent[i + 1];
                        myClass.myStudent[i + 1] = temp;
                        done = false;
                    }
                }
            }
        } ///AHHHH NO == ! It's a string! Use .equals()!
        else if (sortType == "Student ID") {

            while (!done) { //uses bubble sort to sort students by student number
                done = true;

                for (i = 0; i < myClass.myStudent.length - 1; i++) {

                    if (myClass.myStudent[i].studentnumber > myClass.myStudent[i + 1].studentnumber) {
                        Student temp = myClass.myStudent[i];
                        myClass.myStudent[i] = myClass.myStudent[i + 1];
                        myClass.myStudent[i + 1] = temp;
                        done = false;
                    }
                }
            }
        }
        return myClass; //returns sorted StudentClass
    }//SortFile

    //camelCase! Should be checkInput.
    //Primitive not object!
    public static Boolean CheckInput(String studentID, String grade, String gender) {
        //Use the primitive data type!
        Boolean validStudent = true;
        int test;

        try { //checks to see if given student ID is long
            Long.parseLong(studentID);

        } catch (NumberFormatException nfe) {
            validStudent = false;
        }

        try { //checks to see if given grade is int
            Integer.parseInt(grade);

        } catch (NumberFormatException nfe) {
            validStudent = false;
        }

        //tests to see if given gender is either male or female
        test = gender.compareToIgnoreCase("male");

        if (test != 0) {
            test = gender.compareToIgnoreCase("female");

            if (test != 0) {
                validStudent = false;
            }
        }

        return validStudent; //returns boolean deciding whether given info is valid
    }//CheckInput

    //camelCase!
    public static ObservableList<String> FillClassList(StudentClass myClass) {
        ObservableList<String> classOList = FXCollections.observableArrayList();

        //AAHHHH! Use getters and setters! Don't direct variable call!
        for (int i = 0; i < myClass.myStudent.length; i++) { //converts student to string
            //Again this is unnecessarily complicated. Look at what I did- StringBuilders are cool, you should use them.
            String temp = "";
            temp += String.valueOf(myClass.myStudent[i].studentnumber);
            temp += "    \t\t";
            temp += String.valueOf(myClass.myStudent[i].grade);
            temp += "\t\t";
            temp += myClass.myStudent[i].gender;
            temp += "\t\t";
            temp += myClass.myStudent[i].lastname;
            temp += "  ";
            temp += myClass.myStudent[i].firstname;
            classOList.add(temp);
        }
        return classOList; //returns observable list of StudentClass
    }//FillClassList

    //camelCase!
    public static void WriteObjFile(StudentClass myClass) {

        try { //writes StudentClass as object file
            //Bruh the file name doesn't really matter as long as it's consistent, but it still hurts me
            FileOutputStream fos = new FileOutputStream("classlist_object.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myClass);
            fos.close();
            oos.close();
        } catch (IOException exc) {
            System.out.println("Write Error");
        }
    }//WriteObjFile

    //camelCase. You're giving me depression.
    public static StudentClass ReadObjFile() {
        StudentClass temp = null;

        try { //reads object file for StudentClass
            FileInputStream fis = new FileInputStream("classlist_object.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (StudentClass) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException exc) {

            System.out.println("Read Error");

        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return temp;
    }//ReadObjFile

    //camelCase.
    public static Boolean WriteNormalFile(StudentClass myClass, String sortType) {
        int i;
        //Primitive data types! Not the objects themselves!
        Boolean success = true;

        try (FileWriter fw = new FileWriter("classlist_sorted_" + sortType + ".txt")) { //writes sorted StudentClass to file

            for (i = 0; i < myClass.myStudent.length; i++) {
                fw.write(String.valueOf(myClass.myStudent[i].studentnumber));
                fw.write(" ");
                fw.write(String.valueOf(myClass.myStudent[i].grade));
                fw.write(" ");
                fw.write(myClass.myStudent[i].lastname);
                fw.write(" ");
                fw.write(myClass.myStudent[i].firstname);
                fw.write(" ");
                fw.write(myClass.myStudent[i].gender);
                fw.write(" \n");
            }
        } catch (IOException exc) { //in case something goes wrong

            System.out.println("Write Error");

            success = false;
        }
        return success; //returns boolean stating succes of writing file
    }//WriteNormalFile

    //;-; you know what you did.
    public static Student[] ReadStartFile(File userFile) {
        int numofstudents = 0;
        int classSize = 0;
        int studentNumber;
        int grade;
        String lastName = "";
        String firstName = "";
        String gender = "";
        Student[] myStudent;

        try { //finds out how many students there are in user file
            Scanner lineScan = new Scanner(userFile);

            while (lineScan.hasNextLine()) {
                classSize++;
                lineScan.nextLine();
            }

            lineScan.close();
        } catch (IOException exc) {
            classSize = 100;
        }

        myStudent = new Student[classSize];

        try {//reads in user file and fills student array

            Scanner fileScan = new Scanner(userFile);

            while (fileScan.hasNext()) {

                studentNumber = fileScan.nextInt();
                grade = fileScan.nextInt();
                lastName = fileScan.next();
                firstName = fileScan.next();
                gender = fileScan.next();
                myStudent[numofstudents] = new Student(studentNumber, grade, lastName, firstName, gender);
                numofstudents++;
            }
        } catch (IOException exc) {//in case file doesn't open

            System.out.println("Read Error");

        }
        return myStudent;//returns filled array
    }//ReadStartFile

    //AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH. Put this at the beginning or at the end. Putting this before start() is fine,
    //but there shouldn't  be other methods beneath i!
    public static void main(String[] args) {

        //starts Java application
        launch(args);
    }

    @Override
    public void start(Stage myStage) {

        //set up window
        myStage.setTitle("Select Class File");
        GridPane root = new GridPane();
        root.setVgap(5);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 200);

        //file doesn't exist
        Label noFile = new Label();

        //user enters file path
        Label prompt = new Label("File Path:");
        prompt.setFont(new Font("Arial", 16));
        TextField chooseFile = new TextField();
        Button search = new Button();
        search.setText("Search");
        //lambdas are a thing. use "-> () { }" instead of "new EventHandler<ActionEvent>() { }"
        search.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                File temp = new File(chooseFile.getText());

                if (temp.exists()) {
                    SetStage(temp);
                    myStage.close();
                } else {
                    noFile.setText("Couldn't Find File");
                    noFile.setFont(new Font("Arial", 16));
                }
            }
        });

        //display window
        root.add(prompt, 0, 0);
        root.add(chooseFile, 1, 0);
        root.add(search, 2, 0);
        root.add(noFile, 1, 1);
        myStage.setScene(scene);
        myStage.show();
    }//start

    //Sigh.
    public void SetStage(File userFile) {
        //Ok unless this is static and final, make it camelCase.
        double WIDTH = 120;

        //set up file display
        StudentClass myClass = new StudentClass(ReadStartFile(userFile));
        WriteObjFile(myClass);
        ListView<String> classLView = new ListView<String>(FillClassList(myClass));
        MultipleSelectionModel<String> classLViewModel = classLView.getSelectionModel();

        //set up window
        Stage setStage = new Stage();
        setStage.setTitle("Class Information");
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, 600, 600);
        HBox top = new HBox(20);
        top.setPadding(new Insets(10, 15, 10, 15));
        border.setTop(top);
        VBox right = new VBox(30);
        right.setPadding(new Insets(5, 15, 15, 15));
        border.setRight(right);
        border.setCenter(classLView);

        //sort List
        Label labelSort = new Label("Sort By:");
        labelSort.setFont(new Font("Arial", 16));
        labelSort.setPadding(new Insets(0, -10, 0, 0));
        ChoiceBox<String> sort = new ChoiceBox<>(FXCollections.observableArrayList("Original", "Last Name", "Student ID"));
        sort.setValue("Original");
        sort.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number old_val, Number new_val) { //sorts class list according to selected value and updates display
                String sortType = sort.getItems().get((Integer) new_val);
                StudentClass temp = SortFile(ReadObjFile(), sortType);
                classLView.getItems().clear();
                classLView.setItems(FillClassList(temp));
            }
        });

        //download file
        Button choose = new Button();
        choose.setText("Download File");
        choose.setMaxWidth(WIDTH);
        choose.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //downloads displayed class list as file
                String sortType = sort.getValue();
                StudentClass temp = SortFile(ReadObjFile(), sortType);
                Boolean success = WriteNormalFile(temp, sort.getValue());
                DownloadStage(success);
            }
        });

        //help
        Button help = new Button();
        help.setText("Help");
        help.setMaxWidth(WIDTH);
        help.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //opens help window
                HelpStage();
            }
        });

        //edit
        Button edit = new Button();
        edit.setText("Edit Info");
        edit.setMaxWidth(WIDTH);
        edit.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //edits student info and updates display
                String editStudent = "";
                int index;
                String sortType = sort.getValue();

                if (classLView.getSelectionModel().getSelectedIndex() != -1) {
                    editStudent = classLView.getSelectionModel().getSelectedItems().toString();
                    editStudent = editStudent.substring(1, editStudent.length() - 1);
                    EditStudent(editStudent, ReadObjFile());
                    StudentClass temp = SortFile(ReadObjFile(), sortType);
                    classLView.getItems().clear();
                    classLView.setItems(FillClassList(temp));
                } else {
                    SelectStudentStage();
                }
            }
        });

        //add
        Button add = new Button();
        add.setText("Add Student");
        add.setMaxWidth(WIDTH);
        //Lambdas
        add.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //adds student and updates display
                String sortType = sort.getValue();
                AddStudent(ReadObjFile());
                StudentClass temp = SortFile(ReadObjFile(), sortType);
                classLView.getItems().clear();
                classLView.setItems(FillClassList(temp));
            }
        });

        //delete
        Button delete = new Button();
        delete.setText("Delete Student");
        delete.setMaxWidth(WIDTH);
        delete.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //deletes student and updates display
                String deleteStudent = "";
                int index = -1;
                String sortType = sort.getValue();

                if (classLView.getSelectionModel().getSelectedIndex() != -1) {
                    deleteStudent = classLView.getSelectionModel().getSelectedItems().toString();
                    deleteStudent = deleteStudent.substring(1, deleteStudent.length() - 1);
                    DeleteStudent(deleteStudent, ReadObjFile());
                    StudentClass temp = SortFile(ReadObjFile(), sortType);
                    classLView.getItems().clear();
                    classLView.setItems(FillClassList(temp));
                } else {
                    SelectStudentStage();
                }
            }
        });

        //summary
        Button summary = new Button();
        summary.setText("Class Summary");
        summary.setMaxWidth(WIDTH);
        //Lambdas
        summary.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { // gives user class summary

                SummaryStage(ReadObjFile());
            }
        });

        //search
        TextField search = new TextField();
        search.setPromptText("Search by student name");
        Label labelSearch = new Label("Search:");
        labelSearch.setFont(new Font("Arial", 16));
        labelSearch.setPadding(new Insets(0, 0, 0, 30));
        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //searches for student based on user entered name and highlights them
                int i;
                int test;
                int selected = 0;
                String readIn = search.getText();
                String sortType = sort.getValue();
                StudentClass myClass = SortFile(ReadObjFile(), sortType);
                String term1 = "";
                String term2 = "";

                classLView.getSelectionModel().clearSelection();

                if (readIn.contains(" ")) {
                    term1 = readIn.substring(0, readIn.indexOf(" "));
                    term2 = readIn.substring(readIn.indexOf(" ") + 1);

                    for (i = 0; i < myClass.myStudent.length; i++) {
                        test = term1.compareToIgnoreCase(myClass.myStudent[i].lastname);

                        if (test == 0) {
                            test = term2.compareToIgnoreCase(myClass.myStudent[i].firstname);
                        } else {
                            test = term1.compareToIgnoreCase(myClass.myStudent[i].firstname);

                            if (test == 0) {
                                test = term2.compareToIgnoreCase(myClass.myStudent[i].lastname);
                            }
                        }

                        if (test == 0) {
                            selected++;
                            classLView.getSelectionModel().select(i);
                            classLView.scrollTo(i);
                        }
                    }
                } else {

                    for (i = 0; i < myClass.myStudent.length; i++) {
                        test = readIn.compareToIgnoreCase(myClass.myStudent[i].lastname);

                        if (test != 0) {
                            test = readIn.compareToIgnoreCase(myClass.myStudent[i].firstname);
                        }

                        if (test == 0) {
                            selected++;
                            classLView.getSelectionModel().select(i);
                            classLView.scrollTo(i);
                        }
                    }
                }

                if (selected != 1) {
                    NoStudentFound(selected);
                }
            }
        });

        //displays window
        top.getChildren().addAll(labelSort, sort, search, searchButton);
        right.getChildren().addAll(choose, summary, edit, add, delete, help);
        setStage.setScene(scene);
        setStage.show();
    }//SetStage

    public void SummaryStage(StudentClass myClass) {
        //Get rid of unused variables.
        int[] grade;
        int boyCount;
        int girlCount;

        //set up window

        //Define as many variables as you can before assigning them, to prevent clutter and improve readability.
        Stage summaryStage = new Stage();
        summaryStage.setTitle("Class Summary");
        VBox root = new VBox(30);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

        //gender
        boyCount = myClass.getGender();
        girlCount = myClass.myStudent.length - boyCount;
        Label boys = new Label("Boys: " + boyCount);
        Label girls = new Label("Girls: " + girlCount);

        //grades
        int[] grades = myClass.getGrades();
        Label ninth = new Label("9th Grade: " + grades[0]);
        Label tenth = new Label("10th Grade: " + grades[1]);
        Label eleventh = new Label("11th Grade: " + grades[2]);
        Label twelveth = new Label("12th Grade: " + grades[3]);

        //display window
        root.getChildren().addAll(boys, girls, ninth, tenth, eleventh, twelveth);
        summaryStage.setScene(scene);
        summaryStage.show();
    }//SummaryStage

    //camelCase
    public void EditStudent(String editStudent, StudentClass myClass) {

        String firstName = "";
        String lastName = "";
        String gender = "";
        String studentID = "";
        String grade = "";
        //Get rid of unused variables
        Long studentIDLong;
        int gradeInt;

        //set up window
        Stage editStage = new Stage();
        editStage.setTitle("Edit Student Info");
        GridPane root = new GridPane();
        root.setVgap(5);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 250);

        //creates column constraint for column 1
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        root.getColumnConstraints().add(column1);

        //getting student info
        studentID = editStudent.substring(0, editStudent.indexOf("    \t\t"));
        editStudent = editStudent.substring(editStudent.indexOf("    \t\t"));
        editStudent = editStudent.trim();
        grade = editStudent.substring(0, editStudent.indexOf("\t\t"));
        editStudent = editStudent.substring(editStudent.indexOf("\t\t"));
        editStudent = editStudent.trim();
        gender = editStudent.substring(0, editStudent.indexOf("\t\t"));
        editStudent = editStudent.substring(editStudent.indexOf("\t\t"));
        editStudent = editStudent.trim();
        lastName = editStudent.substring(0, editStudent.indexOf(" "));
        editStudent = editStudent.substring(editStudent.indexOf(" "));
        editStudent = editStudent.trim();
        firstName = editStudent;
        final Student oldInfo = new Student(Long.parseLong(studentID), Integer.parseInt(grade), lastName, firstName, gender);

        //student ID
        Label IDLabel = new Label("Student ID:");
        TextField IDField = new TextField();
        IDField.setText(studentID);

        //grade
        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();
        gradeField.setText(grade);

        //first name
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setText(firstName);

        //last name
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setText(lastName);

        //gender
        Label genderLabel = new Label("Gender:");
        TextField genderField = new TextField();
        genderField.setText(gender);

        //save Button
        Button save = new Button();
        save.setText("Save Changes");
        //Lambdas
        save.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //checks to see if info entered is valid before editing student info

                if (IDField.getText().isEmpty() || gradeField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || genderField.getText().isEmpty()) {
                    WarningStage();
                } else {
                    Boolean validStudent = CheckInput(IDField.getText(), gradeField.getText(), genderField.getText());

                    if (validStudent) {
                        Student newInfo = new Student(Long.parseLong(IDField.getText()), Integer.parseInt(gradeField.getText()), lastNameField.getText(), firstNameField.getText(), genderField.getText());
                        myClass.edit(oldInfo, newInfo);
                        WriteObjFile(myClass);
                        editStage.close();

                    } else { //if info isn't valid
                        WarningStage();
                    }
                }
            }
        });

        //cancel Button
        Button cancel = new Button();
        cancel.setText("Cancel");
        cancel.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //changes not saved

                editStage.close();
            }
        });

        //add children
        root.add(firstNameField, 1, 0);
        root.add(lastNameField, 1, 1);
        root.add(IDField, 1, 2);
        root.add(gradeField, 1, 3);
        root.add(genderField, 1, 4);
        root.add(firstNameLabel, 0, 0);
        root.add(lastNameLabel, 0, 1);
        root.add(IDLabel, 0, 2);
        root.add(gradeLabel, 0, 3);
        root.add(genderLabel, 0, 4);
        root.add(save, 1, 5);
        root.add(cancel, 0, 5);

        //show stage
        editStage.setScene(scene);
        editStage.showAndWait();
    }//EditStage

    public void AddStudent(StudentClass myClass) {

        //set up window
        Stage addStage = new Stage();
        addStage.setTitle("Add Student");
        GridPane root = new GridPane();
        root.setVgap(10);
        root.setHgap(15);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 250);

        //creates column constraint for column 1
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        root.getColumnConstraints().add(column1);

        //student ID
        Label ID = new Label("Student ID:");
        TextField IDField = new TextField();
        IDField.setPromptText("Enter student ID");

        //grade
        Label grade = new Label("Grade:");
        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter student grade");

        //first name
        Label firstName = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter student first name");

        //last name
        Label lastName = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter student last name");

        //gender
        Label gender = new Label("Gender:");
        TextField genderField = new TextField();
        genderField.setPromptText("Enter student gender");

        //save Button
        Button save = new Button();
        save.setText("Save Changes");
        //Lambdas
        save.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //checks to see if info entered is valid before adding student

                if (IDField.getText().isEmpty() || gradeField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || genderField.getText().isEmpty()) {
                    WarningStage();
                } else {
                    Boolean validStudent = CheckInput(IDField.getText(), gradeField.getText(), genderField.getText());

                    if (validStudent) {
                        Student temp = new Student(Long.parseLong(IDField.getText()), Integer.parseInt(gradeField.getText()), lastNameField.getText(), firstNameField.getText(), genderField.getText());
                        myClass.add(temp);
                        WriteObjFile(myClass);
                        addStage.close();

                    } else { //if info isn't valid
                        WarningStage();
                    }
                }
            }
        });

        //cancel Button
        Button cancel = new Button();
        cancel.setText("Cancel");
        //Lambdas
        cancel.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //new student not added
                addStage.close();
            }
        });

        //display window
        root.add(firstNameField, 1, 0);
        root.add(lastNameField, 1, 1);
        root.add(IDField, 1, 2);
        root.add(gradeField, 1, 3);
        root.add(genderField, 1, 4);
        root.add(firstName, 0, 0);
        root.add(lastName, 0, 1);
        root.add(ID, 0, 2);
        root.add(grade, 0, 3);
        root.add(gender, 0, 4);
        root.add(save, 1, 5);
        root.add(cancel, 0, 5);
        addStage.setScene(scene);
        addStage.showAndWait();
    }//AddStudent

    //camelCase
    public void DeleteStudent(String deleteStudent, StudentClass myClass) {
        long deleteID;

        //set up window
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete Student");
        BorderPane border = new BorderPane();
        VBox center = new VBox(10);
        center.setPadding(new Insets(20, 15, 0, 15));
        center.setAlignment(Pos.CENTER);
        border.setCenter(center);
        HBox bottom = new HBox(15);
        bottom.setPadding(new Insets(0, 15, 50, 15));
        bottom.setAlignment(Pos.CENTER);
        border.setBottom(bottom);
        Scene scene = new Scene(border, 400, 200);

        deleteID = Long.parseLong(deleteStudent.substring(0, deleteStudent.indexOf("    \t\t")));

        //check if delete and shows info of selected student
        Label prompt = new Label("Are you sure you want to delete this student?");
        Label student = new Label(deleteStudent);
        prompt.setFont(new Font("Arial", 16));

        //cancel button
        Button cancel = new Button();
        cancel.setText("Cancel");
        //Lambdas
        cancel.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //student not deleted
                deleteStage.close();
            }
        });

        //confirm button
        Button confirm = new Button();
        confirm.setText("Delete");
        confirm.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //goodbye student
                myClass.delete(deleteID);
                WriteObjFile(myClass);
                deleteStage.close();
            }
        });

        //display window
        center.getChildren().addAll(prompt, student);
        bottom.getChildren().addAll(cancel, confirm);
        deleteStage.setScene(scene);
        deleteStage.showAndWait();
    }//DeleteStudent

    //camelCase
    public void DownloadStage(Boolean success) {

        //set up window
        Stage downloadStage = new Stage();
        downloadStage.setTitle("Download File");
        VBox root = new VBox(20);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 350, 150);

        //displays status of downloaded file
        Label fileStatus = new Label();

        if (success) { //if succesful
            fileStatus.setText("File succesfully downloaded onto desktop");
        } else {//if failed
            fileStatus.setText("File could not be downloaded");
        }
        fileStatus.setFont(new Font("Arial", 16));

        //ok button
        Button ok = new Button();
        ok.setText("Close");
        ok.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {//closes window
                downloadStage.close();
            }
        });

        //display window
        root.getChildren().addAll(fileStatus, ok);
        downloadStage.setScene(scene);
        downloadStage.show();
    }//DownloadStage

    public void HelpStage() {

        //set up window
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        VBox root = new VBox(20);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 480, 150);

        //very helpful instructions
        Label helpful = new Label("Seriously? Everything's pretty self explanatory, you know...");
        helpful.setFont(new Font("Arial", 16));

        //ok button
        Button ok = new Button();
        ok.setText("Close");
        ok.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //closes window
                helpStage.close();
            }
        });

        //display window
        root.getChildren().addAll(helpful, ok);
        helpStage.setScene(scene);
        helpStage.show();
    }//HelpStage

    public void SelectStudentStage() {

        //set up window
        Stage selectStage = new Stage();
        selectStage.setTitle("No Student Selected");
        VBox root = new VBox(20);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 480, 150);

        //warning
        Label noStudent = new Label("Please first select a student from the list");
        noStudent.setFont(new Font("Arial", 16));

        //close button
        Button ok = new Button();
        ok.setText("Close");
        ok.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {//closes window
                selectStage.close();
            }
        });

        //display window
        root.getChildren().addAll(noStudent, ok);
        selectStage.setScene(scene);
        selectStage.show();
    }//SelectStudentStage

    public void WarningStage() {

        //set up window
        Stage warningStage = new Stage();
        warningStage.setTitle("Error");
        VBox root = new VBox(20);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 200);

        //warning
        Label warning = new Label("Please make sure all fields are filled. Student ID and grade should be integers. Gender should say male or female (gender inclusive features to be added at later date).");
        warning.setWrapText(true);

        //close button
        Button ok = new Button();
        ok.setText("Close");
        ok.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) { //closes window
                warningStage.close();
            }
        });

        //display window
        root.getChildren().addAll(warning, ok);
        warningStage.setScene(scene);
        warningStage.show();
    }//WarningStage

    public void NoStudentFound(int selected) {

        //set up window
        Stage noStudentStage = new Stage();
        noStudentStage.setTitle("Student Search");
        VBox root = new VBox(20);
        root.setPadding(new Insets(15, 15, 15, 15));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 200);

        //warning
        Label warning = new Label(""); //displays info on searched for student

        if (selected == 0) {//if no student found
            warning.setText("No students were found. Please make sure you're entering the student's first and/or last name correctly, with no spaces after names unless seperating first and last name.");
        } else {//if more than one student found
            warning.setText("More than one student has this last/first name but not all are highlighted. Please enter both the student's first and last name for more accuracy.");
        }
        warning.setWrapText(true);

        //close button
        Button ok = new Button();
        ok.setText("Close");
        //Lambdas.
        ok.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {//closes window
                noStudentStage.close();
            }
        });

        //display window
        root.getChildren().addAll(warning, ok);
        noStudentStage.setScene(scene);
        noStudentStage.show();
    }//NoStudentFound

} //HsuALab8

