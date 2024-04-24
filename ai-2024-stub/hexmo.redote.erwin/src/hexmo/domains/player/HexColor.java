package hexmo.domains.player;

/**
 * Represent the two colors of the game
 * @see HexPlayer
 */
public enum HexColor {
    RED("rouge"), BLUE("bleu"), UNKNOWN("libre");

    private final String displayName;

    /**
     * Create a new HexColor with the given display name
     * @param displayName The display name of the color
     */
    HexColor(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return The opposite color of the current one
     */
    public HexColor getOpposite() {
        return this == RED ? BLUE : RED;
    }

    /**
     * @return The display name of the color
     */
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
