package gameoflife.models;

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
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        state[row][col] = init;
      }
    }
  }

  public CellState[][] getState() {
    return state;
  }

  public CellState getCell(int row, int col) {
    if (rowExists(row) && colExists(col)) return state[row][col];
    return null;
  }

  public void setCell(int row, int col, CellState state) {
    this.state[row][col] = state;
  }

  public boolean rowExists(int row) {
    return row >= 0 && row < rows;
  };

  public boolean colExists(int col) {
    return col >= 0 && col < cols;
  };

  /** Starts above the given cell and works clockwise around. */
  public CellState[] getCellNeighbors(int row, int col) {
    CellState[] out = new CellState[8];

    // just spell it out, it's simpler than loops
    if (rowExists(row - 1) && colExists(col)) out[0] = getCell(row - 1, col);
    if (rowExists(row - 1) && colExists(col + 1)) out[1] = getCell(row - 1, col + 1);
    if (rowExists(row) && colExists(col + 1)) out[2] = getCell(row, col + 1);
    if (rowExists(row + 1) && colExists(col + 1)) out[3] = getCell(row + 1, col + 1);
    if (rowExists(row + 1) && colExists(col)) out[4] = getCell(row + 1, col);
    if (rowExists(row + 1) && colExists(col - 1)) out[5] = getCell(row + 1, col - 1);
    if (rowExists(row) && colExists(col - 1)) out[6] = getCell(row, col - 1);
    if (rowExists(row - 1) && colExists(col - 1)) out[7] = getCell(row - 1, col - 1);

    return out;
  }
}
