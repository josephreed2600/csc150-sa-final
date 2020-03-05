package gameoflife.models;

import java.util.ArrayList;

import gameoflife.models.CellState;

public class CellStateList extends ArrayList<CellState> {
	public CellStateList() {}

	public CellState findByName(String name) {
		for(CellState cs : this) if(cs.name.equals(name)) return cs;
		return null;
	}
}
