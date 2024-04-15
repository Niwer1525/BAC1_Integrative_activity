package hexmo.domains;

import hexmo.domains.board.HexBoard;
import hexmo.domains.board.tiles.HexTile;
import hexmo.domains.player.HexColor;
import hexmo.domains.player.HexPlayer;
import hexmo.supervisors.commons.TileType;

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
     */
    public String[] getGameMessages() {
        String[] messages = new String[3];
        messages[0] = String.format("%s (%s) vs %s (%s)",
            this.player1.getName(), 
            this.player2.getName(), 
            this.player1.getColor().getDisplayName(), 
            this.player2.getColor().getDisplayName()
        );
        messages[1] = String.format("Au tour de %s (%s)", this.turnPlayer.getName(), this.turnPlayer.getColor().getDisplayName());
        
        String status = "Libre";
        switch(this.board.getActiveTile().getTileType()) {
            case BLUE:
                status = "Bleu";
                break;
            case RED:
                status = "Rouge";
                break;
            case UNKNOWN: break;
            default: break;
        }
        messages[2] = String.format("Case active (%d %d %d) %s", 0, 0, 0, status);

        return messages;
    }

    /**
     * Play the current tile, It will change the color if the tile is <code>unknown</code> (i.e. the tile is not played yet)
     * The new color will be RED or BLUE depending on the current player
     * @return The tile that has been played or null if the tile is already played
     */
    public HexTile play() {
        HexTile currentTile = this.board.getActiveTile();
        if(currentTile !=null && currentTile.getTileType() == TileType.UNKNOWN) {
            currentTile.setTileType(this.turnPlayer.getColorAsTileType());
            this.switchTurn();
            return currentTile;
        }
        return null;
    }

    /**
     * This will check if the current tile is set and if it's the first turn of the game. If so
     * it will set the tile type to the opposite of the player 1 color
     * @param tile The tile to check
     * @param tileType The tile type to set if it's not the first turn
     * @param playerWantChange If the player want to change the tile type
     * @return The new tile type if the player want to change the tile type, null otherwise
     */
    public TileType onFirstTurn(HexTile tile, TileType tileType, boolean playerWantChange) {
        if(tileType == null) throw new IllegalArgumentException("tile cannot be null");
        if(!this.firstTurn) return tileType;

        this.firstTurn = false; // Set the first turn to false
        if(playerWantChange) {
            if(tileType != TileType.UNKNOWN) {
                final TileType OPPOSITE_TILE_TYPE = this.getTurnPlayer().getColorAsTileType();

                /* Switch players colors */
                HexColor temp = this.player1.getColor();
                this.player1.setColor(this.player2.getColor());
                this.player2.setColor(temp);
                
                /* Set the tile and return for visual use */
                tile.setTileType(OPPOSITE_TILE_TYPE);
                return OPPOSITE_TILE_TYPE;
            }
        }
        return tileType;
    }

    /**
     * @return true if it's the first turn of the game
     */
    public boolean isFirstTurn() {
        return this.firstTurn;
    }

    /**
     * @return The board of the game
     */
    public HexBoard getBoard() {
        return this.board;
    }

    private void switchTurn() {
        this.turnPlayer = this.turnPlayer == this.player1 ? this.player2 : this.player1;
    }
}