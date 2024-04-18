package hexmo.supervisors.gameover;

import hexmo.supervisors.commons.ViewId;


import org.helmo.supervisors.views.View;

/**
 * Décrit comment mettre à jour l'écran représentant la fin de partie.
 * */
public interface GameOverView extends View<ViewId> {

	/**
	 * Affiche les stats dans l'ordre des arguments reçus.
	 * */
	void setStats(String... formattedStats);
}
