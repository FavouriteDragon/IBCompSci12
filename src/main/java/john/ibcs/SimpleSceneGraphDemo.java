package main.java.john.ibcs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class SimpleSceneGraphDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Title
        primaryStage.setTitle("Demonstrate a Simple Scene");
        //FlowPane
        FlowPane rootNode = new FlowPane();
        //Create a scene
        Scene scene = new Scene(rootNode, 300, 200);
        //Set the scene on the stage
        primaryStage.setScene(scene);
        //Label time
        Label label = new Label("A simple JavaFX label.");
        //Add the label to the scene
        rootNode.getChildren().add(label);
        //Show everything
        primaryStage.show();
    }

}
