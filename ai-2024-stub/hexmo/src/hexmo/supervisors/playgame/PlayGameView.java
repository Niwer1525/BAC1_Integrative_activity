package hexmo.supervisors.playgame;

import hexmo.supervisors.commons.TileType;
import hexmo.supervisors.commons.ViewId;
import org.helmo.supervisors.views.View;

/**
 * Décrit comment mettre à jour l'écran représentant une partie en cours.
 * */
public interface PlayGameView extends View<ViewId> {
	
	/**
	 * Supprime les tuiles existantes.
	 * <p>Appelez cette méthode avant de charger une carte pour éviter une superposition de tuiles à l'écran.</p>
	 * */
	void clearTiles();

	/**
	 * Définit une tuile de type {@code TileType} au coordonées (x,y).
	 * */
	void setTileAt(float x, float y, TileType tileType);

	/**
	 * Définit la tuile sélectionnée
	 * */
	void setActiveTile(float x, float y);

	/**
	 * Définit les messages à afficher en réaction à la dernière action
	 * */
	void setActionMessages(String... messages);

	/**
	 * Affiche un message d'erreur sous forme de boite de dialogue.
	 * */
	void displayErrorMessage(String message);

	/**
	 * Pose une question à l'utilisateur.
	 * <p>L'utilisateur y répond par Oui ou Non.</p>
	 * */
	boolean askQuestion(String question);
}
