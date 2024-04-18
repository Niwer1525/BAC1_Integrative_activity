package hexmo.domains.board.tiles;

import hexmo.domains.player.HexColor;

/**
 * Represent a tile on the board
 */
public class HexTile {

    private final AxialCoordinates coords;
    private HexColor type;

    /**
     * Create a new tile with the given coordinates and type
     * @param coords The coordinates of the tile
     * @param type The type of the tile
     */
    public HexTile(AxialCoordinates coords, HexColor type) {
        this.coords = coords;
        this.type = type;
    }

    /**
     * Update the color of this tile
     * @param type The new tile type (Color)
     */
    public void setColor(HexColor type) {
        this.type = type;
    }

    /**
     * @return The current tile type (Color)
     */
    public HexColor getColor() {
        return this.type;
    }

    /**
     * @return True if the tile is avalbile to be claimed
     */
    public boolean isEmpty() {
        return this.type == HexColor.UNKNOWN;
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

    /**
     * @return The S component of the tile
     */
    public int getS() {
        return this.coords.getS();
    }

    @Override
    public String toString() {
        return String.format("Tile at [%s] with type %s", this.coords, this.type);
    }
}
