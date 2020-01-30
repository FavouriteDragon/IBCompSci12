package main.java.john.ibcs.lab10;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class AtkinsJLab10 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane rootNode = new FlowPane();
        Scene scene = new Scene(rootNode, 300, 300);
        primaryStage.setScene(scene);
        Button buttonTop = new Button(), buttonBottom = new Button();
        rootNode.setAlignment(Pos.CENTER);

        buttonTop.setText("Java");

        buttonTop.setTranslateX(-53);
        buttonTop.setTranslateY(-78);
        //  buttonTop.setLayoutX(60);
        //  buttonTop.setLayoutY(60);

        buttonTop.setOnAction(event -> System.out.println("Make Java great again!"));


        buttonBottom.setText("ON");
        buttonBottom.setTranslateX(-92);
        buttonBottom.setTranslateY(-18);
        //  buttonBottom.setLayoutX(60);
        //  buttonBottom.setLayoutY(120);

        //For some reason I can't change the layout, and the buttons default to the middle.
        //So, I translated the buttons based on the default coordinates

        buttonBottom.setOnAction(event -> buttonBottom.setText(buttonBottom.getText().equals("ON") ? "OFF" : "ON"));

        primaryStage.setTitle("Two Buttons Using JavaFX");

        buttonTop.layout();
        buttonBottom.layout();
        rootNode.getChildren().addAll(buttonTop, buttonBottom);


        primaryStage.show();
        System.out.println("Java x layout: " + buttonTop.getLayoutX());
        System.out.println("Java y layout: " + buttonTop.getLayoutY());
        System.out.println("ON x layout: " + buttonBottom.getLayoutX());
        System.out.println("ON y layout: " + buttonBottom.getLayoutY());
    }
}
