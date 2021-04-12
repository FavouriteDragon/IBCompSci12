package main.java.john.ibcs34.automata;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class AtkinsJAutomata1 extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  //Util methods
  private static Number getNumFromString(String input) {
    double d;
    try {
      d = Double.parseDouble(input);
    } catch (NullPointerException | NumberFormatException e) {
      d = Double.NEGATIVE_INFINITY;
    }
    return d;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    int cellSize = 6;
    int gridSize = 71;
    Parameters parameters = getParameters();
    List<String> rawArguments = parameters.getRaw();


    for (int i = 0; i < rawArguments.size(); i++) {
      String raw = rawArguments.get(i);
      if (raw.contains("-c")) {
        cellSize = getInt(rawArguments.get(i + 1));
      }
      if (raw.contains("-s"))
        gridSize = getInt(rawArguments.get(i + 1));
    }

    primaryStage.setTitle("Automata Shell");

    //Canvas shiz
    Pane root = new Pane();

    Canvas base = new Canvas(cellSize * gridSize + gridSize,
        cellSize * gridSize + gridSize);
    Canvas grid = new Canvas(cellSize * gridSize + gridSize,
        cellSize * gridSize + gridSize);

    root.getChildren().addAll(base, grid);
    grid.toFront();


    //Actual visuals
    GraphicsContext baseCtx = base.getGraphicsContext2D();
    GraphicsContext gridCtx = grid.getGraphicsContext2D();

    baseCtx.setStroke(Color.BLACK);
    baseCtx.setFill(Color.BLACK);

    gridCtx.setFill(Color.BLACK);
    gridCtx.setStroke(Color.BLACK);

    //Horizontal and Vertical Lines
    for (int i = 0; i < gridSize; i++) {
      //Left-Right
      gridCtx.strokeLine(0, i * cellSize, grid.getWidth(),
          i * cellSize);
      //Top-Bottom
      gridCtx.strokeLine(i * cellSize, 0,
          i * cellSize, grid.getHeight());
    }

    //Shows the stage
    Scene master = new Scene(root, cellSize * gridSize + gridSize,
        cellSize * gridSize + gridSize);
    primaryStage.setScene(master);
    primaryStage.show();

  }

  public int getInt(String arg) {
    return getNumFromString(arg).intValue();
  }

}
