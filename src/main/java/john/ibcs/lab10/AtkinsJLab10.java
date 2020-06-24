package main.java.john.ibcs.lab10;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AtkinsJLab10 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Pane rootNode = new Pane();
		Scene scene = new Scene(rootNode, 300, 300);
        Text text = new Text(110, 75,"");
        Button buttonTop = new Button(), buttonBottom = new Button();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Two Buttons Using JavaFX");

		buttonTop.setText("Java");
		buttonTop.setLayoutX(60);
		buttonTop.setLayoutY(60);
		buttonTop.setOnAction(event -> text.setText(text.getText().equals("") ?"Make Java Great Again!" : ""));


		buttonBottom.setText("ON");
		buttonBottom.setLayoutX(60);
		buttonBottom.setLayoutY(120);
		buttonBottom.setOnAction(event -> buttonBottom.setText(buttonBottom.getText().equals("ON") ? "OFF" : "ON"));

		rootNode.getChildren().addAll(buttonTop, buttonBottom, text);


		primaryStage.show();
	}
}
