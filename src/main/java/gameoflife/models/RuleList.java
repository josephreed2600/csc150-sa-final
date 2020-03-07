package gameoflife.models;

import java.util.function.BiFunction;
import java.util.ArrayList;

public class RuleList extends ArrayList<IRule> implements IRule {
	@Override
	public CellState apply(CellState cell, CellState[] neighbors) {
		CellState workingCell = new CellState(cell);
		for(IRule rule : this) {
			workingCell = rule.apply(cell, neighbors);
		}
		return workingCell;
	}
}
