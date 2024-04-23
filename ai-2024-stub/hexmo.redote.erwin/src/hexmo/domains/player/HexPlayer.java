package hexmo.domains.player;

/**
 * Represent a player in the game
 */
public class HexPlayer {

    private final String name;
    private HexColor color;

    /**
     * Create a new player with the given name and color
     * @param name The name of the player
     * @param color The color of the player
     * @throws IllegalArgumentException if name is null or empty
     * @throws IllegalArgumentException if color is null
     */
    public HexPlayer(String name, HexColor color) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name must be provided");
        if(color == null) throw new IllegalArgumentException("Color must be provided");
        this.name = name;
        this.color = color;
    }

    /**
     * @return The display name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @return The color of the player
     */
    public HexColor getColor() {
        return this.color;
    }
    
    /**
     * Set the color of the player
     * @param color The new color of the player
     */
    public void setColor(HexColor color) {
        if(color == null) return;
        this.color = color;
    }

    /**
     * Swap the color of this player with the given player
     * @param player2 The player to swap color with
     */
    public void swapColorWith(HexPlayer player2) {
        HexColor tmp = this.color;
        this.color = player2.color;
        player2.color = tmp;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, color.getDisplayName());
    }
}
