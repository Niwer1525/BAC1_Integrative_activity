package hexmo.supervisors.mainmenu;

import java.util.List;
import java.util.Objects;

/**
 * Fournit les données qu'une vue représentant le menu principal doit afficher.
 * <p>Réagit aux événements de haut niveau signalé par sa vue.</p>
 * */
public class MainMenuSupervisor {

	public static final int EXIT_ITEM = 3;

	private MainMenuView view;

	public MainMenuSupervisor() {
	}

	public void setView(MainMenuView view) {
		this.view = Objects.requireNonNull(view, "view is expected to be a reference to a defined view");
		//TODO : définir les items de la vue.
		this.view.setItems(List.of());
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur sélectionne un item.
	 * 
	 * @param itemPos la position de l'item sélectionné. {@code item in [0; items.length[}
	 * */
	public void onItemSelected(int itemPos) {
		if(EXIT_ITEM == itemPos) {
			view.confirmExit();
		} 
		
		//TODO : Démarrer une nouvelle partie
		//TODO : naviguer vers l'écran de jeu
	}

}
