package hexmo.supervisors.playgame;

import hexmo.supervisors.commons.*;

import java.util.Objects;

/**
 * Réagit aux événements utilisateurs de sa vue en mettant à jour une partie en cours et fournit à sa vue les données à afficher.
 * */
public class PlayGameSupervisor {

	private PlayGameView view;


	public PlayGameSupervisor() {
	}

	/**
	 * Définit la vue de ce superviseur à {@code view}.
	 * */
	public void setView(PlayGameView view) {
		this.view = Objects.requireNonNull(view);
	}

	/**
	 * Méthode appelée juste avant que la vue de ce superviseur soit affichée à l'écran.
	 * <p>Le superviseur affiche les données de départ du jeu (cout de la case active, nombre de trésors, bourse du joueur, etc.).
	 * Il dessine également les cases et indique quelle case est active.</p>
	 * */
	public void onEnter(ViewId fromView) {
		if (ViewId.MAIN_MENU == fromView) {
			//TODO : faire le rendu initial de l'écran de jeu
			view.clearTiles();
			view.setTileAt(0, 0, TileType.RED);
			view.setActiveTile(0, 0);
	
			//TODO : afficher le joueur qui a la main
			//TODO : afficher les coordonnées axiale courante
			//TODO : afficher les coordonnées axiales de la tuile active
			view.setActionMessages("Changez-moi");
		}
	}


	/**
	 * Tente de déplacer la case active de {@code deltaRow} lignes et de {@code deltaRow} colonnes.
	 * 
	 * <p>Cette méthode doit vérifier que les coordonnées calculées correspondent bien à une case du terrain.</p>
	 * */
	public void onMove(int dx, int dy) {
		//TODO : valider et changer de case active. Appelez les méthodes adéquates de la vue.
	}

	/**
	 * Tente d'affecter la case active et met à jour l'affichage en conséquence.
	 * 
	 * <p>Ne fait rien si la case active a déjà été affectée.</p>
	 * */
	public void onSet() {
		//TODO : affecter si possible		
	}


	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite interrompre la partie.
	 * 
	 * <p>Ce superviseur demande à sa vue de naviguer vers le menu principal.</p>
	 * */
	public void onStop() {
		//TODO : naviguer vers le menu principal
		view.goTo(ViewId.MAIN_MENU);
	}

}
