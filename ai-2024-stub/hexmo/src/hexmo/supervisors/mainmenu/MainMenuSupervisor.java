package hexmo.supervisors.mainmenu;

import java.util.List;
import java.util.Objects;

import hexmo.domains.HexGameFactory;
import hexmo.supervisors.commons.ViewId;

/**
 * Fournit les données qu'une vue représentant le menu principal doit afficher.
 * <p>Réagit aux événements de haut niveau signalé par sa vue.</p>
 * */
public class MainMenuSupervisor {

	public static final int EXIT_ITEM = 3;

	private MainMenuView view;
	private final HexGameFactory gameFactory;

	public MainMenuSupervisor(HexGameFactory factory) {
		this.gameFactory = Objects.requireNonNull(factory, "factory is expected to be a reference to a defined factory");
	}

	public void setView(MainMenuView view) {
		this.view = Objects.requireNonNull(view, "view is expected to be a reference to a defined view");
		//TODO : définir les items de la vue.
		this.view.setItems(List.of(
			"Nouvelle partie (r=3)",
			"Nouvelle partie (r=4)",
			"Nouvelle partie (r=5)",
			"Quitter"
		));
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur sélectionne un item.
	 * 
	 * @param itemPos la position de l'item sélectionné. {@code item in [0; items.length[}
	 * */
	public void onItemSelected(int itemPos) {
		if(EXIT_ITEM == itemPos) view.confirmExit();

		/* Create the game and swithc view */
		//TODO : Démarrer une nouvelle partie
		gameFactory.startNewGame(3 + itemPos);
		
		//TODO : naviguer vers l'écran de jeu
		view.goTo(ViewId.PLAY_GAME);
	}
}
