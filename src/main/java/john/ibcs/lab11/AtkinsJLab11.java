package main.java.john.ibcs.lab11;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AtkinsJLab11 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Creates the flow plane, scene, and buttons
        FlowPane rootNode = new FlowPane();
        Scene scene = new Scene(rootNode, 400, 400);
        Button colourButton = new Button(), scaleButton = new Button();
        Line line = new Line(100, 10, 10, 10);
        Rectangle filledRect = new Rectangle(), emptyRect = new Rectangle();
        Circle circle = new Circle();

        double circlePosX = scene.getHeight() / 2, circlePosY = scene.getHeight() / 2;
        double scale = 1;
        int[] colours = new int[3];
        colours[0] = colours[1] = colours[2] = 0;

        circle.setCenterX(circlePosX * scale);
        circle.setCenterY(circlePosY * scale);
        circle.setRadius(100 * scale);
        circle.setFill(Color.TRANSPARENT);


        rootNode.setAlignment(Pos.CENTER);
        rootNode.layout();
        primaryStage.setScene(scene);

        colourButton.setText("Colour");
        colourButton.setAlignment(Pos.BOTTOM_CENTER);
        colourButton.setTranslateX(-20);
        scaleButton.setText("Scale");
        scaleButton.setAlignment(Pos.BOTTOM_CENTER);
        scaleButton.setTranslateX(20);

        //Event handlers
        //Colours
        circle.setStroke(Color.rgb(colours[0], colours[1], colours[2]));
        line.setStroke(Color.rgb(colours[0], colours[1], colours[2]));


        rootNode.getChildren().addAll(colourButton, scaleButton, circle);
        primaryStage.show();


    }
}
