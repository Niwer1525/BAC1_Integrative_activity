package hexmo.supervisors.mainmenu;

import hexmo.supervisors.commons.ViewId;
import org.helmo.supervisors.views.View;

import java.util.List;

/**
 * Décrit le comment définir les données à afficher du menu principal.
 * */
public interface MainMenuView extends View<ViewId> {

	/**
	 * Définit les items à afficher.
	 * <p>Quand cette méthode est appelée, l'implémentation met le focus sur le premier item. Ne fait rien si {@code items} est {@code null} ou {@code vide}.</p>
	 * */
	void setItems(List<String> items);
}
