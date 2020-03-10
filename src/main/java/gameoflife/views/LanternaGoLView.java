package gameoflife.views;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.*;
import gameoflife.controllers.GameOfLife;
import gameoflife.models.BoardState;
import gameoflife.models.CellStateList;
import gameoflife.models.RuleList;
import java.io.*;

public class LanternaGoLView implements IGoLView {

  private Terminal term;
  private Screen screen;
  private WindowBasedTextGUI tui;
  private GameOfLife gol;

  public LanternaGoLView() {
    try {
      term = new DefaultTerminalFactory().createTerminal();
      screen = new TerminalScreen(term);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public void setGame(GameOfLife gol) {
    this.gol = gol;
  }

  public void handleInput() {
    try {
      KeyStroke input = term.pollInput();
      if (input != null)
        switch (input.getKeyType()) {
          case Character:
            switch (input.getCharacter()) {
              case 'q':
                gol.quit();
                break;
              case ' ':
                gol.togglePause();
            }
            break;
        }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public void initialize() {
    try {
      term.enterPrivateMode();
      term.setCursorVisible(false);
    } catch (IllegalStateException ise) {
    } // we don't care, just means it's already in private mode
    catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public void shutdown() {
    try {
      term.exitPrivateMode();
      term.close();
    } catch (IllegalStateException ise) {
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public int getWidth() {
    try {
      return term.getTerminalSize().getColumns();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public int getHeight() {
    try {
      return term.getTerminalSize().getRows();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public void displayBoard(BoardState boardState) /*throws IOException*/ {
    try {
      for (int row = 0; row < boardState.rows; row++) {
        for (int col = 0; col < boardState.cols; col++) {
          term.setCursorPosition(col, row);
          TextColor color = TextColor.Factory.fromString(boardState.getCell(row, col).color.hex);
          term.setBackgroundColor(color);
          term.putCharacter(' ');
        }
      }
      term.flush();
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  public void modifyBoard(BoardState boardState) /*throws IOException*/ {}

  public void displayCellStates(CellStateList cellStates) /*throws IOException*/ {}

  public void modifyCellStates(CellStateList cellStates) /*throws IOException*/ {}

  public void displayRules(RuleList rules) /*throws IOException*/ {}

  public void modifyRules(RuleList rules) /*throws IOException*/ {}
}
