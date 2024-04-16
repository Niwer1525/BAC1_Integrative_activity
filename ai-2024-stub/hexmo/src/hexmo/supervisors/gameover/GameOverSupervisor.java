package hexmo.supervisors.gameover;

import hexmo.domains.HexGameFactory;
import hexmo.supervisors.commons.ViewId;

/**
 * Réagit aux événements de haut-niveau de sa vue et lui fournit des données à afficher.
 * */
public class GameOverSupervisor {
	private GameOverView view;
	// private final HexGameFactory gameFactory;

	/**
	 * 
	 * */
	public GameOverSupervisor(HexGameFactory factory) {
		// this.gameFactory = Objects.requireNonNull(factory, "factory is expected to be a reference to a defined factory");
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
		//TODO : générer les résultats et les afficher.
		this.view.setStats("GAME OVER: true","Very trye");
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite retourner au menu principal.
	 * */
	public void onGoToMain() {
		//TODO : retourner au menu principal
		this.view.goTo(ViewId.MAIN_MENU);
	}
}
