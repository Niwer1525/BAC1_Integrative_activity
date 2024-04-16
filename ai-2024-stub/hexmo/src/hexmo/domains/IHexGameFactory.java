package hexmo.domains;

public interface IHexGameFactory {

    /**
     * Start a new game with the specified board size
     * @param boardSize The board size
     */
    void startNewGame(int boardSize);

    /**
     * @return the current playing game
     */
    HexGame getCurrentGame();
}
