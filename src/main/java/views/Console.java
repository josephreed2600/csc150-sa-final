package views;

import java.io.*;

public class Console implements IView {

	private static PrintStream cout = System.out;
	private static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

	public Console() {
	}

	public void display(String s) {
		cout.print(s);
	}

	public String read() {
		return prompt("");
	}

	public String prompt(String prompt) {
		IOException ioe = null;
		do {
			try {
				display(prompt);
				return cin.readLine();
			} catch(IOException e) {
				ioe = e;
				display("IOException caught");
			}
		} while(ioe != null);
		throw new RuntimeException(this.getClass().getSimpleName()+"::"+(new Object().getClass().getEnclosingMethod().getName())+" failed successfully");
	}

	public int readInt() {
		return promptForInt("");
	}

	public int promptForInt(String prompt) {
		NumberFormatException nfe = null;
		do {
			try {
				String s = prompt(prompt);
				return Integer.parseInt(s);
			} catch(NumberFormatException e) {
				nfe = e;
				display("Not a number\n");
			}
		} while(nfe != null);
		throw new RuntimeException(this.getClass().getSimpleName()+"::"+(new Object().getClass().getEnclosingMethod().getName())+" failed successfully");
	}
}
