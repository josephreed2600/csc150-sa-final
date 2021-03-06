package gameoflife.views;

import gameoflife.controllers.GameOfLife;
import gameoflife.models.BoardState;
import gameoflife.models.CellStateList;
import gameoflife.models.RuleList;

public interface IGoLView {
  public void initialize();

  public void shutdown();

  public void setGame(GameOfLife gol);

  public int getWidth();

  public int getHeight();

  public void handleInput();

  public void displayBoard(BoardState boardState);

  public void modifyBoard(BoardState boardState);

  public void displayCellStates(CellStateList cellStates);

  public void modifyCellStates(CellStateList cellStates);

  public void displayRules(RuleList rules);

  public void modifyRules(RuleList rules);
}
