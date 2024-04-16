package hexmo.supervisors.mainmenu;

import java.util.List;
import java.util.Objects;

import hexmo.domains.IHexGameFactory;
import hexmo.supervisors.commons.ViewId;

/**
 * Fournit les données qu'une vue représentant le menu principal doit afficher.
 * <p>Réagit aux événements de haut niveau signalé par sa vue.</p>
 * */
public class MainMenuSupervisor {

	public static final int EXIT_ITEM = 3;

	private MainMenuView view;
	private final IHexGameFactory gameFactory;

	public MainMenuSupervisor(IHexGameFactory factory) {
		this.gameFactory = Objects.requireNonNull(factory, "factory is expected to be a reference to a defined factory");
	}

	public void setView(MainMenuView view) {
		this.view = Objects.requireNonNull(view, "view is expected to be a reference to a defined view");
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
		gameFactory.startNewGame(3 + itemPos);		
		view.goTo(ViewId.PLAY_GAME);
	}
}
