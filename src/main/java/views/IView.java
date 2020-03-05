package views;

public interface IView {
	public abstract void display(String s);

	public abstract String read();

	public abstract String prompt(String prompt);

	public abstract int readInt();

	public abstract int promptForInt(String prompt);
}
