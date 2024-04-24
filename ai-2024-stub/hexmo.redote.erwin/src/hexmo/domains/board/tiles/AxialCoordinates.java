package hexmo.domains.board.tiles;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Represent the coordinates of a tile in the axial system
 */
public class AxialCoordinates {

    private final int q;
    private final int r;
    private final int s;

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
        return super.equals(obj) || (obj instanceof AxialCoordinates coords && coords.q == q && coords.r == r);
    }

    /**
     * Return the coordinates of the tile after adding the given coordinates
     * @param coords The coordinates to add
     * @return The new coordinates
     */
    public AxialCoordinates add(AxialCoordinates coords) {
        return new AxialCoordinates(this.getQ() + coords.getQ(), this.getR() + coords.getR());
    }
    
    //TODO
    public Set<AxialCoordinates> getNeighbors() { // Sets ? Ensemblistes ?
        return List.of(
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
        return null;
    }

    /**
     * @return The diagonals of this tile
     */
    public Set<AxialCoordinates> getDiagonals() {
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
}
