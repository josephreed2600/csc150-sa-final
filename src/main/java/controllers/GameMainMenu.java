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
      .append("  2) Play Go Fish VS A Computer\n")
			.append("  3) Credits\n")
      .append("\n  0) Exit The Application\n")
      .append("\nEnter your selection [0-3]: ");
      userChoice = view.promptForInt(mainMenu.toString(), (input) -> {if(0 <= input && input <= 3) return null; return "Out of bounds; please try again [0-3]";});

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

				case 3:
					view.display("\nPeyton Trujillo, Timothy Sailer, Joe Reed\n\n");
					try {
					Thread.sleep(1000); // a lil pause
					} catch(InterruptedException ie) {} // but it's not that big a deal
					break;

        default:
          System.out.println("Not a valid choice; please try again [0-3]");
          break;
      }

    } while (userChoice != 0);
  }

}
