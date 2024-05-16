package hexmo.domains.board.tiles;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.tngtech.archunit.thirdparty.com.google.common.collect.Sets;

import hexmo.domains.player.HexColor;

/**
 * Represent the coordinates of a tile in the axial system
 */
public class AxialCoordinates {

    private final int q;
    private final int r;
    private final int s;
    private EnumBorder border;

    /**
     * Create a copy of the given coordinates
     * @param original The coordinates to copy
     */
    public AxialCoordinates(AxialCoordinates original) {
        this(original.q, original.r);
    }

    /**
     * Create a new AxialCoordinates with the given <code>Q</code> and <code>R</code>
     * @param q
     * @param r
     */
    public AxialCoordinates(int q, int r) {
        this.q = q;
        this.r = r;
        this.s = -q - r;
    }

    /**
     * Convert the <code>Q</code> to the display coords system
     * @return The converted value for <code>Q</code> inside the display coords system (As the <code>X value</code>)
     */
    public float asX() {
        return (float)(Math.sqrt(3) * q + Math.sqrt(3) / 2.0 * r);
    }

    /**
     * Convert the <code>R</code> to the display coords system
     * @return The converted value for <code>R</code> inside the display coords system (As the <code>Y value</code>)
     */
    public float asY() {
        return (float)(0.0 + 3.0 / 2.0 * r);
    }

    /**
     * @return The <code>Q</code> value
     */
    public int getQ() {
        return q;
    }

    /**
     * @return The <code>R</code> value
     */
    public int getR() {
        return r;
    }

    /*
     * @return The <code>S</code> value
     */
    public int getS() {
        return s;
    }

    @Override
    public String toString() {
        return String.format("q: %d r: %d s: %d", q, r, s);
    }

    @Override
    public int hashCode() {
        return q * 31 + r;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || (obj instanceof AxialCoordinates coords && coords.q == q && coords.r == r && coords.s == s);
    }

    /**
     * Return the coordinates of the tile after adding the given coordinates
     * @param coords The coordinates to add
     * @return The new coordinates
     */
    public AxialCoordinates add(AxialCoordinates coords) {
        return new AxialCoordinates(this.getQ() + coords.getQ(), this.getR() + coords.getR());
    }
    
    /**
     * @return The neighbors of this tile
     */
    public Set<AxialCoordinates> getNeighbors() {
        return Sets.newHashSet(
            new AxialCoordinates(q + 1, r),
            new AxialCoordinates(q - 1, r),
            new AxialCoordinates(q, r + 1),
            new AxialCoordinates(q, r - 1),
            new AxialCoordinates(q + 1, r - 1),
            new AxialCoordinates(q - 1, r + 1)
        );
    }

    /**
     * @return The common neighbors between this <code>AxialCoordinate</code> and the second <code>AxialCoordinate</code> 
     */
    public Collection<AxialCoordinates> getCommonNeighborsWith(AxialCoordinates coords) {
        Set<AxialCoordinates> commonNeighbors = this.getNeighbors();
        commonNeighbors.retainAll(coords.getNeighbors());
        return commonNeighbors;
    }
    
    /**
     * @return The diagonals of this tile
     */
    public List<AxialCoordinates> getDiagonals() {
        return List.of(
            new AxialCoordinates(q + 1, r - 2),
            new AxialCoordinates(q + 2, r - 1),
            new AxialCoordinates(q + 1, r + 1),
            new AxialCoordinates(q - 1, r + 2),
            new AxialCoordinates(q - 2, r + 1),
            new AxialCoordinates(q - 1, r - 1)
        );
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
     * Convert the display coords to the axial coords
     * @param x The X value
     * @param y The Y value
     * @return The converted coordinates
     */
    public static AxialCoordinates fromXYCoords(int x, int y) {
        int q = (int)(x / (Math.sqrt(3) / 2.0));
        int r = (int)(y / (3.0 / 2.0)) + y;
        return new AxialCoordinates(q, r);
    }

    /**
     * Set the border from where this coords are from
     * @param border The border value
     */
    public void setBorder(EnumBorder border) {
        if(this.border == null) this.border = border;
    }

    /**
     * Get the border of the tile
     * @return The border of the tile
     */
    public EnumBorder getBorder() {
        return this.border;
    }

    /**
     * Get the border of the given coords
     * @param coords The coordinates
     * @param boardSize The size of the board
     * @param color The color of the player
     * @return The border on which the coords are
     */
    public static EnumBorder getBorderForCoords(AxialCoordinates coords, int boardSize, HexColor color) {
        /* Bottom & variants */
        if(color == HexColor.BLUE && coords.q == -boardSize) return EnumBorder.BOTTOM_LEFT;
        if(color == HexColor.RED && coords.r == boardSize) return EnumBorder.BOTTOM;
        if(color == HexColor.BLUE && coords.s == -boardSize) return EnumBorder.BOTTOM_RIGHT;

        /* Top & variants */
        if(color == HexColor.RED && coords.q == boardSize) return EnumBorder.TOP_RIGHT;
        if(color == HexColor.BLUE && coords.r == -boardSize) return EnumBorder.TOP;
        if(color == HexColor.RED && coords.s == boardSize) return EnumBorder.TOP_LEFT;
        return EnumBorder.TOP; // Default value (Should never be reached)
    }

    /**
     * Get the distance between two coordinates
     * @param coords The first coordinates
     * @param coords2 The second coordinates
     * @return The distance between the two coordinates
     */
    public static int getDistance(AxialCoordinates a, AxialCoordinates b) {
        return (Math.abs(a.q - b.q) + Math.abs(a.r - b.r) + Math.abs(a.s - b.s)) / 2;
    }
}
