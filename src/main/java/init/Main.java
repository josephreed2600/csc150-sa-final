package init;

import controllers.GameMainMenu;
import views.Console;

public class Main {
    public static void main(String[] args) {
			new GameMainMenu(new Console()).run();
    }
}
