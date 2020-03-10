package gameoflife.models;

import java.util.function.BiFunction;

public interface IRule {
	public CellState apply(CellState cell, CellState[] neighbors);
}
