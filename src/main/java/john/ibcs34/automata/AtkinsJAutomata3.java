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
import javafx.scene.effect.Glow;
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

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

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
        Color.WHITE, Color.RED, Color.CORAL, Color.ORANGE,
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
      }

      //Canvas shiz
      Pane root = new Pane();

      Canvas base = new Canvas(cellSize * gridSize + gridSize,
          cellSize * gridSize + gridSize);
      Canvas grid = new Canvas(cellSize * gridSize + gridSize,
          cellSize * gridSize + gridSize);

      Ant ant = new Ant();
      Polygon centre = new Polygon();

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

      //x and y iterations. 2 different kinds of hexagon rows.
      int xi = 0, yi = 0;

      double hexSize = cellSize, trigMult = Math.sqrt(3) / 2.0;
      int sceneSize = gridSize * cellSize + gridSize - cellSize;
      //Creates and fills up array of hexagons
      for (double y = -cellSize; y < sceneSize; y += hexSize * trigMult * 2) {
        for (double x = -cellSize; x < sceneSize; x += (3.0 / 2.0) * hexSize) {
          if (yi == 0)
            xi++;
        }
        yi++;
      }

      Polygon[][] hexagonField = new Polygon[yi + 1][xi + 1];
      generations = new int[yi + 1][xi + 1];

      int yPos = 0, xPos = 0;
      for (double y = -cellSize; y < sceneSize; y += hexSize * trigMult * 2) {
        for (double x = -cellSize, dy = y; x < sceneSize; x += (3.0 / 2.0) * hexSize) {
          //Points are clockwise
          Polygon tile = new Polygon();
          tile.getPoints().addAll(x, dy,
              x + hexSize, dy,
              x + hexSize * (3.0 / 2.0), dy + hexSize * trigMult,
              x + hexSize, dy + hexSize * trigMult * 2,
              x, dy + hexSize * trigMult * 2,
              x - (hexSize / 2.0), dy + hexSize * trigMult);
          tile.setFill(getColour(0));
          tile.setStrokeWidth(2);
          tile.setStroke(Color.BLACK);

          if (y / sceneSize > 0.495 && y / sceneSize < 0.51 && x / sceneSize > 0.495 && x / sceneSize < 0.51)
            centre = tile;
          dy = dy == y ? dy + hexSize * trigMult : y;
          hexes.add(tile);

          if (xPos >= xi)
            xPos = 0;
          else xPos++;

          hexagonField[yPos][xPos] = tile;
        }
        yPos++;
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
      //Places the ant
      placeAnt(ant, centre);
      ant.setStartHex(centre);
      //Displays the ant
      displayAnt(hexes, ant, antMap);

      //KeyFrame animation
      Polygon finalCentre = centre;
      KeyFrame frame = new KeyFrame(Duration.millis(updateInterval), event -> {
        if (ant.getX() >= finalGridSize - 1 || ant.getY() >= finalGridSize - 1
            || ant.getX() == 0 || ant.getY() == 0) {
          placeAnt(ant, finalCentre);
        }

        //HOW IT WORKS: CHANGE TO NEXT COLOUR IN SEQUENCE, ROTATE BASED ON
        // PREVIOUS COLOUR
        //Updates the ant
        updateHexfield(hexes, ant, generations, antMap);
        updateAnt(hexes, ant, antMap, finalCellSize, generations);
        //Displays
        displayAnt(hexes, ant, antMap);

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

  public void placeAnt(Ant ant, Polygon centre) {
    ant.getPrevHex().setStroke(Color.BLACK);
    ant.setX(getCentreFromHex(centre)[0]);
    ant.setY(getCentreFromHex(centre)[1]);
    ant.setHex(centre);
    ant.setDirection(Direction.N);
  }

  public void displayAnt(List<Polygon> hexes, Ant ant, Map<Paint,
      Direction> antMap) {
    ant.getHex().setEffect(new Glow());
    ant.getHex().setStroke(Color.PURPLE);
  }

  public void updateAnt(LinkedList<Polygon> hexes, Ant ant,
                        Map<Paint, Direction> antMap, int cellSize,
                        int[][] generations) {
    ant.getPrevHex().setStroke(Color.BLACK);
    Polygon hex = ant.getHex();
    //If the hex is null I have other issues; todo print out an error message
    try {
      //Rotates the ant
      rotateAnt(ant, ant.getDirection());
      moveAnt(ant, cellSize, hexes, generations);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }

  }

  public void updateHexfield(List<Polygon> hexes, Ant ant, int[][] generations,
                             Map<Paint, Direction> antMap) {
    //Sets the direction here
    ant.setDirection(antMap.get(ant.getHex().getFill()));
    toggleHexes(ant, antMap, generations);
    ant.getHex().setFill(getColour(generations[(int) ant.getX()]
        [(int) ant.getY()]));
  }

  public void toggleHexes(Ant ant, Map<Paint, Direction> antMap,
                          int[][] generations) {
    //It's errorring (erroring?) cause it's negative. Why, good sir?
    try {
      generations[(int) ant.getX()][(int) ant.getY()]++;
      if (generations[(int) ant.getX()][(int) ant.getY()] >= antMap.size())
        generations[(int) ant.getX()][(int) ant.getY()] = 0;
    } catch (ArrayIndexOutOfBoundsException e) {
      placeAnt(ant, ant.getStartHex());
    }
  }

  public void rotateAnt(Ant ant, Direction direction) {
    ant.setPrevOrient(ant.getOrientation());
    System.out.println(direction);
    switch (direction) {
      case N:
        break;
      //Anti clockwise
      case M:
        ant.setOrientation(ant.getOrientation() + 60);
        break;
      case L:
        ant.setOrientation(ant.getOrientation() + 120);
        break;
      case U:
        ant.setOrientation(ant.getOrientation() + 180);
        break;
      case S:
        ant.setOrientation(ant.getOrientation() - 120);
        break;
      case R:
        ant.setOrientation(ant.getOrientation() - 60);
        break;
    }
  }

  public void moveAnt(Ant ant, int cellSize, LinkedList<Polygon> hexes,
                      int[][] generations) {
    //Ant only ever needs to move 2 side lengths to reach the corner of the
    // next hex
    ant.setPrevX(ant.getX());
    ant.setPrevY(ant.getY());
    ant.getPrevHex().setStroke(Color.BLACK);

    //notes if moving directly across hexagons:
    //Up is -2 y layers.
    //Down is +2 y layers.
    //Diagonal down left is -1 x, -1 y
    //Diagonal up left is -1 x, -1 y
    //Diagonal up right is +1 x, -1 y
    //Diagonal down right is +1 x, + 1 y



    double angle = ant.getOrientation() + 90;
    //No more 30 degree angles here!
    if (!(ant.getDirection().equals(Direction.N) ||
        ant.getDirection().equals(Direction.U))) {
      if (angle % 30 == 0 && angle % 60 != 0)
        if (angle < 0)
          angle -= 30;
        else if (angle > 0)
          angle += 30;
    }
    System.out.println("Angle: " + angle);

    //Trig due to hexes
    //Need to fix?
    //Width is 2 * cellSize
    double moveX = (cellSize * 2 * Math.cos(Math.toRadians(angle)));
    // + 90)));
    //In this case, negative is up, and we want the ant to start facing up
    //Height is cellSize * Math.sqrt(3) / 2
    double moveY = (cellSize * Math.sqrt(3) / 2 * Math.sin
        (Math.toRadians(angle))) * -1;
    ant.setX(ant.getX() + moveX);
    ant.setY(ant.getY() + moveY);

    Polygon hexToSet = null;
    for (Polygon hex : hexes) {
      if (hexToSet == null) {
        Point antPos = new Point((int) ant.getX(), (int) ant.getY());
        //Points on the hexagon
        Point a = new Point(), b = new Point(), c = new Point(),
            d = new Point(), e = new Point(), f = new Point();
        a = fillPoint(hex, a, 0);
        b = fillPoint(hex, b, 1);
        c = fillPoint(hex, c, 2);
        d = fillPoint(hex, d, 3);
        e = fillPoint(hex, e, 4);
        f = fillPoint(hex, f, 5);
        if (hex != ant.getHex() && isPointInHexagon(antPos, a, b, c, d, e, f)) {
          hexToSet = hex;
          ant.setPrevHex(ant.getHex());
          ant.setHex(hexToSet);
          ant.setX(getCentreFromHex(hexToSet)[0]);
          ant.setY(getCentreFromHex(hexToSet)[1]);
        }
      }
    }

    boolean outOfBounds = false;
    try {
      //Tests to see whether the ant is out of bounds
      getColour(generations[(int)
          getCentreFromHex(ant.getHex())[0]][(int)
          getCentreFromHex(ant.getHex())[1]]);
    } catch (ArrayIndexOutOfBoundsException e) {
      outOfBounds = true;
    }
    if (outOfBounds)
      placeAnt(ant, ant.getStartHex());
  }

  /**
   * @param hexagon
   * @param point
   * @param pointOrder The point on the hexagon. Starts at 0, goes to 5.
   * @return
   */
  public Point fillPoint(Polygon hexagon, Point point, int pointOrder) {
    point.setLocation(hexagon.getPoints().get(pointOrder * 2),
        hexagon.getPoints().get(pointOrder * 2 + 1));
    return point;
  }

  //Points go x, y, x2, y2, e.t.c like how they're put in
  public double[] getCentreFromHex(Polygon hex) {
    double[] pos = new double[2];
    double[] xCoords = new double[2];
    double[] yCoords = new double[2];
    //First 2 x coordinates can be divided for the width.
    //First and last y coordinates can be divided for the height.
    for (int i = 0; i < hex.getPoints().size(); i++) {
      if (i == 0)
        xCoords[i] = hex.getPoints().get(i);
      else if (i == 1)
        yCoords[0] = hex.getPoints().get(i);
      else if (i == 2)
        xCoords[1] = hex.getPoints().get(i);
      else if (i == 11)
        yCoords[1] = hex.getPoints().get(i);

    }

    //We want to add and not subtract. While subtracting would find the exact,
    //per-hex size coord, we want the coordinates relative to the grid, and
    // I'm too lazy to redo this.
    double xAvg = xCoords[0] + xCoords[1];
    xAvg /= 2;
    pos[0] = xAvg;

    double yAvg = yCoords[0] + yCoords[1];
    yAvg /= 2;
    pos[1] = yAvg;

    return pos;
  }

  //Methods for whether a point is in a hexagon

  // Determines the distance between point1 and point2
  public double distance(Point point1, Point point2) {
    return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y,
        2));
  }

  // Calculates the midpoint on the segment between point1 and point2
  public Point midpoint(Point point1, Point point2) {
    return new Point((point1.x + point2.x) / 2, (point1.y + point2.y) / 2);
  }

  // Determines on which side of the line between lineA and lineB the given point is.
  public double sign(Point point, Point lineA, Point lineB) {
    return (point.x - lineB.x) * (lineA.y - lineB.y) - (point.y - lineB.y) * (lineA.x - lineB.x);
  }

  // Check if the given point is in the triangle made up of points A, B and C
  public boolean isPointInTriangle(Point point, Point triangleA,
                                   Point triangleB, Point triangleC) {
    double distanceAB = sign(point, triangleA, triangleB);
    double distanceBC = sign(point, triangleB, triangleC);
    double distanceAC = sign(point, triangleC, triangleA);

    boolean hasNegativeSign = (distanceAB < 0) || (distanceBC < 0) || (distanceAC < 0);
    boolean hasPositiveSign = (distanceAB > 0) || (distanceBC > 0) || (distanceAC > 0);

    return !(hasNegativeSign && hasPositiveSign); // XOR
  }

  // Check if the given point is in the hexagon made up of points A, B, C, D, E and F
  public boolean isPointInHexagon(Point point, Point hexagonA, Point hexagonB,
                                  Point hexagonC,
                                  Point hexagonD, Point hexagonE, Point hexagonF) {
    // Determine hexagon circles
    Point centre = midpoint(hexagonA, hexagonD);
    double outerRadius = distance(centre, hexagonA);
    double innerRadius = distance(centre, midpoint(hexagonA, hexagonB));

    // Circle check
//    double distanceToCentre = distance(point, centre);
//    if (distanceToCentre > outerRadius) return false;
//    if (distanceToCentre <= innerRadius) return true;
    //Note: While this is more correct, for the purposes of our lab, we're
    // gonna cheat:
    double distanceToCentre = distance(point, centre);
    if (distanceToCentre > outerRadius)
      return false;
    if (distanceToCentre <= innerRadius)
      return true;

    // Determine closest points
    List<Map.Entry<Point, Double>> distances = new LinkedList(Arrays.asList
        (hexagonA, hexagonB, hexagonC, hexagonD, hexagonE, hexagonF).stream()
        .collect(Collectors.toMap(hexagonPoint -> hexagonPoint, hexagonPoint ->
            distance(point, hexagonPoint))).entrySet());
    distances.sort(Map.Entry.comparingByValue());

    // Optional
    if (distances.get(0).getValue().equals(distances.get(1).getValue()))
      return false;

    // Triangle check
    return isPointInTriangle(point, distances.get(0).getKey(), distances.get(1).getKey(), distances.get(2).getKey());
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

    //TODO: Possibly store a polygon in the ant? Help me
    private double x, y;
    private double prevX, prevY;
    private Direction direction;
    private Direction prevDir;
    //In degrees. Uses a unit circle: 0 is right, 90 is up, 180 is left, 270
    // is down, 360 is right.
    private int orientation;
    private int prevOrient;
    private Polygon hex;
    private Polygon prevHex;
    private Polygon startHex;

    public Ant() {
      this.x = 0;
      this.y = 0;
      this.direction = Direction.N;
      this.prevX = 0;
      this.prevY = 0;
      this.prevDir = Direction.N;
      this.orientation = 0;
      this.prevOrient = 0;
      this.hex = new Polygon();
      this.prevHex = new Polygon();
      this.startHex = new Polygon();
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
      this.hex = new Polygon();
      this.prevHex = new Polygon();
      this.startHex = new Polygon();
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
      this.hex = new Polygon();
      this.prevHex = new Polygon();
      this.startHex = new Polygon();
    }

    public double getX() {
      return this.x;
    }

    public void setX(double x) {
      this.x = x;
    }

    public double getPrevX() {
      return this.prevX;
    }

    public void setPrevX(double x) {
      this.prevX = x;
    }

    public double getY() {
      return this.y;
    }

    public void setY(double y) {
      this.y = y;
    }

    public double getPrevY() {
      return this.prevY;
    }

    public void setPrevY(double y) {
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

    public int getOrientation() {
      //Want the ant to be default facing up
      return this.orientation;
    }

    public void setOrientation(int degrees) {
      this.orientation = degrees;
      if (orientation >= 360)
        orientation = 0;
      else if (orientation <= -360)
        orientation = 0;
    }

    public void setPrevOrient(int degrees) {
      this.prevOrient = degrees;
    }

    public int getPrevOrientation() {
      return this.prevOrient;
    }

    public Polygon getHex() {
      return this.hex;
    }

    public void setHex(Polygon hex) {
      this.hex = hex;
    }

    public Polygon getPrevHex() {
      return this.prevHex;
    }

    public void setPrevHex(Polygon hex) {
      this.prevHex = hex;
    }

    public Polygon getStartHex() {
      return this.startHex;
    }

    public void setStartHex(Polygon hex) {
      this.startHex = hex;
    }
  }

}
