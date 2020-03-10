package gameoflife.models;

import java.util.function.BiFunction;
import java.util.ArrayList;

public class RuleList extends ArrayList<IRule> implements IRule {
	@Override
	public CellState apply(CellState cell, CellState[] neighbors) {
		CellState workingCell = cell;
		for(IRule rule : this) {
			workingCell = rule.apply(workingCell, neighbors);
		}
		return workingCell;
	}

	/**
	 * Returns a string containing the stringified forms of the contained
	 * rules, separated by the ASCII unit separator (31), terminated by
	 * the ASCII record separator (30).
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		String sep = "";
		String unitSep = Character.toString((char)31);
		String recordSep = Character.toString((char)30);
		for(IRule rule : this) {
			out.append(sep).append(rule);
			sep = unitSep;
		}
		out.append(recordSep);
		return out.toString();
	}
}
