package hexmo.domains;

public class HexGameFactory implements IHexGameFactory {

    private HexGame game;

    public void startNewGame(int boardSize) {
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
