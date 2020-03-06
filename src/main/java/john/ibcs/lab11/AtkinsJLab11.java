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
        Scale scale;
        Circle fillInCircle, transparentCircle;
        Text text;
        Line line;
        Rectangle transparentRectangle, fillInRectangle;
        Pane objectPane, buttonPane;
        Button scaleBtn, colorBtn;
        VBox root;

        primaryStage.setTitle("Color and Scale");

        scale = new Scale(1, 1);

        fillInCircle = new Circle(35, 35, 23);

        text = new Text(70, 50, "This is drawn on the canvas");

        transparentCircle = new Circle(185, 185, 100);
        transparentCircle.setFill(null);
        transparentCircle.setStroke(Color.BLACK);

        line = new Line(5, 5, 185, 185);

        transparentRectangle = new Rectangle(20, 160, 40, 180);
        transparentRectangle.setFill(null);
        transparentRectangle.setStroke(Color.BLACK);

        //225, 40
        fillInRectangle = new Rectangle(100, 300, 275, 40);

        objectPane = new Pane();
        objectPane.getTransforms().add(scale);
        objectPane.getChildren().addAll(fillInCircle, text, transparentCircle, line, transparentRectangle, fillInRectangle);

        scaleBtn = new Button("Scale");
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

        colorBtn = new Button("Color");
        colorBtn.setOnAction(event -> {
            //figures out which color to fill
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

        colorBtn.setLayoutX(250);
        colorBtn.setLayoutY(25);

        buttonPane = new Pane();
        buttonPane.getChildren().addAll(scaleBtn, colorBtn);

        root = new VBox();
        root.getChildren().addAll(objectPane, buttonPane);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

}
