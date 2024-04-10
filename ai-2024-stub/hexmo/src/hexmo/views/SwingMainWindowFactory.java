package hexmo.views;

import hexmo.supervisors.gameover.GameOverSupervisor;
import hexmo.supervisors.mainmenu.MainMenuSupervisor;
import hexmo.supervisors.playgame.PlayGameSupervisor;

public class SwingMainWindowFactory implements AppFactory {

	@SuppressWarnings("unchecked")
	@Override
	public SwingMainWindow ofSupervisors(String mainWindowTitle, MainMenuSupervisor mainMenu, PlayGameSupervisor playGame,
			GameOverSupervisor gameOver) {
		return new SwingMainWindow(
				mainWindowTitle,                
				new SwingMainMenuView(mainMenu),
                new SwingPlayGameView(playGame),
                new SwingGameOverView(gameOver));
	}

}
