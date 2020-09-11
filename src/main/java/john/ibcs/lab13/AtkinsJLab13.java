package main.java.john.ibcs.lab13;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.sql.Time;

public class AtkinsJLab13 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		boolean[][] currentGen = new boolean[150][150];
		boolean[][] nextGen = new boolean[150][150];
		Rectangle[][] displayArray = new Rectangle[150][150];

		KeyFrame frame = new KeyFrame(Duration.millis(10),
		e -> {
		});

		Timeline timeline = new Timeline(frame);

		//Stop, play, and next frame buttons

		Canvas canvas = new Canvas();
		primaryStage.setScene(new Scene(new HBox(),300, 300));
		primaryStage.show();
	}
}
