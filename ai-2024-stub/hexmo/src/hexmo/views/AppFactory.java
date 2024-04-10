package hexmo.views;

import hexmo.supervisors.gameover.GameOverSupervisor;
import hexmo.supervisors.mainmenu.MainMenuSupervisor;
import hexmo.supervisors.playgame.PlayGameSupervisor;

/**
 * Méthode de fabrique abstraite retournant une main window et ses vues.
 * */
public interface AppFactory {
	/**
	 * Crée une nouvelle instance de {@code MainWindow} dont les vues seront pilotées par les superviseurs.
	 * */
	<T extends MainWindow> T ofSupervisors(String mainWindowTitle, 
			MainMenuSupervisor mainMenu, 
			PlayGameSupervisor playGame, 
			GameOverSupervisor gameOver);
}
