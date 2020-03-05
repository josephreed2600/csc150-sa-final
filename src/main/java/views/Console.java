package views;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.function.Function;

public class Console implements IView {

	private static PrintStream cout = System.out;
	private static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

	public Console() {
	}

	public void display(String s) {
		cout.print(s);
	}

	public String promptForString(String prompt) {
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

	public int promptForInt(String prompt) {
		NumberFormatException nfe = null;
		do {
			try {
				String s = promptForString(prompt);
				return Integer.parseInt(s);
			} catch(NumberFormatException e) {
				nfe = e;
				display("Not a number\n");
			}
		} while(nfe != null);
		throw new RuntimeException(this.getClass().getSimpleName()+"::"+(new Object().getClass().getEnclosingMethod().getName())+" failed successfully");
	}

	public boolean promptForBoolean(String prompt) {
		RuntimeException re = null;
		do {
			try {
				String s = promptForString(prompt);
				switch(s.toLowerCase()) {
					case "t":
					case "true":
					case "y":
					case "yes":
					case "1":
						return true;
					case "f":
					case "false":
					case "n":
					case "no":
					case "0":
						return false;
					default:
						throw new RuntimeException("Unknown value");
				}
			} catch(RuntimeException e) {
				re = e;
				display("Not a boolean value\n");
			}
		} while(re != null);
		throw new RuntimeException(this.getClass().getSimpleName()+"::"+(new Object().getClass().getEnclosingMethod().getName())+" failed successfully");
	}

	public ArrayList<Integer> promptForInts(String prompt) {
		Pattern regex = Pattern.compile("(?:.*([0-9]+).*)");
		String s = promptForString(prompt);
		Matcher m = regex.matcher(s);
		/*
		if(m.find()) {
		}
		*/
		//return new ArrayList<Integer>();
		throw new UnsupportedOperationException("Unimplemented");
	}

	public String promptForString(String prompt, Function<String, String> validator) {
		String input, error;
		do {
			input = promptForString(prompt);
			error = validator.apply(input);
			if(error != null) display(error + '\n');
		} while(error != null);
		return input;
	}

	public int promptForInt(String prompt, Function<Integer, String> validator) {
		Integer input;
		String error;
		do {
			input = promptForInt(prompt);
			error = validator.apply(input);
			if(error != null) display(error + '\n');
		} while(error != null);
		return input;
	}

	public ArrayList<Integer> promptForInts(String prompt, Function<ArrayList<Integer>, String> validator) {
		ArrayList<Integer> input;
		String error;
		do {
			input = promptForInts(prompt);
			error = validator.apply(input);
			if(error != null) display(error + '\n');
		} while(error != null);
		return input;
	}
}
