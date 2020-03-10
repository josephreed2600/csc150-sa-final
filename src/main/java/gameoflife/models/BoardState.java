package gameoflife.models;

import java.util.function.Function;

import gameoflife.models.CellState;

public class BoardState {
	private CellState[][] state;
	public final int rows, cols;

	public BoardState(int rows, int cols) {
		state = new CellState[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public BoardState(int rows, int cols, CellState init) {
		this(rows, cols);
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				state[row][col] = init;
			}
		}
	}

	public CellState[][] getState() { return state; }

	public CellState getCell(int row, int col) { return state[row][col]; }

	public void setCell(int row, int col, CellState state) { this.state[row][col] = state; }

	/**
	 * Starts above the given cell and works clockwise around.
	 */
	public CellState[] getCellNeighbors(int row, int col) {
		CellState[] out = new CellState[8];
		Function<Integer, Boolean> rowExists = (row_) -> {return row_>0 && row_<state.length;};
		Function<Integer, Boolean> colExists = (col_) -> {return col_>0 && col_<state[0].length;};

		// just spell it out, it's simpler than loops
		if(rowExists.apply(row-1) && colExists.apply(col  )) out[0] = getCell(row-1, col  );
		if(rowExists.apply(row-1) && colExists.apply(col+1)) out[1] = getCell(row-1, col+1);
		if(rowExists.apply(row  ) && colExists.apply(col+1)) out[2] = getCell(row  , col+1);
		if(rowExists.apply(row+1) && colExists.apply(col+1)) out[3] = getCell(row+1, col+1);
		if(rowExists.apply(row+1) && colExists.apply(col  )) out[4] = getCell(row+1, col  );
		if(rowExists.apply(row+1) && colExists.apply(col-1)) out[5] = getCell(row+1, col-1);
		if(rowExists.apply(row  ) && colExists.apply(col-1)) out[6] = getCell(row  , col-1);
		if(rowExists.apply(row-1) && colExists.apply(col-1)) out[7] = getCell(row-1, col-1);

		return out;
	}
}
