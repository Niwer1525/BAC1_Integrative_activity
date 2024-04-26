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

    public static final String FIRST_TURN_QUESTION = "Voulez vous \"swapper\" ?";
    public static final String TILE_ALREADY_CLAIMED = "Case déjà occupée";
    public static final String TILE_INCOMPATIBLE_COLOR = "La case est incompatible avec votre couleur";

    public static final int NO_PLAY_ERROR = 0;
    public static final int ERROR_TILE_NOT_VALID = 1;
    public static final int ERROR_TILE_CLAIMED = 2;

    private final HexBoard board;
    private final HexPlayer player1;
    private final HexPlayer player2;
    private final int boardSize;

    private HexPlayer turnPlayer;
    private boolean firstTurn = true;

    /**
     * Create a new game with a board of size <code>boardSize</code>
     * @param boardSize The size of the board
     * @throws IllegalArgumentException if boardSize is negative
     */
    public HexGame(int boardSize) {
        if(boardSize < 0) throw new IllegalArgumentException("Board size must be positive");
        this.board = new HexBoard(boardSize);
        this.boardSize = boardSize;
        
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
            Character.toUpperCase(tileType.getDisplayName().charAt(0)) + tileType.getDisplayName().substring(1) // Capitalize the first letter
        );

        return messages;
    }

    private void onFirstTurn(HexTile tile, boolean wantSwap) {
        this.firstTurn = false; // Set the first turn to false
        if(wantSwap) {
            /* Switch players colors */
            player1.swapColorWith(player2);

            /* Switch turn */
            this.switchTurn();
        }
    }

    /**
     * Play the current active tile. This can affect the <code>playErrorCode</code>.
     * @param wantSwap If the user want to swap
     * @return The error code
     * @see HexGame#NO_PLAY_ERROR
     * @see HexGame#ERROR_TILE_NOT_VALID
     * @see HexGame#ERROR_TILE_CLAIMED
     */
    public int play(boolean wantSwap) { //TODO
        if(!this.canBeClaimed(this.board.getActiveTile())) return ERROR_TILE_NOT_VALID;
        if(this.board.isActiveTileClaimed(firstTurn)) return ERROR_TILE_CLAIMED;

        HexTile targetTile = this.board.getActiveTile();
		targetTile.setColor(this.turnPlayer.getColor());
        this.switchTurn();
        
        /* If it's the first turn */
        if(this.firstTurn)
            this.onFirstTurn(targetTile, wantSwap);

        return NO_PLAY_ERROR;
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
    
    private void switchTurn() {
        this.turnPlayer = this.turnPlayer.equals(this.player1) ? this.player2 : this.player1;
    }

    @Override
    public String toString() {
        return String.format("Game: %s vs %s", this.player1, this.player2);
    }

    private boolean canBeClaimed(HexTile tile) {
        return tile.isNotOnBorders(boardSize) || tile.contains(this.turnPlayer.getColor() == HexColor.RED ? boardSize : -boardSize);
    }
    
    /**
     * Update the helper tiles
     * @return The helper tiles for the current turn player
     */
    public Collection<HexTile> updateHelper() {
        return this.board.updateHelper(this.turnPlayer.getColor());
    }
}