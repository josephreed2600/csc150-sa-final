package gameoflife.models;

import java.util.function.BiFunction;

import gameoflife.models.IRule;

public class Rule implements IRule {
	private final String description;
	private final BiFunction<CellState, CellState[], CellState> rule;

	public Rule(String description, BiFunction<CellState, CellState[], CellState> rule) {
		this.description = description;
		this.rule = rule;
	}

	@Override
	public CellState apply(CellState cell, CellState[] neighbors) {
		return rule.apply(new CellState(cell), neighbors);
	}

	@Override
	public String toString() {
		return description;
	}
}
