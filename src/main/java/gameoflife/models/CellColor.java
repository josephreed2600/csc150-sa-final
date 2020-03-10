package gameoflife.models;

public class CellColor {
	public final String name, hex, ansi;
	public CellColor(String name, String hex, String ansi) {
		this.name = name;
		this.hex = hex;
		this.ansi = ansi;
	}
}
