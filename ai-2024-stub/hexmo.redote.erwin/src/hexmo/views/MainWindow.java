package hexmo.views;

import hexmo.supervisors.commons.ViewId;


/**
 * Définit les méthodes qu'une fenêtre principale peut recevoir.
 * */
public interface MainWindow {

	/**
	 * Méthode appelée par une implémentation Swing d'un écran pour changer d'écran affiché.
	 * */
	void goTo(ViewId id);

	/**
	 * Affiche le premier écran.
	 * */
	void start(ViewId id);

}