package hexmo.supervisors.playgame;

import java.util.Collection;
import java.util.Objects;

import hexmo.domains.HexGame;
import hexmo.domains.IHexGameFactory;
import hexmo.domains.board.tiles.HexTile;
import hexmo.domains.player.HexColor;
import hexmo.supervisors.commons.TileType;
import hexmo.supervisors.commons.ViewId;

/**
 * Réagit aux événements utilisateurs de sa vue en mettant à jour une partie en cours et fournit à sa vue les données à afficher.
 * 
 * 
 */
public class PlayGameSupervisor {

	private PlayGameView view;
	private final IHexGameFactory gameFactory;

	public PlayGameSupervisor(IHexGameFactory factory) {
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
	 * 
	 * IT-1-q4 :
	 * 
	 * */
	public void onEnter(ViewId fromView) {
		if (ViewId.MAIN_MENU == fromView) {
			view.clearTiles();
			view.setActiveTile(0, 0);
			this.drawBoard();
		}
	}


	/**
	 * Tente de déplacer la case active de {@code deltaRow} lignes et de {@code deltaRow} colonnes.
	 * 
	 * <p>Cette méthode doit vérifier que les coordonnées calculées correspondent bien à une case du terrain.</p>
	 * */
	public void onMove(int dx, int dy) {
		HexTile targetTile = this.gameFactory.getCurrentGame().moveTo(dx, dy);
		this.view.setActiveTile(targetTile.getCoords().asX(), targetTile.getCoords().asY());
		this.updateViewMessages();
	}

	/**
	 * Tente d'affecter la case active et met à jour l'affichage en conséquence.
	 * 
	 * <p>Ne fait rien si la case active a déjà été affectée.</p>
	 * */
	public void onSet() {
		/* Try to play */
		boolean wantSwap = this.gameFactory.getCurrentGame().isFirstTurn() && view.askQuestion(HexGame.FIRST_TURN_QUESTION);
		int playErrorCode = this.gameFactory.getCurrentGame().play(wantSwap);

		/* Handle played tile base on game error code */
		handlePlay(playErrorCode);
	}

	private void handlePlay(int playErrorCode) {
		switch (playErrorCode) {
			case HexGame.NO_PLAY_ERROR:
				this.drawBoard();
				break;
			case HexGame.ERROR_TILE_NOT_VALID:
				view.displayErrorMessage(HexGame.TILE_INCOMPATIBLE_COLOR);
				break;
			case HexGame.ERROR_TILE_CLAIMED:
				view.displayErrorMessage(HexGame.TILE_ALREADY_CLAIMED);
				break;
			default: break;
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

	private TileType asTileType(HexColor color) {
		switch (color) {
			case BLUE: return TileType.BLUE;
			case RED: return TileType.RED;
			case UNKNOWN: return TileType.UNKNOWN;
			default: return TileType.UNKNOWN;
		}
	}

	private void drawBoard() {
		this.view.clearTiles(); // Clearing tiles. Improve perfs since the PlayGameView#setTileAt is adding a new Tile on top of each other...

		/* Reset all tiles to there current color and then update the helper and display the helping tiles */
		this.updateTiles(this.gameFactory.getCurrentGame().getTiles(), null);
		this.updateTiles(this.gameFactory.getCurrentGame().updateHelper(), TileType.HIGHLIGHT);

		/* Update messages */
		this.updateViewMessages();
	}

	private void updateViewMessages() {
		this.view.setActionMessages(this.gameFactory.getCurrentGame().getGameMessages());
	}

	private void updateTiles(Collection<HexTile> tiles, TileType type) {
		for(HexTile tile : tiles)
			this.view.setTileAt(tile.getCoords().asX(), tile.getCoords().asY(), type == null ? asTileType(tile.getColor()) : type);
	}
}