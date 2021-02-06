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
import java.util.Scanner;


public class StudentWindow extends Application {

  //You know what this does
  public static void main(String[] args) {
    launch(args);
  }


  /**
   * @param classRoom the ClassRoom to serialize/turn into an object file.
   */
  public static void serializeStudents(ClassRoom classRoom) {

    //Initializes OutputStream s
    FileOutputStream fileStream = null;
    ObjectOutputStream objectStream = null;

    try {
      //Creates an Object file, name doesn't matter as long as it's consistent.
      fileStream = new FileOutputStream(new File("ObjectStorage").
          getPath());
      objectStream = new ObjectOutputStream(fileStream);
      objectStream.writeObject(classRoom);

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

  /**
   * Same as the above method but used for exporting as an object file.
   *
   * @param classRoom the ClassRoom to serialize/turn into an object file.
   * @param fileName  The file name of the Object file to serialize to.
   */
  public static void serializeStudents(ClassRoom classRoom, String fileName) {

    //Initializes OutputStream s
    FileOutputStream fileStream = null;
    ObjectOutputStream objectStream = null;

    try {
      //Creates an Object file, name doesn't matter as long as it's consistent.
      fileStream = new FileOutputStream(new File(fileName).getPath());
      objectStream = new ObjectOutputStream(fileStream);
      objectStream.writeObject(classRoom);

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

  /**
   * @param filename The name of the Object file to deserialize from.
   * @return Returns a ClassRoom from an Object file.
   */
  public static ClassRoom deserializeStudents(String filename) {

    //Initializes a ClassROom and appropriate InputStream objects.
    ClassRoom classRoom = null;

    FileInputStream fileStream = null;
    ObjectInputStream objectStream = null;

    //Attempts to read in the file
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
    File file = new File(fileName.contains(".txt") ? fileName : fileName +
        ".txt");
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
   * @param fileName Name of the file. Include the extension, and the path if
   *                 necessary.
   * @return ClassRoom Returns the ClassRoom object from the given file
   * location.
   */
  public static ClassRoom readFile(String fileName) {
    String line;

    //Reads the length of the file
    Student[] students = new Student[readFileLength(fileName)];
    int i = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      while ((line = br.readLine()) != null) {
        //Parses the student from the line and assigns it to the array
        students[i] = parseStudentFromLine(line);
        i++;
      }

    } catch (IOException exc) {
      System.out.println("No file found based on file name.");
      exc.printStackTrace();
    }
    return new ClassRoom(students);

  }

  /**
   * @param line The String line in a file.
   * @return A Student parsed from the line.
   */
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

  /**
   * Used for creating an array of students that's the correct length.
   *
   * @param fileName The name of the file.
   * @return The amount of lines in the file as an integer.
   */
  public static int readFileLength(String fileName) {
    int i = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      while (br.readLine() != null) {
        i++;
      }

    } catch (IOException exc) {
      System.out.println("That file doesn't exist");
      exc.printStackTrace();
    }
    return i;
  }


  /**
   * @param size     The size to cap the array to.
   * @param students The initial array of students to cap.
   * @return A new array of students capped to the size passed.
   * <p>
   * Although this method could technically be void due to it editing an array,
   * I find it's better to make it return an array of Students[], to provide
   * consistency
   * and reduce confusion elsewhere.
   */
  public static Student[] capArraySize(int size, Student[] students) {
    Student[] newArray = new Student[size];
    int i = 0;

    while (i < size) {
      newArray[i] = students[i];
      i++;
    }

    return newArray;
  }

  //Same thing but for Panes.
  public static Pane[] capArraySize(int size, Pane[] panes) {
    Pane[] newArray = new Pane[size];
    int i = 0;

    while (i < size) {
      newArray[i] = panes[i];
      i++;
    }

    return newArray;
  }


  /**
   * @param fileName The name of the file to check.
   * @param type     The enum FileType of the file you want it to be.
   * @return Whether or not the file matches the FileType desired.
   */
  public static boolean isValid(String fileName, FileType type) {

    if (type.equals(FileType.IMAGE)) {
      //Creates a temporary object that's just used to test whether it can
      //create a FileStream in the directory provided.
      FileInputStream input;
      try {
        input = new FileInputStream("src/main/resources/" + fileName +
            ".png");
      } catch (FileNotFoundException e) {
        return false;
      }
    } else if (type.equals(FileType.SERIALIZABLE)) {
      //Same thing- creates objects that are just used to see whether
      //InputStream objects can be created at the given location.
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
      //This method is used for reading txt files, so it just attempts that.
      //If it fails then obviously the file isn't a txt file.
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      } catch (IOException exc) {
        return false;
      }
    }

    return true;
  }

  //This is an absolute mess. I'm sorry.
  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Student Lab");

    Button
        //Student Editor
        sort, export, stats, add, delete, toTitle,
        //Title
        create, importFile, load, save,
        //For Importing/Exporting students
        confirm, cancel, exportConfirm, exportCancel;
    TextField   //General
        search, exportField;
    Text
        title, loadError,
        masterTitle, mainMenuMessage;

    VBox
        //Overall layout of the title screen
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
        //fadeMainMenu can be used for all fade messages in the main menu
        onUpdate, fadeError, fadeMainMenu;
    AnimationTimer
        //Used for updating the main screen. Honestly, this is just a
        // work-around for properties;
        //if I was to do this lab again, I wouldn't use timers.
        //Bad practice and hacky
        updateStudents;

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
    //Starts out as "Saved!"
    mainMenuMessage = new Text("Saved!");
    loadError = new Text("No Saved Progress");

    exportField = new TextField("Enter Export File Name");
    search = new TextField("Enter File Path");

    launchTitle(vTitleLayout, hTitleLayout, titleScene, primaryStage, title,
        create, importFile, load);

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
    //TODO: Abstractify this further so I don't have to manually create a
    // timer per text I want to fade.
    //These timers make the message text fade from their colour to white.
    // Looks pretty nice.
    fadeError = new Timer(5, action -> {
      loadError.setOpacity(loadError.getOpacity() - 0.0075F);
      if (loadError.getOpacity() < 0) {
        loadError.setStroke(Color.WHITE);
      }
    });

    //Fades messages on the main menu (Save, Export, Sort).
    fadeMainMenu = new Timer(5, action -> {
      mainMenuMessage.setOpacity(mainMenuMessage.getOpacity() - 0.0075F);
      if (mainMenuMessage.getOpacity() < 0) {
        mainMenuMessage.setStroke(Color.WHITE);
      }

    });

    //Restarts the main menu and updates the student pane. There's a better
    //way to do this but eh.
    updateStudents = new AnimationTimer() {
      @Override
      public void handle(long now) {
        launchMain(vMasterLayout, vStudents, vStudentEditLayout,
            hMasterLayout, masterScene, studentEditScene,
            primaryStage, masterTitle, studentPane, currentStudents, this,
            false, add,
            delete, export, stats, save, toTitle, sort);
        this.stop();
      }
    };

    onUpdate = new Timer(2, action -> {
      cleanTimers(fadeError, loadError);
      cleanTimers(fadeMainMenu, mainMenuMessage);
    });


    //This edits the buttons, launches the title screen.
    //Note: I've noticed that closing the GUI still leaves the program
    // running- possibly because this isn't stopped?
    //Another reason not to use timers.
    onUpdate.start();

    //Title Screen Buttons
    create.setOnAction(event -> {
      currentStudents.clearStudents();
      launchMain(vMasterLayout, vStudents, vStudentEditLayout, hMasterLayout,
          masterScene, studentEditScene,
          primaryStage, masterTitle, studentPane, currentStudents,
          updateStudents, false, add,
          delete, export, stats, save, toTitle, sort);

    });

    //Loads in students from a saved Object file.
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
        loadedStudents.addStudents(deserializeStudents("ObjectStorage"));
        currentStudents.clearStudents();
        currentStudents.addStudents(loadedStudents);
        launchMain(vMasterLayout, vStudentEditLayout, vStudents,
            hMasterLayout, masterScene, studentEditScene,
            primaryStage, masterTitle, studentPane, currentStudents,
            updateStudents, false, add,
            delete, export, stats, save, toTitle, sort);
      }
      if (!vTitleLayout.getChildren().contains(loadError)) {
        vTitleLayout.getChildren().add(loadError);
        vTitleLayout.setLayoutY(vTitleLayout.getLayoutY() +
            loadError.getScaleY() * 14);
      }
    });


    //Imports a file (object or txt)
    importFile.setOnAction(event -> launchReadFile(primaryStage, confirm,
        cancel, search));

    //Confirm button for importing
    confirm.setOnAction(event -> {
      //Variables in lambdas have to be final. So, you just clear everything
      // then add students.
      if (isValid(search.getText(), FileType.SERIALIZABLE)) {
        currentStudents.clearStudents();
        currentStudents.addStudents(deserializeStudents(search.getText()));
        launchMain(vMasterLayout, vStudents, vStudentEditLayout,
            hMasterLayout, masterScene, studentEditScene,
            primaryStage, masterTitle, studentPane, currentStudents,
            updateStudents, false, add,
            delete, export, stats, save, toTitle, sort);
      } else if (isValid(search.getText(), FileType.OTHER)) {
        currentStudents.clearStudents();
        currentStudents.addStudents(readFile(search.getText()));
        launchMain(vMasterLayout, vStudents, vStudentEditLayout,
            hMasterLayout, masterScene, studentEditScene,
            primaryStage, masterTitle, studentPane, currentStudents,
            updateStudents, false, add,
            delete, export, stats, save, toTitle, sort);
      }

    });

    //Cancel just brings us back to the title scene; cancel for importing.
    cancel.setOnAction(event -> switchScene(titleScene, primaryStage));

    //Master Screen
    save.setOnAction(event -> {
      serializeStudents(currentStudents);
      mainMenuMessage.setText("Saved!");
      mainMenuMessage.setStroke(Color.GREEN);
      mainMenuMessage.setScaleY(2);
      mainMenuMessage.setScaleX(2);
      mainMenuMessage.setOpacity(1.0F);

      fadeMainMenu.restart();

      if (!hMasterLayout.getChildren().contains(mainMenuMessage))
        hMasterLayout.getChildren().add(mainMenuMessage);
    });

    //Export it!
    export.setOnAction(event -> {
      launchWriteFile(primaryStage, exportConfirm, exportCancel, exportField);
    });

    //Export screen
    exportConfirm.setOnAction(event -> {
      if (isValid(exportField.getText(), FileType.OTHER))
        writeTextFile(currentStudents, exportField.getText());
      else serializeStudents(currentStudents, exportField.getText());
      switchScene(masterScene, primaryStage);
      //Success message
      mainMenuMessage.setText("Exported!");
      mainMenuMessage.setStroke(Color.BLUE);
      mainMenuMessage.setScaleY(2);
      mainMenuMessage.setScaleX(2);
      mainMenuMessage.setOpacity(1.0F);

      fadeMainMenu.restart();
      if (!hMasterLayout.getChildren().contains(mainMenuMessage))
        hMasterLayout.getChildren().add(mainMenuMessage);
    });

    //Button for cancelling the export
    exportCancel.setOnAction(event -> switchScene(masterScene, primaryStage));

    //Statistics!
    stats.setOnAction(event -> displayStatistics(masterTitle, statsScene,
        statsPlane, vStatsLayout, primaryStage,
        currentStudents, updateStudents));

    //Sorting
    sort.setOnAction(event -> {
      if (Arrays.equals(currentStudents.getStudents(),
          currentStudents.getSortByLastName()))
        currentStudents.sortByID();
      else currentStudents.sortByLastName();
      updateTimer(updateStudents);

      mainMenuMessage.setText("Sorted!");
      mainMenuMessage.setStroke(Color.CYAN);
      mainMenuMessage.setScaleY(2);
      mainMenuMessage.setScaleX(2);
      mainMenuMessage.setOpacity(1.0F);

      fadeMainMenu.restart();
      if (!hMasterLayout.getChildren().contains(mainMenuMessage))
        hMasterLayout.getChildren().add(mainMenuMessage);

    });

    //Back to Title Screen. I need to make this at the top.
    toTitle.setOnAction(event -> {
      switchScene(titleScene, primaryStage);
    });

    //Add a Student.
    add.setOnAction(event -> {
      createStudent(studentScene, masterScene, vStudentLayout, primaryStage,
          currentStudents, updateStudents);
    });

    //Delete a Student.
    delete.setOnAction(event -> {
      launchMain(vMasterLayout, vStudents, vStudentEditLayout, hMasterLayout,
          masterScene, studentEditScene, primaryStage, masterTitle, studentPane,
          currentStudents, updateStudents, true, add, delete,
          export, stats, save, toTitle, sort);
    });


    primaryStage.show();
  }

  /**
   * @param fadeError The timer being used to clear the text.
   * @param fadeText  The text being faded.
   */
  public void cleanTimers(Timer fadeError, Text fadeText) {
    if (fadeText.getOpacity() <= 0F) {
      fadeText.setStroke(Color.WHITE);
      fadeError.stop();
    }
  }

  //Self explanatory; stops and starts the timer.
  public void updateTimer(AnimationTimer timer) {
    timer.stop();
    timer.start();
  }

  //Once each scene is properly loaded you only need to switch between them.
  public void switchScene(Scene scene, Stage stage) {
    stage.setScene(scene);
  }

  /**
   * @param vBox    The controller for the title screen.
   * @param hBox    The main HBox in the title screen.
   * @param scene   The Scene for the title screen.
   * @param stage   The stage being used the entire JavaFX thread.
   * @param title   Title text.
   * @param buttons Array of buttons to be used in the title screen.
   */
  public void launchTitle(VBox vBox, HBox hBox, Scene scene, Stage stage,
                          Text title, Button... buttons) {
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

      Text author = new Text("Made by John.");
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

  /**
   * @param vBox         The main vBox for the main menu.
   * @param students     The box used to display students.
   * @param studentBox   The box used to display the student editor.
   * @param hBox         The primary HBox in the main menu.
   * @param scene        The scene being used for the main menu.
   * @param studentScene The scene used for display the Student editor.
   * @param stage        The stage being used in the JavaFX thread.
   * @param title        The title of the main menu.
   * @param scrollPane   The ScrollPane used to display the students.
   * @param classRoom    The ClassRoom which contains the students.
   * @param timer        The timer used to refresh the student display.
   * @param delete       Whether or not the student display should show the
   *                     delete
   *                     button.
   * @param buttons      Other miscellaneous buttons.
   */
  public void launchMain(
      VBox vBox, VBox students, VBox studentBox, HBox hBox, Scene scene,
      Scene studentScene, Stage stage, Text title, ScrollPane scrollPane,
      ClassRoom classRoom, AnimationTimer timer, boolean delete,
      Button... buttons
  ) {
    //I'm using a scale to try and make sure that if I need to adjust the
    // sizes of things later
    // I can just change the scale. I haven't done this consistently, though.
    Scale scale = new Scale(20, 20);

    //Overall scroll pane, then a second one.
    ScrollPane studentPane = new ScrollPane();

    //Box for the top two buttons
    HBox topBox = new HBox();
    topBox.setAlignment(Pos.TOP_CENTER);
    topBox.setSpacing(scale.getX() * 5);
    topBox.setLayoutX(scale.getX() * 5);


    //Panes used to display each student in the ScrollPane
    Pane[] studentPanes = new Pane[classRoom.getStudents().length];

    vBox.setAlignment(Pos.CENTER);
    vBox.setAlignment(Pos.CENTER);
    hBox.setAlignment(Pos.CENTER);

    //Adjusting the title text
    title.setFont(Font.font("System", FontPosture.ITALIC, 10));
    title.setStroke(Color.RED);
    title.setFill(Color.WHITE);
    title.setStrokeWidth(0.5);
    title.setScaleX(scale.getX() / 5);
    title.setScaleY(scale.getY() / 5);

    //studentPane = the ScrollPane being used to display all the students;
    // confusing, I know.
    studentPane.setScaleX(scale.getX() / 20);
    studentPane.setScaleY(scale.getY() / 20);

    displayStudentPanes(classRoom, studentPane, students, studentBox,
        studentScene, scene,
        studentPanes, stage, timer, delete);

    students.setSpacing(scale.getY() / 4);
    students.setAlignment(Pos.TOP_CENTER);

    //Pane used to show students
    studentPane.setContent(students);
    studentPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    studentPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

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
          //Blue
          button.setStyle("-fx-background-color: #76D2FF");
        } else {
          //Orangey red
          button.setStyle("-fx-background-color: #FFCD76");
        }
      } else {
        //Pure red
        button.setStyle("-fx-background-color: #ff0000");
      }
      button.setTextFill(Color.WHITE);
      l++;
    }
    //Due to a bigger pane I no longer need that whacky dunamic button support.
    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(scale.getX() * 2.5);
    buttonBox.getChildren().addAll(buttons);

    vBox.setSpacing(scale.getY() * 2);
    hBox.setSpacing(scale.getX() * 4);

    //Basically ensures that things aren't added twice, also allows for
    // updating this
    topBox.getChildren().clear();
    vBox.getChildren().clear();

    //Reverse the order so add is on the right and delete is on the left.
    topBox.getChildren().addAll(buttons[1], buttons[0]);

    //Adds everything to the main vBox
    vBox.getChildren().addAll(topBox, title, studentPane, hBox);
    vBox.getChildren().addAll(buttonBox);
    //Just ensures that there's blank space at the bottom; there's probably a
    // better method but eh
    vBox.getChildren().addAll(new Text(""));
    vBox.setLayoutY(-250 + scale.getY() * (vBox.getChildren().size() + 1));


    scrollPane.setContent(vBox);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

    if (!stage.getScene().equals(scene))
      stage.setScene(scene);
  }

  //Displays each student pane in the overall ScrollPane

  /**
   * @param room          The ClassRoom to display.
   * @param studentPane   The ScrollPane to display.
   * @param controller    The overall VBox that controls the display.
   * @param studentEditor The VBox to be used for the student editor display.
   * @param scene         The scene used to display the student editor.
   * @param masterScene   The master scene used for the main menu.
   * @param studentPanes  The array of panes used to store each student.
   * @param stage         The stage used in the JavaFX Thread.
   * @param timer         The timer used to refresh the student display.
   * @param delete        Whether or not to display the delete button.
   */
  public void displayStudentPanes(
      ClassRoom room, ScrollPane studentPane, VBox controller,
      VBox studentEditor, Scene scene, Scene masterScene, Pane[] studentPanes,
      Stage stage, AnimationTimer timer, boolean delete
  ) {
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
    searchField.setLayoutX(220);
    topPane.getChildren().clear();
    topPane.getChildren().addAll(search, searchField);

    //Iterates over all of the ClassRoom's students
    int i = 0;
    for (Student student : room.getStudents()) {
      studentPanes[i] = new Pane();
      //This is for action events
      Pane pane = studentPanes[i];
      names[i] = new Text(student.getFullName());
      grades[i] = new Text(String.valueOf(student.getYear()));
      genders[i] = new Text(student.getGender());
      ids[i] = new Text(String.valueOf(student.getID()));

      //Name
      names[i].setLayoutX(15);
      names[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

      //Year
      grades[i].setLayoutX(200);
      grades[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

      //ID
      ids[i].setLayoutX(320);
      ids[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

      //Gender
      genders[i].setLayoutX(480);
      genders[i].setLayoutY(studentPanes[i].getLayoutY() + 20);

      //Whether or not to display the Delete button. Red.
      if (delete) {
        editButtons[i] = new Button("Delete");
        editButtons[i].setStyle("-fx-background-color: #FF0000");
        editButtons[i].setOnAction(event -> {
          deleteStudent(room, student, studentPanes, pane);
          updateTimer(timer);
        });

      } else {
        //Else display the Edit button. Blue.
        editButtons[i] = new Button("Edit");
        editButtons[i].setStyle("-fx-background-color: #1BCCE4");
        editButtons[i].setOnAction(event -> editStudent(scene, masterScene,
            studentEditor, stage,
            student, timer));
      }

      editButtons[i].setTextFill(Color.WHITE);
      editButtons[i].setAlignment(Pos.CENTER_RIGHT);
      editButtons[i].setLayoutX(545);
      editButtons[i].setLayoutY(studentPanes[i].getLayoutY() + 5);

      studentPanes[i].getChildren().clear();
      studentPanes[i].getChildren().addAll(names[i], grades[i], genders[i],
          ids[i], editButtons[i]);

      //What the heck is this, java???? This is cursed
      studentPanes[i].setBorder(new Border(new BorderStroke(Color.CYAN,
          BorderStrokeStyle.SOLID,
          new CornerRadii(5), BorderWidths.DEFAULT)));
      i++;
    }


    searchField.setOnAction(event -> {
      //Moves the students
      for (int j = 0; j < room.getStudents().length; j++) {
        Student student = room.getStudents()[j];
        if (!(searchField.getText().equals(" ") ||
            searchField.getText().isEmpty())) {
          //Else ifs ensure that things are properly unhighlighted.

          if (student.getFullName().contains(searchField.getText()))
            room.moveStudentsToFront(student);

          else if (student.getFirstName().contains(searchField.getText()))
            room.moveStudentsToFront(student);

          else if (student.getLastName().contains(searchField.getText()))
            room.moveStudentsToFront(student);

          else if (student.getGender().contains(searchField.getText()))
            room.moveStudentsToFront(student);

          else if (String.valueOf(student.getYear()).
              contains(searchField.getText()))
            room.moveStudentsToFront(student);

          else if (String.valueOf(student.getID()).
              equals(searchField.getText()))
            room.moveStudentsToFront(student);

        }
      }
      //Redisplays them
      displayStudentPanes(room, studentPane, controller, studentEditor, scene
          , masterScene, studentPanes,
          stage, timer, delete);

      //Visual Display (highlighting)
      for (int j = 0; j < room.getStudents().length; j++) {
        Student student = room.getStudents()[j];
        studentPanes[j].setBackground(Background.EMPTY);
        //For some reason Background.EMPTY doesn't work. Great.
        Background toSet = new Background(new BackgroundFill(Color.LIGHTGREEN
            , new CornerRadii(5),
            new Insets(0)));
        if (!(searchField.getText().equals(" ") ||
            searchField.getText().isEmpty())) {
          //Else ifs ensure that things are properly unhighlighted.

          if (student.getFullName().contains(searchField.getText()))
            studentPanes[j].setBackground(toSet);

          else if (student.getFirstName().contains(searchField.getText()))
            studentPanes[j].setBackground(toSet);

          else if (student.getLastName().contains(searchField.getText()))
            studentPanes[j].setBackground(toSet);

          else if (student.getGender().contains(searchField.getText()))
            studentPanes[j].setBackground(toSet);

          else if (String.valueOf(student.getYear()).
              contains(searchField.getText()))
            studentPanes[j].setBackground(toSet);

          else if (String.valueOf(student.getID()).
              equals(searchField.getText()))
            studentPanes[j].setBackground(toSet);

        }
      }
    });

    controller.getChildren().clear();
    controller.getChildren().add(topPane);
    controller.getChildren().addAll(studentPanes);
    studentPane.setContent(controller);
  }

  public void launchReadFile(Stage stage, Button confirm, Button cancel,
                             TextField field) {
    VBox vbox = new VBox();
    HBox buttonBox = new HBox();
    Scale scale = new Scale(1, 1);
    Text error = new Text("Please enter a valid file name.");

    scale.setX(scale.getX() * 20);
    scale.setY(scale.getY() * 20);

    field.setMaxHeight(scale.getX() / 2);
    field.setOnAction(event -> {
      if (isValid(field.getText(), FileType.OTHER) || isValid(field.getText()
          , FileType.SERIALIZABLE)) {
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

  public void launchWriteFile(Stage stage, Button confirm, Button cancel,
                              TextField field) {
    VBox vbox = new VBox();
    HBox buttonBox = new HBox();
    Scale scale = new Scale(1, 1);
    Text error = new Text("Please enter a valid file name.");

    scale.setX(scale.getX() * 20);
    scale.setY(scale.getY() * 20);

    field.setMaxHeight(scale.getX() / 2);

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
      input = new FileInputStream("src/main/resources/" + fileName +
          ".png");
      Image image = new Image(input);
      imageView = new ImageView(image);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    imageView.setFitHeight(50);
    imageView.setFitWidth(50);
    return imageView;
  }

  /**
   * @param scene        The scene to display for creating a student (similar to
   *                     editing a student).
   * @param masterScene  The scene to switch back to for the main menu.
   * @param controller   The VBox that controls the create student display.
   * @param stage        The display used in the JavaFX thread.
   * @param room         The ClassRoom to be added to.
   * @param masterUpdate Timer used to refresh the main menu's student display.
   */
  public void createStudent(Scene scene, Scene masterScene, VBox controller,
                            Stage stage, ClassRoom room,
                            AnimationTimer masterUpdate) {
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
      room.addStudents(new Student(studentFields[0].getText(),
          studentFields[1].getText(),
          studentFields[2].getText(),
          Integer.parseInt(studentFields[3].getText()),
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

  public void editStudent(Scene scene, Scene masterScene, VBox controller,
                          Stage stage, Student student,
                          AnimationTimer masterUpdate) {
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
      student.setFullName(studentFields[0].getText() + " " +
          studentFields[1].getText());
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

    stage.setScene(new Scene(controller, 600, 600));
  }

  public void deleteStudent(ClassRoom classRoom, Student student,
                            Pane[] studentPanes, Pane pane) {

    classRoom.removeStudents(student);

    LinkedList<Pane> paneList = new LinkedList<>(Arrays.asList(studentPanes));

    for (Pane allPanes : studentPanes) {
      paneList.removeIf(toRemove -> allPanes.equals(toRemove)
          && toRemove.equals(pane));
    }

    int i = 0;
    for (Pane toAdd : paneList) {
      studentPanes[i] = toAdd;
      i++;
    }
    studentPanes = StudentWindow.capArraySize(paneList.size(), studentPanes);

  }

  public void displayStatistics(Text title, Scene statsScene,
                                ScrollPane displayPane, VBox display,
                                Stage stage,
                                ClassRoom room, AnimationTimer timer) {
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
        pane.setBorder(new Border(new BorderStroke(Color.CYAN,
            BorderStrokeStyle.SOLID,
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

  //Clears all panes in an array.
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
