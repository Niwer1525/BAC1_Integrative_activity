package hexmo.domains.board.tiles;

import hexmo.supervisors.commons.TileType;

/**
 * Represent a tile on the board
 */
public class HexTile {

    private final AxialCoordinates coords;
    private TileType type;

    /**
     * Create a new tile with the given coordinates and type
     * @param coords The coordinates of the tile
     * @param type The type of the tile
     */
    public HexTile(AxialCoordinates coords, TileType type) {
        this.coords = coords;
        this.type = type;
    }

    /**
     * Update the color of this tile
     * @param type The new tile type (Color)
     */
    public void setTileType(TileType type) {
        this.type = type;
    }

    /**
     * @return The current tile type (Color)
     */
    public TileType getTileType() {
        return this.type;
    }

    /**
     * @return True if the tile is avalbile to be claimed
     */
    public boolean isEmpty() {
        return this.type == TileType.UNKNOWN;
    }

    /**
     * @return The coordinates of the tile
     */
    public AxialCoordinates getCoords() {
        return this.coords;
    }

    /**
     * @return The coordinates of the tile
     */
    public int getQ() {
        return this.coords.getQ();
    }

    /**
     * @return The coordinates of the tile
     */
    public int getR() {
        return this.coords.getR();
    }

    @Override
    public String toString() {
        return String.format("Tile at [%s] with type %s", this.coords, this.type);
    }
}
