package hexmo.domains;

public class HexGameFactory {

    private HexGame game;

    /**
     * Start a new game with the specified board size
     * @param boardSize The board size
     */
    public void startNewGame(int boardSize) {
        this.game = new HexGame(boardSize);
    }

    /**
     * @return the current playing game
     */
    public HexGame getCurrentGame() {
        return game;
    }

    @Override
    public String toString() {
        return String.format("HexGameFactory{game=%s}", game);
    }
}
