package hexmo.supervisors.playgame;

import java.util.Collection;
import java.util.Objects;

import hexmo.domains.HexGame;
import hexmo.domains.IHexGameFactory;
import hexmo.domains.TestHexGame;
import hexmo.domains.board.tiles.AxialCoordinates;
import hexmo.domains.board.tiles.HexTile;
import hexmo.domains.player.HexColor;
import hexmo.supervisors.commons.TileType;
import hexmo.supervisors.commons.ViewId;

/**
 * Réagit aux événements utilisateurs de sa vue en mettant à jour une partie en cours et fournit à sa vue les données à afficher.
 * 
 * @see onEnter : IT-1-q4
 * @see drawBoard : IT-2-q4
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
	 * IT-1-q4 : Il semble que draw board soit O(n) si l'on prends également encompte la réinitialisation des cases de la vue alors on peut dire que la fonction onEnter() est O(n^2)
	 * 
	 * */
	public void onEnter(ViewId fromView) {
		if (ViewId.MAIN_MENU == fromView) {
			view.clearTiles(); // O(n) ou n correspond au nombre de cases que contient la collection de la vue
			view.setActiveTile(0, 0);
			TestHexGame.move(this.gameFactory.getCurrentGame(), 0, -3).setColor(HexColor.RED);
			TestHexGame.move(this.gameFactory.getCurrentGame(), 0, -1).setColor(HexColor.RED);
			TestHexGame.move(this.gameFactory.getCurrentGame(), 1, -1).setColor(HexColor.RED);
			TestHexGame.move(this.gameFactory.getCurrentGame(), 2, -1).setColor(HexColor.RED);
			// TestHexGame.move(this.gameFactory.getCurrentGame(), 3, -3).setColor(HexColor.RED);
			this.drawBoard(true); // O(n) ou n correspond au nombre de cases que contient la collection produite par getTiles()
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
				this.drawBoard(false);
				break;
			case HexGame.ERROR_TILE_NOT_VALID:
				view.displayErrorMessage(HexGame.TILE_INCOMPATIBLE_COLOR);
				break;
			case HexGame.ERROR_TILE_CLAIMED:
				view.displayErrorMessage(HexGame.TILE_ALREADY_CLAIMED);
				break;
			case HexGame.END_GAME:
				view.goTo(ViewId.END_GAME);
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

	/**
	 * It-2-q4 : La méthode "updateHelper()" est celle qui gère le calcul des cases à mettre en surbrillance.
	 * updateHelper est O(n) ou n correspond au nombre de cases qui seront retournées par la récursion de cette fonction.
	 * @see HexGame#updateHelper()
	 */
	private void drawBoard(boolean entry) { // O(n) ou n correspond au nombre de cases que contient la collection produite par getTiles() si entry est en false (Soit lors de l'entrée dans le jeu)
		if(!entry) 
			this.view.clearTiles(); // Clearing tiles. Improve perfs since the PlayGameView#setTileAt is adding a new Tile on top of each other... 

		/* Reset all tiles to there current color and then update the helper and display the helping tiles */
		this.updateTiles(this.gameFactory.getCurrentGame().getTiles(), null); // O(n) ou n correspond au nombre de cases que contient la collection produite par getTiles()
		// if(!entry) // Useful to prevent from loading and calculating the helper tiles when entering the game
		// 	this.updateTiles(this.gameFactory.getCurrentGame().updateHelper(), TileType.HIGHLIGHT);

		for(AxialCoordinates tile : this.gameFactory.getCurrentGame().cccp())
			this.view.setTileAt(tile.asX(), tile.asY(), TileType.HIGHLIGHT);
		
		/* Update messages */
		this.updateViewMessages();
	}

	private void updateViewMessages() {
		this.view.setActionMessages(this.gameFactory.getCurrentGame().getGameMessages());
	}

	private void updateTiles(Collection<HexTile> tiles, TileType type) { // O(n) ou n correspond au nombre de cases que contient la collection "tiles"
		for(HexTile tile : tiles) // O(n) ou n correspond au nombre de cases que contient la collection "tiles"
			this.view.setTileAt(tile.getCoords().asX(), tile.getCoords().asY(), type == null ? asTileType(tile.getColor()) : type); // O(1) ou O(n) car bien qu'il s'aggise d'une arrayList, la list pourrait être redimensionée 
	}
}