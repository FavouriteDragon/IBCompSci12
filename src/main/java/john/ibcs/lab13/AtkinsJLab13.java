package main.java.john.ibcs.lab13;

import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	}
}
