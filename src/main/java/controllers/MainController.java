package controllers;

import views.IView;

public class MainController implements IController {
	private IView view;
	private IController handoff; // TODO change this

	public MainController(IView view) {
		this.view = view;
	}

	public void run() { 
		view.display("Running main controller");
	}

}
