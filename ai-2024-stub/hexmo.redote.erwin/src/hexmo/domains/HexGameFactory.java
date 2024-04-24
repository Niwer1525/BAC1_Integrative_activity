package hexmo.domains;

public class HexGameFactory implements IHexGameFactory {

    private HexGame game;

    /**
     * @throws IllegalArgumentException boardSize in negative
     */
    @Override
    public void startNewGame(int boardSize) {
        if (boardSize < 0) throw new IllegalArgumentException("Board size must be positive");
        this.game = new HexGame(boardSize);
    }

    @Override
    public HexGame getCurrentGame() {
        return game;
    }

    @Override
    public String toString() {
        return String.format("HexGameFactory{game=%s}", game);
    }
}
