package hexmo.domains.board;

import java.util.List;

import com.tngtech.archunit.thirdparty.com.google.common.collect.Lists;

import hexmo.domains.board.tiles.AxialCoordinates;
import hexmo.domains.board.tiles.HexTile;
import hexmo.supervisors.commons.TileType;

/**
 * Represent a game board, containing all the tiles
 */
public class HexBoard {
    private final List<HexTile> tiles;
    private HexTile activeTile = null;

    /**
     * Create a new board with the given size
     * @param boardSize The size of the board
     */
    public HexBoard(int boardSize) {
        this.tiles = Lists.newArrayList();

        /* Adjusted loop limits for creating a hexagon */
        for (int q = -boardSize; q <= boardSize; q++) {
            for (int r = Math.max(-boardSize, -q - boardSize); r <= Math.min(boardSize, -q + boardSize); r++) {
                AxialCoordinates axialCoords = new AxialCoordinates(q, r);
                HexTile tile = new HexTile(axialCoords, TileType.UNKNOWN);
                this.tiles.add(tile);
                if(q == 0 && r == 0) this.activeTile = tile; // Set the active tile to the default one (0, 0)
            }
        }
    }

    /**
     * This is called on player's movement(s), it will move to a the case in the <code>[dx, dy]</code> direction.
     * @param dx The delta X (In the display coords system)
     * @param dy The delta Y (In the display coords system)
     * @return The target tile, Or null if outside the board dimensions
     */
    public HexTile moveTo(int dx, int dy) {
        int dq = (int)(dx / (Math.sqrt(3) / 2.0));
        int dr = (int)(dy / (3.0 / 2.0)) + dy;

        for(HexTile tile : this.tiles) {
            if(tile.getQ() == this.activeTile.getQ() + dq && tile.getR() == this.activeTile.getR() + dr) {
                this.activeTile = tile;
                return tile;
            }
        }

        return null;
    }

    /**
     * @return All the tiles in this board
     */
    public List<HexTile> getTiles() {
        return tiles;
    }

    /**
     * @return The active tile (Focused by the user)
     */
    public HexTile getActiveTile() {
        return this.activeTile;
    }
}
