package hexmo.domains.player;

import java.util.Random;

/**
 * Represent the two colors of the game
 * @see HexPlayer
 */
public enum HexColor {
    RED("rouge"), BLUE("bleu");

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
        if(this == RED) return BLUE;
        return RED;
    }

    /**
     * @return Choose one of the two colors
     * @deprecated
     */
    public static HexColor getRandomColor() {
        Random rand = new Random();
        HexColor[] colors = HexColor.values();
        return colors[rand.nextInt(0, colors.length)];
    }

    public String getDisplayName() {
        return displayName;
    }
}
