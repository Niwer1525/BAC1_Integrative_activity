package hexmo.domains.board.tiles;

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
        return String.format("q: %d r: %d", q, r);
    }

    @Override
    public int hashCode() {
        return q * 31 + r;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || (obj instanceof AxialCoordinates coords && coords.q == q && coords.r == r);
    }

    public static AxialCoordinates fromXYCoords(int x, int y) {
        int q = (int)(x / (Math.sqrt(3) / 2.0));
        int r = (int)(y / (3.0 / 2.0)) + y;
        return new AxialCoordinates(q, r);
    }
}
