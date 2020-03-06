package main.java.john.ibcs.lab11;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class AtkinsJLab11 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scale scale = new Scale(1, 1);
        Text text = new Text(70, 50, "This is drawn on the canvas");
        Line line = new Line(5, 5, 185, 185);

        Circle filledCircle = new Circle(35, 35, 23),
                transparentCircle = new Circle(185, 185, 100);

        Rectangle transparentRectangle = new Rectangle(20, 160, 40, 180),
                filledRectangle = new Rectangle(100, 300, 275, 40);

        Pane objectPane = new Pane(), buttonPane = new Pane();
        VBox root = new VBox();

        Button scaleBtn = new Button("Scale"),
                colourBtn = new Button("Colour");



        primaryStage.setTitle("Colour and Scale");

        transparentCircle.setFill(null);
        transparentCircle.setStroke(Color.BLACK);


        transparentRectangle.setFill(null);
        transparentRectangle.setStroke(Color.BLACK);

        objectPane.getTransforms().add(scale);
        objectPane.getChildren().addAll(filledCircle, text, transparentCircle, line, transparentRectangle, filledRectangle);

        scaleBtn.setOnAction(event -> {
            scale.setX(scale.getX() * 2);
            scale.setY(scale.getY() * 2);
            if (scale.getX() > 1) {
                scale.setX(scale.getX() / 8);
                scale.setY(scale.getY() / 8);
            }
        });

        scaleBtn.setLayoutX(150);
        scaleBtn.setLayoutY(25);

        colourBtn.setOnAction(event -> {
            //figures out which colour to fill
            Paint currentColour = ((Shape) objectPane.getChildren().get(0)).getFill();
            if (currentColour.equals(Color.BLACK))
                currentColour = Color.RED;
            else if (currentColour.equals(Color.RED))
                currentColour = Color.BLUE;
            else if (currentColour.equals(Color.BLUE))
                currentColour = Color.GREEN;
            else
                currentColour = Color.BLACK;


            int size = objectPane.getChildren().size();
            for (int i = 0; i < size; i++) {
                Shape object = (Shape) objectPane.getChildren().get(i);
                if (object.getFill() == null)
                    object.setStroke(currentColour);
                else
                    object.setFill(currentColour);

            }
        });

        colourBtn.setLayoutX(250);
        colourBtn.setLayoutY(25);

        buttonPane.getChildren().addAll(scaleBtn, colourBtn);

        root.getChildren().addAll(objectPane, buttonPane);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

}
