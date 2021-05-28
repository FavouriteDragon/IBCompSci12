package main.java.john.ibcs34.automata;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

//Your grid is not overlayed correctly smh
public class AtkinsJAutomata3 extends Application {

  public static void main(String[] args) {
    System.out.println("Did you know that you can edit the update interval, " +
        "shape, width, and grid size of cells and the ant?");
    System.out.println("-c for Cell Size (individual cells). Pass an integer.");
    System.out.println("-s for Grid Size (size of overall pane). Pass an " +
        "integer.");
    System.out.println("-t for Update Interval (how fast the program runs). " +
        "Pass an Integer.");
//    System.out.println("-h for whether to make the grid use hexagons instead " +
//        "of squares. Pass a Boolean.");
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

  //Util methods
  private static Boolean getBoolFromString(String input) {
    boolean b;
    try {
      b = Boolean.parseBoolean(input);
    } catch (NullPointerException | NumberFormatException e) {
      b = false;
    }
    return b;
  }

  public Image getImage(String fileName) {
    FileInputStream input;
    Image image = null;
    try {
      input = new FileInputStream("src/main/resources/" + fileName +
          ".png");
      //We need to be able to resize the image so we have to convert to
      // imageview
      image = new Image(input);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return image;
  }

  public Paint[] getColourArray() {
    return new Paint[]{
        Color.WHITE, Color.RED, Color.CORNSILK, Color.CORAL, Color.ORANGE,
        Color.YELLOW, Color.LIGHTGOLDENRODYELLOW, Color.YELLOWGREEN,
        Color.GREEN, Color.TURQUOISE, Color.AZURE,
        Color.BLUE, Color.INDIGO, Color.PURPLE, Color.VIOLET, Color.PINK,
        Color.FUCHSIA, Color.BROWN, Color.BLACK
    };
  }

  public Paint[] getColourArray(int length) {
    Paint[] array = new Paint[length];
    for (int i = 0; i < getColourArray().length; i++)
      array[i] = getColourArray()[i];

    return array;
  }

  //This assumes that the directions are in order!
  public LinkedHashMap<Paint, Direction> getColourDirections(Direction[] directions) {
    LinkedHashMap<Paint, Direction> directionMap = new LinkedHashMap<>();
    for (int i = 0; i < directions.length; i++) {
      directionMap.put(getColour(i), directions[i]);
    }

    return directionMap;
  }

  public Paint getColour(int location) {
    return getColourArray()[location];
  }

  public Direction[] directionsFromString(String string) {
    Direction[] directions = new Direction[string.length()];
    for (int i = 0; i < string.length(); i++) {
      directions[i] = directionFromChar(String.valueOf(string.charAt(i)));
    }

    return directions;
  }

  //Works for single-letter string characters
  public Direction directionFromChar(String string) {
    switch (string) {
      case "N":
        return Direction.N;
      case "R":
        return Direction.R;
      case "S":
        return Direction.S;
      case "M":
        return Direction.M;
      case "L":
        return Direction.L;
      case "U":
        return Direction.U;
    }
    return Direction.N;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Pane inputRoot = new Pane();
    Text title = new Text("Ant Direction Controller");
    TextField inputField = new TextField("Please enter directions.");
    Button inputSet = new Button("Confirm Directions");

    title.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD,
        FontPosture.ITALIC, Font.getDefault().getSize()));

    Scale scale = new Scale();
    scale.setX(scale.getX() * 1.5);
    scale.setY(scale.getY() * 1.5);

    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);
    box.setLayoutX(45);
    box.setLayoutY(20);
    box.setSpacing(20);
    box.getChildren().addAll(title, inputField, inputSet);

    inputRoot.getTransforms().add(scale);
    inputRoot.getChildren().addAll(box);

    Scene inputScene = new Scene(inputRoot, 400, 400);
    primaryStage.setScene(inputScene);
    primaryStage.setTitle("Langston's Ant");

    inputSet.setOnAction(press -> {
      Map<Paint, Direction> antMap =
          getColourDirections(directionsFromString(inputField.getText()));

      //Variables for everything. Abstraction station.
      int cellSize = 6;
      int gridSize = 71;
      int updateInterval = 20;
      //White is 0, block is 1.
      int[][] generations;
      LinkedList<Polygon> hexes = new LinkedList<>();

      Parameters parameters = getParameters();
      List<String> rawArguments = parameters.getRaw();


      for (int i = 0; i < rawArguments.size(); i++) {
        String raw = rawArguments.get(i);
        if (raw.contains("-c"))
          cellSize = getInt(rawArguments.get(i + 1));
        if (raw.contains("-s"))
          gridSize = getInt(rawArguments.get(i + 1));
        if (raw.contains("-t"))
          updateInterval = getInt(rawArguments.get(i + 1));
//      if (raw.contains("-h"))
//        hexagon = getBool(rawArguments.get(i + 1));
      }

      //Canvas shiz
      Pane root = new Pane();

      Canvas base = new Canvas(cellSize * gridSize + gridSize,
          cellSize * gridSize + gridSize);
      Canvas grid = new Canvas(cellSize * gridSize + gridSize,
          cellSize * gridSize + gridSize);

      generations = new int[(int) grid.getWidth()][(int) grid.getHeight()];

      Ant ant = new Ant();
      placeAnt(ant, (int) grid.getHeight());

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
      //Hexagon time??
      //TODO: Store squares in array vs drawing them like this/use layers

      double hexSize = cellSize, v = Math.sqrt(3) / 2.0;
      int sceneSize = gridSize * cellSize + gridSize - cellSize;
      for (double y = -cellSize; y < sceneSize; y += hexSize * Math.sqrt(3)) {
        for (double x = -cellSize, dy = y; x < sceneSize; x += (3.0 / 2.0) * hexSize) {
          Polygon tile = new Polygon();
          tile.getPoints().addAll(x, dy,
              x + hexSize, dy,
              x + hexSize * (3.0 / 2.0), dy + hexSize * v,
              x + hexSize, dy + hexSize * Math.sqrt(3),
              x, dy + hexSize * Math.sqrt(3),
              x - (hexSize / 2.0), dy + hexSize * v);
          tile.setFill(getColour(0));
          tile.setStrokeWidth(2);
          tile.setStroke(Color.BLACK);
          dy = dy == y ? dy + hexSize * v : y;
          hexes.add(tile);
        }
      }

      root.getChildren().addAll(hexes);

      //Shows the stage
      Scene master = new Scene(root, cellSize * gridSize + gridSize,
          cellSize * gridSize + gridSize);
      primaryStage.setScene(master);

      //Actual update logic
      int finalGridSize = (int) grid.getHeight();
      //Lambdas are wack and don't let you just use gridSize, so you just use a
      // temp variable. Same for cellSize.
      int finalCellSize = cellSize;
      //Sets all generation cells to 0 in case of bugs
      setGenerations(generations);
      //Displays the ant
      //displayAnt();

      //KeyFrame animation
      KeyFrame frame = new KeyFrame(Duration.millis(updateInterval), event -> {
        if (ant.getX() >= finalGridSize - 1 || ant.getY() >= finalGridSize - 1
            || ant.getX() == 0 || ant.getY() == 0) {
          placeAnt(ant, finalGridSize);
        }
        //Updates the ant
        //updateAnt(gridCtx, ant, generations, finalCellSize);
        //Displays
        //displayAnt(gridCtx, ant, generations, finalCellSize);

      });
      Timeline timeline = new Timeline(frame);
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
    });
    primaryStage.show();

  }

  public void setGenerations(int[][] generations) {
    for (int[] generation : generations) {
      Arrays.fill(generation, 0);
    }
  }

  public void placeAnt(Ant ant, int gridSize) {
    ant.setX(gridSize / 2);
    ant.setY(gridSize / 2);
    ant.setDirection(Direction.N);
  }

  public void displayAnt(List<Polygon> hexes, Ant ant, Map<Paint,
      Direction> antMap) {

  }

  public void updateAnt(List<Polygon> hexes, Ant ant,
                        Map<Paint, Direction> antMap) {

  }

  public void updateHexfield(List<Polygon> hexes, int[][] generations) {

  }

  public void rotateAnt(Direction direction) {

  }

  public void moveAnt(Ant ant, int cellSize) {
    ant.setPrevX(ant.getX());
    ant.setPrevY(ant.getY());

    int moveX = cellSize;
    int moveY = cellSize;
    switch (ant.getDirection()) {
      case N:
        switch (ant.getPrevDirection()) {
          case N:
            break;
        }
        case
    }

    ant.setX(ant.getX() + moveX);
    ant.setY(ant.getY() + moveY);
  }

  public int getInt(String arg) {
    return getNumFromString(arg).intValue();
  }

  public boolean getBool(String arg) {
    return getBoolFromString(arg);
  }

  enum Direction {
    //No Change
    N,
    //60 clockwise
    R,
    //120 clockwise
    S,
    //120 anticlockwise
    L,
    //180
    U,
    //60 anticlockwise
    M
  }

  public class Ant {

    private int x, y;
    private int prevX, prevY;
    private Direction direction;
    private Direction prevDir;
    //In degrees. Uses a unit circle: 0 is right, 90 is up, 180 is left, 270
    // is down, 360 is right.
    private int orientation;
    private int prevOrient;

    public Ant() {
      this.x = 0;
      this.y = 0;
      this.direction = Direction.N;
      this.prevX = 0;
      this.prevY = 0;
      this.prevDir = Direction.N;
      this.orientation = 0;
      this.prevOrient = 0;
    }

    public Ant(int x, int y) {
      this.x = x;
      this.y = y;
      this.direction = Direction.N;
      this.prevX = 0;
      this.prevY = 0;
      this.prevDir = Direction.N;
      this.orientation = 0;
      this.prevOrient = 0;
    }

    public Ant(int x, int y, Direction direction) {
      this.x = x;
      this.y = y;
      this.direction = direction;
      this.prevX = 0;
      this.prevY = 0;
      this.prevDir = Direction.N;
      this.orientation = 0;
      this.prevOrient = 0;
    }

    public int getX() {
      return this.x;
    }

    public void setX(int x) {
      this.x = x;
    }

    public int getPrevX() {
      return this.prevX;
    }

    public void setPrevX(int x) {
      this.prevX = x;
    }

    public int getY() {
      return this.y;
    }

    public void setY(int y) {
      this.y = y;
    }

    public int getPrevY() {
      return this.prevY;
    }

    public void setPrevY(int y) {
      this.prevY = y;
    }

    public Direction getDirection() {
      return this.direction;
    }

    public void setDirection(Direction direction) {
      this.direction = direction;
    }

    public Direction getPrevDirection() {
      return this.prevDir;
    }

    public void setPrevDirection(Direction direction) {
      this.prevDir = direction;
    }

    public void setOrientation(int degrees) {
      this.orientation = degrees;
    }

    public int getOrientation() {
      return this.orientation;
    }

    public void setPrevOrient(int degrees) {
      this.prevOrient = degrees;
    }

    public int getPrevOrientation() {
      return this.prevOrient;
    }
  }

}
