package hexmo.domains.board.tiles;

import hexmo.domains.player.HexColor;

/**
 * Represent a tile on the board
 */
public class HexTile {

    private final AxialCoordinates coords;
    private HexColor color;

    /**
     * Create a new tile with the given coordinates and type
     * @param coords The coordinates of the tile
     * @param color The color of the tile
     * @throws IllegalArgumentException if coords is null
     * @throws IllegalArgumentException if color is null
     */
    public HexTile(AxialCoordinates coords, HexColor color) {
        if(coords == null) throw new IllegalArgumentException("Coordinates must be provided");
        if(color == null) throw new IllegalArgumentException("Color must be provided");
        this.coords = coords;
        this.color = color;
    }

    /**
     * Update the color of this tile
     * @param type The new tile type (Color)
     */
    public void setColor(HexColor type) {
        this.color = type;
    }

    /**
     * @return The current tile type (Color)
     */
    public HexColor getColor() {
        return this.color;
    }

    /**
     * @return True if the tile is avalbile to be claimed
     */
    public boolean isEmpty() {
        return this.color == HexColor.UNKNOWN;
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

    /**
     * Check if at least one of the components is equals to the value
     * @param x The value to check
     * @return True if at least one of the components is equals to the value
     */
    public boolean contains(int x) {
        return this.getQ() == x || this.getR() == x || this.getS() == x;
    }

    /**
     * Check if the tile is not on the borders of the board
     * @param boardSize The size of the board
     * @return True if the tile is not on the borders of the board
     * @throws IllegalArgumentException if boardSize is negative
     */
    public boolean isNotOnBorders(int boardSize) {
        if(boardSize < 0) throw new IllegalArgumentException("Size must be positive");
        boolean a = this.getQ() != boardSize && this.getQ() != -boardSize;
        boolean b = this.getR() != boardSize && this.getR() != -boardSize;
        boolean c = this.getS() != boardSize && this.getS() != -boardSize;
        
        return a && b && c;
    }

    /**
     * Return the coordinates of the tile after adding the given coordinates
     * @param coords The coordinates to add
     * @return The new coordinates
     */
    public AxialCoordinates add(AxialCoordinates coords) {
        return new AxialCoordinates(this.getQ() + coords.getQ(), this.getR() + coords.getR());
    }

    @Override
    public String toString() {
        return String.format("Tile at [%s] with type %s", this.coords, this.color);
    }
}
