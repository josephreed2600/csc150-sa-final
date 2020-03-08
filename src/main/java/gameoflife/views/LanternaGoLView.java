package gameoflife.views;

import gameoflife.models.BoardState;
import gameoflife.models.CellStateList;
import gameoflife.models.RuleList;

import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.gui2.*;

public class LanternaGoLView implements IGoLView{
	
	private Terminal term;
	private Screen screen;
	private WindowBasedTextGUI tui;

	public void displayBoard(BoardState boardState) {
		// FIXME
		System.out.println("Call made to LanternaGoLView::displayBoard");
	}

	public void modifyBoard(BoardState boardState) {
	}

	public void displayCellStates(CellStateList cellStates) {
	}

	public void modifyCellStates(CellStateList cellStates) {
	}

	public void displayRules(RuleList rules) {
	}

	public void modifyRules(RuleList rules) {
	}

}
