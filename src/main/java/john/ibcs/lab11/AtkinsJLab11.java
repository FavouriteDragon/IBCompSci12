package main.java.john.ibcs.lab11;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        int state1;
        colours[0] = colours[1] = colours[2] = 0;

        circle.setCenterX(circlePosX * scale);
        circle.setCenterY(circlePosY * scale);
        circle.setRadius(100 * scale);
        circle.setFill(Color.TRANSPARENT);

        rootNode.setAlignment(Pos.CENTER);
        rootNode.layout();
        primaryStage.setScene(scene);

        colourButton.setText("Colour");
        colourButton.setMinSize(colourButton.getMinWidth() * scale, colourButton.getMinHeight() * scale);
        colourButton.setTranslateX(scene.getWidth() / 2 - colourButton.getMinWidth());
        colourButton.setTranslateY(150 * scale);
        colourButton.setOnAction(event -> {
            int state = state1 == null ? 0 : state1;
            state = state > 2 ? 0 : state++;
            state1 = state;
            toggleColour(colours, state);
        });

        scaleButton.setText("Scale");
        scaleButton.setMinSize(colourButton.getMinWidth() * scale, colourButton.getMinHeight() * scale);
        scaleButton.setTranslateX(scene.getWidth() / 2 - colourButton.getMinWidth());
        scaleButton.setTranslateY(150 * scale);

        //Event handlers
        //Colours
        circle.setStroke(Color.rgb(colours[0], colours[1], colours[2]));
        line.setStroke(Color.rgb(colours[0], colours[1], colours[2]));


        rootNode.getChildren().addAll(colourButton, scaleButton, circle);
        primaryStage.show();


    }

    private void toggleColour(int[] colours, int state) {
      switch (state) {
          case 0:
              colours[0] = colours[1] = colours[2] = 0;
              break;
          case 1:
              colours[0] = 255;
          case 2:
              colours[0] = 0;
              colours[1] = 255;
              break;
          case 3:
              colours[1] = 0;
              colours[2] = 255;
              break;
          default:
              break;
      }
    }
}