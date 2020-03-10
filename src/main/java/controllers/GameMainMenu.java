package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import controllers.IController;
import views.*;

import gameoflife.controllers.*;
import gameoflife.views.*;
import gofish.controllers.*;

// TODO refactor to use IView
public class GameMainMenu implements IController {
	private IView view;

	public GameMainMenu(IView view) {
		this.view = view;
	}

	private String introText;
	// do we need this?
	private String encryptionString = "";

	public void run(){
		introText = "Welcome To Conway's Game Of Life/Go Fish!";
		mainMenu();
	}
	public int mainMenu() {
		int userChoice =-2;

		do {
			StringBuilder mainMenu = new StringBuilder(introText);

			mainMenu.append("Choose A Option Below: \n");
			mainMenu.append("1)Play Conway's Game Of Life\n");
			mainMenu.append("2)Play Go Fish VS A Computer\n");
			mainMenu.append("0)Exit The Application\n\n");
			mainMenu.append("Enter You Choice: ");
			userChoice = promptForInt(mainMenu.toString(), 0, 2);

			switch (userChoice) {
				case 0:
					System.out.println("Goodbye!");
					break;
				case 1:
					//Game of Life goes here
					new GameOfLife(new LanternaGoLView()).run();
					break;

				case 2:
					//Go Fish goes here
					new GoFish(new Console()).run();
					break;

				default:
					System.out.println("Not A Valid Choice");
					break;

			}

		} while (userChoice != 0);

		return userChoice;
	}

	public String promptForString(String prompt) {
		//declare buffered reader
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		//declare and initialize to null string input
		String input = null;
		//Declare and initialize to true boolean isInvalid
		boolean isInvalid = true;

		do {
			System.out.println(prompt);
			//try
			try {
				input = bf.readLine();
				//set isInvalid to try if input is null, empty or just white space
				isInvalid = input == null || input.trim().isEmpty();
				//if isInvalid is true
				if (isInvalid) {
					//inform the user their input was invalid
					System.out.println("Your input cannot be null, empty, or just white space, pls try again");
				}
			} catch (IOException ioe) {
				System.out.println("Tech issue pls try again");
			}

		} while (isInvalid);
		encryptionString = input;
		return input;
	}

	public int promptForInt(String prompt, int min, int max) {
		int userNum = 0;

		//declare and initialize to true boolean isInvalid
		boolean isInvalid = true;
		//declare String input and initialize to promptForString(prompt)
		do {
			String input = promptForString(prompt);

			try {
				userNum = Integer.parseInt(input);
				isInvalid = userNum < min || userNum > max;
			} catch (NumberFormatException nfe) {
				System.out.println("enter a valid number");
			}
			if(isInvalid==true){
				System.out.println("Please enter a valid number");
			}

		}while(isInvalid);
		return userNum;
	}
}
