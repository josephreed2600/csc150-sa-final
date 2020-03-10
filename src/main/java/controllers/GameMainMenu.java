package controllers;

import gameoflife.controllers.*;
import gameoflife.views.*;
import gofish.controllers.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import views.*;

// TODO refactor to use IView
public class GameMainMenu implements IController {
  private IView view;

  public GameMainMenu(IView view) {
    this.view = view;
  }

  public void run() {
    int userChoice = -2;

    do {
      StringBuilder mainMenu = new StringBuilder('\n')
			.append("Welcome To Conway's Game of Life & Go Fish!\n")
      .append("Choose an option: \n")
      .append("  1) Play Conway's Game Of Life\n")
      .append("  2) Play Go Fish VS A Computer\n\n")
      .append("  0) Exit The Application\n\n")
      .append("Enter your selection [0-2]: ");
      userChoice = view.promptForInt(mainMenu.toString(), (input) -> {if(0 <= input && input <= 2) return null; return "Out of bounds; please try again [0-2]";});

      switch (userChoice) {
        case 0:
          System.out.println("\nGoodbye!");
          break;

        case 1:
          // Game of Life goes here
          new GameOfLife(new LanternaGoLView()).run();
          break;

        case 2:
          // Go Fish goes here
          new GoFish(new Console()).run();
          break;

        default:
          System.out.println("Not a valid choice; please try again [0-2]");
          break;
      }

    } while (userChoice != 0);
  }

}
