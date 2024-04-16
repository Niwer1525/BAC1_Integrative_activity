package hexmo.supervisors.playgame;

import java.util.Objects;

import hexmo.domains.HexGame;
import hexmo.domains.HexGameFactory;
import hexmo.domains.board.tiles.HexTile;
import hexmo.supervisors.commons.TileType;
import hexmo.supervisors.commons.ViewId;

/**
 * Réagit aux événements utilisateurs de sa vue en mettant à jour une partie en cours et fournit à sa vue les données à afficher.
 * 
 * IT-1-q4 :
 * 
 */
public class PlayGameSupervisor {

	private PlayGameView view;
	private final HexGameFactory gameFactory;

	public PlayGameSupervisor(HexGameFactory factory) {
		this.gameFactory = Objects.requireNonNull(factory, "Factory is expected to be a reference to a defined factory");
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
			view.clearTiles();
			view.setTileAt(0, 0, TileType.UNKNOWN);
			view.setActiveTile(0, 0);
			for(HexTile tile : this.gameFactory.getCurrentGame().getBoard().getTiles()) {
				view.setTileAt(tile.getCoords().asX(), tile.getCoords().asY(), tile.getTileType());
			}
			this.updateViewMessages();
		}
	}


	/**
	 * Tente de déplacer la case active de {@code deltaRow} lignes et de {@code deltaRow} colonnes.
	 * 
	 * <p>Cette méthode doit vérifier que les coordonnées calculées correspondent bien à une case du terrain.</p>
	 * */
	public void onMove(int dx, int dy) {
		HexTile targetTile = this.gameFactory.getCurrentGame().getBoard().moveTo(dx, dy);
		if(targetTile != null) {
			this.view.setActiveTile(targetTile.getCoords().asX(), targetTile.getCoords().asY());
			this.updateViewMessages();
		}
	}

	/**
	 * Tente d'affecter la case active et met à jour l'affichage en conséquence.
	 * 
	 * <p>Ne fait rien si la case active a déjà été affectée.</p>
	 * */
	public void onSet() {
		TileType tileType = this.gameFactory.getCurrentGame().getTurnPlayer().getColorAsTileType();
		HexTile targetTile = this.gameFactory.getCurrentGame().play();
		if(targetTile != null) {
			/* If it's the first turn */
			if(this.gameFactory.getCurrentGame().isFirstTurn())
				tileType = this.gameFactory.getCurrentGame().onFirstTurn(targetTile, tileType, view.askQuestion(HexGame.FIRST_TURN_QUESTION));
			
			/* Update the tile */
			this.view.setTileAt(targetTile.getCoords().asX(), targetTile.getCoords().asY(), tileType);
			this.updateViewMessages();
		}
	}


	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite interrompre la partie.
	 * 
	 * <p>Ce superviseur demande à sa vue de naviguer vers le menu principal.</p>
	 * */
	public void onStop() {
		view.goTo(ViewId.MAIN_MENU);
	}

	private void updateViewMessages() {
		this.view.setActionMessages(this.gameFactory.getCurrentGame().getGameMessages());
	}
}
