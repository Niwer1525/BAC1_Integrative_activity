package hexmo.domains;

import java.util.Collection;

import hexmo.domains.board.HexBoard;
import hexmo.domains.board.tiles.HexTile;
import hexmo.domains.player.HexColor;
import hexmo.domains.player.HexPlayer;

/**
 * Represent a game, contains the two players and the board.
 */
public class HexGame {

    public static final String FIRST_TURN_QUESTION = "Voulez vous changer la couleur de la case ?";
    private final HexBoard board;
    private final HexPlayer player1;
    private final HexPlayer player2;

    private HexPlayer turnPlayer;
    private boolean firstTurn = true;

    /**
     * Create a new game with a board of size <code>boardSize</code>
     * @param boardSize The size of the board
     */
    public HexGame(int boardSize) {
        this.board = new HexBoard(boardSize);
        
        // For testing purposes, the first player is the red one and the second player is the blue one
        this.player1 = new HexPlayer("P1", HexColor.RED);
        this.player2 = new HexPlayer("P2", HexColor.BLUE);

        this.turnPlayer = this.player1; // Set the first player to play
    }

    /**
     * @return The player that has the turn
     */
    public HexPlayer getTurnPlayer() {
        return this.turnPlayer;
    }

    /**
     * Create the game messages according to the current game state
     * @return The game messages
        //TODO : afficher les coordonnées axiale courante
        //TODO : afficher les coordonnées axiales de la tuile active
     */
    public String[] getGameMessages() {
        String[] messages = new String[3];
        HexColor tileType = this.board.getActiveTile().getColor();

        /* Write messages */
        messages[0] = String.format("%s (%s) vs %s (%s)",
            this.player1.getName(),
            this.player1.getColor().getDisplayName(),
            this.player2.getName(),
            this.player2.getColor().getDisplayName()
        );
        messages[1] = String.format("Au tour de %s (%s)", this.turnPlayer.getName(), this.turnPlayer.getColor().getDisplayName());
        messages[2] = String.format("Case active (q: %d r: %d %d) %s", 
            this.board.getActiveTile().getQ(),
            this.board.getActiveTile().getR(),
            this.board.getActiveTile().getS(),
            (tileType == HexColor.RED ? "Rouge" : (tileType == HexColor.BLUE ? "Bleu" : "Libre")) //TODO use display name of HexColor
        );

        return messages;
    }

    private HexTile onPlayTile() {
        HexTile currentTile = this.board.getActiveTile();
        if(currentTile == null) return null;
        if(currentTile.isEmpty()) {
            currentTile.setColor(this.turnPlayer.getColor());

            /* Next turn */
            this.switchTurn();
        }
        return currentTile;
    }

    private HexColor onFirstTurn(HexTile tile, HexColor color, boolean wantSwap) {
        if(!this.firstTurn || tile == null) return color == null ? HexColor.UNKNOWN : color;

        this.firstTurn = false; // Set the first turn to false
        if(wantSwap && !tile.isEmpty()) {
            /* Save the color of the player for the tile type */
            final HexColor PREVIOUS_COLOR = this.getTurnPlayer().getColor();

            /* Switch players colors */
            swapPlayers();
            
            /* Set the tile and return for visual use */
            tile.setColor(PREVIOUS_COLOR);
            return PREVIOUS_COLOR;
        }
        return color;
    }

    /**
     * Play the current active tile
     * @param wantSwap If the user want to swap
     * @return The played tile
     */
    public HexTile play(boolean wantSwap) {
        HexColor color = this.turnPlayer.getColor();
		HexTile targetTile = this.onPlayTile();
		if(targetTile == null) return null;

        /* If it's the first turn */
        if(this.firstTurn)
            color = this.onFirstTurn(targetTile, color, wantSwap);

        /* Update the tile */
        if(targetTile.isEmpty())
            targetTile.setColor(color);

        return targetTile;
    }

    /**
     * @return true if it's the first turn of the game
     */
    public boolean isFirstTurn() {
        return this.firstTurn;
    }

    /**
     * Move the active tile to the given coordinates
     * @param dx The x coordinate
     * @param dy The y coordinate
     * @return The target tile
     */
    public HexTile moveTo(int dx, int dy) {
        return this.board.moveTo(dx, dy);
    }

    /**
     * @return Get all tiles of the game
     */
    public Collection<HexTile> getTiles() {
        return this.board.getTiles();
    }

    /**
     * @return The active tile
     */
    public HexTile getActiveTile() {
        return this.board.getActiveTile();
    }

    private void switchTurn() {
        this.turnPlayer = this.turnPlayer == this.player1 ? this.player2 : this.player1;
    }

    private void swapPlayers() {
        HexColor temp = this.player1.getColor();
        this.player1.setColor(this.player2.getColor());
        this.player2.setColor(temp);
    }

    @Override
    public String toString() {
        return String.format("Game: %s vs %s", this.player1, this.player2);
    }
}