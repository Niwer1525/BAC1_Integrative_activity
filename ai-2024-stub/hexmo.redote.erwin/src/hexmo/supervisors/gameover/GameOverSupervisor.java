package hexmo.supervisors.gameover;

import java.util.Objects;

import hexmo.domains.IHexGameFactory;
import hexmo.domains.board.stats.HexStats;
import hexmo.supervisors.commons.ViewId;

/**
 * Réagit aux événements de haut-niveau de sa vue et lui fournit des données à afficher.
 * 
 * 
 * /!\ Vous pouvez trouver l'algorithme de calcul des chemins dans PathFinder ainsi que les autres questions (It-3-q1, It-3-q2, It-3-q3) dans le fichier PathFinder.java
 * @see hexmo.domains.board.path.PathFinder
 * @see hexmo.domains.board.path.PathFinder#findPath(hexmo.domains.player.HexColor, int)
 * */
public class GameOverSupervisor {
	private GameOverView view;
	private final IHexGameFactory gameFactory;

	/**
	 * 
	 * */
	public GameOverSupervisor(IHexGameFactory factory) {
		this.gameFactory = Objects.requireNonNull(factory, "factory is expected to be a reference to a defined factory");
	}
	
	public void setView(GameOverView view) {
		if(view == null) {
			return;
		}
		
		this.view = view;
	}
	
	/**
	 * Méthode appelée par la vue quand une navigation vers elle est en cours.
	 * 
	 * @param fromView la vue d'origine. Correspond a priori à une constante définie dans ViewNames.
	 * */
	public void onEnter(ViewId fromView) {
		HexStats stats = this.gameFactory.getCurrentGame().getStats();
		this.view.setStats(
			"RESULTATS DE LA PARTIE",
			stats.getWinner(),
			stats.getWinnerScore(),
			stats.getLoserScore(),
			stats.getBoardFillRate(),
			"",
			"STATISTIQUES TOTAL",
			stats.getPlayerTotalScore(this.gameFactory.getCurrentGame().getPlayer1()),
			stats.getPlayerTotalScore(this.gameFactory.getCurrentGame().getPlayer2()),
			stats.getAverageBoardFillRate()
		);
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite retourner au menu principal.
	 * */
	public void onGoToMain() {
		this.view.goTo(ViewId.MAIN_MENU);
	}
}
