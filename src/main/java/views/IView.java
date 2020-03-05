package views;

import java.util.ArrayList;
import java.util.function.Function;

public interface IView {
	public void display(String s);

	public String promptForString(String prompt);
	public int promptForInt(String prompt);
	public boolean promptForBoolean(String prompt);
	public ArrayList<Integer> promptForInts(String prompt);

	public String promptForString(String prompt, Function<String, String> validator);
	public int promptForInt(String prompt, Function<Integer, String> validator);
	public ArrayList<Integer> promptForInts(String prompt, Function<ArrayList<Integer>, String> validator);
}
