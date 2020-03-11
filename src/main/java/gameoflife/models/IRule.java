package gameoflife.models;

public interface IRule {
  public CellState apply(CellState cell, CellState[] neighbors);
}
