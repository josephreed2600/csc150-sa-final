package gameoflife.controllers;

import gameoflife.models.*;
import gameoflife.views.*;
import java.util.concurrent.*;

public class GameOfLife {

  private BoardState currentState;
  private RuleList rules = new RuleList();
  private CellStateList cellStates = new CellStateList();
  private IGoLView view;
  private CellColorList availableColors = new CellColorList();

  private boolean paused = false;
  private boolean quit = false;

  public GameOfLife(IGoLView view) {
    this.view = view;
    this.view.setGame(this);

    // FIXME add ansi colors
    // or don't
    availableColors.add(new CellColor("black", "#000000", ""));
    availableColors.add(new CellColor("white", "#ffffff", ""));

    cellStates.add(new CellState("dead", availableColors.findByName("black")));
    cellStates.add(new CellState("alive", availableColors.findByName("white")));

    Rule identityRule =
        new Rule(
            "identity rule",
            (cell, neighbors) -> {
              return cell;
            });
    Rule inverseRule =
        new Rule(
            "inverse",
            (cell, neighbors) -> {
              switch (cell.toString()) {
                case "alive":
                  cell = cellStates.findByName("dead");
                  break;
                case "dead":
                  cell = cellStates.findByName("alive");
                  break;
              }
              return cell;
            });
    Rule logRule =
        new Rule(
            "log",
            (cell, neighbors) -> {
              System.out.print(cell);
              System.out.print("\t[");
              String sep = "";
              for (CellState n : neighbors) {
                System.out.print(sep);
                System.out.print(n);
                sep = " ";
              }
              System.out.print("]\n");
              return cell;
            });
    Rule classicRule =
        new Rule(
            "classic",
            (cell, neighbors) -> {
              int livingNeighbors = Rule.countState(neighbors, cellStates.findByName("alive"));
              switch (cell.name) {
                case "dead":
                  if (livingNeighbors == 3) {
                    return cellStates.findByName("alive");
                  }
                  return cellStates.findByName("dead");
                case "alive":
                  if (livingNeighbors == 2 || livingNeighbors == 3) {
                    return cellStates.findByName("alive");
                  }
                default:
                  return cellStates.findByName("dead");
              }
            });

    rules.add(classicRule);
    // rules.add(logRule);
  }

  private void advance() {
    BoardState nextState = new BoardState(currentState.rows, currentState.cols);
    for (int r = 0; r < currentState.rows; r++)
      for (int c = 0; c < currentState.cols; c++)
        nextState.setCell(
            r, c, rules.apply(currentState.getCell(r, c), currentState.getCellNeighbors(r, c)));
    currentState = nextState;
  }

  private void onLoop() {
    view.handleInput();
    if (!paused) advance();
    view.displayBoard(currentState);
  }

  public void applyTestPattern() {
    String initial = "glider gun";
    switch (initial) {
      case "glider":
        // Glider bearing SE
        currentState.setCell(0, 0, cellStates.findByName("dead"));
        currentState.setCell(0, 1, cellStates.findByName("alive"));
        currentState.setCell(0, 2, cellStates.findByName("dead"));
        currentState.setCell(1, 0, cellStates.findByName("dead"));
        currentState.setCell(1, 1, cellStates.findByName("dead"));
        currentState.setCell(1, 2, cellStates.findByName("alive"));
        currentState.setCell(2, 0, cellStates.findByName("alive"));
        currentState.setCell(2, 1, cellStates.findByName("alive"));
        currentState.setCell(2, 2, cellStates.findByName("alive"));
        break;
      case "log":
        // 1x3
        currentState.setCell(0, 0, cellStates.findByName("dead"));
        currentState.setCell(0, 1, cellStates.findByName("dead"));
        currentState.setCell(0, 2, cellStates.findByName("dead"));
        currentState.setCell(1, 0, cellStates.findByName("alive"));
        currentState.setCell(1, 1, cellStates.findByName("alive"));
        currentState.setCell(1, 2, cellStates.findByName("alive"));
        currentState.setCell(2, 0, cellStates.findByName("dead"));
        currentState.setCell(2, 1, cellStates.findByName("dead"));
        currentState.setCell(2, 2, cellStates.findByName("dead"));
        break;
      case "glider gun":
        int[] xs = {
          25, 23, 25, 13, 14, 21, 22, 35, 36, 12, 16, 21, 22, 35, 36, 1, 2, 11, 17, 21, 22, 1, 2,
          11, 15, 17, 18, 23, 25, 11, 17, 25, 12, 16, 13, 14
        };
        int[] ys = {
          1, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 7,
          7, 7, 8, 8, 9, 9
        };
        if (xs.length != ys.length)
          throw new RuntimeException(
              "Initial state has coords that don't pair up: array length mismatch");
        for (int i = 0; i < xs.length; i++)
          currentState.setCell(ys[i], xs[i], cellStates.findByName("alive"));
        break;
      default:
        break;
    }
  }

  public void run() {
    view.initialize();
    currentState = new BoardState(view.getHeight(), view.getWidth(), cellStates.findByName("dead"));
    applyTestPattern();
    try {
      while (!quit) {
        onLoop();
        // heck it, concurrency stuffs are not worth the effort right now
        Thread.sleep(500);
      }
    } catch (InterruptedException ie) {
      throw new RuntimeException(ie);
    }
    view.shutdown();
  }

  public void togglePause() {
    paused = !paused;
  }

  public void pause() {
    paused = true;
  }

  public void play() {
    paused = false;
  }

  public void quit() {
    quit = true;
  }
}
