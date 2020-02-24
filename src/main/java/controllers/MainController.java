package controllers;

import ml.jtreed.util.Log;
import ml.jtreed.util.Menu;
import views.IView;

public class MainController implements IController {
	private IView view;
	private IController handoff; // TODO change this
	private final Menu mainMenu;

	public MainController(IView view) {
		this.view = view;

		mainMenu = new Menu(
			"",
			new String[]{
				"exit"
			},
			"\n>   ",
			new Runnable[]{
			},
			true
		);
	}

	public void run() { mainMenu.enter(view::read, view::display); }

}
/* vim: set ts=2 :*/
